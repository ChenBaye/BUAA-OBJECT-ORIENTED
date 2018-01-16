package elevatornew;

import static org.junit.Assert.*;

import org.junit.Test;

public class DespatcherTest {
	Despatcher des=new Despatcher();
	@Test
	public void testPlan() {
		int [] floor0={1,5,5,10,5,1};
		double [] time0={0,0.5,1.5,3,4,5};
		int [] FrOrEr0={1,0,0,1,0,1};
		int num0=6;
		assertTrue(des.Plan(floor0, time0, num0, FrOrEr0, FrOrEr0)==13.0);
		
		des=new Despatcher();
		int [] floor1={10,5,5,1};
		double [] time1={0,3,4,6};
		int [] FrOrEr1={1,0,0,1};
		int num1=4;
		assertTrue(des.Plan(floor1, time1, num1, FrOrEr1, FrOrEr1)==12.0);
		
		des=new Despatcher();
		int [] floor2={10,1};
		double [] time2={0,100000};
		int [] FrOrEr2={1,0};
		int num2=2;
		assertTrue(des.Plan(floor2, time2, num2, FrOrEr2, FrOrEr2)==100005.5);
		
	}

}
