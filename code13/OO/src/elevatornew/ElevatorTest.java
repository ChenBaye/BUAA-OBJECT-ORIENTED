package elevatornew;

import static org.junit.Assert.*;

import org.junit.Test;

public class ElevatorTest {
	private Elevator elevator=new Elevator();
	
	
	@Test
	public void testRepOk(){
		assertTrue(elevator.repOK()==true);
		elevator.setPosition(11);
		assertTrue(elevator.repOK()==false);
		elevator.setPosition(-1);
		assertTrue(elevator.repOK()==false);
		elevator.setStateNum(111);
		assertTrue(elevator.repOK()==false);
	}
	

	@Test
	public void testToStringStringIntDouble() {
		assertEquals(elevator.toString("UP", 9, 0.5),"(9,UP,0.5)");
		assertEquals(elevator.toString("STILL", 1, 1000.5),"(1,STILL,1000.5)");
		assertEquals(elevator.toString("DOWN", 8, 90),"(8,DOWN,90.0)");
	}

	@Test
	public void testUp() {
		assertEquals(elevator.up(),"UP");
	}

	@Test
	public void testDown() {
		assertEquals(elevator.down(),"DOWN");
	}

	@Test
	public void testStill() {
		assertEquals(elevator.still(),"STILL");
	}

	@Test
	public void testGetPosition() {
		elevator.setPosition(2);
		assertEquals(2,elevator.getPosition());
		elevator.setPosition(1);
		assertEquals(1,elevator.getPosition());
		elevator.setPosition(10);
		assertEquals(10,elevator.getPosition());
	}

	@Test
	public void testSetPosition() {
		elevator.setPosition(1);
		assertEquals(1,elevator.getPosition());
		elevator.setPosition(5);
		assertEquals(5,elevator.getPosition());
	}

	@Test
	public void testGetStateNum() {
		elevator.setStateNum(2);
		assertEquals(2,elevator.getStateNum());
		elevator.setStateNum(1);
		assertEquals(1,elevator.getStateNum());
		elevator.setStateNum(0);
		assertEquals(0,elevator.getStateNum());
	}

	@Test
	public void testSetStateNum() {
		elevator.setStateNum(2);
		assertTrue(elevator.getStateNum()==2);
		elevator.setStateNum(1);
		assertTrue(elevator.getStateNum()==1);
		elevator.setStateNum(0);
		assertTrue(elevator.getStateNum()==0);
	}

	@Test
	public void testGoTo() {
		//测试向上
		elevator.setPosition(5);
		assertTrue(elevator.GoTo(10,1.5)==1.5+(10-5)*0.5+1);
		assertTrue(elevator.getStateNum()==0);
		//测试同层
		assertTrue(elevator.GoTo(10,9.5)==9.5+1);
		assertTrue(elevator.getStateNum()==2);
		//测试向下
		assertTrue(elevator.GoTo(1,20)==20+(10-1)*0.5+1);
		assertTrue(elevator.getStateNum()==1);
	}

}
