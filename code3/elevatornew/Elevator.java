package elevatornew;
//interface
interface ElevatorMove{
	public String up();
	public String down();
	public String still();
}

class Elevator implements ElevatorMove{						//电梯类
	private static String  state[]={"UP","DOWN","STILL"};	//电梯状态:UP; DOWN; STILL;
	private int StateNum=2;
	private int position=1;									//电梯位置

	//overload toString
	public String toString(String s,int destination,double EndTime){
		return "("+destination+","+s+","+EndTime+")";
	}
	
	
	public String up(){
		return "UP";
	}
	
	public String down(){
		return "DOWN";
	}
	
	public String still(){
		return "STILL";
	}


	public int getPosition() {
		return position;
	}


	public void setPosition(int position) {
		this.position = position;
	}


	public int getStateNum() {
		return StateNum;
	}


	public void setStateNum(int stateNum) {
		StateNum = stateNum;
	}


	double GoTo(int destination,double StartTime){	//电梯受调度运行
	int floors=0;								//相距层数
	double EndTime=0;
	if(position==destination){					//同层
		floors=0;
		EndTime=StartTime+1;
		StateNum=2;
		//toString(state[2],destination,EndTime);
	}
	else if(position>destination){				//向下
		floors=position-destination;
		EndTime=StartTime+0.5*floors;
		StateNum=1;
		//toString(state[1],destination,EndTime);
		EndTime++;
	}
	else if(position<destination){				//向上
		floors=destination-position;
		EndTime=StartTime+0.5*floors;
		StateNum=0;
		//toString(state[0],destination,EndTime);
		EndTime++;
	}
	position=destination;						//到达目的地
	return EndTime;
	}
}
