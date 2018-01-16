package elevator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Request {//请求类
	public static void main(String[] args){
		try{
		String str1 = "a";
		String str2;
		int i=0;
		Scanner sc=new Scanner(System.in);
		System.out.println("input requests:");
		String frstring="\\(FR,([1-9]|(10)),((UP)|(DOWN)),\\d{1,8}\\)";			//楼层请求
		String erstring="\\(ER,([1-9]|(10)),\\d{1,8}\\)";						//电梯请求
		Pattern frPattern = Pattern.compile(frstring);
		Pattern erPattern = Pattern.compile(erstring);
		
		Queue Queue=new Queue();
		Despatcher Despatcher=new Despatcher();
		
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
				System.out.println("无效输入！");
				}
			}
		}
		sc.close();
		Despatcher.Plan(Queue.getFloor(), Queue.getTime(), Queue.getNum(), Queue.getFrOrEr(), Queue.getUpOrDown());//调度器
		} catch (Exception e) {
			System.out.println("输入错误");
		}
	}
}
