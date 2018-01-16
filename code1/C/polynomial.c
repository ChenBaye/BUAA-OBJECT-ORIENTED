#include<stdio.h> 
#include<string.h>



int polyConstant[2000000]={0};
char str[999999];
char str1[999999];
int main(void){
	int i=0;
	int j=0;
	int len=0;
	printf("请输入表达式：");
	gets(str);
	len=strlen(str);
	for(i=0;i<len;i++){
		if(str[i]!=' '){
			str1[j]=str[i];
			j++;
		}
	}
	len=strlen(str1);
	
	i=0;
	j=0; 
	int zhengfu=1;//1~+,-1~-
	int jiajian=1;//1~+,-1~-
	int inbig=0;
	int insmall=0;
	int left=1;
	int temp_con=0;
	int temp_deg=0;
	for(i=0;i<len;i++){
		if(i==0){
			jiajian=str1[i]=='-'?-1:1;
		}
		if(inbig==0 && i<len && str1[i+1]=='{'){
			jiajian=str1[i]=='-'?-1:1;
		}	
		if(str1[i]==',' && left==1 && insmall==1){
			left=0;
		}
		switch(str1[i]){
			case '{':inbig=1;	break;
			case '}':inbig=0;	break;
			case '(':
				insmall=1;	
				left=1;	
				zhengfu=str1[i+1]=='-'?-1:1;
				break;
			case ')':
				insmall=0;	
				polyConstant[temp_deg]+=temp_con*zhengfu*jiajian;
				temp_con=0;	
				temp_deg=0;	
				break;
		}		
		if(isdigit(str1[i])==1 && inbig==1 && insmall==1){
			if(left==1){
				temp_con=temp_con*10+str1[i]-'0';
			}
			if(left==0){
				temp_deg=temp_deg*10+str1[i]-'0';
			}
		}	
	}
	printf("{");
	i=0;
	for(j=0;j<2000000;j++){
			if(polyConstant[j]!=0){
				if(i!=0){
					printf(",");
				}
				i++;
				printf("(%d,%d)",polyConstant[j],j);
			}
		}
		printf("}");

	return 0 ; 
	
	
	
	
}
