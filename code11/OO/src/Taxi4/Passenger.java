package Taxi4;
import java.lang.Math;
import java.util.LinkedList;

public class Passenger extends Thread{
	
	//表示对象:MyPoint src;MyPoint dst;long ReqTime;Taxi [] taxi;Matrix matrix;OutPut output;LinkedList <String> StrList=new LinkedList<String>();LinkedList <Taxi> temp=new LinkedList<Taxi>();LinkedList <Taxi> list=new LinkedList<Taxi>();
	//
	//抽象函数：AF(c)=(src_x,src_y,dst_x,dst_y,ReqTime,taxi[],matrix,output)where src_x==c.src.x;src_y==c.src.y;dst_x==c.dst.x;dst_y==c.dst.y;ReqTime==c.ReqTime;,taxi==c.taxi;matrix==c.matrix.output==c.output
	//
	//不变式:0<=c.src_x<=79&&0<=c.src_y<=79&&0<=c.dst_x<=79&&0<=c.dst_y<=79&&c.taxi!=null&&c.matrix!=null&&c.output!=null
	MyPoint src;
	MyPoint dst;
	long ReqTime=0;
	Taxi [] taxi;
	Matrix matrix;
	OutPut output;
	LinkedList <String> StrList=new LinkedList<String>();
	LinkedList <Taxi> temp=new LinkedList<Taxi>();
	LinkedList <Taxi> list=new LinkedList<Taxi>();
	
	public boolean repOK(){
		/*@Effects: \result==invariant(this).
		*/
		if(src.x>79 || src.x<0){
			return false;
		}
		if(src.y>79 || src.y<0){
			return false;
		}
		if(dst.x>79 || dst.x<0){
			return false;
		}
		if(dst.y>79 || dst.y<0){
			return false;
		}
		if(taxi==null || matrix==null || output==null){
			return false;
		}
		return true;
	}
	
	public Passenger(int src_x,int src_y,int dst_x,int dst_y,long ReqTime,Taxi [] taxi,Matrix matrix,OutPut output){
		/*@REQUIRES:0<=src_x<=79,0<=src_y<=79,0<=dst_x<=79,0<=dst_y<=79,taxi!=null,matrix!=null,output!=null
		 @
		 @
		 @MODIFIES:this.src,this.dst,this.ReqTime,this.taxi,this.matrix,this.output
		 @
		 @EFFECTS:这是Passenger的构造方法，为Passenger类中的src,dst,ReqTime,taxi,matrix,output赋值
		 @
		 */
		this.src=new MyPoint(src_x,src_y);
		this.dst=new MyPoint(dst_x,dst_y);
		this.ReqTime=ReqTime;
		this.taxi=taxi;
		this.matrix=matrix;
		this.output=output;
		/*
		System.out.println("src: ("+src.x+","+src.y+")");
		System.out.println("dst: ("+dst.x+","+dst.y+")");
		System.out.println("ReqTime: "+ReqTime+"*100ms");
		*/
	}
	
	
	public void run(){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:this.StrList,this.list,this.temp,passenger.txt
		 @
		 @EFFECTS:将出租车路径存进way1和way2并调度出租车运行;打印接客出租车信息
		 @
		 */
		try{
		LinkedList<MyPoint> way1=new LinkedList<MyPoint>();
		LinkedList<MyPoint> way2=new LinkedList<MyPoint>();
		StrList.add("src: ("+src.x+","+src.y+") "+"dst: ("+dst.x+","+dst.y+") "+" ReqTime: "+ReqTime+"*100ms");
		StrList.add("记录发出请求时4x4格内车的信用、状态:\r\n");
		for(int i=0;i<100;i++){
			if(InArea(src,taxi[i])){
				//记录发出请求时4x4格内车的信用、状态
				StrList.add("taxi"+i+" credit:"+taxi[i].credit+" status:"+taxi[i].status+"\r\n");
			}
		}
		
		StrList.add("抢单窗口时间抢单的出租车:\r\n");
		
		do{		//抢单窗口时间
			for(int i=0;i<100;i++){
				//在4x4内且等待服务
				if(InArea(src,taxi[i]) && taxi[i].status==2){
					if(!list.contains(taxi[i])){
						list.add(taxi[i]);
						//信用度加1
						taxi[i].credit++;
						StrList.add("taxi:"+i+"\r\n");
					}
				}
			}	
		}while(((System.currentTimeMillis()/100)-ReqTime)<30);//30*100ms
		
		
		if(list.size()==0){
			System.out.println("src: ("+src.x+","+src.y+") "+"dst: ("+dst.x+","+dst.y+") "+" ReqTime: "+ReqTime+"*100ms"+"无可响应车");
			return;
		}
		
		//将响应的出租车按信用及距离排序
		Sort();
		
		
		StrList.add("系统选择响应的出租车：\r\n");
		int i=0;
		for(i=0;i<temp.size();i++){
			synchronized(temp.get(i)){
				if(temp.get(i).status==2){
				
					StrList.add("taxi:"+temp.get(i).index);
					way1=temp.get(i).matrix.Dijkstra(matrix.adjacency,temp.get(i).point,src);
					way2=temp.get(i).matrix.Dijkstra(matrix.adjacency,src,dst);
				
					temp.get(i).way1=way1;
					temp.get(i).way2=way2;
					String s="src: ("+src.x+","+src.y+") "+"dst: ("+dst.x+","+dst.y+") "+" ReqTime: "+ReqTime+"*100ms";
					temp.get(i).Message(s,way1,way2);
					
				
					//接单
			
					temp.get(i).status=3;
					break;
				}
			}
		}	
		//车没有一辆等待服务 
		if(i==temp.size()){
			System.out.println("src: ("+src.x+","+src.y+") "+"dst: ("+dst.x+","+dst.y+") "+" ReqTime: "+ReqTime+"*100ms "+"无可响应车");
			return;
		}
		StrList.add("选中taxi:"+temp.get(i).index+"接客路径长："+(way1.size()-1)+"送客路径长："+(way2.size()-1)+"此时时间为"+System.currentTimeMillis()/100+"*100ms");
		//System.out.println("选中taxi:"+temp.get(i).index+"接客路径长："+(way1.size()-1)+"送客路径长："+(way2.size()-1));
		/*
		StrList.add("接乘客路径：");
		for(int j=0;j<way1.size();j++){
			StrList.add("("+way1.get(j).x+","+way1.get(j).y+") ");
			System.out.println("("+way1.get(j).x+","+way1.get(j).y+") ");
		}
		StrList.add("送乘客路径：");
		for(int j=0;j<way2.size();j++){
			StrList.add("("+way2.get(j).x+","+way2.get(j).y+") ");
			System.out.println("("+way2.get(j).x+","+way2.get(j).y+") ");
		}*/
		
		StrList.add("******************************************");
		output.PassWriteFile(StrList);
		
		}catch(Exception e){
			System.out.println("");
		}
	}
	
	
	
	
	
