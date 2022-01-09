package break_out.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import break_out.Constants;
import break_out.model.Stone;
import net.miginfocom.swing.MigLayout;

/**
 * The field represents the board of the game. All components are on the board
 * 
 * @author dmlux, modified by iSchumacher, modified by Nele Meyer and Drenusha Emrulahu
 * 
 */
public class Field extends JPanel {

	/**
	 * Automatic generated serial version UID
	 */
	private static final long serialVersionUID = 2434478741721823327L;

	/**
	 * The connected view object
	 */
	private View view;

	/**
	 * The background color
	 */
	private Color background;

	/**
	 * The constructor needs a view
	 * 
	 * @param view The view of this board
	 */
	public Field(View view) {
		super();

		this.view = view;
		this.background = new Color(177, 92, 107);

		setFocusable(true);

		// Load settings
		initialize();
	}

	/**
	 * Initializes the settings for the board
	 */
	private void initialize() {
		// creates a layout
		setLayout(new MigLayout("", "0[grow, fill]0", "0[grow, fill]0"));
	}

	/**
	 * Change the background color
	 * @param color The new color
	 */
	public void changeBackground(Color color) {
		background = color;
		repaint();
	}
	
	/**
	 * This method is called when painting/repainting the playground
	 * @param g the graphics object
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		double w = Constants.SCREEN_WIDTH;
		double h = Constants.SCREEN_HEIGHT;

		// Setting the dimensions of the playground
		setPreferredSize(new Dimension((int) w, (int) h));
		setMaximumSize(new Dimension((int) w, (int) h));
		setMinimumSize(new Dimension((int) w, (int) h));

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Setting the background color
		g2.setColor(background);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		// Setting the color for the following components
		g2.setColor(new Color(200, 200, 200));
		
		// Fonts for score and lives
		Font currentFont = g2.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 2F);
		g2.setFont(newFont);
		
		this.drawGrid(g2);
		
		// Calls the method for drawing the ball
		drawBall(g2);
		
		// Draw the level paddle
		drawPaddle(g2);
		
		// Draw the level stones
		drawStones(g2);
		
		// Draw the current score
		drawScore(g2);
		
		// Draw the current lives
		drawLives(g2);
	}

	/**
	 * Draws the ball
	 * @param g2 The graphics object
	 */
	private void drawBall(Graphics2D g2) {
		g2.fillOval((int) view.getGame().getLevel().getBall().getPosition().getX(),
				(int) view.getGame().getLevel().getBall().getPosition().getY(),
				Constants.BALL_DIAMETER,
				Constants.BALL_DIAMETER);
	}
	/**
	 * Draws the paddle
	 * @param g2 The graphics objects
	 */
	private void drawPaddle(Graphics2D g2) {
		g2.setColor(view.getGame().getLevel().getPaddle().getColor());
		g2.fillRoundRect(
			(int) view.getGame().getLevel().getPaddle().getPosition().getX(), 
			(int) view.getGame().getLevel().getPaddle().getPosition().getY(), 
			(int) view.getGame().getLevel().getPaddle().getWidth(), 
			(int) view.getGame().getLevel().getPaddle().getHeight(), 
			0, 0
		);
	}
	
	/**
	 * Draw the level stones
	 * @param g2 The graphics objects
	 */
	private void drawStones(Graphics2D g2) {
		ArrayList<Stone> stones = view.getGame().getLevel().getStones();
		for(int i = 0; i < stones.size(); i++) {
			g2.setColor(stones.get(i).getColor());
			g2.fillRoundRect((int)(stones.get(i).getPosition().getX() + 1),(int)(stones.get(i).getPosition().getY() + 1),
            		(Constants.SCREEN_WIDTH/Constants.SQUARES_X) - 2, 
            		(Constants.SCREEN_HEIGHT/Constants.SQUARES_Y) - 2, 10, 10);
		}
	}
	
	/**
	 * Draws the grid
	 * @param g2 The graphics object
	 */
	public void drawGrid(Graphics2D g2) {
		for(int i = 1; i < Constants.SQUARES_Y; i++) {
			g2.drawLine(0, i * (Constants.SCREEN_HEIGHT / Constants.SQUARES_Y), Constants.SCREEN_WIDTH, i * (Constants.SCREEN_HEIGHT / Constants.SQUARES_Y));
		}
		
		for(int i = 0; i < Constants.SQUARES_X; i++) {
			g2.drawLine(i * (Constants.SCREEN_WIDTH / Constants.SQUARES_X), 0, i * (Constants.SCREEN_WIDTH / Constants.SQUARES_X), Constants.SCREEN_HEIGHT);
		}
	}

	/**
	 * Draw the current score
	 * @param g2
	 */
	private void drawScore(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.drawString("Score: " + view.getGame().getLevel().getScore(), 10, 30);
	}
	
	/**
	 * Draw the current number of lives
	 * @param g2
	 */
	private void drawLives(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.drawString("Lives: " + view.getGame().getLevel().getLifeCounter(), 10, 60);
	}
}
