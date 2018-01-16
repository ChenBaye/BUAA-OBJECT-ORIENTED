package elevatornew;
//interface
interface ElevatorMove{
	public String up();
	public String down();
	public String still();
}

class Elevator implements ElevatorMove{						//电梯类
	//表示对象:String  state[],int StateNum,int position
	//
	//抽象函数：AF(c)=()
	//
	//不变式:none
	private static String  state[]={"UP","DOWN","STILL"};	//电梯状态:UP; DOWN; STILL;
	private int StateNum=2;
	private int position=1;									//电梯位置

	public boolean repOK(){
		/*@Effects: \result==invariant(this).
		*/
		return (StateNum==0 || StateNum==1 || StateNum==2)&&(1<=position && position <=10) ;
	}

	public String toString(String s,int destination,double EndTime){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:返回到达目的地时的楼层、时间等状态
		 @
		 */
		return "("+destination+","+s+","+EndTime+")";
	}
	
	
	public String up(){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:return "UP"
		 @
		 */
		return "UP";
	}
	
	public String down(){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:return "DOWN"
		 @
		 */
		return "DOWN";
	}
	
	public String still(){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:return "STILL"
		 @
		 */
		return "STILL";
	}


	public int getPosition() {
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:return position
		 @
		 */
		return position;
	}


	public void setPosition(int position) {
		/*@REQUIRES:1<=position<=10
		 @
		 @
		 @MODIFIES:this
		 @
		 @EFFECTS:this.position=position
		 @
		 */
		this.position = position;
	}


	public int getStateNum() {
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:return StateNum
		 @
		 */
		return StateNum;
	}


	public void setStateNum(int stateNum) {
		/*@REQUIRES:statenum=0或1或2
		 @
		 @
		 @MODIFIES:this
		 @
		 @EFFECTS:this.StateNum=statenum
		 @
		 */
		StateNum = stateNum;
	}


	double GoTo(int destination,double StartTime){	//电梯受调度运行
		/*@REQUIRES:1<=destination<=10
		 @
		 @
		 @MODIFIES:this
		 @
		 @EFFECTS:电梯按指导书要求从规定时间开始向规定地点运动,返回关门时间
		 @
		 */
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
