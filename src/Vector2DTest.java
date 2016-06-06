import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Vector2DTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void zeroVectorIsZero() {
		Vector2D myVector = new Vector2D();
		assertEquals(myVector.getX(), 0, 0);
		assertEquals(myVector.getY(), 0, 0);
		assertEquals(myVector.getMagnitude(), 0, 0);
		assertEquals(myVector.getDirection(), 0, 0);
	}

	@Test
	public void vectorWithValues() {
		Vector2D myVector = new Vector2D(1, 1);
		assertEquals(myVector.getX(), 1, 0);
		assertEquals(myVector.getY(), 1, 0);
		assertEquals(myVector.getMagnitude(), Math.sqrt(2), 0);
		assertEquals(myVector.getDirection(), Math.PI / 4, 0);
	}

	@Test
	public void randomVector() {
		Vector2D myVector = new Vector2D(10);
		
		if (myVector.getMagnitude() > 10) {
			fail("magnitude bigger than maximum length");
			System.out.println("Magnitude: " + myVector.getMagnitude());
		}
	}

	@Test
	public void addingVectors() {
		Vector2D vector1 = new Vector2D(1, 1);
		Vector2D vector2 = new Vector2D(-1, 1);
		Vector2D vector3 = vector1.addTwoVectors(vector1, vector2);
		assertEquals(vector3.getX(), 0, 0);
		assertEquals(vector3.getY(), 2, 0);

		vector1.add(vector2);
		assertEquals(vector1.getX(), 0, 0);
		assertEquals(vector1.getY(), 2, 0);
	}

	@Test
	public void test4() {
		Vector2D myVector = new Vector2D();
		fail("Not yet implemented");
	}

	@Test
	public void test5() {
		Vector2D myVector = new Vector2D();
		fail("Not yet implemented");
	}

	@Test
	public void test6() {
		Vector2D myVector = new Vector2D();
		fail("Not yet implemented");
	}
}
