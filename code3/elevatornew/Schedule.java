package elevatornew;
//Schedule继承自上一次的调度类Despatcher类
class Schedule extends Despatcher{
	
	private double NowTime=0;
	private int i=0;
	private int j=0;
	private int [] v=new int[100000];
	private String state;
	
	// override Plan()
	void Plan(int []floor,double []time,int num,int []FrOrEr,int [] UpOrDown){
		int k=0;
		int m=0;
		int n=0;
		int point=0;
		int position=1;
		int take=0;
		int request=0;
		Elevator Elevator = new Elevator();			//调用电梯类
		
		for(n=0;n<num;n++){
			if(take==1){
				i=request;
				n--;
			}
			else{
				i=n;
			}
			if(v[i]==1){
				continue;
			}
			else{
				
				
				
				
				
				if(position==floor[i]){					//STILL
					state=Elevator.still();
					OutputRequest(i,floor,time,FrOrEr,UpOrDown);
					
					NowTime=NowTime>time[i]?NowTime:time[i];
		
					System.out.println("/"+Elevator.toString(state, floor[i], NowTime+1));
					NowTime++;
					v[i]=1;
					for(j=0;j<num;j++){
						if(v[j]!=1 && time[j]<=NowTime){
							if( FrOrEr[i]==FrOrEr[j] && 
					            UpOrDown[i]==UpOrDown[j] && 
					            floor[i]==floor[j]){
								v[j]=1;
								OutputRequest(j,floor,time,FrOrEr,UpOrDown);
								System.out.println("/same request");
							}
						}
					}
				}
				
				
				
				
				
				else if(position<floor[i]){								//UP
					state=Elevator.up();
					position++;	
					NowTime=NowTime>time[i]?NowTime:time[i];			//校准命令执行开始时间
					NowTime+=0.5;
					for(;position<=floor[i];position++,NowTime+=0.5){   //层层运行
						point=0;
						if(position==floor[i]){							//到达目的地
							OutputRequest(i,floor,time,FrOrEr,UpOrDown);
							System.out.println("/"+Elevator.toString(state, floor[i], NowTime));
							v[i]=1;
							point=1;
							
							
							for(k=0;k<num;k++){							//删去与主命令重复的命令
								if(v[k]!=1 && time[k]<=NowTime+1){
									if( FrOrEr[k]==FrOrEr[i] && 
							            UpOrDown[k]==UpOrDown[i] && 
							            floor[k]==floor[i]){
										v[k]=1;
										OutputRequest(k,floor,time,FrOrEr,UpOrDown);
										System.out.println("/same request");
									}
								}
							}
						}
						for(j=0;j<num;j++){								//遍历寻找本层捎带命令
							if(v[j]==1){
								continue;
							}
							else{
								if(floor[j]==position && NowTime>time[j] && FrOrEr[j]==1 && UpOrDown[j]==1 ||   //找到捎带命令                   
								   floor[j]==position && NowTime>time[j] && FrOrEr[j]==0){
									OutputRequest(j,floor,time,FrOrEr,UpOrDown);
									System.out.println("/"+Elevator.toString(state, floor[j], NowTime));
									v[j]=1;
									point=1;
									
									
									for(k=0;k<num;k++){							//删去与捎带命令重复的命令
										if(v[k]!=1 && time[k]<=NowTime+1){
											if( FrOrEr[k]==FrOrEr[j] && 
									            UpOrDown[k]==UpOrDown[j] && 
									            floor[k]==floor[j]){
												v[k]=1;
												OutputRequest(k,floor,time,FrOrEr,UpOrDown);
												System.out.println("/same request");
											}
										}
									}
								}
							}
						}
						if(point==1){
							NowTime++;
						}
					}
					position--;
					NowTime-=0.5;//寻找未完成捎带命令
					take=0;
					for(m=0;m<num;m++){
						if(v[m]!=1 && FrOrEr[m]==0 && time[m]<NowTime-1 && floor[m]>position){//主请求到达前已发出
							take=1;
							request=m;						
						}
							
					}
					
				}
				
				
				
				
				
				
				else if(position>floor[i]){								//DOWN
					state=Elevator.down();
					position--;	
					NowTime=NowTime>time[i]?NowTime:time[i];			//校准命令执行开始时间
					NowTime+=0.5;
					for(;position>=floor[i];position--,NowTime+=0.5){   //层层运行
						point=0;
						if(position==floor[i]){							//到达目的地
							OutputRequest(i,floor,time,FrOrEr,UpOrDown);
							System.out.println("/"+Elevator.toString(state, floor[i], NowTime));
							v[i]=1;
							point=1;
							
							
							for(k=0;k<num;k++){							//删去与主命令重复的命令
								if(v[k]!=1 && time[k]<=NowTime+1){
									if( FrOrEr[k]==FrOrEr[i] && 
							            UpOrDown[k]==UpOrDown[i] && 
							            floor[k]==floor[i]){
										v[k]=1;
										OutputRequest(k,floor,time,FrOrEr,UpOrDown);
										System.out.println("/same request");
									}
								}
							}
						}
						for(j=0;j<num;j++){								//遍历寻找本层捎带命令
							if(v[j]==1){
								continue;
							}
							else{
								if(floor[j]==position && NowTime>time[j] && FrOrEr[j]==1 && UpOrDown[j]==0 ||   //找到捎带命令                   
								   floor[j]==position && NowTime>time[j] && FrOrEr[j]==0){
									OutputRequest(j,floor,time,FrOrEr,UpOrDown);
									System.out.println("/"+Elevator.toString(state, floor[j], NowTime));
									v[j]=1;
									point=1;
									
									
									for(k=0;k<num;k++){							//删去与捎带命令重复的命令
										if(v[k]!=1 && time[k]<=NowTime+1){
											if( FrOrEr[k]==FrOrEr[j] && 
									            UpOrDown[k]==UpOrDown[j] && 
									            floor[k]==floor[j]){
												v[k]=1;
												OutputRequest(k,floor,time,FrOrEr,UpOrDown);
												System.out.println("/same request");
											}
										}
									}
								}
							}
						}
						if(point==1){
							NowTime++;
						}
					}
					position++;
					NowTime-=0.5;
					take=0;
					for(m=0;m<num;m++){
						if(v[m]!=1 && FrOrEr[m]==0 && time[m]<NowTime-1 && floor[m]<position){//主请求到达前已发出
							take=1;
							request=m;						
						}
							
					}
					
				}
			}
		
		}
		
	}
	
	void OutputRequest(int i,int []floor,double []time,int []FrOrEr,int [] UpOrDown){
		if(FrOrEr[i]==1 && UpOrDown[i]==1){
			System.out.print("[FR,"+floor[i]+",UP,"+(int)time[i]+"]");
		}
		else if(FrOrEr[i]==1 && UpOrDown[i]==0){
			System.out.print("[FR,"+floor[i]+",DOWN,"+(int)time[i]+"]");
		}
		else if(FrOrEr[i]==0){
			System.out.print("[ER,"+floor[i]+","+(int)time[i]+"]");
		}
	}
	
	
	
}
