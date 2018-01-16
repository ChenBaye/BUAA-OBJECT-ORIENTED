package Filemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;

public class FileOperate extends Thread{
	ReadWriteLock lock;
	
	public FileOperate(ReadWriteLock lock){
		this.lock=lock;
	}
	
	
	public void run(){
		
		try{
		do{
			lock.writeLock().lock();
			
			System.out.println("You can operate files now.");
			Scanner sc=new Scanner(System.in);
			String str1=sc.nextLine();	
			String [] s=str1.split("\\|");
			System.out.println(s[0]);
			if(s[0].equals("create")){
				File file=new File(s[1]);
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(s[0].equals("delete")){
				File file=new File(s[1]);
				
					file.delete();
				
			}
			else if(s[0].equals("write")){
				WriteFile(s[1],s[2]);
				
			}
			else if(s[0].equals("move")){
				File file1=new File(s[1]);
				File file2=new File(s[2]);
				file1.renameTo(file2);
			}
			else if(s[0].equals("rename")){
				File file1=new File(s[1]);
				File file2=new File(s[2]);
				file1.renameTo(file2);
			}
			else if(s[0].equals("infor")){
				File file=new File(s[1]);
				System.out.println("name:"+file.getName());
				System.out.println("ModTime:"+file.lastModified());
				System.out.println("size:"+GetSize(file));
			}
			else{
				System.out.println("Error");
			}
			
			lock.writeLock().unlock();
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}while(true);
		}catch (Exception e) {
			System.out.println("invalid input");
		}
	}
	
	
	public void WriteFile(String file1,String s){
		 File file = new File(file1);
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
	
	
}
