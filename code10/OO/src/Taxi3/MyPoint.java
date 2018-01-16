package Taxi3;

public class MyPoint {
	//表示对象:int x,int y
	//
	//抽象函数：AF(c)=(x,y)where x==c.x && y==c.y
	//
	//不变式:0<=c.x<=79,0<=c.y<=79
	int x;
	int y;
	public boolean repOK(){
		/*@Effects: \result==invariant(this).
		*/
		if(x<0 || x>79){
			return false;
		}
		if(y<0 || y>79){
			return false;
		}
		return true;
	}
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
