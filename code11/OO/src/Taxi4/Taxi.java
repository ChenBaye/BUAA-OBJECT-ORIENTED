package Taxi4;

import java.awt.Point;
import java.util.LinkedList;

public class Taxi extends Thread{
	//表示对象:Matrix matrix;MyPoint point;int index;int status;int credit;int count;int direction;OutPut output;LinkedList <String> StrList=new LinkedList<String>();TaxiGUI gui;Light light;LinkedList<MyPoint> way1=new LinkedList<MyPoint>();LinkedList<MyPoint> way2=new LinkedList<MyPoint>();
	//
	//抽象函数：AF(c)=(Matrix matrix,int index,OutPut output,TaxiGUI gui,Light light)where matrix==c.matrix,index==c.index,output==c.output,gui==c.gui,light==c.light
	//
	//不变式:c.matrix!=null&&0<=c.index<=99&&c.output!=null&&c.gui!=null&&c.light!=null
	Matrix matrix;
	MyPoint point=new MyPoint((int)(Math.random()*matrix.size),(int)(Math.random()*matrix.size));			//位置
	int index;				//编号0-99
	int status=2;				//状态0-停止运行，1-服务，2-等待服务，3-接单
	int credit=0;
	int count=0;
	int direction=1;
	//1-西->东,2-东->西,3-南->北,4-北->南
	OutPut output;
	LinkedList <String> StrList=new LinkedList<String>();
	TaxiGUI gui;
	Light light;
	//接顾客
	LinkedList<MyPoint> way1=new LinkedList<MyPoint>();
	//去目的地
	LinkedList<MyPoint> way2=new LinkedList<MyPoint>();
	int IsCarzy=0;
	
	public boolean repOK(){
		/*@Effects: \result==invariant(this).
		*/
		if(matrix==null || output==null || gui==null || light==null){
			return false;
		}
		if(!(index>=0 && index<=99)){
			return false;
		}
		return true;
	}
	
	
	public Taxi(Matrix matrix,int index,OutPut output,TaxiGUI gui,Light light){
		/*@REQUIRES:matrix!=null,0<=index<=99,output!=null,gui!=null
		 @
		 @
		 @MODIFIES:this.matrix,this.index,this.gui,this.output
		 @
		 @EFFECTS:这是Taxi的构造方法，为Taxi类中的matrix,index,gui,output赋值
		 @
		 */
		this.matrix=matrix;
		this.index=index;
		this.gui=gui;
		this.output=output;
		this.light=light;
	}
	
	
	
