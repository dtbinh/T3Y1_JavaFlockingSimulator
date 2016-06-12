import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

/**
 * <h1>Boid</h1> This class represents a 2D boid.
 *
 * @author Y3508038
 * @version 1.0
 * @since 13-04-2015
 */

public class Boid {
	/***********************************************
	 * INSTANCE VARIABLES AND "DEFINITIONS"
	 ***********************************************/

	private static double maxVelocity = 5; // TODO Make adjustable: 1.7
	// DISTANCE from centre to points behind the boid
	private static double d1 = 6;// TODO Make adjustable:
	// Distance from centre to front of boid,
	private static double d2 = 2 * d1;
	// Number of points in the drawing of a triangular boid
	private static final int N_POINTS = 3;
	private static double neighbourhoodRange = 8 * d1; // TODO Make adjustable:
	private static double neighbourhoodAngle = Math.PI / 2;

	// Preset weightings that will implement flocking behaviour
	private static double weightingSeparation = 0.1; // TODO Make adjustable:
	private static double weightingAlignment = 0.36; // TODO Make adjustable:
	private static double weightingCohesion = 0.008; // TODO Make adjustable:

	// For controlling the weightings with sliders at decimal values.
	// A value of 100 is equal to 1*weighting
	private static double influenceSeparation;
	private static double influenceAlignment;
	private static double influenceCohesion;

	// private Graphics graphicsObject;

	private Coordinate position;
	private Vector2D velocity;
	private ArrayList<Boid> nearBoids;

	private static boolean drawBoidSimply = true;
	private static boolean drawLinesBetweenSeenBoids = true;
	private static double randomMovementAmount = 3; // Set between 0 and max
													// velocity?

	/***********************************************
	 * INSTANCE VARIABLES AND "DEFINITIONS" END
	 ***********************************************/

	/**
	 * Constructor which takes two doubles as position for the boid,
	 * instantiates its vectors and its array list of near boids
	 * 
	 * @param xPosition
	 *            Used as x part of position
	 * @param yPosition
	 *            Used as y part of position
	 */
	public Boid(double xPosition, double yPosition) {
		this.position = new Coordinate(xPosition, yPosition);

		this.nearBoids = new ArrayList<Boid>();

		this.velocity = new Vector2D(maxVelocity);
	}

	/**
	 * Draw function of the boid. Draws the boid by a filled polygon
	 * 
	 * @param myGraphicsObject
	 *            A graphics object
	 */
	public void drawBoid(Graphics myGraphicsObject) {
		double xPosition = this.position.getxCoord();
		double yPosition = this.position.getyCoord();
		double speed = this.velocity.getMagnitude();
		double direction = this.velocity.getDirection();

		myGraphicsObject.setColor(Color.BLACK);
		if (drawBoidSimply) {
			myGraphicsObject.drawOval((int) (xPosition - d1), (int) (yPosition - d1), (int) d2, (int) d2);
			myGraphicsObject.setColor(Color.LIGHT_GRAY);
			myGraphicsObject.drawLine((int) xPosition, (int) yPosition,
					(int) (xPosition + speed * d1 * Math.cos(direction)),
					(int) (yPosition + speed * d1 * Math.sin(direction)));
		} else {
			myGraphicsObject.drawPolygon(this.calculateXPoints(), this.calculateYPoints(), N_POINTS);
		}

		if (drawLinesBetweenSeenBoids) {
			myGraphicsObject.setColor(Color.CYAN);
			for (Boid boid : this.nearBoids) {
				if (!this.selfCheck(boid)) {

					myGraphicsObject.drawLine((int) this.position.getxCoord(), (int) this.position.getyCoord(),
							(int) boid.position.getxCoord(), (int) boid.position.getyCoord());
				}
			}
		}
	}

	/**
	 * Calculates which boids in the boidList are presently within the
	 * neighbourhood, adds them to the nearBoids array list.
	 * 
	 * @param boidList
	 *            The array list of all the boids
	 */
	public void calculateVisibleBoids(ArrayList<Boid> boidList) {
		// Clears any previous neighbours
		this.nearBoids.clear();

		for (Boid boid : boidList) {
			if (this.selfCheck(boid)) {
				continue;
			}

			if (this.distanceBetweenBoids(boid) < neighbourhoodRange) {
				Vector2D vectorBetweenBoidPositions = new Vector2D(this.position, boid.position);
				double angleBetweenDirectionAndBoidVector = this.velocity.getDirection()
						- vectorBetweenBoidPositions.getDirection();

				if (angleBetweenDirectionAndBoidVector <= neighbourhoodAngle
						&& angleBetweenDirectionAndBoidVector >= -neighbourhoodAngle) {
					this.nearBoids.add(boid);
				}
			}
		}
	}

