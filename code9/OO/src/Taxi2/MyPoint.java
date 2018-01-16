package Taxi2;

public class MyPoint {
	int x;
	int y;
	public MyPoint(int x,int y){
		/*@REQUIRES:0<=x<=79,0<=y<=79
		 @
		 @
		 @MODIFIES:this.x,this.y
		 @
		 @EFFECTS:这是MyPoint的构造方法，为MyPoint的x和y赋值
		 @
		 */
		this.x=x;
		this.y=y;
	}
}
