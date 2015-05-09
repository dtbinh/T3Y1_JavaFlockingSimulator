import javax.swing.*;
import java.awt.*;
import javax.swing.BorderFactory;
import java.util.Random;

public class BoidPanel extends JPanel
{

    private final int X_DIMENSION = 800;
	private final int Y_DIMENSION = 600;

    private static final double D1 = 20;
    private static final double D2 = 1.7* D1;
	private double angle;

    public BoidPanel()
	{
        this.setBorder(BorderFactory.createEtchedBorder());

    }

	protected void paintComponent(Graphics g)
    {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

        // Set the rendering options. Taken from labs
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        //Test for drawing of boids at random angles using claclualte x/y points
        for(int i = 0; i<10; i++)
            {
                g2.fillPolygon(this.calculateXPoints(), this.calculateYPoints(), 3);
            }
	}

 	private int[] calculateXPoints()
    {
        Random myRandom = new Random();
        this.angle = Math.toRadians(myRandom.nextInt(360));
        double x = myRandom.nextInt(800);
        int[] xPoints = new int[3];

        xPoints[0] = (int) (x + D1 * Math.cos(this.angle + 5 * Math.PI / 4));
        xPoints[1] = (int) (x + D2 * Math.cos(this.angle));
        xPoints[2] = (int) (x + D1 * Math.cos(this.angle + 3 * Math.PI / 4));

        return xPoints;
    }

    private int[] calculateYPoints()
    {
        Random myRandom = new Random();
        double y = myRandom.nextInt(600);
        int[] yPoints = new int[3];

        yPoints[0] = (int) (y + D1 * Math.sin(this.angle + 5 * Math.PI / 4));
        yPoints[1] = (int) (y + D2 * Math.sin(this.angle));
        yPoints[2] = (int) (y + D1 * Math.sin(this.angle + 3 * Math.PI / 4));

        return yPoints;
    }

	public Dimension getPreferredSize()
	{
		return new Dimension(this.X_DIMENSION, this.Y_DIMENSION);
	}
}