	/**
	 * Calculate new position of boid from current position and velocity vector
	 */
	public void calculateNewPosition() {
		double newX, newY;
		double speed = this.velocity.getMagnitude();
		double direction = this.velocity.getDirection();

		newX = this.position.getxCoord() + speed * Math.cos(direction);
		newY = this.position.getyCoord() + speed * Math.sin(direction);

		this.position = new Coordinate(newX, newY);
	}

	/**
	 * Calculate new position of boid while implementing "infinite sandbox mode"
	 * : Borders are connected
	 * 
	 * @param worldSizeX
	 *            Size of world in x-direction
	 * @param worldSizeY
	 *            Size of world in y-direction
	 */
	public void calculateNewPositionInfiniteSandbox(double worldSizeX, double worldSizeY) {
		this.calculateNewPosition();

		if (this.position.getxCoord() > worldSizeX) {
			this.position.setxCoord(0);
		}
		if (this.position.getxCoord() < 0) {
			this.position.setxCoord(worldSizeX);
		}

		if (this.position.getyCoord() > worldSizeY) {
			this.position.setyCoord(0);
		}
		if (this.position.getyCoord() < 0) {
			this.position.setyCoord(worldSizeY);
		}
	}

	/**
	 * Calculate the velocity of the boid by calculating the rules separately,
	 * adding them to the current velocity and finally limiting the length of
	 * the vectors
	 */
	public void updateVelocity() {
		if (this.nearBoids.size() == 0) { // If no boids in sight, be a bit random
											// in speed and direction
			double speed = this.velocity.getMagnitude();
			double direction = this.velocity.getDirection();

			Random randomMovementNumber = new Random();
			double newXComponent = speed * Math.cos(direction) * randomMovementNumber.nextDouble()
					* randomMovementAmount;
			double newYComponent = speed * Math.sin(direction) * randomMovementNumber.nextDouble()
					* randomMovementAmount;
			Vector2D randomVelocity = new Vector2D(newXComponent, newYComponent);
			
			this.velocity.add(randomVelocity);
		} 
		else {
			Vector2D separation = this.calculateSeparationVector();
			Vector2D alignment = this.calculateAlignmentVector();
			Vector2D cohesion = this.calculateCohesionVector();

			separation.mult(influenceSeparation / 100);
			alignment.mult(influenceAlignment / 100);
			cohesion.mult(influenceCohesion / 100);

			this.velocity.add(separation);
			this.velocity.add(alignment);
			this.velocity.add(cohesion);
		}

		double speed = this.velocity.getMagnitude();
		if (speed > maxVelocity) {
			this.velocity.div(speed);
			this.velocity.mult(maxVelocity);
		}
	}

	/**
	 * Calculates separation vector for the boid, updates
	 * <code>this.separation</code>
	 * 
	 * @return
	 */
	private Vector2D calculateSeparationVector() {
		Vector2D updatedSeparationVector = new Vector2D();

		if (this.nearBoids.size() > 0) {

			for (Boid nearBoid : this.nearBoids) {
				updatedSeparationVector.add(new Vector2D(this.getCoordinate(), nearBoid.getCoordinate()));
			}

			if (updatedSeparationVector.getMagnitude() != 0) {
				double separationFactor = weightingSeparation
						* Math.expm1((d1 / (updatedSeparationVector.getMagnitude())));
				updatedSeparationVector.mult(separationFactor);
				updatedSeparationVector.inv();
			}
		}
		return updatedSeparationVector;
	}

	/**
	 * Calculates alignment vector for the boid, updates
	 * <code>this.alignment</code>
	 */
	private Vector2D calculateAlignmentVector() {
		Vector2D updatedAlignmentVector = new Vector2D();

		if (this.nearBoids.size() > 0) {
			for (Boid nearBoid : this.nearBoids) {
				// Adds the velocity of each boid together to get an average
				// angle
				updatedAlignmentVector.add(nearBoid.getVelocity());
			}
			// Dividing by number of close boids to get a shorter averaged
			// vector
			updatedAlignmentVector.div(this.nearBoids.size());
			updatedAlignmentVector.mult(weightingAlignment);
		}
		return updatedAlignmentVector;
	}

