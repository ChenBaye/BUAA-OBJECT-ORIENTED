package Taxi2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TaxiMessage extends Thread{
	Taxi taxi[];
	int i;
	int status;
	public TaxiMessage(Taxi [] taxi,int i,int status){
		/*@REQUIRES:taxi!=null,0<=i<=79,status=0或1或2或3
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:TaxiMessage的构造方法，为TaxiMessage的taxi，i，status赋值
		 @
		 */
		this.taxi=taxi;
		this.i=i;
		this.status=status;
	}
	public void run(){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:message.txt
		 @
		 @EFFECTS:每200ms向message.txt中输入指定出租车和指定状态的出租车信息
		 @
		 */
		try{
		
		do{
		try {
			sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WriteFile("index:    "+taxi[i].index);
		WriteFile("point:    x="+taxi[i].point.x+"    y="+taxi[i].point.y);
		WriteFile("status:    "+taxi[i].status);
		WriteFile("credit:    "+taxi[i].credit+"\r\n");
		WriteFile("time: "+System.currentTimeMillis()/100+"*100ms\r\n");
		
		
		
		WriteFile("状态为"+status+"的车：");
		for(int j=0;j<100;j++){
			if(taxi[j].status==status){
				WriteFile(Integer.toString(j));
			}
		}
		WriteFile("***************************");
		
		
		
		}while(true);
	
		
		}catch(Exception e){
			System.out.println("");
		}
	}
	
	
	
	
	
	public void WriteFile(String s){
		/*@REQUIRES:s!=null
		 @
		 @
		 @MODIFIES:message.txt
		 @
		 @EFFECTS:将s输入到message.txt中
		 @
		 */
		 File file = new File("message.txt");
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
