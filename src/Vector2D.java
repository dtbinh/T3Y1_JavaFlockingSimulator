import java.lang.Math;
import java.util.Random;

/**
 * <h1>Vector2D</h1> This class represents a 2D vector and stores two components
 * vectors Vx and Vy for a vector V
 *
 * @author Y3508038
 * @version 1.0
 * @since 13-04-2015
 */
public class Vector2D {
	/***********************************************
	 * INSTANCE VARIABLES
	 ***********************************************/

	private double x;
	private double y;

	/***********************************************
	 * INSTANCE VARIABLES END
	 ***********************************************/

	/**
	 * Default constructor which produces a zero vector
	 */
	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructor which produces a vector V with Vx and Vy as component values
	 * 
	 * @param xComponent
	 *            Component vector xComponent
	 * @param yComponent
	 *            Component vector yComponent
	 */
	public Vector2D(double xComponent, double yComponent) {
		this.x = xComponent;
		this.y = yComponent;
	}

	/**
	 * Constructor which produces a pseudo random vector which has magnitude
	 * less than maxMagnitude
	 * 
	 * @param maxMagnitude
	 *            Vectors magnitude will be set between 0 and maxMagnitude
	 */
	public Vector2D(double maxMagnitude) {
		Random randomNumber = new Random();
		//this.x = (randomNumber.nextInt(2 * (int) maxMagnitude) - maxMagnitude);
		//this.y = (randomNumber.nextInt(2 * (int) maxMagnitude) - maxMagnitude);
		this.x = 10;
		this.y= 5;
		
		double magnitude = this.getMagnitude();
		
		if (magnitude > maxMagnitude) {
			this.x *= maxMagnitude / magnitude;
			this.y *= maxMagnitude / magnitude;
		}
	}

	/**
	 * Constructor that produces a vector from one coordinate to a second
	 * coordinate
	 * 
	 * @param coordinate1
	 *            Coordinate one - Vector goes from this point
	 * @param coordinate2
	 *            Coordinate two - Vector goes to this point
	 */
	public Vector2D(Coordinate coordinate1, Coordinate coordinate2) {
		this.x = coordinate2.getxCoord() - coordinate1.getxCoord();
		this.y = coordinate2.getyCoord() - coordinate1.getyCoord();
	}

	/***********************************************
	 * Physics Vector Implementation
	 ***********************************************/
	/**
	 * Adds this vector with another vector
	 * 
	 * @param addedVector
	 *            Vector is added to the current vector
	 */
	public void add(Vector2D addedVector) {
		this.x += addedVector.getX();
		this.y += addedVector.getY();
	}

	public Vector2D addTwoVectors(Vector2D vector1, Vector2D vector2) {
		double newX = vector1.getX() + vector2.getX();
		double newY = vector1.getX() + vector2.getY();
		return new Vector2D(newX, newY);
	}

	/**
	 * Multiplies this vector with another vector
	 * 
	 * @param factor
	 *            Vector is multiplied by this factor
	 */
	public void mult(double factor) {
		this.x *= factor;
		this.y *= factor;
	}

	public Vector2D multVector(Vector2D vector1, double factor) {
		double newX = vector1.getX() * factor;
		double newY = vector1.getX() * factor;
		return new Vector2D(newX, newY);
	}

	/**
	 * Divides this vector with another vector
	 * 
	 * @param factor
	 *            Vector is divided by this factor
	 */
	public void div(double factor) {
		this.x /= factor;
		this.y /= factor;
	}

	public Vector2D divVector(Vector2D vector1, double factor) {
		double newX = vector1.getX() / factor;
		double newY = vector1.getX() / factor;
		return new Vector2D(newX, newY);
	}

	/**
	 * Inverts the current vector
	 */
	public void inv() {
		this.x *= -1;
		this.y *= -1;
	}

	public Vector2D invVector(Vector2D vector) {
		double newX = vector.getX() * -1;
		double newY = vector.getX() * -1;
		return new Vector2D(newX, newY);
	}

	/**
	 * Makes the current vector a unit vector
	 */
	public void normalise() // TODO check that this works
	{
		if (this.getMagnitude() != 0) {
			this.x /= this.getMagnitude();
			this.y /= this.getMagnitude();
		}
	}

	public Vector2D normaliseVector(Vector2D vector) {
		if (vector.getMagnitude() != 0) {
			double newX = vector.getX() / vector.getMagnitude();
			double newY = vector.getX() / vector.getMagnitude();
			return new Vector2D(newX, newY);
		} else {
			return new Vector2D();
		}
	}

	/***********************************************
	 * Getters / Setters
	 ***********************************************/

	/**
	 * Gets the X-component of the vector
	 * 
	 * @return X-component
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Gets the Y-component of the vector
	 * 
	 * @return Y-component
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Calculates and returns the length(hypotenus) of the vector
	 * 
	 * @return Returns the length of the vector
	 */
	public double getMagnitude() {
		return Math.hypot(this.x, this.y);
	}

	/**
	 * Calculates the angle of the vector. The angle is given as theta in a
	 * polar coordinate
	 * 
	 * @return Returns the angle(theta) of the vector
	 */
	public double getDirection() {
		return (Math.atan2(this.y, this.x));
	}
}