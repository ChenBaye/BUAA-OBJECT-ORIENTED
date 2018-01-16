package elevatornew;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {//主类
	public static void main(String[] args){
		try{
		String str1 = "a";
		String str2;
		int i=0;
		Scanner sc=new Scanner(System.in);
		System.out.println("input requests:");
		String frstring="\\(FR,\\+?\\d+,((UP)|(DOWN)),\\+?\\d+\\)";			//楼层请求
		String erstring="\\(ER,\\+?\\d+,\\+?\\d+\\)";						//电梯请求
		Pattern frPattern = Pattern.compile(frstring);
		Pattern erPattern = Pattern.compile(erstring);
		
		Queue Queue=new Queue();
		Schedule scheduler=new Schedule();
		
		while(i<10000){
			str1 = sc.nextLine();											//键入值
			i++;
			str2 = str1.replaceAll(" ","");									//去空格
			Matcher frMatcher = frPattern.matcher(str2);
			Matcher erMatcher = erPattern.matcher(str2);
			if(frMatcher.matches()){
				Queue.CreateQueue(str2);                               //构造队列
			}
			else if(erMatcher.matches()){
				Queue.CreateQueue(str2);
			}
			else{
				if(str2.equals("run")){
					break;
				}
				else{
				System.out.println("invalid input:"+str2);
				}
			}
		}
		sc.close();
		scheduler.Plan(Queue.getFloorQueue(), Queue.getTime(), Queue.getNum(), Queue.getFrOrEr(), Queue.getUpOrDown());//调度器
		} catch (Exception e) {
			System.out.println("invalid input");
		}
	}
}
