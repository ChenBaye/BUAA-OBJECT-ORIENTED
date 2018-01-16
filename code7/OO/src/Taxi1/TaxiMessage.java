package Taxi1;

public class TaxiMessage extends Thread{
	Taxi taxi[];
	int i;
	int status;
	public TaxiMessage(Taxi [] taxi,int i,int status){
		this.taxi=taxi;
		this.i=i;
		this.status=status;
	}
	public void run(){
		try{
		
		do{
		try {
			sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		taxi[i].Get();
		for(int j=0;j<100;j++){
			if(taxi[j].status==status){
				System.out.print(j+" ");
			}
		}
		System.out.println("");
		
		
		
		}while(true);
	
		
		}catch(Exception e){
			System.out.println("");
		}
	}
}
