import javax.swing.*;
import java.awt.*;

/**
 * <h1>MainFrame</h1>
 * This class represents the initialisation of the Flocking Simulator application.
 * Run <code>this</code> with
 *  -   <code>BoidPanel.java</code>
 *  -   <code>MenuPanel.java</code>
 *  -   <code>WorldSimulation.java</code>
 *  -   <code>Boid.Java</code>
 *  -   <code>Vector2D.java</code>
 *  -   <code>Coordinate.java</code>
 * to start the application.
 *
 * @author  Y3508038
 * @version 1.0
 * @since   13-04-2015
 */
public class FlockingSimulator implements Runnable
{
    /***********************************************
     *      INSTANCE VARIABLES
     ***********************************************/

    private JFrame simulatorFrame;
    private JFrame settingsFrame;
    private BoidPanel boidPane;
    private MenuPanel menuPane;
    private WorldSimulation worldSimulation;

    /***********************************************
     *      INSTANCE VARIABLES END
     ***********************************************/

    /**
     * Takes no parameters.
     * Instantiates a mainFrame which implements Runnable,
     *  then uses SwingUtilities.invokeLater to "start and run" the rest of the application
     * @param args No arguments needed or taken into account
     */
    public static void main(String [] args)
	{
		FlockingSimulator myFlockingSimulator = new FlockingSimulator();
		SwingUtilities.invokeLater(myFlockingSimulator);
	}

    /**
     * Implements method in java.lang.Runnable
     * Instantiates a frame, two panels(one for graphical simulation and one for options),
     * the simulation of the world, and sets up "connections" between them.
     * {@inheritDoc}
     */@Override
    public void run()
	{
        this.simulatorFrame = new JFrame("Flocking Simulator");
        this.settingsFrame = new JFrame("Flocking Sim - Settings");
        this.boidPane = new BoidPanel();
        this.menuPane = new MenuPanel();
        this.worldSimulation = new WorldSimulation(this.boidPane, this.menuPane);

        this.setupSimulatorWindows();
        this.setupCloseWindowMethod();
    }

	/**
     * Sets the mainFrame to use BorderLayout.
     * Adds the contents onto the frame, and sets settings
     *  regarding visuals and closing of the frame
     */
	private void setupSimulatorWindows()
	{
        BorderLayout myBorderLayout = new BorderLayout();
       
        this.simulatorFrame.setLayout(myBorderLayout);
        this.simulatorFrame.add(this.boidPane, BorderLayout.CENTER);
        this.simulatorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.simulatorFrame.setLocationRelativeTo(null);
        this.simulatorFrame.setResizable(true);
        this.simulatorFrame.pack();
        this.simulatorFrame.setVisible(true);
        
        this.settingsFrame.setLayout(myBorderLayout);
        this.settingsFrame.add(this.menuPane, BorderLayout.CENTER);
		this.settingsFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.settingsFrame.setResizable(true);
        this.settingsFrame.pack();
        this.settingsFrame.setVisible(true);
	}

	private void setupCloseWindowMethod() {
		// TODO Potentially Save active program?
		
	}
}