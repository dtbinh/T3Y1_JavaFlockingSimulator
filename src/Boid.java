import java.util.ArrayList;
import java.awt.*;

/**
 * <h1>Boid</h1>
 * This class represents a 2D boid.
 *
 * @author  Y3508038
 * @version 1.0
 * @since   13-04-2015
 */

public class Boid
{
    /***********************************************
     *      INSTANCE VARIABLES AND "DEFINITIONS"
     ***********************************************/

    private static final double MAX_VELOCITY = 1.7;
    //DISTANCE from centre to points behind the boid
    private static final double D1 = 6;
    //Distance from centre to front of boid,
    private static final double D2 = 2 * D1;
    //Number of points in the drawing of a boid
    private static final int N_POINTS = 3;
    private static final double NEIGHBOURHOOD = 7 * D1;

    //Preset weightings that will implement flocking behaviour
    private static final double WEIGHTING_SEPARATION = 0.1;
    private static final double WEIGHTING_ALIGNMENT = 0.36;
    private static final double WEIGHTING_COHESION = 0.008;

    //For controlling the weightings with sliders at decimal values.
    //A value of 100 is equal to 1*weighting
    private static double influenceSeparation;
    private static double influenceAlignment;
    private static double influenceCohesion;

    private Coordinate myCoordinate;
    private Vector2D velocity;
    private Vector2D separation;
    private Vector2D alignment;
    private Vector2D cohesion;
    private ArrayList<Boid> nearBoids;

    /***********************************************
     *      INSTANCE VARIABLES AND "DEFINITIONS" END
     ***********************************************/

    /**
     * Constructor which takes two doubles as position for the boid,
     *  instantiates its vectors and its array list of near boids
     * @param xPos Used as x part of position
     * @param yPos Used as y part of position
     */
    public Boid(double xPos, double yPos)
    {
        this.myCoordinate = new Coordinate(xPos, yPos);

        this.nearBoids = new ArrayList<Boid>();

        this.velocity = this.setInitialVelocity();
        this.limitVelocities();
    }

    /**
     * Draw function of the boid. Draws the boid by a filled polygon
     * @param myGraphicsObject A graphics object
     */
    public void drawBoid(Graphics myGraphicsObject)
    {
        myGraphicsObject.fillPolygon(this.calculateXPoints(), this.calculateYPoints(), N_POINTS);
    }

    /**
     * Calculates which boids in the boidList are presently within the neighbourhood,
     *  adds them to the nearBoids array list.
     * @param boidList The array list of all the boids
     */
    public void calculateNearBoids(ArrayList<Boid> boidList)
    {
        //Clears any previous neighbours
        this.nearBoids.clear();

        for (Boid boid : boidList)
        {
            if (this.selfCheck(boid))
            {
                continue;
            }

            if (this.distanceBetweenBoids(boid) < NEIGHBOURHOOD)
            {
                this.nearBoids.add(boid);
            }
        }
    }

    /**
     * Calculate new position of boid from current position and velocity vector
     */
    public void calculateNewPosition()
    {
        double newX, newY;
        double speed = this.velocity.getLength();
        double angle = this.velocity.getAngle();

        newX = this.myCoordinate.getxCoord() + speed * Math.cos(angle);
        newY = this.myCoordinate.getyCoord() + speed * Math.sin(angle);

        this.myCoordinate = new Coordinate(newX, newY);
    }

    /**
     * Calculate new position of boid while implementing
     *  "infinite sandbox mode": Borders are connected
     * @param worldSizeX Size of world in x-direction
     * @param worldSizeY Size of world in y-direction
     */
    public void calculateNewPositionInfiniteSandbox(double worldSizeX, double worldSizeY)
    {
        this.calculateNewPosition();

        if (this.myCoordinate.getxCoord() > worldSizeX)
        {
            this.myCoordinate.setxCoord(0);
        }
        if (this.myCoordinate.getxCoord() < 0)
        {
            this.myCoordinate.setxCoord(worldSizeX);
        }

        if (this.myCoordinate.getyCoord() > worldSizeY)
        {
            this.myCoordinate.setyCoord(0);
        }
        if (this.myCoordinate.getyCoord() < 0)
        {
            this.myCoordinate.setyCoord(worldSizeY);
        }
    }

