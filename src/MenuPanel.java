import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

/**
 * <h1>MenuPanel</h1>
 * This class represents a display of options for the WorldSimulation
 *  - of type JPanel
 *
 * @author  Y3508038
 * @version 1.0
 * @since   13-04-2015
 */
public class MenuPanel extends JPanel
{
	/***********************************************
	 *      INSTANCE VARIABLES AND "DEFINITIONS"
	 ***********************************************/

	private final int X_DIMENSION = 200;
	private final int Y_DIMENSION = 600;
	private final int MIN_X_DIMENSION = 100;
	private final int MIN_Y_DIMENSION = 200;

	private final int PRESET_FPS = 50;
	private final int PRESET_INFLUENCE_RULES = 100;
	private final int SPACING_INITIAL = 20;
	private final int SPACING_BETWEEN_MINOR = 5;
	private final int SPACING_BETWEEN_MAJOR = 15;

	private WorldSimulation worldSimulation;
	private JButton play_pause;
	private JButton reset;
	private JButton plus;
	private JButton minus;
	private JSlider sliderFPS;
	private JSlider sliderSeparation;
	private JSlider sliderAlignment;
	private JSlider sliderCohesion;
    private JLabel watchNBoids;

	/***********************************************
	 *      INSTANCE VARIABLES AND "DEFINITIONS" END
	 ***********************************************/

	/**
	 * Constructor which sets up the menuPanel.
	 */
	public MenuPanel()
	{
		BoxLayout menuPanelLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);

