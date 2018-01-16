package elevator;

class Elevator {									        //电梯类
	private static String  state[]={"UP","DOWN","STILL"};	//电梯状态:UP; DOWN; STILL;
	private int StateNum=3;
	private int position=1;									//电梯位置
	
	
	
	double GoTo(int destination,double StartTime){	//电梯受调度运行
		int floors=0;								//相距层数
		double EndTime=0;
		if(position==destination){					//同层
			StateNum=2;
			floors=0;
			EndTime=StartTime+1;
			System.out.println("("+destination+",STILL,"+EndTime+")");
		}
		else if(position>destination){				//向下
			StateNum=1;
			floors=position-destination;
			EndTime=StartTime+0.5*floors;
			System.out.println("("+destination+",DOWN,"+EndTime+")");
			EndTime++;
		}
		else if(position<destination){				//向下
			StateNum=0;
			floors=destination-position;
			EndTime=StartTime+0.5*floors;
			System.out.println("("+destination+",UP,"+EndTime+")");
			EndTime++;
		}
		position=destination;						//到达目的地
		return EndTime;
	}
}
