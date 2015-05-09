import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel
{
	private final int X_DIMENSION = 200;
	private final int Y_DIMENSION = 600;

	private JButton test1;
	private JButton test2;

	public MenuPanel()
	{
		BoxLayout menuPanelLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);

		this.setLayout(menuPanelLayout);

		this.add(this.test1 = new JButton("test1"));
		this.test1.setToolTipText("test1");
		this.add(this.test2 = new JButton("test2"));
		this.test2.setToolTipText("test2");	
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(this.X_DIMENSION, this.Y_DIMENSION);
	}
}