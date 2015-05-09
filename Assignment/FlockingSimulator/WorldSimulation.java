import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * <h1>WorldSimulation</h1>
 * This class represents a "world" for boids to live in.
 *
 * @author  Y3508038
 * @version 1.0
 * @since   13-04-2015
 */
public class WorldSimulation
{
    /***********************************************
     *      INSTANCE VARIABLES AND "DEFINITIONS"
     ***********************************************/

    private Timer myTimer;
    private ArrayList<Boid> boidList;
    private BoidPanel boidPane;
    private MenuPanel menuPane;
    private double worldSizeX, worldSizeY;

    /***********************************************
     *      INSTANCE VARIABLES AND "DEFINITIONS" END
     ***********************************************/

    /**
     * Constructor which sets up communication between <code>this</code> and the menu/boid-panel.
     *  Listeners for resizing and mouse actions are set up.
     *  After initiating communication, the timer is set up.
     * @param boidPanel A boidPanel to interact with
     * @param menuPanel A menuPanel to interact with
     */
    public WorldSimulation(BoidPanel boidPanel, MenuPanel menuPanel)
    {
        this.boidPane = boidPanel;
        this.menuPane = menuPanel;
        this.menuPane.setWorldSimulation(this);

        this.setupSizeListener();
        this.setupMouseListener();

        this.boidList = new ArrayList<Boid>();
        this.boidPane.setBoidList(this.boidList);
        this.menuPane.setWatchNBoids(this.boidList.size());

        this.initiateTimer();
    }

    /**
     * Creates n boids placed randomly within the range of the boidPanels size, adds them to the arrayList.
     * @param nBoids Number of boids to create
     */
    public void createBoids(int nBoids)
    {
        Random randomSeed = new Random();

        for (int i = 0; i < nBoids; i++)
        {
            this.createBoid(randomSeed.nextInt((int) worldSizeX), randomSeed.nextInt((int) worldSizeY));
        }
    }

    /**
     * Instantiates a boid with coordinates x and y.
     *  Also sets the influence-sliders values of each boid to the actual slider-value
     * @param x Used as x-coordinate
     * @param y Used as y-coordinate
     */
    public void createBoid(int x, int y)
    {
        this.boidList.add(new Boid(x, y));

        for (Boid boid : boidList)
        {
            boid.setInfluenceSeparation(menuPane.getSliderSeparationValue());
            boid.setInfluenceAlignment(menuPane.getSliderAlignmentValue());
            boid.setInfluenceCohesion(menuPane.getSliderCohesionValue());
        }

        this.menuPane.setWatchNBoids(this.boidList.size());
        this.boidPane.repaint();
    }

    /**
     * Tries to remove the last n boids from the boidList, then repaints
     * @param nBoids Number of boids to remove
     */
    public void removeBoids(int nBoids)
    {
        int size = this.boidList.size();

        try {
            for (int i = size; i > size - nBoids; i--)
            {
                this.boidList.remove(i - 1);
            }
        } catch (IndexOutOfBoundsException e) {
            //Do nothing
        } finally
        {
            this.menuPane.setWatchNBoids(this.boidList.size());
            this.boidPane.repaint();
        }
    }

    /**
     * Setting up the boidPanel's mouse listener. Only mouseClicked is implemented.
     * When mouse is clicked: Create boid with at mouse coordinates
     */
    private void setupMouseListener()
    {
        boidPane.addMouseListener(new MouseListener() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                createBoid(e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //Do nothing
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Do nothing
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //Do nothing
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //Do nothing
            }
        });
    }

    /**
     * Initiate a changeListener that updates the worlds bounds
     */
    private void setupSizeListener()
    {
        boidPane.addComponentListener(new ComponentListener() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void componentResized(ComponentEvent e) {
                worldSizeX = e.getComponent().getWidth();
                worldSizeY = e.getComponent().getHeight();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                //Do nothing
            }

            @Override
            public void componentShown(ComponentEvent e) {
                //Do nothing
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                //Do nothing
            }
        });
    }

    /**
     * Setting up a timer that fires an event every time interval
     * A inner class is ran every time the event fires
     *  Inner class: Update world and repaint boidPanel
     */
    private void initiateTimer()
    {
        int initialUpdateRate = (1000 / menuPane.getInitialFPS());

        this.myTimer = new Timer(initialUpdateRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateWorld();
                boidPane.repaint();
            }
        });
    }

    /**
     * Updates world by updating position, neighbors and velocities for each boid
     */
    private void updateWorld()
    {
        this.updatePositions();
        this.updateNeighbors();
        this.updateVelocities();
    }

    /**
     * For each boid in array: Calculate and move the boid to its next position
     */
    private void updatePositions()
    {
        for (Boid boid : this.boidList) {
            boid.calculateNewPositionInfiniteSandbox(this.worldSizeX, this.worldSizeY);
        }
    }

    /**
     * For each boid in array: Update the boids neighboring boid-list
     */
    private void updateNeighbors()
    {
        for (Boid boid : boidList) {
            boid.calculateNearBoids(this.boidList);
        }
    }

    /**
     * For each boid in array: Update the boids velocity
     */
    private void updateVelocities()
    {
        for (Boid boid : this.boidList)
        {
            boid.calculateVelocity();
        }
    }

    /**
     * Starts/stops the timer based on its current state
     */
    public void play_pause()
    {
        if (this.myTimer.isRunning())
        {
            this.myTimer.stop();
        }
        else
        {
            this.myTimer.start();
        }
    }

    /**
     * Returns a boolean variable where the timer is either running(true) or not running(false)
     * @return "Timer is running" - true
     */
    public boolean timerIsRunning()
    {
        return this.myTimer.isRunning();
    }

    /**
     * Resets the world by clearing the array of boids and stopping the timer.
     */
    public void reset()
    {
        this.boidList.clear();
        this.boidPane.repaint();
        this.myTimer.stop();
        this.menuPane.setWatchNBoids(this.boidList.size());
    }

    /***********************************************
     *      Getters / Setters
     ***********************************************/
    /**
     * Sets the desired update rate of the timer (and thus the desired FPS of the graphics)
     * @param FPS Desired Frames Per Second
     */
    public void setFPS(int FPS)
    {
        myTimer.setDelay(1000 / FPS);
    }

    /**
     * Sets the influence rate of separation equal to <code>influenceGrade / 100</code>
     * @param influenceGrade Grade of influence
     */
    public void setSeparationBoids(int influenceGrade)
    {
        for (Boid boid : this.boidList)
        {
            boid.setInfluenceSeparation(influenceGrade);
        }
    }

    /**
     * Sets the influence rate of separation equal to <code>influenceGrade / 100</code>
     * @param influenceGrade Grade of influence
     */
    public void setAlignmentBoids(int influenceGrade)
    {
        for (Boid boid : this.boidList)
        {
            boid.setInfluenceAlignment(influenceGrade);
        }
    }

    /**
     * Sets the influence rate of separation equal to <code>influenceGrade / 100</code>
     * @param influenceGrade Grade of influence
     */
    public void setCohesionBoids(int influenceGrade)
    {
        for (Boid boid : this.boidList)
        {
            boid.setInfluenceCohesion(influenceGrade);
        }
    }
}
