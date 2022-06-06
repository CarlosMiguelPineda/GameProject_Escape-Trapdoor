package com.trapdoor_escape.src.main;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import com.trapdoor_escape.src.character.Player;


/**
 * <b><i>UserInterface</i></b> provides a channel for the user and the game to communicate every time
 * an interaction or event happens. It displays essential information for the user.
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.2
 * @since 05 JUN 2022
 */
public class UserInterface{
	private static GamePanel gamePanel;
	private static Font arial_25;
	private static Font arial_20;
	private static Font arial_40;
	private static BufferedImage moveCounterImage;
	private static BufferedImage timerImage;
	
	/**
	 * stores the current floor level of the user (max 3). 
	 */
	public static int floorLevel;
	
	/**
	 * stores the time limit before the loser will lose.
	 */
	static final int TIME_LIMIT = 100;
	
	/**
	 * stores the total time the user before the user had won.
	 */
	static double playTime;
	static DecimalFormat playTimeFormat = new DecimalFormat("#0.00");
	
	/**
	 * stores true if there is an incoming message from an interaction otherwise false.
	 */
	static public boolean turnMessage; 
	static int messageDuration;
	
	/**
	 * stores what happened during the interaction or event. 
	 */
	public String message;
	
	/**
	 * Initializes the different font style, moveCounterImage, timerImage, floorLevel and set turnMessage
	 * to false by default. 
	 * @param gPanel - serves as the communication channel for other classes. 
	 */
	public UserInterface(GamePanel gPanel) {
		UserInterface.gamePanel = gPanel;
		arial_25 = new Font("Arial", Font.PLAIN, 25);
		arial_20 = new Font("Arial", Font.PLAIN, 20);
		arial_40 = new Font("Arial", Font.BOLD, 40);
		
		try {
			moveCounterImage = ImageIO.read(getClass().getResourceAsStream("/com/trapdoor_escape/drawings/icon/moveCounter.png"));
			timerImage = ImageIO.read(getClass().getResourceAsStream("/com/trapdoor_escape/drawings/icon/timer.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}

		floorLevel = 1;
		turnMessage = false;
		playTime = TIME_LIMIT;
	}
	
	/**
	 * Displays the message to the user.
	 * @param message - interaction between the player and any objectItem.
	 */
	public void showMessage(String message) { //this will be called every time the player interacts with the objectItem. 
		this.message = message;
		turnMessage = true;
	}
	
	/**
	 * Writes the interaction or event that has happened to inform the user.
	 * An interaction happens between the player and objectItem.
	 * An event happens when the user has won or lost.
	 * @param g2 - A Graphics2D that allows several methods.
	 */
	public void draw(Graphics2D g2) {
		int messageAbscissa;
		int messageOrdinate;
		int messageLength;

		if(playTime <= 0 || floorLevel > 3) { 	/*sets up the event when the user has won or lost*/
			UserInterface.gamePanel.stopAudio();
			
			if(floorLevel > 3) { 				/*event specifically when the user has won*/
				UserInterface.gamePanel.playBackgroundMusic(6);
				message = "Your time is " + (int)(100 - playTime) +"s";
				g2.setFont(arial_25); 
				g2.setColor(Color.yellow);
				
				/*We want to display the game time of the user in the center of the x-axis.*/ 
				messageLength = (int) g2.getFontMetrics().getStringBounds(message, g2).getWidth(); 
				messageAbscissa = (GamePanel.SCREEN_WIDTH / 2) - (messageLength / 2);
				messageOrdinate = 300;
				g2.drawString(message, messageAbscissa, messageOrdinate);
				
				message = "CONGRATULATIONS!!"; 	/*set again another message*/
			} else { 							/*sets up the event when the user has lost.*/
				UserInterface.gamePanel.playBackgroundMusic(6);
				message = "YOU LOSE!";
				g2.setColor(Color.red);
			}
			
			g2.setFont(arial_40); 
			messageLength = (int) g2.getFontMetrics().getStringBounds(message, g2).getWidth(); 
			messageAbscissa = (GamePanel.SCREEN_WIDTH / 2) - (messageLength / 2); 
			messageOrdinate = 270;
			g2.drawString(message, messageAbscissa, messageOrdinate);
			
			UserInterface.gamePanel.gameThread = null; 
			/*This marks the end of the game!*/
		}
		
		else {									
			
			/*drawing the move icon and move counter value*/
			g2.setColor(Color.white);	
			g2.setFont(arial_25);
			g2.drawImage(moveCounterImage, 25, GamePanel.TILE_SIZE * 11, GamePanel.TILE_SIZE / 2,
					GamePanel.TILE_SIZE / 2, null);
			g2.drawString(":" + Player.moveCounter + "/" + Player.MOVE_LIMIT, 50, 550); 
			
			/*drawing the level display*/
			g2.setColor(Color.red);	
			g2.drawString("Level " + floorLevel, 225, 550);
			
			/*drawing the timer icon and playTime value*/
			g2.setColor(Color.white);	
			playTime -= (double) 1 / 7;				/*we set our FPS = 7, hence, 1s takes 7 updated frames*/
			g2.setFont(arial_25);
			g2.drawImage(timerImage, 415, 530, GamePanel.TILE_SIZE / 2, GamePanel.TILE_SIZE / 2, null);
			g2.drawString(":" + playTimeFormat.format(playTime), 440, 550); 
			
			if(turnMessage == true) { 				/*sets up the message for a specific interaction*/
				g2.setFont(arial_20);
				g2.setColor(Color.red);
				messageLength = (int) g2.getFontMetrics().getStringBounds(message, g2).getWidth(); 
				messageAbscissa = (GamePanel.SCREEN_WIDTH / 2) - (messageLength / 2); 
				messageOrdinate = 580;
				g2.drawString(message, messageAbscissa, messageOrdinate);
				messageDuration++; 
				
				/*take note that we set our FPS = 7, then we want our message to last only for 2s, thats why 14.*/
				if(messageDuration > 14) {
					messageDuration = 0;
					turnMessage = false;
				}
			}
		}
		
	}
}
