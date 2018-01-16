package Taxi4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class CrazyTaxi extends Taxi {
			//表示对象:list,iterator,Matrix matrix;MyPoint point;int index;int status;int credit;int count;int direction;OutPut output;LinkedList <String> StrList=new LinkedList<String>();TaxiGUI gui;Light light;LinkedList<MyPoint> way1=new LinkedList<MyPoint>();LinkedList<MyPoint> way2=new LinkedList<MyPoint>();
			//
			//抽象函数：AF(c)=AF_Taxi(c)
			//
			//不变式:I_sub(c) implies I_super(c)
	LinkedList<ServiceMessage> list = new LinkedList<ServiceMessage>();
	ListIterator<ServiceMessage> iterator = list.listIterator();

	public boolean repOK(){
		/*@Effects: \result==invariant(this).
		*/
		if(matrix==null || output==null || gui==null || light==null){
			return false;
		}
		if(!(index>=0 && index<=99)){
			return false;
		}
		return true;
	}
	
	
	public CrazyTaxi(Matrix matrix, int index, OutPut output, TaxiGUI gui, Light light) {
		/*@REQUIRES:matrix!=null,0<=index<=99,output!=null,gui!=null
		 @
		 @
		 @MODIFIES:this.matrix,this.index,this.gui,this.output
		 @
		 @EFFECTS:这是CrazyTaxi的构造方法，为CrazyTaxi类中的matrix,index,gui,output赋值
		 @
		 */
		super(matrix, index, output, gui, light);
	}

	public void Message(String s, LinkedList<MyPoint> way1, LinkedList<MyPoint> way2) {
		/*@REQUIRES:s!=null,way1!=null,way2!=null
		 @
		 @
		 @MODIFIES:this
		 @
		 @EFFECTS:记录服务信息
		 @
		 */
		ServiceMessage ser=new ServiceMessage();
		ser.Service(s, way1, way2);
		list.add(ser);
	}

	public void iterator_next() {
		/*@REQUIRES:iterator!=null
		 @
		 @
		 @MODIFIES:this,ServiceMessage.txt
		 @
		 @EFFECTS:迭代器输出next信息
		 @
		 */
		if (iterator.hasNext()) {
			ServiceMessage se=iterator.next();
			output.WriteFile(se);
		} else {
			System.out.println("no next");
		}
	}

	public void iterator_pre() {
		/*@REQUIRES:iterator!=null
		 @
		 @
		 @MODIFIES:this,ServiceMessage.txt
		 @
		 @EFFECTS:迭代器输出previous信息
		 @
		 */
		if (iterator.hasPrevious()) {
			ServiceMessage se=iterator.previous();
			output.WriteFile(se);
		} else {
			System.out.println("no previous");
		}
	}



}
