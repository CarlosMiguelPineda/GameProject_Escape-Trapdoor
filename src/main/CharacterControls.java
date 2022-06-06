package com.trapdoor_escape.src.main;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * <b><i>CharacterControls</i></b> implements the interface KeyListener and overrides its three methods.
 * This class facilitates the interaction of the <b><i>Player</i></b> and <b><i>objectItem</i></b>, 
 * and sets the allowable moves for the <b><i>Player</i></b> depending on the tileType where it is going.
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 05 JUN 2022
 */
public class CharacterControls implements KeyListener{ 
	
	/*
	 * implements a built-in interface for receiving keyboard events. Overrides three methods.
	 */
	
	/**
	 * stores true when up arrow is pressed
	 */
	public static boolean upButton;
	
	/**
	 * stores true when the down arrow is pressed
	 */
	public static boolean downButton; 
	
	/**
	 * stores true when the right arrow is pressed
	 */
	public static boolean rightButton; 
	
	/**
	 * stores true when the left arrow is pressed
	 */
	public static boolean leftButton; 
	
	/**
	 * stores true when the space button is pressed
	 */
	public static boolean spaceButton;
	
	private static GamePanel gamePanel;
	
	/**
	 * Constructor for instantiating <b><i>CharacterControls</i></b>
	 * @param gamePanel - serves as the communication channel for other classes. 
	 */
	public CharacterControls(GamePanel gamePanel) {
		CharacterControls.gamePanel = gamePanel;
	}
	
	
	/**
	 * Since this is the first implementation of KeyLister, we need to set-up all of its methods
	 * even the unused one. We keep this one empty since we are using the keyPressed for controls.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	/**
	 * Updates and sets the button variables true whenever its corresponding key is pressed. Constant 
	 * for numeric keypad is used to identify the keys.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode(); 
		
		switch(key) { 
		case KeyEvent.VK_UP:
			upButton = true;
			break;
		case KeyEvent.VK_DOWN:
			downButton = true;
			break;
		case KeyEvent.VK_RIGHT:
			rightButton = true;
			break;
		case KeyEvent.VK_LEFT:
			leftButton = true;
			break;
		case KeyEvent.VK_SPACE:
			spaceButton = true;
			checkObjectItem();
			break;
		default:	
		}
	}
	
	/**
	 * Updates the sets the button variables false whenever its corresponding key is released or not pressed.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode(); 
		
		switch(key) { 
		case KeyEvent.VK_UP:
			upButton = false;
			break;
		case KeyEvent.VK_DOWN:
			downButton = false;
			break;
		case KeyEvent.VK_RIGHT:
			rightButton = false;
			break;
		case KeyEvent.VK_LEFT:
			leftButton = false;
			break;
		case KeyEvent.VK_SPACE:
			spaceButton = false;
			break;
		default:
		}
	}
	
	/**
	 * Sets up the interaction of the <b><i>Player</i></b> and a specific <b><i>objectItem</i></b> if they
	 * intersect. The interaction will be reflected on the <b><i>userInterface</i></b> and followed
	 * by playing a special effect. Each objectItem has a specific interaction to the player. 
	 * If it is a spirit, player's color suit transforms to the color of the spirit. 
	 * If it locked trapdoor, then player can only open it if their color is matched. 
	 * And if it is unlocked, then the player can exit the floor and go to the next floor.
	 */
	public void checkObjectItem() { 
		boolean objectExist = false;
		
		for(int index = 0; index < gamePanel.objectItem.length ; index++) { 
			
			/*Checks if there is an object on the tile of the player*/
			if(gamePanel.player.getAbscissa() == gamePanel.objectItem[index].getAbscissa() 
					&& gamePanel.player.getOrdinate() == gamePanel.objectItem[index].getOrdinate()) {
				objectExist = true;
				
				/*Identifies the specific objectItem*/
				switch(gamePanel.objectItem[index].getObjectName()) {
				case "spiritRed":	
					gamePanel.userInterface.showMessage("Player turns red!");
					gamePanel.player.setColor("Red");
					gamePanel.playSoundEffect(2);
					break;
				case "spiritBlue":
					gamePanel.userInterface.showMessage("Player turns blue!");
					gamePanel.player.setColor("Blue");
					gamePanel.playSoundEffect(2);
					break;
				case "spiritYellow":
					gamePanel.userInterface.showMessage("Player turns yellow!");
					gamePanel.player.setColor("Yellow");
					gamePanel.playSoundEffect(2);
					break;
				case "trapdoorRed":
					
					 if(gamePanel.player.getColor() == "Red") {
						 gamePanel.objectItem[index].update();
					 } else {
						 gamePanel.userInterface.showMessage("Find a spirit to help you open the trapdoor!");
						 gamePanel.playSoundEffect(4);
					 }
					 break;
				case "trapdoorBlue":
					
					if(gamePanel.player.getColor() == "Blue") { 
						gamePanel.objectItem[index].update();
					 } else {
						 gamePanel.userInterface.showMessage("Find a spirit to help you open the trapdoor!");
						 gamePanel.playSoundEffect(4);
					 }
					break;
				case "trapdoorYellow": 
					
					if(gamePanel.player.getColor() == "Yellow") { 
						gamePanel.objectItem[index].update();
					 } else {
						 gamePanel.userInterface.showMessage("Find a spirit to help you open the trapdoor!");
						 gamePanel.playSoundEffect(4);
					 }
					break;
				case "trapdoorUnlocked":
					gamePanel.userInterface.showMessage("Player goes down!");
					gamePanel.playSoundEffect(3);
					
					if(UserInterface.floorLevel <= 3) { 	/*We only set up to three levels*/
						UserInterface.floorLevel ++;
						if(UserInterface.floorLevel <= 3) {	
							gamePanel.reset();
						}
						
					}
					break;
				default:
					break;
				}
			}
		}
		
		if(objectExist == false) {
			gamePanel.userInterface.showMessage("There is no object on the floor!");
			gamePanel.playSoundEffect(2);
		}
	}
}
