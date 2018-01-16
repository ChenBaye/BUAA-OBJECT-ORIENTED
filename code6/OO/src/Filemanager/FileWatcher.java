package Filemanager;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;

public class FileWatcher extends Thread{
	AFile FileMessage;
	Summary summary;
	Detail detail;
	int WatcherNum;
	int MissionNum;
	int No;
	ReadWriteLock lock;
	LinkedList<AFile> FileList=new LinkedList<AFile>();//空AFile链表
	int ListNum=0;
	AFile FileMessage_temp;
	LinkedList<AFile> LostList=new LinkedList<AFile>();
	LinkedList<AFile> NewList=new LinkedList<AFile>();
	public FileWatcher(File file,int WatcherNum,int MissionNum,int No,Summary summary,Detail detail,ReadWriteLock lock){				//构造方法
		FileMessage=new AFile(file);
		this.WatcherNum=WatcherNum;
		this.MissionNum=MissionNum;
		this.No=No;
		this.summary=summary;
    	this.detail=detail;
    	this.lock=lock;
    	
    	if(file.isFile()){				//监控文件
    		for(int i=0;i<file.getParentFile().listFiles().length;i++){
    			AFile afile=new AFile(file.getParentFile().listFiles()[i]);
    			FileList.add(afile);
    		}
    	}
    	else{							//监控目录
    		ReadDir(FileList,file);
    	}
    	
    	
    	ListNum=FileList.size();
    	System.out.println("list of file:");
    	for(int i=0;i<ListNum;i++){
			System.out.print(i+":"+FileList.get(i).filename+" ");
		}
		System.out.println("");
		System.out.println("fileName:"+this.FileMessage.filename);
		System.out.println("parent:"+this.FileMessage.parent);
		System.out.println("path:"+this.FileMessage.path);
		System.out.println("ModTIme:"+this.FileMessage.ModTime+"ms");
		System.out.println("size:"+this.FileMessage.size+"B");
		System.out.println("WatcherNum:"+this.WatcherNum);
		System.out.println("MissionNum:"+this.MissionNum);
		System.out.println("No:"+No);
	}
	
  
	
	
	public void run(){
		try{
		do{
			lock.readLock().lock();
			
			FileMessage_temp=FileMessage;
			int i=0;
			switch(WatcherNum){
			case 1:i=RenamedCheck();break;
			case 2:i=ModCheck();break;
			case 3:i=PathCheck();break;
			case 4:i=SizeCheck();break;
				default:break;
			}
			if(i==-1){									//文件消失
				System.out.println("No such file! "+FileMessage.filename);
				break;
			}
			else{
				if(MissionNum==3){							//recover
					Recover(LostList,NewList);
					FileMessage_temp=FileMessage;
				}
			}
			
			
			FileMessage=new AFile(FileMessage_temp.file);//更新
			File file=FileMessage.file;
			FileList=new LinkedList<AFile>();
			if(file.isFile()){				//监控文件
	    		for(int i1=0;i1<file.getParentFile().listFiles().length;i1++){
	    			AFile afile=new AFile(file.getParentFile().listFiles()[i1]);
	    			FileList.add(afile);
	    		}
	    	}
	    	else{							//监控目录
	    		ReadDir(FileList,file);
	    	}
			
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("No: "+No+" is ending");
			
			lock.readLock().unlock();
		}while(true);
		
		}catch (Exception e) {
			System.out.println("invalid input");
		}
	}
	
	
	
	
	
	
	
