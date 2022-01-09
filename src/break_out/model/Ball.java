package break_out.model;

import break_out.Constants;
import java.util.ArrayList;
import java.awt.*;

/**
 * This class contains the information about the balls characteristics and behavior
 * 
 * @author iSchumacher, modified by Nele Meyer and Drenusha Emrulahu
 *
 */
public class Ball {

	/**
	 * The balls position on the playground
	 */
	private Position position;
	
	/**
	 * The balls direction
	 */
	private Vector2D direction;
	
	/**
	 * getroffener Stein
	 */
	private Stone hitStone;
	
	/**
	 * The constructor of a ball
	 * The balls position and direction are initialized here.
	 */
	public Ball() {
		this.position = new Position(
			(Constants.SCREEN_WIDTH/2) - (Constants.BALL_DIAMETER/2), 
			Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER - Constants.PADDLE_HEIGHT
		);
		
		this.direction = new Vector2D(3, 3);
		
		this.direction.normalize();
		this.direction.scale(Constants.BALL_SPEED);
	}
	
	/**
	 * The getter for the balls position
	 * @return position The balls current position
	 */
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * The getter for the balls direction
	 * @return direction The balls current direction
	 */
	public Vector2D getDirection() {
		return this.direction;
	}
	
	/**
	 * Position wird konstant angepasst
	 */
	public void updatePosition() {
		this.position.setX(this.position.getX() + this.direction.getDx());
		this.position.setY(this.position.getY() + this.direction.getDy());
	}
	
	/**
	 * Detect if the ball has been lost
	 * @return boolean whether ball is lost
	 */
	public boolean isLost() {
		return ((this.getPosition().getY() + Constants.BALL_DIAMETER + 0.5) > (Constants.SCREEN_HEIGHT));
	}
	
	/**
	 * Ball reagiert an den Rändern nach dem Prinzip Einfallswinkel = Ausfallswinkel
	 */
	public void reactOnBorder() {
		if(this.position.getX() < 0) {
			this.position.setX(0);
			this.direction.setDx(-this.direction.getDx());
			this.direction.setDy(this.direction.getDy());
		}
		
		if(this.position.getY() < 0) {
			this.position.setY(0);
			this.direction.setDx(this.direction.getDx());
			this.direction.setDy(-this.direction.getDy());
		}
		
		
		if(this.position.getX() > (Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER)) {
			this.position.setX(Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER);
			this.direction.setDx(-this.direction.getDx());
			this.direction.setDy(this.direction.getDy());
		}
		
		if(this.position.getY() > (Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER)) {
			this.position.setY(Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER);
			this.direction.setDx(this.direction.getDx());
			this.direction.setDy(-this.direction.getDy());
		}
	}
	/**
	 * gibt zurück, ob der Ball das Paddle trifft, wenn ja wird reflectOnPaddle(p) aufgerufen
	 * @param p  Aktuelles Paddle-Objekt
	 * @return Boolean Gibt true oder false zurück
	 */
	public boolean hitsPaddle(Paddle p) {
		if((this.position.getX() + Constants.BALL_DIAMETER) >= p.getPosition().getX() && this.position.getX() <= (p.getPosition().getX() + Constants.PADDLE_WIDTH)) {
			if(this.position.getY() + Constants.BALL_DIAMETER >= Constants.SCREEN_HEIGHT-Constants.PADDLE_HEIGHT) {
				return true;
			}
			else {
				return false;
			} 
		}
		else {
			return false;
		}
	}
	
	/**
	 * ändert die Richtung vom Ball je nach Auftreffpunkt
	 * @param p Aktuelles Paddle-Objekt
	 */
	public void reflectOnPaddle(Paddle p) {
		double startpunktX = p.getPosition().getX() + Constants.PADDLE_WIDTH/2;
		double startpunktY = p.getPosition().getY() + Constants.REFLECTION_OFFSET;
		Position startpunkt = new Position(startpunktX, startpunktY);
		double endpunktX = this.position.getX() + Constants.BALL_DIAMETER/2;
		double endpunktY = this.position.getY() + Constants.BALL_DIAMETER/2;
		Position endpunkt = new Position(endpunktX, endpunktY);
		Vector2D neueRichtung = new Vector2D(endpunkt, startpunkt);
		
		this.direction.setDx(neueRichtung.getDx());
		this.direction.setDy(neueRichtung.getDy());
		this.direction.normalize();
		this.direction.scale(Constants.BALL_SPEED);
	}
	
	/**
	 * Prüft, ob der Ball einen Stein trifft
	 * @param stones ArrayList der vorhandenen Steine
	 * @return boolean gibt zurueck, ob ein Stein getroffen wurde
	 */
	public boolean hitsStone(ArrayList<Stone> stones){
		boolean getroffen = false;
		Rectangle ballRect = new Rectangle((int)this.getPosition().getX() ,
				(int) this.getPosition().getY(), Constants.BALL_DIAMETER, Constants.BALL_DIAMETER);
		for(int i =0; i < stones.size(); i++) {
		    Rectangle stoneRect = new Rectangle((int)stones.get(i).getPosition().getX(),(int)stones.get(i).getPosition().getY(),
				                  Constants.SCREEN_WIDTH/Constants.SQUARES_X, Constants.SCREEN_HEIGHT/Constants.SQUARES_Y);
		   
		    if(stoneRect.intersects(ballRect)) {
			    getroffen = true;
			    hitStone = stones.get(i);
			    reactOnStone(ballRect, stoneRect);
		    }
		}
		return getroffen;
	}
	
	/**
	 * Gibt den Index des getroffenen Steins zurueck
	 * @return Stone der getroffene Stein
	 */
	public Stone getHitStone(){
		return hitStone;
	}
	
	/**
	 * Laesst den Ball am Stein abprallen
	 * @param ballRect Rechteck um den Ball
	 * @param stoneRect Rechteck um den Stein
	 */
	private void reactOnStone(Rectangle ballRect, Rectangle stoneRect){
		if(stoneRect.intersection(ballRect).height < stoneRect.intersection(ballRect).width){
			this.direction.setDy(-this.direction.getDy());
		}
		else if(stoneRect.intersection(ballRect).height > stoneRect.intersection(ballRect).width){
			this.direction.setDx(-this.direction.getDx());
		}
		else if(stoneRect.intersection(ballRect).height == stoneRect.intersection(ballRect).width){
			this.direction.setDx(-this.direction.getDx());
			this.direction.setDy(-this.direction.getDy());
		}
	}
}
