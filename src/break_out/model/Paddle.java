package break_out.model;

import break_out.Constants;
import java.awt.Color;

/**
 * This class contains the information about the paddles characteristics and behavior
 * 
 * @author Nele Meyer and Drenusha Emrulahu
 */

public class Paddle {
	
	/**
	 * The paddles position on the playground
	 */
	private Position position;
	
	/**
	 * Paddle height
	 */
	private double height = Constants.PADDLE_HEIGHT;
	
	/**
	 * Paddle width
	 */
	private double width = Constants.PADDLE_WIDTH;
	
	/**
	 * Paddle color
	 */
	private Color color = Color.WHITE;
	
	/**
	 * Paddle direction
	 */
	private int direction;
	
	public Paddle() {
		this.position = new Position(
			(Constants.SCREEN_WIDTH/2) - (Constants.PADDLE_WIDTH/2), 
			Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT
		);
	}
	
	/**
	 * Gets paddle position
	 * @return	position New Paddle-Position
	 */
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * Sets paddle position
	 * @param position New Paddle-Position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/**
	 * Gets paddle height
	 * @return height New Paddle-Height
	 */
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * Sets paddle height
	 * @param height New Paddle-Height
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	/**
	 * Gets paddle width
	 * @return width New Paddle-Width
	 */
	public double getWidth() {
		return this.width;
	}	
	
	/**
	 * Sets paddle width
	 * @param width New Paddle-Width 
	 */
	public void setWidth(double width) {
		this.width = width;
	}
	
	/**
	 * Gets paddle color
	 * @return Color The new color
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Sets paddle color
	 * @param color The new color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Gets paddle direction
	 * @return direction Aktuelle Paddle-Richtung
	 */
	public int getDirection() {
		return this.direction;
	}
	
	/**
	 * Sets paddle direction
	 * @param direction Aktuelle Paddle-Richtung
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	/**
	 * updates paddle position if the value of direction changes
	 * @param direction Aktuelle Paddle-Richtung
	 */
	public void updatePosition(int direction) {
		if(this.direction == -1 && this.position.getX() > 0) {
			this.position.setX(this.position.getX()-Constants.DX_MOVEMENT);
		}
		else if(this.direction == 1 && this.position.getX() < Constants.SCREEN_WIDTH-Constants.PADDLE_WIDTH) {
			this.position.setX(this.position.getX()+Constants.DX_MOVEMENT);
		}
	}
}