	/**
	 * Calculates cohesion vector for the boid, updates
	 * <code>this.cohesion</code>
	 */
	private Vector2D calculateCohesionVector() {
		Vector2D updatedCohesionVector = new Vector2D();
		double averageXpos = 0;
		double averageYpos = 0;

		if (this.nearBoids.size() == 0) {
			averageXpos = this.position.getxCoord();
			averageYpos = this.position.getyCoord();
		}

		else {
			for (Boid nearBoid : this.nearBoids) {
				averageXpos += nearBoid.getCoordinate().getxCoord();
				averageYpos += nearBoid.getCoordinate().getyCoord();
			}
			averageXpos /= this.nearBoids.size();
			averageYpos /= this.nearBoids.size();

			Coordinate averagePosition = new Coordinate(averageXpos, averageYpos);
			updatedCohesionVector = new Vector2D(this.getCoordinate(), averagePosition);
			updatedCohesionVector.mult(weightingCohesion);
		}
		return updatedCohesionVector;
	}

	/**
	 * Compares the passed boid to <code>this</code> boid; returns true if they
	 * are the same
	 * 
	 * @param otherBoid
	 *            Boid to compare
	 * @return True if boid and <code>this</code> are the same
	 */
	private boolean selfCheck(Boid otherBoid) {
		boolean boidIsEqual = false;

		if (otherBoid == this) {
			boidIsEqual = true;
		}
		return boidIsEqual;
	}

	/**
	 * Calculates the distance between <code>this</code> boid and the compared
	 * boid.
	 * 
	 * @param otherBoid
	 *            Boid to calculate distance to
	 * @return The positive total distance between the two boids centres
	 */
	private double distanceBetweenBoids(Boid otherBoid) {
		Coordinate otherBoidCoordinate = otherBoid.getCoordinate();
		double distanceX = this.position.getxCoord() - otherBoidCoordinate.getxCoord();
		double distanceY = this.position.getyCoord() - otherBoidCoordinate.getyCoord();

		return Math.hypot(distanceX, distanceY);
	}

	/**
	 * Calculates the x values of the boids draw-functions polygon
	 * 
	 * @return An int array with three x-coordinates in
	 */
	private int[] calculateXPoints() {
		double angle = this.velocity.getDirection();
		double x = position.getxCoord();
		int[] xPoints = new int[N_POINTS];

		xPoints[0] = (int) (x + d1 * Math.cos(angle + 5 * Math.PI / 4));
		xPoints[1] = (int) (x + d2 * Math.cos(angle));
		xPoints[2] = (int) (x + d1 * Math.cos(angle + 3 * Math.PI / 4));

		return xPoints;
	}

	/**
	 * Calculates the y values of the boids draw-functions polygon
	 * 
	 * @return An int array with three y-coordinates in
	 */
	private int[] calculateYPoints() {
		double angle = this.velocity.getDirection();
		double y = position.getyCoord();
		int[] yPoints = new int[N_POINTS];

		yPoints[0] = (int) (y + d1 * Math.sin(angle + 5 * Math.PI / 4));
		yPoints[1] = (int) (y + d2 * Math.sin(angle));
		yPoints[2] = (int) (y + d1 * Math.sin(angle + 3 * Math.PI / 4));

		return yPoints;
	}

	/***********************************************
	 * Getters / Setters
	 ***********************************************/

	/**
	 * Returns the boids coordinate
	 * 
	 * @return The boids coordinate
	 */
	public Coordinate getCoordinate() {
		return position;
	}

	/**
	 * Returns the boids velocity vector
	 * 
	 * @return The boids velocity vector
	 */
	public Vector2D getVelocity() {
		return velocity;
	}

	/**
	 * Sets the influence of separation
	 * 
	 * @param influenceSeparationSlider
	 *            Influence of separation, a value of 100 is equal to 1 times
	 *            the weight of the rule
	 */
	public void setInfluenceSeparation(int influenceSeparationSlider) {
		influenceSeparation = influenceSeparationSlider;
	}

	/**
	 * Sets the influence of alignment
	 * 
	 * @param influenceAlignmentSlider
	 *            Influence of alignment, a value of 100 is equal to 1 times the
	 *            weight of the rule
	 */
	public void setInfluenceAlignment(int influenceAlignmentSlider) {
		influenceAlignment = influenceAlignmentSlider;
	}

	/**
	 * Sets the influence of cohesion
	 * 
	 * @param influenceCohesionSlider
	 *            Influence of cohesion, a value of 100 is equal to 1 times the
	 *            weight of the rule
	 */
	public void setInfluenceCohesion(int influenceCohesionSlider) {
		influenceCohesion = influenceCohesionSlider;
	}
}