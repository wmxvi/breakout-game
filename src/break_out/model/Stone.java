package break_out.model;

import java.awt.Color;
import break_out.Constants;

/**
 * This class contains the information about the stones characteristics and behavior
 * 
 * @author Nele Meyer and Drenusha Emrulahu
 */

public class Stone {
	
	/**
	 *The stone type
	 */
	private int type;
	
	/**
	 * The stone value
	 */
	private int value;

	/**
	 * The stone color
	 */
	private Color color;
	
	/**
	 *  The stones position
	 */
	private Position position;
	
	
	/**
	 * The constructor of a stone
	 * The stones type and position are initialized here.
	 */
	public Stone(int type, Position pos) {
		if(type == 0) {
			return;
		}
		
		this.setType(type);
		this.setPosition(pos);
		
		//System.out.println("Created new stone: " + type);
	}
	
	/**
	 * Gets stone type
	 * @return type New stone type
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Sets the new stone type
	 * @param type New stone type
	 */
	public void setType(int type) {
		this.type = type;
		
		this.setValue(2 * this.type);
		
		switch(this.type) {
			case 1:
				this.color = Constants.STONE_TYPE_1;
				break;
			case 2:
				this.color = Constants.STONE_TYPE_2;
				break;
			case 3:
				this.color = Constants.STONE_TYPE_3;
				break;
			case 4:
				this.color = Constants.STONE_TYPE_4;
				break;
		}
	}
	
	/**
	 * Gets stone value
	 * @return value New stone value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets stone value
	 * @param value New stone value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Gets stone color
	 * @return color New stone color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Sets stone color
	 * @param color New stone color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Gets stone position
	 * @return position New Stone-Position
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * Sets stones position
	 * @param position New Stone-Position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
}