	public int RenamedCheck(){
		LostList=new LinkedList<AFile>();
		NewList=new LinkedList<AFile>();
		if(FileMessage.isfile==true){						//监控文件
			if(FileMessage.file.exists()==true){			//未被重命名
				return 0;
			}	
			else{
				File [] temp=FileMessage.parent.listFiles();
				if(temp==null){
					System.out.println("point1");
					return -1;
					
				}
				for(int i=0;i<temp.length;i++){
					boolean bool=false;
					for(int j=0;j<FileList.size();j++){
						if(temp[i].getName().equals(FileList.get(j).filename)){
							bool=true;						//是原来文件
						}
					}
					if(bool==false && 
					   temp[i].lastModified()==FileMessage.ModTime && 
					   temp[i].length()==FileMessage.size && 
				       !(temp[i].getName().equals(FileMessage.filename))
				       ){
						AFile file1=new AFile(temp[i]);
						LostList.add(FileMessage);
						NewList.add(file1);			
						if(MissionNum==1){
							summary.add(1);
						}
						if(MissionNum==2){
							detail.add(FileMessage,file1, 1,null,0);
						}
						FileMessage_temp=file1;
						return 1;
					}
				}
				return -1;
			}
		}
		else{										//监控目录
			for(int i=0;i<FileList.size();i++){
				if(FileList.get(i).isfile && !(FileList.get(i).file.exists())){
					LostList.add(FileList.get(i));	//可能被重命名的文件
				}
			}
			
			if(LostList.size()==0){				//目录中没有文件被重命名
				return 0;
			}
			else{
				for(int j=0;j<LostList.size();j++){
					File [] temp=LostList.get(j).parent.listFiles();//被重命名文件同层文件
					if(temp==null){
						LostList.remove(j);
						j--;
					}
					else{
						boolean bool1=false;
						for(int i=0;i<temp.length;i++){
							boolean bool=false;
							for(int j1=0;j1<FileList.size();j1++){
								if(temp[i].getName().equals(FileList.get(j1).filename)){
									bool=true;						//是原来文件
								}
							}
							if(bool==false && 						//是重命名后文件
							   temp[i].lastModified()==LostList.get(j).ModTime && 
							   temp[i].length()==LostList.get(j).size && 
						       !(temp[i].getName().equals(LostList.get(j).filename))
						       ){
								AFile file1=new AFile(temp[i]);
								NewList.add(file1);
								if(MissionNum==1){
									summary.add(1);
								}
								if(MissionNum==2){
									detail.add(LostList.get(j),file1, 1,null,0);
								}
								bool1=true;
								break;
							}
						}
						
						
						if(bool1==false){
							LostList.remove(j);
							j--;
						}
						
					}
				}
			}
		}
		return 0;	
	}
	
	
	
	
    public int PathCheck(){
    	LostList=new LinkedList<AFile>();
		NewList=new LinkedList<AFile>();
		if(FileMessage.isfile==true){						//监控文件
			if(FileMessage.file.exists()==true){			//未改变路径
				return 0;
			}	
			else{
				LinkedList<AFile> list=new LinkedList<AFile>();
				if(FileMessage.parent==null){
					return -1;
				}
				ReadDir(list,FileMessage.parent);
				for(int i=0;i<list.size();i++){
					boolean bool=false;
					for(int j=0;j<FileList.size();j++){
						if(list.get(i).filename.equals(FileList.get(j).filename)
								&& list.get(i).path.equals(FileList.get(j).path)){//不是新增文件
							bool=true;
							break;
						}
					}
			
					if(bool==false && 
					   list.get(i).filename.equals(FileMessage.filename) &&
					   list.get(i).size==FileMessage.size && 
					   list.get(i).ModTime==FileMessage.ModTime &&
					   (!list.get(i).path.equals(FileMessage.path))){		//找到移动后文件
						AFile file1=list.get(i);
						LostList.add(FileMessage);
						NewList.add(file1);
						System.out.println("1"+LostList.size());
						if(MissionNum==1){
							summary.add(3);
						}
						if(MissionNum==2){
							detail.add(FileMessage,file1, 3,null,0);
						}
						FileMessage_temp=file1;
						return 1;
					   }
				}
				return -1;
			}
		}
		else{										//监控目录
			for(int i=0;i<FileList.size();i++){
				if(FileList.get(i).isfile && !(FileList.get(i).file.exists())){
					LostList.add(FileList.get(i));	//可能被移动路径的文件
				}
			}
			if(LostList.size()==0){				//目录中没有文件移动路径
				return 0;
			}
			else{
				LinkedList <AFile> list=new LinkedList<AFile>();//重新遍历
				ReadDir(list,FileMessage.file);
				for(int k=0;k<LostList.size();k++){
					boolean bool1=false;
					for(int i=0;i<list.size();i++){
						boolean bool=false;
						for(int j=0;j<FileList.size();j++){
							if(list.get(i).filename.equals(FileList.get(j).filename) && list.get(i).path.equals(FileList.get(j).path)){
								bool=true;
								break;
							}
						}
					
						if(bool==false && 
						   list.get(i).filename.equals(LostList.get(k).filename) &&
						   list.get(i).ModTime==LostList.get(k).ModTime &&
						   list.get(i).size==LostList.get(k).size &&
						   !(list.get(i).path.equals(LostList.get(k).path))){
							if(MissionNum==1){
								summary.add(3);
							}
							if(MissionNum==2){
								detail.add(LostList.get(k),list.get(i), 3,null,0);
							}
							NewList.add(list.get(i));
							System.out.println("here:"+LostList.size());
							bool1=true;
							break;
						}
					}
					if(bool1==false){
						LostList.remove(k);
						k--;
					}
					
				}
				
			}	
			
		}
		return 0;	
	}
    
    
    
    
    
	
	public int ModCheck(){
		if(!FileMessage.file.exists()){
			return -1;						//文件不见了
		}
		else{
			if(FileMessage.file.isFile()){	//监控文件
				if(FileMessage.file.lastModified()!=FileMessage.ModTime){
					AFile file1=new AFile(FileMessage.file); 
					System.out.println(FileMessage.filename+"Modified");
					if(MissionNum==2){
						detail.add(FileMessage,file1,2,null,0);
					}
					if(MissionNum==1){
						summary.add(WatcherNum);
					}
					return 1;				//发生变化
				}
				else{
					return 0;				//未变化
				}
			}
			else{							//监控目录
				LinkedList<AFile> List=new LinkedList<AFile>();
				ReadDir(List,FileMessage.file);//再次扫描
				boolean bool=false;
				for(int i=0;i<FileList.size();i++){
					for(int j=0;j<List.size();j++){
						if(FileList.get(i).filename.equals(List.get(j).filename) && 
						   FileList.get(i).ModTime!=List.get(j).ModTime){
							bool=true;
							System.out.println("Watcher "+No+" "+List.get(j).filename+" Modified");
							if(MissionNum==1){
								summary.add(WatcherNum);
							}
							if(MissionNum==2){
								detail.add(FileList.get(i),List.get(j),2,null,0);
							}
						}
					}
				}
				if(bool==true){
					return 1;
				}
				else{
					return 0;
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	public int SizeCheck(){
		if(!FileMessage.file.exists()){
			if(MissionNum==1){
				summary.add(No);
			}
			if(MissionNum==2){
				detail.add(FileMessage,FileMessage,4,FileMessage.filename+":"+Long.toString(FileMessage.size)+"->0(not exist now)",1);
			}
			return -1;						//文件不见了
		}
		else{
			if(FileMessage.file.isFile()){  //监控文件
				long temp=FileMessage.file.length();
				AFile file1=new AFile(FileMessage.file);
				if(FileMessage.size!=temp){//大小变化
					System.out.println(FileMessage.filename+" size-changed");
					if(MissionNum==1){
						summary.add(WatcherNum);
					}
					if(MissionNum==2){
						detail.add(FileMessage,file1,4,null,0);
					}
					
					return 1;
				}
				return 0;
			}
			else{												//监控目录
				
				
				for(int i=0;i<FileList.size();i++){
					if(!FileList.get(i).file.exists()){			//目录中文件不见
						System.out.println(FileList.get(i).filename+" size-changed");
						if(MissionNum==2){
							detail.add(FileMessage,FileMessage,4,FileList.get(i).filename+":"+Long.toString(FileList.get(i).size)+"->0(not exist now)",1);
						}
						if(MissionNum==1){
							summary.add(WatcherNum);
						}
					}
					else{
						long temp=GetSize(FileList.get(i).file);//原文件还在
						AFile file1=new AFile(FileList.get(i).file);
						if(FileList.get(i).size!=temp){			//目录中文件size-changed
							System.out.println(FileList.get(i).filename+" size-changed");
							if(MissionNum==2){
								detail.add(FileList.get(i),file1,4,null,0);
							}
							if(MissionNum==1){
								summary.add(WatcherNum);
							}
							
						}
					}
				}
				
				LinkedList<AFile> list= new LinkedList<AFile>();
				ReadDir(list,FileMessage.file);
				for(int i=0;i<list.size();i++){					//有无新建文件		
					boolean bool=false;
					for(int j=0;j<FileList.size();j++){
						if(list.get(i).filename.equals(FileList.get(j).filename) && 
						   list.get(i).path.equals(FileList.get(j).path)){
							bool=true;							//不是新建文件
							break;
						}
					}
					if(bool==false){
						if(MissionNum==1){
							summary.add(WatcherNum);
						}
						if(MissionNum==2){
							detail.add(FileMessage,FileMessage,4,list.get(i).filename+":0->"+Long.toString(list.get(i).size)+"(new created)",1);
						}
					}
				}
				
				return 1;
			}
		}
	}
	
	
	
	
	
	
	

	
	
	public void Recover(LinkedList<AFile> LostList,LinkedList<AFile> NewList){
		if(WatcherNum==1 || WatcherNum==3){
			System.out.println("LostList:"+LostList.size());
			System.out.println("NewList:"+LostList.size());
			for(int i=0;i<LostList.size();i++){
				System.out.println("*************");
				(NewList.get(i).file).renameTo(LostList.get(i).file);
			}
		}
	}
	
	
	
	public long GetSize(File file){
		long size=0;
		int i=0;
		if(file.isFile()){
			return file.length();
		}
		else{
			File list[]=file.listFiles();
			if(list==null){
				return 0;
			}
			for(i=0;i<list.length;i++){
				if(list[i].isFile()){
					size+=list[i].length();
				}
			}
			return size;
		}
	}
	
	
	
	
	
	
	
	
	
	public void ReadDir(LinkedList<AFile> FileList,File file){
		
		if(file.isDirectory()){
			int i=0;
			AFile afile=new AFile(file);
			FileList.add(afile);
			File []list=file.listFiles();
			if(list.length==0){
				return;
			}
			for(i=0;i<list.length;i++){	
				ReadDir(FileList,list[i]);
			}
		}
		else{
			AFile afile=new AFile(file);
			FileList.add(afile);
		}
	}
	
	
}
