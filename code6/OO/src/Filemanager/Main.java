package Filemanager;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



public class Main {
	public static void main(String [] args){
		File file = null;
		FileWatcher [] WatcherList=new FileWatcher[100];//监视器组
		int count=0;
		int j=0;
		boolean bool=true;
		String str1=null;
		String str2=null;
		
		
		ReadWriteLock lock=new ReentrantReadWriteLock();
		Summary summary=new Summary();
		Detail detail=new Detail();
		
		
		
		while(true){
			System.out.println("input watcher:");
			String [] StrList=new String[10];
			bool=true;
			int WatcherNum=0;
			int MissionNum=0;
			Scanner sc=new Scanner(System.in);
			str1=sc.nextLine();		
			str2=str1;			
			if(str2.equals("end")){
				break;
			}
			StrList=str2.split("\\|");				
			if(StrList.length!=5){
				System.out.println("INVALID: "+str1);
				continue;
			}
			else{
				bool=StrList[0].equals("IF") &&
					(StrList[2].equals("renamed") ||  StrList[2].equals("Modified") || StrList[2].equals("path-changed") || StrList[2].equals("size-changed"))&&
					 StrList[3].equals("THEN") &&
					(StrList[4].equals("record-summary") || StrList[4].equals("record-detail") || StrList[4].equals("recover"));
				WatcherNum= StrList[2].equals("renamed")?1:
					        StrList[2].equals("Modified")?2:
					        StrList[2].equals("path-changed")?3:
					        StrList[2].equals("size-changed")?4:0; 
				MissionNum=(StrList[4].equals("record-summary"))?1:
							StrList[4].equals("record-detail")?2:
							StrList[4].equals("recover")?3:0; 
				file=new File(StrList[1]);
				bool=bool && file.exists();	
				if(file.exists()==false){
					System.out.println("No such file!");
				}
				
			}
			
			if(MissionNum==3){
				if(WatcherNum==1 || WatcherNum==3){
					bool=bool && true;
				}
				else{
					bool=bool && false;
				}
			}
			
			for(j=0;j<count;j++){
				if(WatcherList[j].FileMessage.file.equals(file) && 
				   WatcherList[j].WatcherNum==WatcherNum &&
				   WatcherList[j].MissionNum==MissionNum){
						bool=false;
				}
			}
			if(bool==false){
				System.out.println("INVALID: "+str1);
				continue;
			}
			
			else if(bool==true){
				WatcherList[count]=new FileWatcher(file,WatcherNum,MissionNum,count,summary,detail,lock);
				count++;
			}
			else{
				System.out.println("INVALID: "+str1);
			}
		}
		
		System.out.println("Watcher:"+count);
		
		
		summary.start();
		detail.start();
		for(int i=0;i<count;i++){
			WatcherList[i].start();
			System.out.println("Watcher"+i+" start");
		}
		FileOperate operate=new FileOperate(lock);
		operate.start();							//启动一个文件操作线程,传入锁
		
	}
	
	
		
}
	
