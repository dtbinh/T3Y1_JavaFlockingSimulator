import java.lang.Math;
import java.util.Random;

public class Vector2D
{
	private double x;
	private double y;

    public static void main(String[] args)	    //For testing only      /TODO put in a separate test class
	{
		Vector2D myTest = new Vector2D(3,3);
		Vector2D myTest1 = new Vector2D(0 ,3);

		System.out.println("Test0: length " + myTest.getLength() + " angle: " + myTest.getAngle());
		System.out.println("Test1: length " + myTest1.getLength() + " angle: " + myTest1.getAngle());

		myTest.add(myTest1);
		System.out.println("length after adding V0 to V1 " + myTest.getLength());
		System.out.println("angle after adding V0 to V1 " + myTest.getAngle());

		myTest.mult(2);
		System.out.println("length after multiplying V0 by 2 " + myTest.getLength());
		System.out.println("angle after multiplying V0 by 2 " + myTest.getAngle());
        myTest.div(2);
        System.out.println("length after dividing V0 by 2 " + myTest.getLength());
        System.out.println("angle after dividing V0 by 2 " + myTest.getAngle());

        Vector2D myTest2 = new Vector2D(3, 0);
        System.out.println("Test2: length: " + myTest2.getLength() + " and angle: " + myTest2.getAngle());

        Vector2D test3 = new Vector2D(1, 0);
        Vector2D test4 = new Vector2D(0, -1);
        Vector2D test5 = new Vector2D(-1, 0);
        Vector2D test6  = new Vector2D(0, 1);
        System.out.println("Angle of V3-6: " + " " + test3.getAngle() + " " + test4.getAngle() + " " + test5.getAngle() + " " + test6.getAngle());
	}

    public Vector2D()
    {
        this.x  = 0;
        this.y = 0;
    }

    public Vector2D(double vX, double vY)
	{
		this.x = vX;
		this.y = vY;
	}

    public Vector2D(double max_component)
    {
        Random randomSeed = new Random();
        this.x  = (int) (randomSeed.nextInt(2 * (int) max_component) - max_component);
        this.y = (int) (randomSeed.nextInt(2 * (int) max_component) - max_component);
    }

    public Vector2D(Coordinate coordinate1, Coordinate coordinate2)
    {
        this.x = coordinate2.getxCoord() - coordinate1.getxCoord();
        this.y = coordinate2.getyCoord() - coordinate1.getyCoord();
    }

    public void add(Vector2D two)
    {
        this.x += two.getX();
        this.y += two.getY();
    }

	public void mult(double factor)
	{
		this.x *= factor;
		this.y *= factor;
	}

    public void div(double factor)
    {
        this.x /= factor;
        this.y /= factor;
    }

    public void inv()
    {
        this.x *= -1;
        this.y *= -1;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getLength()
    {
        return Math.hypot(this.x, this.y);
    }

    public double getAngle()
    {
        return (Math.atan2(this.y, this.x));
    }
}