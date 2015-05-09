
public class Coordinate
{
	private double xCoord;
	private double yCoord;

	public static void main(String [] args)
	{
		Coordinate myCoordinate = new Coordinate(2,2);
		System.out.println("current: " + myCoordinate.getxCoord() + " " + myCoordinate.getyCoord());

		myCoordinate.setxCoord(5);
		myCoordinate.setyCoord(3);

		System.out.println("new: " + myCoordinate.getxCoord() + " " + myCoordinate.getyCoord());
	}

	public Coordinate(double x, double y)
	{
		this.xCoord = x;
		this.yCoord = y;
	}

	public double getxCoord()
	{
		return this.xCoord;
	}

    public double getyCoord()
    {
        return this.yCoord;
    }

	public void setxCoord(double x)
	{
		this.xCoord = x;
	}

    public void setyCoord(double y)
	{
		this.yCoord = y;
	}
}