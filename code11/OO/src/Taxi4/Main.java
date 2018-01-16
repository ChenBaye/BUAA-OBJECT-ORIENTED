package Taxi4;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
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
	
	
	public static void main(String [] args){
		/* @REQUIRES:无
		 @MODIFIES:无
		 @
		 @EFFECTS:这是程序的主入口，用于申明各种类和开启线程
		 @
		*/
		try{
		TaxiGUI gui=new TaxiGUI();
		OutPut output=new OutPut();
		InputMessage InputMessage=new InputMessage();
		Matrix matrix1=new Matrix();
		matrix1.CreateMatrix();
		matrix1.CreateAdjacency();
		gui.LoadMap(matrix1.map, 80);
		Light light=new Light(gui);
		
		Matrix matrix2=new Matrix();
		matrix2.CreateMatrix();
		matrix2.CreateAdjacency();
		Taxi taxi[]=init_taxi(matrix1,matrix2,output,gui,light);
		
	
		for(int j=0;j<100;j++){
			taxi[j].start();
		}
		
		light.start();
		//实时获得出租车i信息
		//及实时获得状态为status的出租车
		//TaxiMessage taximessage=new TaxiMessage(taxi,i,status);
		//taximessage.start();
		
		
		InputMessage.Input(taxi,matrix1,output,gui);
		}catch(Exception e){
			System.out.println("1");
		}
	}
		
	
	public static  Taxi[] init_taxi(Matrix matrix1,Matrix matrix2,OutPut output,TaxiGUI gui,Light light){
		/* @REQUIRES:matrix1!=null,matrix2!=null,output!=null,gui!=null,light!=null;
		 @MODIFIES:无
		 @
		 @EFFECTS:初始化出租车
		 @
		*/
		
		//普通出租车使用matrix1，特殊出租车使用matrix2,以下代码为样例
		
		Taxi[] taxi=new Taxi[100];
		for(int i=0;i<100;i++){
			if(i<70){
				taxi[i]=new Taxi(matrix1,i,output,gui,light);
				//普通出租车
				gui.SetTaxiType(i,0);
			}
			else{
				taxi[i]=new CrazyTaxi(matrix2,i,output,gui,light);
				//特殊出租车
				gui.SetTaxiType(i,1);
			}
		}
		return taxi;
		
	}
	
	
	
}
