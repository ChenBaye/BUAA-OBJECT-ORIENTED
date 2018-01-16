package Filemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Summary extends Thread {
	int[] action = new int[4];
	int i = 0;
	int j = 0;
	boolean lock = false;
	
	
	public synchronized void add(int i) {
		lock = true;
		action[i-1]++;
		lock = false;
	}

	public void run() {
		int i=0;
		do {
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
			
			WriteFile("renamed："+Integer.toString(action[0]));
			WriteFile("Modified:"+Integer.toString(action[1]));
			WriteFile("path-changed:"+Integer.toString(action[2]));
			WriteFile("size-changed:"+Integer.toString(action[3]));
			WriteFile("*********************************************");
		} while (true);
	}
	
	
	
	public void WriteFile(String s){
		 File file = new File("summary.txt");
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
	
	
	
	public String WatcherStr(int i){
		switch (i){
			case 1:return "renamed";
			case 2:return "Modified";
			case 3:return "path-changed";
			case 4:return "size-changed";
			default:return "Error";
		}
	}

	public String MissionStr(int i){
		switch (i){
			case 1:return "record-summary";
			case 2:return "record-detail";
			case 3:return "recover";
			default:return "Error";
		}
	}
}
