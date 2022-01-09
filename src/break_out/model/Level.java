package break_out.model;

import break_out.Constants;
import break_out.controller.JSONReader;
import break_out.view.StartScreen;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * This class contains information about the running game
 * 
 * @author dmlux, modified by Nele Meyer and Drenusha Emrulahu
 * @author I. Schumacher
 */
public class Level extends Thread {

    /**
     * The game to which the level belongs 
     */
    private Game game;
	 
    /**
   	 * The number of the level
   	 */
    private int levelnr;

	/**
	 * The score of the level
	 */
    private int score;
    
    /**
     * Number of lives
     */
    
    private int lifeCounter;
    
    /**
     * The ball of the level
     */
    private Ball ball;
    
    /**
     * Current game stones
     */
    private ArrayList<Stone> stones = new ArrayList<>();
    
    /**
     * The paddle of the level
     */
    private Paddle paddle;
    
    /**
     * Flag that shows if the ball was started
     */
    private boolean ballWasStarted = false;
    
    /**
     * Flag that shows if the game is finished
     */
    private boolean finished = false;
   
    /**
     * The constructor creates a new level object and needs the current game object, 
     * the number of the level to be created and the current score
     * @param game The game object
     * @param levelnr The number of the new level object
     * @param score The score
     */
    public Level(Game game, int levelnr, int score) {
    	this.game = game;
    	this.levelnr = levelnr;
    	this.score = score;
    	this.paddle = new Paddle();
    	
        this.ball = new Ball();
        
        this.loadLevelData(this.levelnr);
    }

    /**
     * The getter for the ball object
     * @return ball The ball of the level
     */
    public Ball getBall() {
    	return this.ball;
    }
   
    /**
     * Get the level paddle
     * @return Paddle
     */
    public Paddle getPaddle() {
    	return this.paddle;
    }
    
    /**
     * Get the game level stones
     * @return ArrayList<Stone>
     */
    public ArrayList<Stone> getStones() {
    	ArrayList<Stone> copy = new ArrayList<>();
    	copy.addAll(stones);
    	return copy;
    }
    
    /**
     * The getter for the score
     * @return score The score of the level
     */
    public int getScore() {
    	return score;
    }
    
    /**
     * Sets ballWasStarted to true, the ball is moving
     */
    public void startBall() {
        ballWasStarted = true;
    }

    /**
     * Sets ballWasStarted to false, the ball is stopped
     */
    public void stopBall() {
        ballWasStarted = false;
    }
    
    /**
     * Returns if the ball is moving or stopped
     * @return ballWasStarted True: the ball is moving; false: the ball is stopped
     */
    public boolean ballWasStarted() {
        return ballWasStarted;
    }

    /**
     * The method of the level thread
     */
    public void run() {	
    	game.notifyObservers();
    		
    	// endless loop 
    	while (!finished) {
    		// if ballWasStarted is true, the ball is moving
	        if (ballWasStarted) {
	                
	        	// Call here the balls method for updating his position on the playground
	        	this.ball.updatePosition();
	            		            	
	        	// Call here the balls method for reacting on the borders of the playground
		        this.ball.reactOnBorder();
		        
		        // ruft Methode auf, um zu prüfen, ob das Paddle getroffen wurde
	        	if(this.ball.hitsPaddle(paddle)) {
	        		this.ball.reflectOnPaddle(paddle);
	        	}
	        	// prueft ob ein Stein getroffen wurde
	        	if(this.ball.hitsStone(stones)) {
	        		this.updateStonesAndScore();
	        	}
	        	
	        	if(this.ball.isLost()) {
	        		this.decreaseLives();
	        		this.ball = new Ball();
	        		this.paddle = new Paddle();
	        	}
	     
	        	if(this.allStonesBroken()) {
	        		this.levelnr++;
	        		this.game.getLevel().finished = true;
	        		this.game.createLevel(levelnr, score);
	        	}
	        	
	        	this.paddle.updatePosition(this.paddle.getDirection());
	        
	            // Tells the observer to repaint the components on the playground
	            game.notifyObservers();    
	        }
	        // The thread pauses for a short time 
	        try {
	            Thread.sleep(4);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
    	}   
    }
    
    /**
    * Loads the information for the level from a json-file located in the folder /res of the project
    * @param levelnr The number X for the LevelX.json file
    */
    private void loadLevelData(int levelnr) {
    	String levelFileName = "res/Level" + levelnr + ".json";
    	JSONReader jsonReader = new JSONReader(levelFileName);
    	
    	int[][] loadedStones = jsonReader.getStones2DArray();
    	
    	this.lifeCounter = jsonReader.getLifeCounter();
    	
    	for(int x = 0; x < loadedStones.length; x++ ) {
    		for(int y = 0; y < loadedStones[x].length; y++) {
    			int stoneType = loadedStones[x][y];
    			
    			if(stoneType > 0) {
    				Position stonePosition = new Position((y * (Constants.SCREEN_WIDTH/Constants.SQUARES_X)), ( x * (Constants.SCREEN_HEIGHT/Constants.SQUARES_Y)));
	    			Stone stone = new Stone(stoneType, stonePosition);
	    			
	    			this.stones.add(stone);
    			}
			}
    	}
    }
    
    /**
     * Setzt finished sobald die Steine fertig geladen sind
     * @param finished
     */
    public void setFinished(boolean finished) {
    	this.finished = finished;
    }
    
    /**
     * Passt den Typ des Steins und den Punktestand an
     */
    private void updateStonesAndScore() {
    	score += ball.getHitStone().getValue();
    	
    	if(ball.getHitStone().getType() > 1) {
    		if(ball.getHitStone().getType() == 4) {
    			this.getPaddle().setWidth(this.getPaddle().getWidth() * 1.5);
    			this.stones.remove(ball.getHitStone());
    		} else {
    			ball.getHitStone().setType(ball.getHitStone().getType() - 1);
    		}
    	} else {
    		this.stones.remove(ball.getHitStone());
    	}
    }
    
    /**
     * Prueft, ob alle Steine getroffen wurden
     * @return boolen Gibt true zurueck, wenn alle Steine getroffen sind
     */
    private boolean allStonesBroken() {
    	return this.stones.isEmpty();
    }
    
    /**
     * Decrease the player lives
     */
    private void decreaseLives() {
    	this.lifeCounter--;
    	
    	if(this.lifeCounter < 1) {
    		// Game lost
    		this.lifeCounter = 0;
    		this.finished = true;
    		this.game.getController().toStartScreen();
    		return;
    	}
    	
    	this.ballWasStarted = false;
    }
    
    /**
	 * @return the lifeCounter
	 */
	public int getLifeCounter() {
		return lifeCounter;
	}

	/**
	 * @param lifeCounter the lifeCounter to set
	 */
	public void setLifeCounter(int lifeCounter) {
		this.lifeCounter = lifeCounter;
	}
}
    


	
