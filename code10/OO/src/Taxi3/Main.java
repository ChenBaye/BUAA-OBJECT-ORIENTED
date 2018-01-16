package Taxi3;

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
		Taxi taxi[]=new Taxi[100];
		Matrix matrix=new Matrix();
		matrix.CreateMatrix();
		matrix.CreateAdjacency();
		gui.LoadMap(matrix.map, 80);
		Light light=new Light(gui);
		for(int i=0;i<100;i++){
			taxi[i]=new Taxi(matrix,i,output,gui,light);
		}
		for(int j=0;j<100;j++){
			taxi[j].start();
		}
		
		light.start();
		//实时获得出租车i信息
		//及实时获得状态为status的出租车
		//TaxiMessage taximessage=new TaxiMessage(taxi,i,status);
		//taximessage.start();
		
		
		InputMessage.Input(taxi,matrix,output,gui);
		}catch(Exception e){
			System.out.println("");
		}
	}
		
	
}
