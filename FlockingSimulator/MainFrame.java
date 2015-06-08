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
public class MainFrame implements Runnable
{
    /***********************************************
     *      INSTANCE VARIABLES
     ***********************************************/

    private JFrame mainFrame;
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
		MainFrame myMain = new MainFrame();
		SwingUtilities.invokeLater(myMain);
	}

    /**
     * Implements method in java.lang.Runnable
     * Instantiates a frame, two panels(one for graphical simulation and one for options),
     * the simulation of the world, and sets up "connections" between them.
     * {@inheritDoc}
     */@Override
    public void run()
	{
        this.mainFrame = new JFrame("Flocking Simulator");
        this.boidPane = new BoidPanel();
        this.menuPane = new MenuPanel();
        this.worldSimulation = new WorldSimulation(this.boidPane, this.menuPane);

        this.setupSimulatorWindow();
    }

    /**
     * Sets the mainFrame to use BorderLayout.
     * Adds the contents onto the frame, and sets settings
     *  regarding visuals and closing of the frame
     */
	private void setupSimulatorWindow()
	{
        BorderLayout frameLayout = new BorderLayout();
        this.mainFrame.setLayout(frameLayout);
        this.mainFrame.add(this.boidPane, BorderLayout.CENTER);
        this.mainFrame.add(this.menuPane, BorderLayout.EAST);

        this.mainFrame.pack();
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setResizable(true);
        this.mainFrame.setVisible(true);
	}
}