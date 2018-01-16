package polynomial;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComputePoly {
	private  int[] polyConstant=new int[1000000];

	
	public static void main(String[] args) {
		ComputePoly CP=new ComputePoly();
		int i=0;
		int k=0;
		Scanner sc=new Scanner(System.in);
		String str = null;
	    System.out.println("请输入表达式：");
	    str = sc.nextLine();
	    
	    String str1 = str.replaceAll(" ","");//去空格
	    boolean bool =true;
	    String str2 ="\\([\\+,-]?\\d{1,6},[\\+]?\\d{1,6}\\)";
	    String str3 ="(,"+str2+")";
	    String str4 ="[\\+,-]?\\{"+str2+str3+"{0,49}";
	    String str5 ="[\\+,-]\\{"+str2+str3+"{0,49}";
	    String [] s=str1.split("}");
	    Pattern mPattern = Pattern.compile(str4);
	    Matcher mMatcher = mPattern.matcher(s[0]);
	    bool=bool && mMatcher.matches();
	    for(i=1;i<s.length;i++){
	    	mPattern = Pattern.compile(str5);
	    	mMatcher = mPattern.matcher(s[i]);
	    	bool=bool &&  mMatcher.matches();
	    }
	    		
	    		
	   
	    if(i>19 || bool==false){
	    	System.out.println("输入不符合规范");
	    }    
	    else{
	    	CP.Calculate(str1);
	    	CP.PrintRes(CP.polyConstant);
	    }    
	       sc.close();
	}
	
	private void Calculate(String str1){
		int i=0;
		int j=0;
		int zhengfu=1;//1~+,-1~-
		int jiajian=1;//1~+,-1~-
		int inbig=0;
		int insmall=0;
		int left=1;
		int temp_con=0;
		int temp_deg=0;
		for(i=0;i<str1.length();i++){
			if(i==0){
				jiajian=str1.charAt(i)=='-'?-1:1;
			}
			if(inbig==0 && i<str1.length()-1 &&  str1.charAt(i+1)=='{'){
				jiajian=str1.charAt(i)=='-'?-1:1;
			}	
			if(str1.charAt(i)==',' && left==1 && insmall==1){
				left=0;
			}
			switch(str1.charAt(i)){
				case '{':inbig=1;	break;
				case '}':inbig=0;	break;
				case '(':
					insmall=1;	
					left=1;	
					zhengfu=str1.charAt(i+1)=='-'?-1:1;
					break;
				case ')':
					insmall=0;	
					if(temp_deg<=999999 && temp_con<=999999){
					polyConstant[(int)temp_deg]+=(int)(temp_con*zhengfu*jiajian);
					}
					temp_con=0;	
					temp_deg=0;	
					break;
			}		
			if(Character.isDigit(str1.charAt(i))==true && inbig==1 && insmall==1){
				if(left==1){
					temp_con=temp_con*10+str1.charAt(i)-'0';
				}
				if(left==0){
					temp_deg=temp_deg*10+str1.charAt(i)-'0';
				}
			}	
		}
	}
	
	
 	/*private boolean GramCheck(String str1){
		String str2 ="\\([\\+,-]?\\d{1,6},[\\+]?\\d{1,6}\\)";
	    String str3 ="(,"+str2+")";
	    String str4 ="[\\+,-]?\\{"+str2+str3+"{0,49}\\}";
	    String str5 =str4+"([\\+,-]\\{"+str2+str3+"{0,49}\\}){0,19}";
	       
	    Pattern mPattern = Pattern.compile(str5);
	    Matcher mMatcher = mPattern.matcher(str1);
	    boolean bool=mMatcher.matches();
	    return bool;
	}*/


	private void PrintRes(int [] polyConstant){
		int j=0;
		int i=0;
		System.out.print("{");
		for(j=0;j<polyConstant.length;j++){
			if(polyConstant[j]!=0){
				if(i!=0){
					System.out.print(",");
				}
				i++;
				System.out.print("("+polyConstant[j]+","+j+")");
			}
		}
		System.out.print("}");
	}
}