	public void Sort(){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:this.temp
		 @
		 @EFFECTS:(\all int i, j;0<=i&i<j&j<temp.size();list.get(i).credit>temp.get(j).credit || (list.get(i).credit==temp.get(j).credit) && distance(list.get(i))<distance(temp.get(j));
		 @
		 */
		temp.add(list.get(0));
		for(int i=0;i<list.size();i++){
			if(list.get(i).credit>temp.get(0).credit || 
					(list.get(i).credit==temp.get(0).credit) && distance(list.get(i))<distance(temp.get(0))){
				temp.addFirst(list.get(i));
				continue;
			}
			if(list.get(i).credit<temp.getLast().credit ||
					(list.get(i).credit==temp.getLast().credit && 
					distance(list.get(i))>distance(temp.getLast()))
					){
				temp.addLast(list.get(i));
				continue;
			}	
			for(int j=0;j<temp.size();j++){
				if(
				(temp.get(j).credit>list.get(i).credit || (temp.get(j).credit==list.get(i).credit && distance(temp.get(j))<distance(list.get(i)))) &&
				(temp.get(j+1).credit<list.get(i).credit || (temp.get(j+1).credit==list.get(i).credit && distance(temp.get(j+1))>distance(list.get(i))))
				){
					temp.add(j+1,list.get(i));
					break;
				}
			}
		}
	}
	
	
	
	
	
	public boolean InArea(MyPoint src,Taxi taxi){
		/*@REQUIRES:taxi!=null
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:如果taxi在src方圆4格含边界内，返回true，否则返回false
		 @
		 */
		if(taxi.point.x<=src.x+2 && taxi.point.x>=src.x-2 && 
		   taxi.point.y<=src.y+2 && taxi.point.y>=src.y-2){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	/*public void CreditTaxi(){
		int credit;
		credit=list.get(0).credit;
		for(int i=0;i<list.size();i++){
			if(list.get(i).credit<credit){
				list.remove(i);
			}
			else{
				credit=list.get(i).credit;
			}
		}
	}
	
	
	
	public void LatestTaxi(){
		int distance=distance(list.get(0));
		for(int i=0;i<list.size();i++){
			if(distance(list.get(i))>distance){
				list.remove(i);
			}
			else{
				distance=distance(list.get(i));
			}
		}
	}
	
	
	public void DeleteTaxi(){
		for(int i=0;i<list.size();i++){
			if(list.get(i).status!=2){
				list.remove(i);
			}
		}
	}*/
	
	
	
	
	public int distance(Taxi taxi){
		/*@REQUIRES:
		 @
		 @
		 @MODIFIES:
		 @
		 @EFFECTS:
		 @
		 */
		int x=src.x-taxi.point.x;
		int y=src.y-taxi.point.y;
		int distance=x*x+y*y;
		return distance;
	}
	
	
	
}
