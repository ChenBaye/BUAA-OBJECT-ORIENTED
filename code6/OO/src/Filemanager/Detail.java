package Filemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class Detail extends Thread{
	boolean lock=false;
	LinkedList<String> renamed=new LinkedList<String>();
	LinkedList<String> Modified=new LinkedList<String>();
	LinkedList<String> pathchanged=new LinkedList<String>();
	LinkedList<String> sizechanged=new LinkedList<String>();
	
	public void run(){
		do{
			try {
				this.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			while (lock == true) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			WriteInfo();
			
		}while(true);
		
	}
	
	public  synchronized void add(AFile file1,AFile file2,int i,String str,int mod){
		if(mod==0){
			String s="\r\n["+file1.filename+"]:\r\n"+
				 "size:"+Long.toString(file1.size)+"\r\n"+
				 "ModifiedTime:"+Long.toString(file1.ModTime)+"\r\n"+
				 "path:"+file1.path+"----------------------------------->\r\n["+
				 file2.filename+"]:\r\n"+
				 "size:"+Long.toString(file2.size)+"\r\n"+
				 "ModifiedTime:"+Long.toString(file2.ModTime)+"\r\n"+
				 "path:"+file2.path+"\r\n";
			switch(i){
			case 1:renamed.add(s);return;
			case 2:Modified.add(s);return;
			case 3:pathchanged.add(s);return;
			case 4:sizechanged.add(s);return;
			default:break;
			}
		}
		else{
			switch(i){
			case 1:renamed.add(str);return;
			case 2:Modified.add(str);return;
			case 3:pathchanged.add(str);return;
			case 4:sizechanged.add(str);return;
			default:break;
			}
		}
	}
	
	
	
	
	
	public  void WriteInfo(){
		WriteFile("renamed:");
		for(int i=0;i<renamed.size();i++){
			WriteFile("    "+renamed.get(i));
		}
		WriteFile("Modified:");
		for(int i=0;i<Modified.size();i++){
			WriteFile("    "+Modified.get(i));
		}
		WriteFile("path-changed:");
		for(int i=0;i<pathchanged.size();i++){
			WriteFile("    "+pathchanged.get(i));
		}
		WriteFile("size-changed:");
		for(int i=0;i<sizechanged.size();i++){
			WriteFile("    "+sizechanged.get(i));
		}
		WriteFile("************************************************************************");
	}
	
	
	public void WriteFile(String s){
		 File file = new File("detail.txt");
		  String content =s+"\r\n";
		  
		  try (FileOutputStream fop = new FileOutputStream(file,true)) {
		   // if file doesn't exists, then create it
		   if (!file.exists()) {
		    file.createNewFile();
		   }

		   // get the content in bytes
		   byte[] contentInBytes = content.getBytes();

		   fop.write(contentInBytes);
		   fop.flush();
		   fop.close();
		
		  }catch (IOException e) {
			   e.printStackTrace();
		  }	
	}
	
	
	
	
	
	

}
