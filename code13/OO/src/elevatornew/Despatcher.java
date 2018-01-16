package elevatornew;

class Despatcher {//父类调度器类
	//表示对象:int destination,double StartTime,double EndTime,int i,int []v,int j,double NowTime,int i,int j, int [] v,String state
	//
	//抽象函数：AF(c)=()
	//
	//不变式:none
	private int destination=1;
	private double StartTime=0;
	private double EndTime =0;
	private int i=0;
	private int j=0;
	private int []v=new int[100000];
	double Plan(int []floor,double []time,int num,int []FrOrEr,int [] UpOrDown){
		/*@REQUIRES:floor!=null且在1到10之间,time!=null且元素从大到小,num!=null,FrOrEr!=null且元素为0或1,UpOrDown!=null且元素为0或1;floor.size=time.size=ReOeEr.size=UpOrDown.size=num
		 @
		 @
		 @MODIFIES:this
		 @
		 @EFFECTS:按指导书要求调度电梯运动,返回最后关门时间
		 @
		 */
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
		return EndTime;
	}
	
}