	public void run(){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:this.status,this.credit,this.point,this.way1,this.way2,this.StrList,this.count,this.direction
		 @
		 @EFFECTS:Taxi类的run方法，Taxi按指导书要求运动
		 @
		 */
		//try{
		gui.SetTaxiStatus(index, new Point(point.x,point.y),2);
		do{	
			//等待服务
			if(status==2){
				try {
					sleep(200);
				} catch (Exception e) {
					
				}
				if(status==2){
					RandomMove();
					count++;
				}
				//等待状态20s后停止1s
				if(count>=100){
					status=0;
					count=0;
				}
			}
			//停止运行
			else if(status==0){
				gui.SetTaxiStatus(index, new Point(point.x,point.y), 0);
				try {
					sleep(1000);
				} catch (Exception e) {
				}
				if(status==0){
					status=2;
				}
			}
			else if(status==3){
				//接客送客
				Move();
				credit+=3;
			}
		}while(true);
		
	//	}catch(Exception e){
	//		System.out.println("");
	//	}
		
	}
	
	
	public void RandomMove(){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:this.point，this.count,this.direction
		 @
		 @EFFECTS:出租车按指导书随机游走，point随出租车位置变化
		 @
		 */
		LinkedList<MyPoint> random=new LinkedList<MyPoint>();
		if(point.x-1>=0){
			if(matrix.map[point.x-1][point.y]==2 || matrix.map[point.x-1][point.y]==3){
				MyPoint temp=new MyPoint(point.x-1,point.y);
				random.add(temp);
			}
		}
		if(point.x+1<=79){
			if(matrix.map[point.x][point.y]==2 || matrix.map[point.x][point.y]==3){
				MyPoint temp=new MyPoint(point.x+1,point.y);
				random.add(temp);
			}
		}
		if(point.y-1>=0){
			if(matrix.map[point.x][point.y-1]==1 || matrix.map[point.x][point.y-1]==3){
				MyPoint temp=new MyPoint(point.x,point.y-1);
				random.add(temp);
			}
		}
		if(point.y+1<=79){
			if(matrix.map[point.x][point.y]==1 || matrix.map[point.x][point.y]==3){
				MyPoint temp=new MyPoint(point.x,point.y+1);
				random.add(temp);
			}
		}
		
		
		if(random.size()==0){
			gui.SetTaxiStatus(index, new Point(point.x,point.y), 2);
		}
		else {
			int i=(int) (Math.random()*(random.size()));
			
			IsWait(random.get(i).x,random.get(i).y);
			this.direction=Direction(point.x,point.y,random.get(i).x,random.get(i).y);
			point.x=random.get(i).x;
			point.y=random.get(i).y;
			
			gui.SetTaxiStatus(index, new Point(point.x,point.y), 2);
		}	
	}
	
	
	
	
	public void Move(){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:this.StrList,this.point,this.way1,this.way2,this.status,this.credit,this.direction
		 @
		 @EFFECTS:将出租车前去接客，送客的路径记录在StrList,同时改变出租车的status和point、direction
		 @
		 */
		StrList=new LinkedList<String>();
		StrList.add("出发于:"+"("+way1.get(0).x+","+way1.get(0).y+")");
		StrList.add("taxi:"+index+"接客路径:\r\n");
		if(way1.size()==1){
		}
		else{
			for(int i=1;i<way1.size();i++){
				if(i==1){
					if(!(matrix.adjacency[way1.get(0).x*80+way1.get(0).y][way1.get(i).x*80+way1.get(i).y]<9999)){
						way1=matrix.Dijkstra(matrix.adjacency,point,way1.getLast());
						i=0;
						continue;
					}
				}
				else{
					if(!(matrix.adjacency[point.x*80+point.y][way1.get(i).x*80+way1.get(i).y]<9999)){
						way1=matrix.Dijkstra(matrix.adjacency,point,way1.getLast());
						i=0;
						continue;
					}
				}
				
				IsWait(way1.get(i).x,way1.get(i).y);
				this.direction=Direction(point.x,point.y,way1.get(i).x,way1.get(i).y);
				
				try {
					sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				point.x=way1.get(i).x;
				point.y=way1.get(i).y;
				
				StrList.add("("+point.x+","+point.y+") time:"+System.currentTimeMillis()/100+"*100ms");
				gui.SetTaxiStatus(index, new Point(point.x,point.y), 3);

			}
		}
		
		
		//停止1s
		status=0;
		gui.SetTaxiStatus(index, new Point(point.x,point.y), 0);
		try {
			sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		status=1;
		
		StrList.add("送客路径：\r\n");
		for(int j=1;j<way2.size();j++){
			
			if(matrix.adjacency[point.x*80+point.y][way2.get(j).x*80+way2.get(j).y]!=1){
				way2=matrix.Dijkstra(matrix.adjacency,point,way2.getLast());
				j=0;
				continue;
			}
			
			IsWait(way2.get(j).x,way2.get(j).y);
			this.direction=Direction(point.x,point.y,way2.get(j).x,way2.get(j).y);
			
			
			try {
				sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			point.x=way2.get(j).x;
			point.y=way2.get(j).y;
			
			
			
			
			StrList.add("("+point.x+","+point.y+") time:"+System.currentTimeMillis()/100+"*100ms");
			gui.SetTaxiStatus(index, new Point(point.x,point.y), 1);
			
		}
		
		//停止1s
		status=0;
		gui.SetTaxiStatus(index, new Point(point.x,point.y), 0);
		try {
			sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StrList.add("*************************************************");
		output.TaxiWriteFile(StrList);
		status=2;
	}
	
	
	
	
	
	
	//获得出租车的属性
	public synchronized void Get(){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:打印出租车此时状态
		 @
		 @THREAD_REQUIRES:none
		 @
		 @THREAD_EFFECTS:\locked()
		 */
		System.out.println("index:    "+index);
		System.out.println("point:    x="+point.x+"    y="+point.y);
		System.out.println("status:    "+status);
		System.out.println("credit:    "+credit+"\r\n");
		System.out.println("time: "+System.currentTimeMillis()/100+"*100ms");
	}
	
	
	
	
	public int Direction(int src_x,int src_y,int dst_x,int dst_y){
		/*@REQUIRES:0<=src_x<=79,0<=src_y<=79,0<=dst_x<=79,0<=dst_y<=79,
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:返回出租车的行驶方向
		 @
		 */
		if(src_x-dst_x==0 && src_y-dst_y==-1){
			return 1;
			//西->东
		}
		else if(src_x-dst_x==0 && src_y-dst_y==1){
			return 2;
			//东->西
		}
		else if(src_x-dst_x==-1 && src_y-dst_y==0){
			return 3;
			//南->北
		}
		else{
			return 4;
			//北->南
		}
	}
	
	
	
	public void IsWait(int dst_x,int dst_y){
		/*@REQUIRES:0<=dst_x<=79,0<=dst_y<=79,
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:出租车按指导书的行驶规则决定是否等待红灯
		 @
		 */
		if(light.light[point.x][point.y]==0){
			return;
		}
		
		if(direction==1){
			//由西向东
			if(dst_x-point.x==0 && dst_y-point.y==-1){
				return;
			}
			if(light.light[point.x][point.y]==1){
				//东西向绿灯
				if(Direction(point.x,point.y,dst_x,dst_y)==1 || Direction(point.x,point.y,dst_x,dst_y)==3){
					return;
				}
				else{
					do{
						try {
							sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while(light.light[point.x][point.y]==1);
				}
			}
			else{
				//南北向绿灯
				if(Direction(point.x,point.y,dst_x,dst_y)==3 || Direction(point.x,point.y,dst_x,dst_y)==4){
					return;
				}
				else{
					do{
						try {
							sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while(light.light[point.x][point.y]==2);
				}
			}
			
		}
		
		
		else if(direction==2){
			//由东向西
			if(dst_x-point.x==0 && dst_y-point.y==1){
				return;
			}
			if(light.light[point.x][point.y]==1){
				//东西向绿灯
				if(Direction(point.x,point.y,dst_x,dst_y)==2 || Direction(point.x,point.y,dst_x,dst_y)==4){
					return;
				}
				else{
					do{
						try {
							sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while(light.light[point.x][point.y]==1);
				}
			}
			else{
				//南北向绿灯
				if(Direction(point.x,point.y,dst_x,dst_y)==3 || Direction(point.x,point.y,dst_x,dst_y)==4){
					return;
				}
				else{
					do{
						try {
							sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while(light.light[point.x][point.y]==2);
				}
			}
		}
		
		
		else if(direction==3){
			//由南向北
			if(dst_x-point.x==1 && dst_y==point.y){
				return;
			}
			if(light.light[point.x][point.y]==1){
				//东西向绿灯
				if(Direction(point.x,point.y,dst_x,dst_y)==1 || Direction(point.x,point.y,dst_x,dst_y)==2){
					return;
				}
				else{
					do{
						try {
							sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while(light.light[point.x][point.y]==1);
				}
			}
			else{
				//南北向绿灯
				if(Direction(point.x,point.y,dst_x,dst_y)==3 || Direction(point.x,point.y,dst_x,dst_y)==1){
					return;
				}
				else{
					do{
						try {
							sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while(light.light[point.x][point.y]==2);
				}
			}
		}
		
		
		else{
			//由北向南
			if(dst_x-point.x==-1 && dst_y==point.y){
				return;
			}
			if(light.light[point.x][point.y]==1){
				//东西向绿灯
				if(Direction(point.x,point.y,dst_x,dst_y)==1 || Direction(point.x,point.y,dst_x,dst_y)==2){
					return;
				}
				else{
					do{
						try {
							sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while(light.light[point.x][point.y]==1);
				}
			}
			else{
				//南北向绿灯
				if(Direction(point.x,point.y,dst_x,dst_y)==4 || Direction(point.x,point.y,dst_x,dst_y)==2){
					return;
				}
				else{
					do{
						try {
							sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while(light.light[point.x][point.y]==2);
				}
			}
		}
		
	}
	
	
	
	
	public void Message(String s,LinkedList<MyPoint> way1,LinkedList<MyPoint> way2){
		/*@REQUIRES:s!=null,way1=null,way2!=null
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:none
		 @
		 */
		return;
	}
	
	
	
	
	
	
}




