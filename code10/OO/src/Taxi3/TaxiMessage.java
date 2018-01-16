package Taxi3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TaxiMessage extends Thread{
	//表示对象:Taxi taxi[];int i;int status;
	//
	//抽象函数：AF(c)=(taxi,i,status)where taxi=c.taxi,i==c.i,status==c.status
	//
	//不变式:taxi!=null && 0<=i<=99 && status=0或1或2或3
	Taxi taxi[];
	int i;
	int status;
	
	public boolean repOK(){
		/*@Effects: \result==invariant(this).
		*/
		if(taxi==null || i<0 || i>99){
			return false;
		}
		if(!(status==0 || status==1 || status==2 || status==3)){
			return false;
		}
		return true;
	}
	
	
	
	
	public TaxiMessage(Taxi [] taxi,int i,int status){
		/*@REQUIRES:taxi!=null,0<=i<=99,status=0或1或2或3
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
