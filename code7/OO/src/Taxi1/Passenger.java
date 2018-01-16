package Taxi1;
import java.lang.Math;
import java.util.LinkedList;

public class Passenger extends Thread{
	MyPoint src;
	MyPoint dst;
	long ReqTime=0;
	Taxi [] taxi;
	Matrix matrix;
	OutPut output;
	LinkedList <String> StrList=new LinkedList<String>();
	LinkedList <Taxi> temp=new LinkedList<Taxi>();
	LinkedList <Taxi> list=new LinkedList<Taxi>();
	public Passenger(int src_x,int src_y,int dst_x,int dst_y,long ReqTime,Taxi [] taxi,Matrix matrix,OutPut output){
		this.src=new MyPoint(src_x,src_y);
		this.dst=new MyPoint(dst_x,dst_y);
		this.ReqTime=ReqTime;
		this.taxi=taxi;
		this.matrix=matrix;
		this.output=output;
		System.out.println("src: ("+src.x+","+src.y+")");
		System.out.println("dst: ("+dst.x+","+dst.y+")");
		System.out.println("ReqTime: "+ReqTime+"*100ms");
	}
	
	
	public void run(){
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
			if(temp.get(i).status==2){
				
				StrList.add("taxi:"+temp.get(i).index);
				way1=matrix.Dijkstra(matrix.adjacency,temp.get(i).point,src);
				way2=matrix.Dijkstra(matrix.adjacency,src,dst);
				
				temp.get(i).way1=way1;
				temp.get(i).way2=way2;
				
				//接单
				temp.get(i).status=3;
				break;
			}
		}	
		//车没有一辆等待服务 
		if(i==temp.size()){
			System.out.println("src: ("+src.x+","+src.y+") "+"dst: ("+dst.x+","+dst.y+") "+" ReqTime: "+ReqTime+"*100ms"+"无可响应车");
			return;
		}
		StrList.add("选中taxi:"+temp.get(i).index+"接客路径长："+(way1.size()-1)+"送客路径长："+(way2.size()-1)+"此时时间为"+System.currentTimeMillis()/100+"*100ms");
		System.out.println("选中taxi:"+temp.get(i).index+"接客路径长："+(way1.size()-1)+"送客路径长："+(way2.size()-1));
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
	
	
	
	
	//将响应的出租车按信用及距离排序
	public void Sort(){
		temp.add(list.get(0));
		for(int i=0;i<list.size();i++){
			//插入最前
			if(list.get(i).credit>temp.get(0).credit || 
					(list.get(i).credit==temp.get(0).credit) && distance(list.get(i))<distance(temp.get(0))){
				temp.addFirst(list.get(i));
				continue;
			}
			//插入最后
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
		if(taxi.point.x<=src.x+2 && taxi.point.x>=src.x-2 && 
		   taxi.point.y<=src.y+2 && taxi.point.y>=src.y-2){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	public void CreditTaxi(){
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
	}
	
	
	
	
	public int distance(Taxi taxi){
		int x=src.x-taxi.point.x;
		int y=src.y-taxi.point.y;
		int distance=x*x+y*y;
		return distance;
	}
	
	
	
}
