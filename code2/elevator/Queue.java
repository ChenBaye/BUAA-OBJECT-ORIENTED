package elevator;

class Queue {//请求队列类
	private int floor[]=new int[100000];
	private int time[]=new int[100000];
	private int num=0;
	private String [] str=null;
	private String str1=null;
	private String str2=null;
	private boolean bool=true;
	private int FrOrEr[]=new int[100000];						//FR-1,ER-0
	private int UpOrDown[]=new int[100000];
	
	
	
	
	void CreateQueue(String request){
		bool=true;
		str1=request.replaceAll("\\(","");				//去左括号
		str2=str1.replaceAll("\\)","");					//去右括号
		str=str2.split(",");							
		//str[i]={FR,楼层,UP|DOWN,时刻} 或  {ER,楼层,时刻}
		
		Floor Floor=new Floor();
		
		if(Integer.parseInt(str[1])==Floor.getNum() && str[2].equals("UP") || 
		   Integer.parseInt(str[1])==1  && str[2].equals("DOWN")){
			
		   System.out.println("输入有误");
		   bool=false;
		}
		
		else if(num==0){
			if(str[0].equals("FR") && Integer.parseInt(str[3])!=0 || 
			   str[0].equals("ER") && Integer.parseInt(str[2])!=0){
				
			   System.out.println("输入有误");
		       bool=false;
			}
		}
		
		else if(num!=0){
		      if(str[0].equals("FR") && Integer.parseInt(str[3])<time[num-1] || 
		         str[0].equals("ER") && Integer.parseInt(str[2])<time[num-1]         )
		   {
			System.out.println("输入有误");
			bool=false;
		   }
		}
		
		if(bool==true){
			if(str[0].equals("FR")){
				floor[num]=Integer.parseInt(str[1]);
				time[num] =Integer.parseInt(str[3]);
				FrOrEr[num]=1;
				UpOrDown[num]=str[2].equals("UP")?1:0;
			}
			else{
				floor[num]=Integer.parseInt(str[1]);
				time[num] =Integer.parseInt(str[2]);
				FrOrEr[num]=0;
			}
			num++;
		}
		
	}


	public int[] getFloor() {
		return floor;
	}

	public int[] getTime() {
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

}




