package elevatornew;

class Queue {//请求队列类
	private int FloorQueue[]=new int[100000];
	private double time[]=new double[100000];
	private int num=0;
	private String [] str=null;
	private String str1=null;
	private String str2=null;
	private boolean bool=true;
	private int FrOrEr[]=new int[100000];				//FR-1,ER-0
	private int UpOrDown[]=new int[100000];				//UP-1,DOWN-0
	
	
	
	
	void CreateQueue(String request){
		bool=true;
		str1=request.replaceAll("\\(","");				//去左括号
		str2=str1.replaceAll("\\)","");					//去右括号
		str=str2.split(",");							
														//str[i]={FR,楼层,UP|DOWN,时刻} 或  {ER,楼层,时刻}
		Floor floor=new Floor();
		
		
		bool&=TenUpOneDown(str,floor,request);                   //10 UP,1DOWN	2
		
		if(num==0 && bool==true){										
			bool&=FirstRequest(request);				   		 //第一条为(FR,1,UP,0)	3
		}
		else if(num!=0 && bool==true){										
			bool&=TimeSequence(str,time,num,request);			 //时间后大于前 	4
		}
		
		if(bool==true){
		bool&=NumberRange(str,floor,request);					//floor:0-10;time:0-2147483647	5
		}
		
		if(bool==true){
			if(str[0].equals("FR")){
				FloorQueue[num]=Integer.parseInt(str[1]);
				time[num] =Double.parseDouble(str[3]);
				FrOrEr[num]=1;
				UpOrDown[num]=str[2].equals("UP")?1:0;
			}
			else{
				FloorQueue[num]=Integer.parseInt(str[1]);
				time[num] =Double.parseDouble(str[2]);
				FrOrEr[num]=0;
			}
			num++;
		}
		
	}


	public int[] getFloorQueue() {
		return FloorQueue;
	}

	public double[] getTime() {
		return time;
	}

	public int getNum() {
		return num;
	}

	public int[] getFrOrEr() {
		return FrOrEr;
	}
	
	
	public int[] getUpOrDown() {
		return UpOrDown;
	}		

	public boolean TimeSequence(String []str,double [] time,int num,String request){
		 if(str[0].equals("FR") && Double.parseDouble(str[3])<time[num-1] || 
		    str[0].equals("ER") && Double.parseDouble(str[2])<time[num-1] )
		 {
			 System.out.println("invalid input4:"+request);
			 return false;
		 }
		 else{
			 return true;
		 }
	}
	
	public boolean FirstRequest(String request){
		if(!request.equals(("(FR,1,UP,0)"))){
			   System.out.println("invalid input3:"+request);
		       return false;
			}
		else{
			return true;
		}
	}
	
	public boolean TenUpOneDown(String [] str,Floor floor,String request){
		if(Double.parseDouble(str[1])==floor.getNum() && str[2].equals("UP") || 
				   Double.parseDouble(str[1])==1  && str[2].equals("DOWN")){
				   System.out.println("invalid input2:"+request);
				   return false;
				}
		else{
			return true;
		}
	}

	public boolean NumberRange(String [] str,Floor floor,String request){
		if(!   (
				(str[0].equals("FR") && 							//floor:0-10
						Double.parseDouble(str[3])<=2147483647 && 	//t:0-2147483647
						Double.parseDouble(str[3])>=0 && 
						Integer.parseInt(str[1])<=floor.getNum() &&
						Integer.parseInt(str[1])>=0) ||
				(str[0].equals("ER") && 
						Double.parseDouble(str[2])<=2147483647 && 
						Double.parseDouble(str[2])>=0 &&
						Integer.parseInt(str[1])<=floor.getNum() &&
						Integer.parseInt(str[1])>=0)
			    )
		   ){
			System.out.println("invalid input5:"+request);
			return false;
		}
		else{
			return true;
		}
	}


}