		this.setLayout(menuPanelLayout);
		this.addContents();
	}

	/**
	 * Part of constructor: Sets up and adds contents to the menuPanel
	 */
	private void addContents()
	{
		this.add(Box.createVerticalStrut(SPACING_INITIAL));

		this.setupButtonPlayPause();
		this.add(Box.createVerticalStrut(SPACING_BETWEEN_MINOR));
		this.setupButtonReset();

		this.add(Box.createVerticalStrut(SPACING_BETWEEN_MAJOR));
		this.setupButtonPlus();
		this.add(Box.createVerticalStrut(SPACING_BETWEEN_MINOR));
		this.setupButtonMinus();

		this.add(Box.createVerticalGlue());
		this.setupNBoidsWatch();

		this.add(Box.createVerticalGlue());
		this.setupSlidersFlockingRules();

		this.add(Box.createVerticalGlue());
		this.setupSliderFPS();
	}

	/**
	 * Instantiates a JLabel to monitor the total number of boids on screen
	 */
	private void setupNBoidsWatch()
	{
		this.add(this.watchNBoids = new JLabel("N Boids: "));
		this.watchNBoids.setToolTipText("Monitors the total number of boids");
	}

	/**
	 * Instantiates a PLay/Pause-button with an inner class that that executes a
	 * 	method in worldSimulation, <code>play_pause()</code>, and changing the
	 * 	button text to "Play" or "Pause" depending on its current state
	 */
	private void setupButtonPlayPause()
	{
		this.add(this.play_pause = new JButton("Play"));
		this.play_pause.setToolTipText("Play and pause the game");

		this.play_pause.addActionListener(new ActionListener() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (worldSimulation.timerIsRunning()) {
					play_pause.setText("Play");
				} else {
					play_pause.setText("Pause");
				}
				worldSimulation.play_pause();
			}
		});
	}

	/**
	 * Instantiates a Reset-button with an inner class that executes a
	 * 	method in worldSimulation, <code>reset()</code>, as well as <code>this.resetMenuPanel</code>
	 */
	private void setupButtonReset()
	{
		this.add(this.reset = new JButton("Reset"));
		this.reset.setToolTipText("Reset the boids and the sliders");

		this.reset.addActionListener(new ActionListener() {
			/**
			 * {@inheritDoc}
			 */@Override
			public void actionPerformed(ActionEvent e) {
				worldSimulation.reset();
				resetMenuPanel();
			}
		});
	}

	/**
	 * Instantiates a +(Plus)-button with an inner class that executes a
	 * 	method in worldSimulation making 5 boids
	 */
	private void setupButtonPlus()
	{
		this.add(this.plus = new JButton("+"));
		this.plus.setToolTipText("Add 5 boids");

		this.plus.addActionListener(new ActionListener() {
			/**
			 * {@inheritDoc}
			 */@Override
			public void actionPerformed(ActionEvent e) {
				int nBoidsAdd = 5;
				worldSimulation.createBoids(nBoidsAdd);
			}
		});
	}

	/**
	 * Instantiates a -(Minus)-button with an inner class that executes a
	 * 	method in worldSimulation removing 5 boids
	 */
	private void setupButtonMinus()
	{
		this.add(this.minus = new JButton("-"));
		this.minus.setToolTipText("Remove 5 boids");

		this.minus.addActionListener(new ActionListener() {
			/**
			 * {@inheritDoc}
			 */@Override
			public void actionPerformed(ActionEvent e) {
				int nBoidsRemove = 5;
				worldSimulation.removeBoids(nBoidsRemove);
			}
		});
	}

	/**
	 * Instantiates three sliders, sets up tooltip, labels and a HashTable for each of the three sliders.
	 * 	Adds the three sliders with glue in between each other to <code>this</code>
	 * 	Adds an inner changeListener to each of the three sliders
	 */
	private void setupSlidersFlockingRules()
	{
		int maximum = 400;
		int minimum = 0;

		this.sliderSeparation = new JSlider(minimum, maximum, this.PRESET_INFLUENCE_RULES);
		this.sliderAlignment = new JSlider(minimum, maximum, this.PRESET_INFLUENCE_RULES);
		this.sliderCohesion = new JSlider(minimum, maximum, this.PRESET_INFLUENCE_RULES);

		JLabel separationLabel = new JLabel("Separation", SwingConstants.CENTER);
		JLabel alignmentLabel = new JLabel("Alignment", SwingConstants.CENTER);
		JLabel cohesionLabel = new JLabel("Cohesion", SwingConstants.CENTER);

		sliderSeparation.setToolTipText("Changes the boids influence of this rule");
		sliderAlignment.setToolTipText("Changes the boids influence of this rule");
		sliderCohesion.setToolTipText("Changes the boids influence of this rule");

		Hashtable<Integer, JLabel> lableTable = new Hashtable<Integer, JLabel>();
		lableTable.put(minimum, new JLabel("0"));
		lableTable.put(this.PRESET_INFLUENCE_RULES, new JLabel("Initial"));
		lableTable.put(maximum, new JLabel("x 4"));

		sliderSeparation.setLabelTable(lableTable);
		sliderSeparation.setPaintLabels(true);
        sliderSeparation.setMinorTickSpacing(100);
        sliderSeparation.setPaintTicks(true);

		sliderAlignment.setLabelTable(lableTable);
		sliderAlignment.setPaintLabels(true);
        sliderAlignment.setMinorTickSpacing(100);
        sliderAlignment.setPaintTicks(true);

		sliderCohesion.setLabelTable(lableTable);
		sliderCohesion.setPaintLabels(true);
        sliderCohesion.setMinorTickSpacing(100);
        sliderCohesion.setPaintTicks(true);

		this.add(separationLabel);
		this.add(sliderSeparation);
		this.add(Box.createVerticalStrut(SPACING_BETWEEN_MINOR));

		this.add(alignmentLabel);
		this.add(sliderAlignment);
		this.add(Box.createVerticalStrut(SPACING_BETWEEN_MINOR));

		this.add(cohesionLabel);
		this.add(sliderCohesion);

		sliderSeparation.addChangeListener(new ChangeListener() {
			/**
			 * {@inheritDoc}
			 */@Override
			public void stateChanged(ChangeEvent e) {
				worldSimulation.setSeparationBoids(((JSlider) e.getSource()).getValue());
			}
		});
		sliderAlignment.addChangeListener(new ChangeListener() {
			/**
			 * {@inheritDoc}
			 */@Override
			public void stateChanged(ChangeEvent e) {
				worldSimulation.setAlignmentBoids(((JSlider) e.getSource()).getValue());
			}
		});
		sliderCohesion.addChangeListener(new ChangeListener() {
			/**
			 * {@inheritDoc}
			 */@Override
			public void stateChanged(ChangeEvent e) {
				worldSimulation.setCohesionBoids(((JSlider) e.getSource()).getValue());
			}
		});
	}

	/**
	 * Instantiates an FPS slider, sets up tooltip, labels and options.
	 * 	Adds the slider to <code>this</code>
	 * 	Adds a changeListener to the slider
	 */
	private void setupSliderFPS()
	{
		int maxFPS = 100;
		int minFPS = 20;

		this.sliderFPS = new JSlider(minFPS, maxFPS, this.PRESET_FPS);

		JLabel fpsSliderLabel = new JLabel("Frames Per Second", SwingConstants.CENTER);

		sliderFPS.setMinorTickSpacing(10);
		sliderFPS.setMajorTickSpacing(20);
		sliderFPS.setPaintTicks(true);
		sliderFPS.setPaintLabels(true);
		sliderFPS.setToolTipText("Change the desired frame rate - Computer capacity might limit actual FPS");

		this.add(fpsSliderLabel);
		this.add(sliderFPS);

		sliderFPS.addChangeListener(new ChangeListener() {
			/**
			 * {@inheritDoc}
			 */@Override
			public void stateChanged(ChangeEvent e) {
				worldSimulation.setFPS(((JSlider) e.getSource()).getValue());
			}
		});
	}

	/**
	 * Resets the sliders an the buttons of the panel
	 */
	private void resetMenuPanel()
	{
		this.sliderFPS.setValue(PRESET_FPS);
		this.sliderSeparation.setValue(PRESET_INFLUENCE_RULES);
		this.sliderAlignment.setValue(PRESET_INFLUENCE_RULES);
		this.sliderCohesion.setValue(PRESET_INFLUENCE_RULES);
		this.play_pause.setText("Play");
	}

	/***********************************************
	 *      Getters / Setters
	 ***********************************************/

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
	 * Sets worldSimulation for communication
	 * @param worldSimulation Object of class worldSimulation
	 */
	public void setWorldSimulation(WorldSimulation worldSimulation)
	{
		this.worldSimulation = worldSimulation;
	}

	/**
	 * Get initial FPS
	 * @return initial FPS
	 */
	public int getInitialFPS()
	{
		return this.PRESET_FPS;
	}

	/**
	 * Get separation value from slider
	 * @return Slider value of separation slider
	 */
	public int getSliderSeparationValue()
	{
		return sliderSeparation.getValue();
	}

	/**
	 * Get alignment value from slider
	 * @return Slider value of alignment slider
	 */
	public int getSliderAlignmentValue()
	{
		return sliderAlignment.getValue();
	}

	/**
	 * Get cohesion value from slider
	 * @return Slider value of cohesion slider
	 */
	public int getSliderCohesionValue()
	{
		return sliderCohesion.getValue();
	}

	/**
	 * Sets the text of the NBoids "watch" / label equal to "N Boids: x"
	 * @param NBoids Number of boids
	 */
	public void setWatchNBoids(int NBoids)
	{
		this.watchNBoids.setText("N Boids: " + NBoids);
	}
}