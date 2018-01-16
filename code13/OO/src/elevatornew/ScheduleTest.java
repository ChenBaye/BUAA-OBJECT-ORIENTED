package elevatornew;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScheduleTest {

	Schedule des=new Schedule();
	@Test
	public void testPlan() {
		int [] floor0={1,5,5,10,5,1};
		double [] time0={0,0.5,1.5,3,4,5};
		int [] FrOrEr0={1,0,0,1,0,1};
		int num0=6;
		assertTrue(des.Plan(floor0, time0, num0, FrOrEr0, FrOrEr0)==13.0);
		
		des=new Schedule();
		int [] floor1={10,5,5,1};
		double [] time1={0,3,4,6};
		int [] FrOrEr1={1,0,0,1};
		int num1=4;
		assertTrue(des.Plan(floor1, time1, num1, FrOrEr1, FrOrEr1)==12.0);
		
		des=new Schedule();
		int [] floor2={10,1};
		double [] time2={0,100000};
		int [] FrOrEr2={1,0};
		int num2=2;
		assertTrue(des.Plan(floor2, time2, num2, FrOrEr2, FrOrEr2)==100005.5);
		
		
		
		
		des=new Schedule();
		int [] floor3={10,2,2,7,7,6,6,1,1,10,10,9,9,7,7};
		double [] time3={0,100000,100000,100000,100000,100000,100000,100000,100000,1000000,1000000,1000000,1000000,1000000,1000000};
		int [] FrOrEr3={1,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int num3=15;
		
		assertTrue(des.Plan(floor3, time3, num3, FrOrEr3, FrOrEr3)==1000007.5);
		
		
		
		
		des=new Schedule();
		int [] floor4={1,1};
		double [] time4={0,0};
		int [] FrOrEr4={1,1};
		int num4=2;
		assertTrue(des.Plan(floor4, time4, num4, FrOrEr4, FrOrEr4)==1);
		
		
		des=new Schedule();
		int [] floor5={5,10};
		double [] time5={0,1};
		int [] FrOrEr5={1,0};
		int num5=2;
		assertTrue(des.Plan(floor5, time5, num5, FrOrEr5, FrOrEr5)==6.5);
	}

	@Test
	public void testRepOK() {
		assertTrue(des.repOK()==true);
	}

	@Test
	public void testOutputRequest() {
		int []floor={1,3,2};
		double []time={0,0.5,0.5};
		int []FrOrEr={1,0,1};
		int []UpOrDown={1,0,0};
		des.OutputRequest(0, floor, time, FrOrEr, UpOrDown);
		des.OutputRequest(1, floor, time, FrOrEr, UpOrDown);
		des.OutputRequest(2, floor, time, FrOrEr, UpOrDown);
		//控制台应该输出[FR,1,UP,0][ER,3,0][FR,2,DOWN,0]
		
	}

}
