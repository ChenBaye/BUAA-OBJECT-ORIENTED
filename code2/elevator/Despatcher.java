package elevator;

class Despatcher {//调度器类
	private int destination=1;
	private double StartTime=0;
	private double EndTime =0;
	private int i=0;
	private int j=0;
	private int []v=new int[100000];
	void Plan(int []floor,int []time,int num,int []FrOrEr,int [] UpOrDown){
		
		Elevator Elevator = new Elevator();			//调用电梯类
		
		for(i=0;i<num;i++){
			destination=floor[i];
			if(v[i]==1){
				continue;
			}
			else{
				if(EndTime<time[i]){
					StartTime=time[i];
				}
				else{
					StartTime=EndTime;
				}
				EndTime=Elevator.GoTo(destination,StartTime);
				for(j=i+1;j<num;j++){
					if(time[j]<=EndTime && 
					   FrOrEr[i]==FrOrEr[j] && 
					   UpOrDown[i]==UpOrDown[j] && 
					   floor[i]==floor[j]){
						v[j]=1;
					}
				}
			}
		}
		
	}
	
}
