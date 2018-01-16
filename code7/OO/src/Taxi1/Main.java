package Taxi1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
	public static void main(String [] args){
		try{
		TaxiGUI gui=new TaxiGUI();
		OutPut output=new OutPut();
		Request request=new Request();
		Taxi taxi[]=new Taxi[100];
		Matrix matrix=new Matrix();
		matrix.CreateMatrix();
		matrix.CreateAdjacency();
		for(int i=0;i<100;i++){
			taxi[i]=new Taxi(matrix,i,output,gui);
		}
		for(int j=0;j<100;j++){
			taxi[j].start();
		}
		
		gui.LoadMap(matrix.map, 80);
		
		//实时获得出租车i信息
		//及实时获得状态为status的出租车
		//TaxiMessage taximessage=new TaxiMessage(taxi,i,status);
		//taximessage.start();
		
		
		request.InputRequest(taxi,matrix,output,gui);
		}catch(Exception e){
			System.out.println("");
		}
	}
		
		
	
	
	
	
	
	
}
