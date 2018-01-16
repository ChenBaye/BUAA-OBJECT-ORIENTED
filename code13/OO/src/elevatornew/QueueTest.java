package elevatornew;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueueTest {
	Queue queue=new Queue();
	
	@Test
	public void testRepOK() {
		assertTrue(queue.repOK()==true);
	}

	@Test
	public void testCreateQueue() {
		queue.CreateQueue("(FR,1,UP,0)");
		assertTrue(queue.getFrOrEr()[0]==1);
		assertTrue(queue.getFloorQueue()[0]==1);
		assertTrue(queue.getTime()[0]==0);
		assertTrue(queue.getNum()==1);
		assertTrue(queue.getFrOrEr()[0]==1);
		queue.CreateQueue("(FR,8,UP,0)");
		assertTrue(queue.getFrOrEr()[1]==1);
		assertTrue(queue.getFloorQueue()[1]==8);
		assertTrue(queue.getTime()[1]==0);
		assertTrue(queue.getNum()==2);
		assertTrue(queue.getFrOrEr()[1]==1);
		queue.CreateQueue("(FR,8,DOWN,10)");
		assertTrue(queue.getFrOrEr()[2]==1);
		assertTrue(queue.getFloorQueue()[2]==8);
		assertTrue(queue.getTime()[2]==10);
		assertTrue(queue.getNum()==3);
		assertTrue(queue.getFrOrEr()[2]==1);
		queue.CreateQueue("(ER,8,100)");
		assertTrue(queue.getFrOrEr()[3]==0);
		assertTrue(queue.getFloorQueue()[3]==8);
		assertTrue(queue.getTime()[3]==100);
		assertTrue(queue.getNum()==4);
		assertTrue(queue.getFrOrEr()[3]==0);
	}

	@Test
	public void testGetFloorQueue() {
		queue=new Queue();
		queue.CreateQueue("(FR,1,UP,0)");
		assertTrue(queue.getFloorQueue()[0]==1);
		queue.CreateQueue("(FR,8,UP,0)");
		assertTrue(queue.getFloorQueue()[1]==8);
	}

	@Test
	public void testGetTime() {
		queue=new Queue();
		queue.CreateQueue("(FR,1,UP,0)");
		assertTrue(queue.getTime()[0]==0);
		queue.CreateQueue("(FR,8,UP,10)");
		assertTrue(queue.getTime()[1]==10);
	}

	@Test
	public void testGetNum() {
		queue=new Queue();
		queue.CreateQueue("(FR,1,UP,1)");
		queue.CreateQueue("(FR,1,UP,0)");
		assertTrue(queue.getNum()==1);
		queue.CreateQueue("(FR,4,UP,100)");
		assertTrue(queue.getNum()==2);
	}

	@Test
	public void testGetFrOrEr() {
		queue=new Queue();
		queue.CreateQueue("(FR,1,UP,0)");
		assertTrue(queue.getFrOrEr()[0]==1);
		queue.CreateQueue("(ER,10,0100)");
		assertTrue(queue.getFrOrEr()[1]==0);
		queue.CreateQueue("(ER,10,0100)");
		assertTrue(queue.getFrOrEr()[2]==0);
	}

	@Test
	public void testGetUpOrDown() {
		queue=new Queue();
		queue.CreateQueue("(FR,1,UP,0)");
		assertTrue(queue.getUpOrDown()[0]==1);
		queue.CreateQueue("(FR,10,DOWN,0)");
		assertTrue(queue.getUpOrDown()[1]==0);
	}

	@Test
	public void testTimeSequence() {
		String []s0={"FR","1","UP","2"};
		double []time={0,0.5,7};
		int num=3;
		assertTrue(queue.TimeSequence(s0, time, num, "(FR,1,UP,2)")==false);
		
		String []s1={"FR","1","UP","10"};
		assertTrue(queue.TimeSequence(s1, time, num, "(FR,1,UP,10)")==true);
		
		String []s2={"ER","1","10"};
		assertTrue(queue.TimeSequence(s2, time, num, "(ER,1,10)")==true);
		
		String []s3={"ER","1","0"};
		assertTrue(queue.TimeSequence(s3, time, num, "(ER,1,0)")==false);
		
	
	}

	@Test
	public void testFirstRequest() {
		assertTrue(queue.FirstRequest("(FR,1,UP,0)")==true);
		assertTrue(queue.FirstRequest("(FR,5,UP,0)")==false);
	}

	@Test
	public void testTenUpOneDown() {
		Floor floor=new Floor();
		String []s0={"FR","1","UP","0"};
		assertTrue(queue.TenUpOneDown(s0,floor,"(FR,1,UP,0)")==true);
		
		String []s1={"FR","10","UP","0"};
		assertTrue(queue.TenUpOneDown(s1,floor,"(FR,10,UP,0)")==false);
		
		String []s2={"FR","1","DOWN","0"};
		assertTrue(queue.TenUpOneDown(s2,floor,"(FR,1,DOWN,0)")==false);
	}

	@Test
	public void testNumberRange() {
		Floor floor=new Floor();
		String []s0={"FR","1","UP","0"};
		assertTrue(queue.NumberRange(s0,floor,"(FR,1,UP,0)")==true);
		
		String []s1={"ER","10","0"};
		assertTrue(queue.NumberRange(s1,floor,"(ER,10,0)")==true);
		
		String []s2={"FR","11","UP","0"};
		assertTrue(queue.NumberRange(s2,floor,"(FR,11,UP,0)")==false);
		
		String []s3={"ER","11","0"};
		assertTrue(queue.NumberRange(s3,floor,"(ER,11,0)")==false);
		
		String []s4={"FR","0","UP","0"};
		assertTrue(queue.NumberRange(s4,floor,"(FR,0,UP,0)")==false);
		
		String []s5={"ER","0","0"};
		assertTrue(queue.NumberRange(s5,floor,"(ER,0,0)")==false);
		
		String []s6={"FR","1","DOWN","-1"};
		assertTrue(queue.NumberRange(s6,floor,"(ER,1,DOWN,-1)")==false);
		
		String []s7={"ER","1","-1"};
		assertTrue(queue.NumberRange(s7,floor,"(ER,1,-1)")==false);
		
		String []s8={"FR","1","DOWN","99999999999"};
		assertTrue(queue.NumberRange(s8,floor,"(ER,1,DOWN,99999999999)")==false);
		
		String []s9={"ER","1","99999999999"};
		assertTrue(queue.NumberRange(s9,floor,"(ER,1,99999999999)")==false);
		
		
	}

}
