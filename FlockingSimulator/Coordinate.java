/**
 * <h1>Coordinate</h1>
 * This class represents a cartesian coordinates stored as two double values x and y
 *
 * @author  Y3508038
 * @version 1.0
 * @since   13-04-2015
 */
public class Coordinate
{
	/***********************************************
	 *      INSTANCE VARIABLES
	 ***********************************************/

	private double xCoord;
	private double yCoord;

	/***********************************************
	 *      INSTANCE VARIABLES END
	 ***********************************************/

	/**
	 * Constructor which produces a similar to cartesian coordinate
	 * @param x X-value of the coordinate
	 * @param y Y-value of the coordinate
	 */
	public Coordinate(double x, double y)
	{
		this.xCoord = x;
		this.yCoord = y;
	}

	/**
	 * Gets the x-value of the coordinate
	 * @return Returns x-value of the coordinate
	 */
	public double getxCoord()
	{
		return this.xCoord;
	}

	/**
	 * Gets the y-value of the coordinate
	 * @return Returns y-value of the coordinate
	 */
    public double getyCoord()
    {
        return this.yCoord;
    }

	/**
	 * Sets the x-value of the coordinate
	 * @param x Value to be used as xCoord
	 */
	public void setxCoord(double x)
	{
		this.xCoord = x;
	}

	/**
	 * Sets the y-value of the coordinate
	 * @param y Value to be used as yCoord
	 */
    public void setyCoord(double y)
	{
		this.yCoord = y;
	}
}