    /**
     * Calculate the velocity of the boid by calculating the rules separately,
     *  adding them to the current velocity and finally limiting the length of the vectors
     */
    public void calculateVelocity()
    {
        this.calculateSeparation();
        this.calculateAlignment();
        this.calculateCohesion();

        this.addVelocityVectors();
        this.limitVelocities();
    }

    /**
     * Multiplies each rule vector with an influence value, and adding them to the current velocity
     */
    private void addVelocityVectors()
    {
        this.separation.mult(influenceSeparation / 100);
        this.alignment.mult(influenceAlignment   / 100);
        this.cohesion.mult(influenceCohesion     / 100);

        this.velocity.add(this.separation);
        this.velocity.add(this.alignment);
        this.velocity.add(this.cohesion);
    }

    /**
     * Limits the length of the velocity vector (and at the same rate the three rule vectors)
     *  until the velocities length is below <code>MAX_VELOCITY</code>
     */
    private void limitVelocities()
    {
        int i = 0;
        while (this.velocity.getLength() > MAX_VELOCITY)
        {
            this.velocity.mult(0.9);
            i++;
        }
        this.separation.mult(Math.pow(0.8, i));
        this.alignment.mult(Math.pow(0.8, i));
        this.cohesion.mult(Math.pow(0.8, i));
    }

    /**
     * Calculates separation vector for the boid, updates <code>this.separation</code>
     */
    private void calculateSeparation()
	{
        if (this.nearBoids.size() == 0)
        {
            this.separation = new Vector2D();
        }

        else
        {
            Vector2D totalSeparation = new Vector2D();

            for (Boid nearBoid : this.nearBoids)
            {
                if (distanceBetweenBoids(nearBoid) < (3 * D1))
                {
                    //if(close range), then: add vector between them to totalSeparation
                    totalSeparation.add(new Vector2D(this.getCoordinate(), nearBoid.getCoordinate()));
                }
            }

            if (totalSeparation.getLength() != 0)
            {
                double separationFactor = WEIGHTING_SEPARATION * Math.expm1((D1 / (totalSeparation.getLength())));
                totalSeparation.mult(separationFactor);
                totalSeparation.inv();

//                this.separation.add(totalSeparation);
                this.separation = totalSeparation;
            }
        }
    }

    /**
     * Calculates alignment vector for the boid, updates <code>this.alignment</code>
     */
    private void calculateAlignment()
	{
        if (this.nearBoids.size() == 0)
        {
            this.alignment = new Vector2D();
        }

        else
        {
            Vector2D averageVelocity = new Vector2D();

            for (Boid nearBoid : this.nearBoids)
            {
                //Adds the velocity of each boid together to get an average angle
                averageVelocity.add(nearBoid.getVelocity());
            }
            //Dividing by number of close boids to get a shorter averaged vector
            averageVelocity.div(this.nearBoids.size());
            averageVelocity.mult(WEIGHTING_ALIGNMENT);

            this.alignment.add(averageVelocity);
            //this.alignment = averageVelocity;
        }
	}

    /**
     * Calculates cohesion vector for the boid, updates <code>this.cohesion</code>
     */
    private void calculateCohesion()
	{
        double averageXpos = 0;
        double averageYpos = 0;

        if (this.nearBoids.size() == 0)
        {
            averageXpos = this.myCoordinate.getxCoord();
            averageYpos = this.myCoordinate.getyCoord();
        }

        else
        {
            for (Boid nearBoid : this.nearBoids)
            {
                averageXpos += nearBoid.getCoordinate().getxCoord();
                averageYpos += nearBoid.getCoordinate().getyCoord();
            }
            averageXpos /= this.nearBoids.size();
            averageYpos /= this.nearBoids.size();
        }

        Coordinate averagePosition = new Coordinate(averageXpos, averageYpos);
        Vector2D tempCohesion = new Vector2D(this.getCoordinate(), averagePosition);

        tempCohesion.mult(WEIGHTING_COHESION);
//        this.cohesion.add(tempCohesion);
        this.cohesion = tempCohesion;
    }

