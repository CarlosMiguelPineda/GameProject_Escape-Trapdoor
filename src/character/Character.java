package com.trapdoor_escape.src.character;


import java.awt.image.BufferedImage;


/**
 * This is a superclass for all active entities. For this version, only player subclass has been developed but for 
 * future reference, we can add subclasses of enemies and non-player character (NPC).
 * 
 * <p>
 * A <b><i>Character</i></b> is a superclass object that interacts with any <b><i>objectItem</i></b> in the game.
 * It can be controlled and animated using the arrow keys as long as there is no collision. Properties are encapsulated.
 * Each <b><i>Character</i></b> consumes one tile of area.
 * </p>
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 05 JUN 2022
 */
public class Character {
	/*properties for moving.*/
	protected int abscissa; 
	protected int ordinate; 
	protected int speed;
		
	/*properties for animating the moves*/
	protected BufferedImage up1;
	protected BufferedImage up2; 
	protected BufferedImage down1;
	protected BufferedImage down2;
	protected BufferedImage right1;
	protected BufferedImage right2; 
	protected BufferedImage left1; 
	protected BufferedImage left2; 
	protected String direction;
	protected String color;
	protected int spriteNumber = 0;
		
	/**
	 * stores true if it player can move in the direction.
	 */
	public boolean isCollide = false;
	
	/**
	 * Gives access for getting the abscissa of the instance <b><i>Character</i></b>.
	 * @return the x-component of the Character's position from the leftmost of the panel in terms of pixels.
	 */
	public int getAbscissa() {
		return abscissa;
	}
	
	/**
	 * Gives access for getting the ordinate of the instance <b><i>Character</i></b>.
	 * @return the y-component of the Character's position from the topmost of the panel in terms of pixels.
	 */
	public int getOrdinate() {
		return ordinate;
	}
	
	/**
	 * Gives access for getting the direction when the <b><i>Character</i></b>. is about to move.
	 * @return one of the strings: up, down, right, and left.
	 */
	public String getDirection() {
		return direction;
	}
	
	/**
	 * Gives access for getting the color suit of the instance of <b><i>Character</i></b>.
	 * @return one of the strings: red, blue, and yellow
	 */
	public String getColor() {
		return color;
	}
	
	/**
	 * Gives access for setting the color suit of the instance of <b><i>Character</i></b>
	 *  after interacting a <b><i>Spirit</i></b>.
	 * @param color - Defines the current color suit of the Character.
	 */
	public void setColor(String color) {
		this.color = color;
	}
}

