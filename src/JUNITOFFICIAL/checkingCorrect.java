package JUNITOFFICIAL;


import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import NeuralNetwork.ITCF;

public class checkingCorrect 
{
	ITCF InterTransContenentalFigure = new ITCF();
	
	@Test
	public void testingCaseStructuralIntegrity()
	{
		assertEquals(4, ITCF.StructuralIntegrity(1));
		assertEquals(8, ITCF.StructuralIntegrity(2));
		assertEquals(12, ITCF.StructuralIntegrity(3));
		assertEquals(16, ITCF.StructuralIntegrity(4));
		assertEquals(20, ITCF.StructuralIntegrity(5));
		assertEquals(24, ITCF.StructuralIntegrity(6));
	}
	
	@Test 
	public void MacroEcoTestDoc12Assertion()
	{
		assertEquals(5, ITCF.MacroEcoTestDoc12(1));
		assertEquals(10, ITCF.MacroEcoTestDoc12(2));
		assertEquals(15, ITCF.MacroEcoTestDoc12(3));
		assertEquals(20, ITCF.MacroEcoTestDoc12(4));
		assertEquals(25, ITCF.MacroEcoTestDoc12(5));
		assertEquals(30, ITCF.MacroEcoTestDoc12(6));
	}
	
	@Test 
	public void CrossEntripitacalAccelerationOfParts()
	{
		assertEquals(0, ITCF.CrossEntropiticalAccelerationOfParts(1, 2, 3));
		assertEquals(0, ITCF.CrossEntropiticalAccelerationOfParts(2, 3, 5));
		assertEquals(0, ITCF.CrossEntropiticalAccelerationOfParts(18, 21, 4));
		assertEquals(0, ITCF.CrossEntropiticalAccelerationOfParts(18, 81, 44));
		assertEquals(0, ITCF.CrossEntropiticalAccelerationOfParts(8002, 40, 33));
		assertEquals(0, ITCF.CrossEntropiticalAccelerationOfParts(50, 32, 12));
	}
	

}
