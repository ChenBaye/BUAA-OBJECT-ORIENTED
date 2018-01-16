package Taxi4;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InputMessage {
	//表示对象:none
	//
	//抽象函数：AF(c)=()
	//
	//不变式:none
	public boolean repOK(){
		/*@Effects: \result==invariant(this).
		*/
		return true;
	}
	
	
	public void Input(Taxi [] taxi,Matrix matrix,OutPut output,TaxiGUI gui){
		/*@REQUIRES:taxi!=null,matrix!=null,output!=null,gui!=null
		 @
		 @
		 @MODIFIES:matrix,taxi,gui
		 @
		 @EFFECTS:接收控制台输入并将其转成乘客请求或道路修改请求
		 @
		 */
		while(true){
			System.out.println("input request or set road status:");
			Scanner sc=new Scanner(System.in);
			String str=sc.nextLine();
			String str1=str.replaceAll(" ","");
			if(str1.equals("end")){
				sc.close();
				break;
			}
			else if(str1.equals("request")){
				InputRequest(taxi,matrix,output,gui);
			}
			else if(str1.equals("road")){
				SetRoad(matrix,gui);
			}
			else{
				System.out.println("invalid:"+str1);
			}
		}
	}
	
	
	
	
	public void InputRequest(Taxi [] taxi,Matrix matrix,OutPut output,TaxiGUI gui){
		/*@REQUIRES:taxi!=null,matrix!=null,output!=null,gui!=null
		 @
		 @
		 @MODIFIES:matrix,taxi,gui
		 @
		 @EFFECTS:接收乘客请求并生成乘客线程
		 @
		 */
		LinkedList<Passenger> list=new LinkedList<Passenger>();
		try{
		int i=0;
		long ReqTime=0;
		boolean bool=true;
		while(true){
			System.out.println("input request:");
			Scanner sc1=new Scanner(System.in);
			String str=sc1.nextLine();
			long Time=System.currentTimeMillis()/100;
			String str1=str.replaceAll(" ","");
			if(str1.equals("requestend")){
				break;
			}
			String [] s=str1.split(";");
			if(s.length==0){
				System.out.println("invalid:"+str1);
				continue;
			}
			for(i=0;i<s.length;i++){
				String request="\\[CR,\\(\\+?0*\\d{1,2},\\+?0*\\d{1,2}\\),\\(\\+?0*\\d{1,2},\\+?0*\\d{1,2}\\)\\]";
				Pattern pattern = Pattern.compile(request);
				Matcher matcher = pattern.matcher(s[i]);
				bool=matcher.matches();
				if(bool==false){
					System.out.println("invalid:"+s[i]);
					continue;
				}
				String str2=s[i].replaceAll("\\[|\\]|\\(|\\)","");
				String [] s1=str2.split(",");
				int src_x=Integer.parseInt(s1[1]);
				int src_y=Integer.parseInt(s1[2]);
				int dst_x=Integer.parseInt(s1[3]);
				int dst_y=Integer.parseInt(s1[4]);
				
				if(src_x==dst_x && src_y==dst_y){
					System.out.println("invalid:"+s[i]);
					continue;
				}
				if(!(range(src_x) && range(src_y) && range(dst_x) && range(dst_y))){
					System.out.println("invalid:"+str1);
					continue;
				}
				
				bool=true;
				for(int j=0;j<list.size();j++){
					if(list.get(j).src.x==src_x && list.get(j).src.y==src_y &&
							list.get(j).dst.y==dst_y && list.get(j).dst.x==dst_x
							&& list.get(j).ReqTime==Time){
						bool=false;
						break;
					}
				}
				
				if(bool==false){
					System.out.println("same:"+s[i]);
					continue;
				}
				
				ReqTime=Time;
				
				Passenger passenger=new Passenger(src_x,src_y,dst_x,dst_y,ReqTime,taxi,matrix,output);
				gui.RequestTaxi(new Point(src_x,src_y), new Point(dst_x,dst_y));
				passenger.start();
				list.add(passenger);
				
			}	
			
		}
		}catch(Exception e){
		}
	}
	
	
	
	
	public void SetRoad(Matrix matrix,TaxiGUI gui){
		/*@REQUIRES:matrix!=null,gui!=null
		 @
		 @
		 @MODIFIES:matrix
		 @
		 @EFFECTS:根据输入对道路进行截断、连接，并输出错误信息。
		 @
		 */
		while(true){
			System.out.println("set road status:");
			boolean bool=true;
			Scanner sc2=new Scanner(System.in);
			String str=sc2.nextLine();
			String str1=str.replaceAll(" ","");
			if(str1.equals("roadend")){
				break;
			}
			String [] s=str1.split(";");
			if(s.length==0){
				System.out.println("invalid:"+str1);
			}
			
			for(int i=0;i<s.length;i++){
				String request="\\[\\(\\+?0*\\d{1,2},\\+?0*\\d{1,2}\\),\\(\\+?0*\\d{1,2},\\+?0*\\d{1,2}\\),0*[01]\\]";
				Pattern pattern = Pattern.compile(request);
				Matcher matcher = pattern.matcher(s[i]);
				bool=matcher.matches();
				if(bool==false){
					System.out.println("invalid:"+s[i]);
					continue;
				}
				String str2=s[i].replaceAll("\\[|\\]|\\(|\\)","");
				String [] s1=str2.split(",");
				int src_x=Integer.parseInt(s1[0]);
				int src_y=Integer.parseInt(s1[1]);
				int dst_x=Integer.parseInt(s1[2]);
				int dst_y=Integer.parseInt(s1[3]);
				int status=Integer.parseInt(s1[4]);
				if(!(range(src_x) && range(src_y) && range(dst_x) && range(dst_y))){
					System.out.println("invalid:"+str1);
					continue;
				}
				int temp=-1;
				temp=adj(src_x,src_y,dst_x,dst_y,matrix);
				
				if(temp<0){
					System.out.println("invalid:"+str1);
					continue;
				}
				
				if(status==1){
					matrix.adjacency[src_x*80+src_y][dst_x*80+dst_y]=1;
					matrix.adjacency[dst_x*80+dst_y][src_x*80+src_y]=1;
					if(temp==1){
						if(matrix.map[dst_x][dst_y]==2){
							matrix.map[dst_x][dst_y]=3;
						}
						else if(matrix.map[dst_x][dst_y]==0){
							matrix.map[dst_x][dst_y]=1;
						}
					}
					else if(temp==2){
						if(matrix.map[src_x][src_y]==2){
							matrix.map[src_x][src_y]=3;
						}
						else if(matrix.map[src_x][src_y]==0){
							matrix.map[src_x][src_y]=1;
						}
					}
					else if(temp==3){
						if(matrix.map[dst_x][dst_y]==1){
							matrix.map[dst_x][dst_y]=3;
						}
						else if(matrix.map[dst_x][dst_y]==0){
							matrix.map[dst_x][dst_y]=2;
						}
					}
					else{
						if(matrix.map[src_x][src_y]==1){
							matrix.map[src_x][src_y]=3;
						}
						else if(matrix.map[src_x][src_y]==0){
							matrix.map[src_x][src_y]=2;
						}
					}
					gui.SetRoadStatus(new Point(src_x,src_y),new Point(dst_x,dst_y),1);
				}
				else if(status==0){
					matrix.adjacency[src_x*80+src_y][dst_x*80+dst_y]=99999;
					matrix.adjacency[dst_x*80+dst_y][src_x*80+src_y]=99999;
					
					
					if(temp==1){
						if(matrix.map[dst_x][dst_y]==3){
							matrix.map[dst_x][dst_y]=2;
						}
						else if(matrix.map[dst_x][dst_y]==1){
							matrix.map[dst_x][dst_y]=0;
						}
					}
					else if(temp==2){
						if(matrix.map[src_x][src_y]==3){
							matrix.map[src_x][src_y]=2;
						}
						else if(matrix.map[src_x][src_y]==1){
							matrix.map[src_x][src_y]=0;
						}
					}
					else if(temp==3){
						if(matrix.map[dst_x][dst_y]==3){
							matrix.map[dst_x][dst_y]=1;
						}
						else if(matrix.map[dst_x][dst_y]==2){
							matrix.map[dst_x][dst_y]=0;
						}
					}
					else{
						if(matrix.map[src_x][src_y]==3){
							matrix.map[src_x][src_y]=1;
						}
						else if(matrix.map[src_x][src_y]==2){
							matrix.map[src_x][src_y]=0;
						}
					}
					
					gui.SetRoadStatus(new Point(src_x,src_y),new Point(dst_x,dst_y),0);
				}
				else{
					System.out.println("invalid:"+str1);
					continue;
				}
				
			}
			
		}
	}
	
	
	
	
	public boolean range(int i){
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:\result==(i>=0 && i<=79)?true:false
		 @
		 */
		if(i>=0 && i<=79){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	public int adj(int src_x,int src_y,int dst_x,int dst_y,Matrix matrix){
		/*@REQUIRES:0<=src_x<=79,0<=src_y<=79,0<=dst_x<=79,0<=dst_y<=79，matrix!=null
		 @
		 @
		 @MODIFIES:none
		 @
		 @EFFECTS:src在dst右一格，返回1；src在dst左一格，返回2；src在dst下一格，返回3；src在dst上一格，返回4；否则返回-1
		 @
		 */
		if(src_x==dst_x && src_y-dst_y==1){//src在dst右
			return 1;
		}
		if(src_x==dst_x && src_y-dst_y==-1){//src在dst左
			return 2;
		}
		if(src_y==dst_y && src_x-dst_x==1){//src在dst下
			return 3;
		}
		if(src_y==dst_y && src_x-dst_x==-1){//src在dst上
			return 4;
		}
		return -1;
		
	}
	
	
}
