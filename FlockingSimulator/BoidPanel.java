import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * <h1>BoidPanel</h1>
 * This class represents a graphical display of the WorldSimulation
 *  - of type JPanel
 *
 * @author  Y3508038
 * @version 1.0
 * @since   13-04-2015
 */
public class BoidPanel extends JPanel
{
    /***********************************************
     *      INSTANCE VARIABLES AND "DEFINITIONS"
     ***********************************************/

    private final int X_DIMENSION = 800;
	private final int Y_DIMENSION = 600;
    private final int MIN_X_DIMENSION = 200;
    private final int MIN_Y_DIMENSION = 200;

    private ArrayList<Boid> boidList;

    /***********************************************
     *      INSTANCE VARIABLES AND "DEFINITIONS" END
     ***********************************************/

    /**
     * Default constructor:
     *  Sets up the boidPanel(extending JPanel) with a border
     */
    public BoidPanel()
	{
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setToolTipText("Click to add Boids");
    }

    /**
     * The paintComponent method overrides a method in javax.swing.JComponent
     * <b>NB: You never need to call this method yourself.</b> If a paint is wanted: use repaint().
     * Sets rendering options, and paints each boid in the array of boids.
     *
     * @param g Graphic object
     * {@inheritDoc}
     */@Override
	protected void paintComponent(Graphics g)
    {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

        // Set the rendering options. Taken from labs
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for(Boid boid : this.boidList)
        {
            boid.drawBoid(g2);
        }
	}

    /**
     * {@inheritDoc}
     */@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(this.X_DIMENSION, this.Y_DIMENSION);
	}

    /**
     * {@inheritDoc}
     */@Override
    public Dimension getMinimumSize()
    {
        return new Dimension(this.MIN_X_DIMENSION, this.MIN_Y_DIMENSION);
    }


    /**
     * Sets the boidList to the given boidList
     * @param boidList Dynamic array of boids that will be used as boidList
     */
    public void setBoidList(ArrayList<Boid> boidList)
	{
		this.boidList = boidList;
	}
}