    /**
     * Compares the passed boid to <code>this</code> boid;
     *  returns true if they are the same
     * @param otherBoid Boid to compare
     * @return True if boid and <code>this</code> are the same
     */
    private boolean selfCheck(Boid otherBoid)
    {
        boolean equal = false;

        if (otherBoid == this)
        {
            equal = true;
        }
        return equal;
    }

    /**
     * Calculates the distance between <code>this</code> boid and the passed boid.
     * @param otherBoid Boid to calculate distance to
     * @return The positive total distance between the two boids centres
     */
    private double distanceBetweenBoids(Boid otherBoid)
    {
        Coordinate otherBoidCoordinate = otherBoid.getCoordinate();
        double distanceX = this.myCoordinate.getxCoord() - otherBoidCoordinate.getxCoord();
        double distanceY = this.myCoordinate.getyCoord() - otherBoidCoordinate.getyCoord();

        return Math.hypot(distanceX, distanceY);
    }

    /**
     * Instantiates initial random vectors for the three rule vectors,
     *  as well as returning a random velocity vector.
     * @return A pseudo random velocity vector
     */
    private Vector2D setInitialVelocity()
    {
        // (20*___) to get a better random result than with solely the MAX_VELOCITY range.
        this.separation = new Vector2D(20 * MAX_VELOCITY);
        this.alignment = new Vector2D(20 * MAX_VELOCITY);
        this.cohesion = new Vector2D(20 * MAX_VELOCITY);

        return new Vector2D(20 * MAX_VELOCITY);
    }

    /**
     * Calculates the x values of the boids draw-functions polygon
     * @return An int array with three x-coordinates in
     */
    private int[] calculateXPoints()
    {
        double angle = this.velocity.getAngle();
        double x = myCoordinate.getxCoord();
        int[] xPoints = new int[N_POINTS];

        xPoints[0] = (int) (x + D1 * Math.cos(angle + 5 * Math.PI / 4));
        xPoints[1] = (int) (x + D2 * Math.cos(angle));
        xPoints[2] = (int) (x + D1 * Math.cos(angle + 3 * Math.PI / 4));

        return xPoints;
    }

    /**
     * Calculates the y values of the boids draw-functions polygon
     * @return An int array with three y-coordinates in
     */
    private int[] calculateYPoints()
    {
        double angle = this.velocity.getAngle();
        double y = myCoordinate.getyCoord();
        int[] yPoints = new int[N_POINTS];

        yPoints[0] = (int) (y + D1 * Math.sin(angle + 5 * Math.PI / 4));
        yPoints[1] = (int) (y + D2 * Math.sin(angle));
        yPoints[2] = (int) (y + D1 * Math.sin(angle + 3 * Math.PI / 4));

        return yPoints;
    }

    /***********************************************
     *      Getters / Setters
     ***********************************************/

    /**
     * Returns the boids coordinate
     * @return The boids coordinate
     */
    public Coordinate getCoordinate()
    {
        return myCoordinate;
    }

    /**
     * Returns the boids velocity vector
     * @return The boids velocity vector
     */
    public Vector2D getVelocity()
    {
        return velocity;
    }

    /**
     * Sets the influence of separation
     * @param influenceSeparationSlider Influence of separation,
     *                            a value of 100 is equal to 1 times the weight of the rule
     */
    public void setInfluenceSeparation(int influenceSeparationSlider)
    {
        influenceSeparation = influenceSeparationSlider;
    }

    /**
     * Sets the influence of alignment
     * @param influenceAlignmentSlider Influence of alignment,
     *                            a value of 100 is equal to 1 times the weight of the rule
     */
    public void setInfluenceAlignment(int influenceAlignmentSlider)
    {
        influenceAlignment = influenceAlignmentSlider;
    }

    /**
     * Sets the influence of cohesion
     * @param influenceCohesionSlider Influence of cohesion,
     *                            a value of 100 is equal to 1 times the weight of the rule
     */
    public void setInfluenceCohesion(int influenceCohesionSlider)
    {
        influenceCohesion = influenceCohesionSlider;
    }
}