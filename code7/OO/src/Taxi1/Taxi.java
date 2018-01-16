package Taxi1;

import java.awt.Point;
import java.util.LinkedList;

public class Taxi extends Thread{
	Matrix matrix;
	MyPoint point=new MyPoint((int)(Math.random()*matrix.size),(int)(Math.random()*matrix.size));			//位置
	int index;				//编号0-99
	int status=2;				//状态0-停止运行，1-服务，2-等待服务，3-接单
	int credit=0;
	OutPut output;
	LinkedList <String> StrList=new LinkedList<String>();
	TaxiGUI gui;
	//接顾客
	LinkedList<MyPoint> way1=new LinkedList<MyPoint>();
	//去目的地
	LinkedList<MyPoint> way2=new LinkedList<MyPoint>();
	
	public Taxi(Matrix matrix,int index,OutPut output,TaxiGUI gui){
		this.matrix=matrix;
		this.index=index;
		this.gui=gui;
		this.output=output;
		OutInfo();
	}
	
	
	
	public void run(){
		try{
		gui.SetTaxiStatus(index, new Point(point.x,point.y),2);
		int count=0;
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
		
		}catch(Exception e){
			System.out.println("");
		}
		
	}
	
	
	//随机行走
	public void RandomMove(){
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
			if(matrix.map[point.x][point.y]==2 || matrix.map[point.x][point.y]==3){
				MyPoint temp=new MyPoint(point.x,point.y+1);
				random.add(temp);
			}
		}
		
		
		if(random.size()==0){
			return;
		}
		else{
			int i=(int) (Math.random()*(random.size()));
			point.x=random.get(i).x;
			point.y=random.get(i).y;
			gui.SetTaxiStatus(index, new Point(point.x,point.y), 2);
		}
		
	}
	
	
	
	
	public void Move(){
		StrList=new LinkedList<String>();
		StrList.add("出发于:"+"("+way1.get(0).x+","+way1.get(0).y+")");
		StrList.add("taxi:"+index+"接客路径:\r\n");
		if(way1.size()==1){
		}
		else{
			for(int i=1;i<way1.size();i++){
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
				//System.out.println("("+point.x+","+point.y+") ");
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
			//System.out.println("("+point.x+","+point.y+") ");
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
	
	
	
	
	public void OutInfo(){
		System.out.println("index:    "+index);
		System.out.println("point:    x="+point.x+"    y="+point.y);
		System.out.println("status:    "+status);
		System.out.println("credit:    "+credit+"\r\n");
		
	}
	
	
	
	//获得出租车的属性
	public synchronized void Get(){
		System.out.println("index:    "+index);
		System.out.println("point:    x="+point.x+"    y="+point.y);
		System.out.println("status:    "+status);
		System.out.println("credit:    "+credit+"\r\n");
		System.out.println("time: "+System.currentTimeMillis()/100+"*100ms");
	}
	
}




