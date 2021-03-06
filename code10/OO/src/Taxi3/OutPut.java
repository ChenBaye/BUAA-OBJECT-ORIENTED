package Taxi3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class OutPut {
	//表示对象:none
	//
	//抽象函数：AF(c)=()
	//
	//不变式:none
	
	public boolean repOK(){
		/*@Effects: \result==invariant(this).
		*/
		return true;
	}
	
	
	public  synchronized void PassWriteFile(LinkedList<String>list){
		/*@REQUIRES:list!=null
		 @
		 @
		 @MODIFIES:passenger.txt
		 @
		 @EFFECTS:将list中的内容输入到passenger.txt
		 @
		 @THREAD_REQUIRES:none
		 @
		 @THREAD_EFFECTS:\locked()
		 @
		 */
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
		/*@REQUIRES:list!=null
		 @
		 @
		 @MODIFIES:taxi.txt
		 @
		 @EFFECTS:将list中的内容输入到taxi.txt
		 @
		 @THREAD_REQUIRES:none
		 @
		 @THREAD_EFFECTS:\locked()
		 @
		 */
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
