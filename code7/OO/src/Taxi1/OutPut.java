package Taxi1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class OutPut {
	public  synchronized void PassWriteFile(LinkedList<String>list){
		for(int i=0;i<list.size();i++){
			String s=list.get(i);
			 File file = new File("passenger.txt");
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
	
	
	
	
	public  synchronized void TaxiWriteFile(LinkedList<String>list){
		for(int i=0;i<list.size();i++){
			String s=list.get(i);
			 File file = new File("taxi.txt");
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
	
	
	
	
}
