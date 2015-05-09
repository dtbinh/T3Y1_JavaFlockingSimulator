import javax.swing.*;
import java.awt.*;
import javax.swing.BorderFactory;

public class MainFrame implements Runnable
{

    private JFrame mainFrame;
    private BoidPanel boidPane;
    private MenuPanel menuPane;

    public static void main(String [] args)
	{
        MainFrame myMain = new MainFrame();
		SwingUtilities.invokeLater(myMain);
	}

    public void run()
	{
        mainFrame = new JFrame("Flocking Simulator Test");
        boidPane = new BoidPanel();
        menuPane = new MenuPanel();

        BorderLayout frameLayout = new BorderLayout();
        mainFrame.setLayout(frameLayout);
        mainFrame.add(boidPane, BorderLayout.CENTER);
        mainFrame.add(menuPane, BorderLayout.EAST);

        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
    }
}