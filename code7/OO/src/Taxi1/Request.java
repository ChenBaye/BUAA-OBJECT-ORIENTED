package Taxi1;

import java.awt.Point;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Request {
	public void InputRequest(Taxi [] taxi,Matrix matrix,OutPut output,TaxiGUI gui){
		try{
		int i=0;
		long ReqTime=0;
		boolean bool=true;
		while(true){
			Scanner sc=new Scanner(System.in);
			System.out.println("input a request:");
			String str=sc.nextLine();
			long Time=System.currentTimeMillis()/100;
			String str1=str.replaceAll(" ","");
			if(str1.equals("end")){
				sc.close();
				break;
			}
			String request="\\[CR,\\(\\+?0*\\d{1,2},\\+?0*\\d{1,2}\\),\\(\\+?0*\\d{1,2},\\+?0*\\d{1,2}\\)\\]";
			Pattern pattern = Pattern.compile(request);
			Matcher matcher = pattern.matcher(str1);
			bool=matcher.matches();
			if(bool==false){
				System.out.println("invalid:"+str1);
				continue;
			}
			String str2=str1.replaceAll("\\[|\\]|\\(|\\)","");
			String [] s=str2.split(",");
			int src_x=Integer.parseInt(s[1]);
			int src_y=Integer.parseInt(s[2]);
			int dst_x=Integer.parseInt(s[3]);
			int dst_y=Integer.parseInt(s[4]);
			
			if(src_x==dst_x && src_y==dst_y){
				System.out.println("invalid:"+str1);
				continue;
			}
			if(!(range(src_x) && range(src_y) && range(dst_x) && range(dst_y))){
				System.out.println("invalid:"+str1);
				continue;
			}
			if(Time==ReqTime){
				System.out.println("same:"+str1);
				continue;
			}
			ReqTime=Time;
			//创建并启动乘客线程 
			Passenger passenger=new Passenger(src_x,src_y,dst_x,dst_y,ReqTime,taxi,matrix,output);
			gui.RequestTaxi(new Point(src_x,src_y), new Point(dst_x,dst_y));
			passenger.start();
			i++;
			System.out.println("i="+i);
		}
		}catch(Exception e){
			
		}
	}
	
	
	public boolean range(int i){
		if(i>=0 && i<=79){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
}
