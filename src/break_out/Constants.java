package break_out;

import java.awt.Color;

/**
 * A class that contains all constant values to configure the game
 *
 * @author dmlux, modified by I. Schumacher
 *
 */
public class Constants {

    /**
     * The playground width in pixels
     */
    public static final Integer SCREEN_WIDTH = 800;

    /**
     * The playground height in pixels
     */
    public static final Integer SCREEN_HEIGHT = 600;

    /**
     * the application name
     */
    public static final String APP_TITLE = "iBreakOut";

    /**
     * Debugging flag for special rendering hints
     */
    public static final boolean DEBUG_MODE = false;

    /**
     * The background color for the game menu
     */
    public static final Color BACKGROUND = new Color(52, 152, 219);

    /**
     * Color for stone type 1
     */
    public static final Color STONE_TYPE_1 = new Color(221,160,221);
   
    /**
     * Color for stone type 2
     */
    public static final Color STONE_TYPE_2 = new Color(255,192,203);
    
    /**
     * Color for stone type 3
     */
    public static final Color STONE_TYPE_3 = new Color(255,99,71);
    
    /**
     * Color for stone type 3
     */
	public static final Color STONE_TYPE_4 = new Color(175,248,219);
    
    /**
     * Amount of columns for blocks
     */
    public static final Integer SQUARES_X = 20;

    /**
     * Amount of the rows
     */
    public static final Integer SQUARES_Y = 24;

    /**
     * The paddle width in pixels
     */
    public static final Integer PADDLE_WIDTH = 80;

    /**
     * The paddle height in pixels
     */
    public static final Integer PADDLE_HEIGHT = 15;

    /**
     * The distance between paddle and the lower reflection offset.
     */
    public static final Double REFLECTION_OFFSET = 35.0;

    /**
     * The ball diameter in pixels
     */
    public static final Integer BALL_DIAMETER = 15;

    /**
     * The paddle speed
     */
    public static final Double DX_MOVEMENT = 4.5;

    /**
     * The ball speed
     */
    public static final Double BALL_SPEED = 1.2;

}