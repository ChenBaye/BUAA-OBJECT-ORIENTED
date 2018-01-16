package Taxi4;

import java.util.LinkedList;

public class ServiceMessage {
		//表示对象:request
		//
		//抽象函数：AF(c)=()
		//
		//不变式:none
	LinkedList<String> request=new LinkedList<String>();
	
	public boolean repOK(){
		/*@Effects: \result==invariant(this).
		*/
		return true;
	}
	
	public void Service(String s,LinkedList<MyPoint> way1,LinkedList<MyPoint> way2){
		/*@REQUIRES:s=null,way1!=null,way2!=null
		 @
		 @
		 @MODIFIES:this
		 @
		 @EFFECTS:接收并储存特殊出租车的服务信息
		 @
		 */
		this.request.add(s);
		request.add("出发于:"+"("+way1.get(0).x+","+way1.get(0).y+")");
		for(int i=1;i<way1.size();i++){
			request.add("("+way1.get(i).x+","+way1.get(i).y+")");
		}
		for(int j=0;j<way2.size();j++){
			request.add("("+way2.get(j).x+","+way2.get(j).y+")");
		}
	}
}
