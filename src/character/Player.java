package com.trapdoor_escape.src.character;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.trapdoor_escape.src.main.CharacterControls;
import com.trapdoor_escape.src.main.GamePanel;

/**
 * <b><i>Player</i></b> a subclass of <b><i>Character</i></b>. It is controlled by the user through<b><i>CharacterControls</i></b>
 * can interact with the <b><i>ObjectItem</i></b> to find and exit through the <b><i>Trapdoor</i></b> before the time runs out. 
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 06 JUN 2022
 */
public class Player extends Character{
	private static GamePanel gamePanel;
	
	/**
	 * stores how many moves the user has done and resets 0 when it reaches to 30.
	 */
	public static int moveCounter;

	/**
	 * stores the number of moves before objects will be displaced again
	 */
	public static final int MOVE_LIMIT = 30;
	
	/**
	 * Constructor for new <b><i>Player</i></b> with default white suit and resets the moveCounter.
	 * @param gamePanel - serves as the communication channel for other classes. 
	 * 
	 * @see #setCharacterDefault()
	 * @see #getPlayerImage()
	 */
	public Player (GamePanel gamePanel) { 
		Player.gamePanel = gamePanel; 
		moveCounter = 0;
		setCharacterDefault();
		getPlayerImage();
	}
	
	/**
	 * Defines the initial position of the <b><i>Player</i></b>, which is in the center of the playing field.
	 * Let initial direction be an arbitrary direction. 
	 * Let initial speed be the pixel for one tile(48 pixels), and initial color be the white suit.
	 */
	public void setCharacterDefault() {
		abscissa = GamePanel.TILE_SIZE * 5; 
		ordinate = GamePanel.TILE_SIZE * 5;
		speed = GamePanel.TILE_SIZE;
		direction = "down"; 
		color = "White";
	}
	
	/**
	 * Defines the current color and direction of the <b><i>Player</i></b>. Color and direction are subject to change when
	 * encountering spirits and key arrow commands, respectively. Atleast two images are needed for each direction
	 * to create an animation of walking.
	 */
	public void getPlayerImage() { 
		String folderDirectory = "/com/trapdoor_escape/drawings/";
		
		try {
			switch (color) { 
			case "White":
				up1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "white_player/up1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "white_player/up2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "white_player/down1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "white_player/down2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "white_player/right1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "white_player/right2.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "white_player/left1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "white_player/left2.png"));
				break;	
			case "Red":
				up1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "red_player/up1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "red_player/up2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "red_player/down1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "red_player/down2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "red_player/right1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "red_player/right2.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "red_player/left1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "red_player/left2.png"));
				break;
			case "Blue":
				up1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "blue_player/up1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "blue_player/up2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "blue_player/down1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "blue_player/down2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "blue_player/right1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "blue_player/right2.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "blue_player/left1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "blue_player/left2.png"));
				break;
			case "Yellow":
				up1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "yellow_player/up1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "yellow_player/up2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "yellow_player/down1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "yellow_player/down2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "yellow_player/right1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "yellow_player/right2.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "yellow_player/left1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "yellow_player/left2.png"));
				break;	
			default: 
				break;	
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Changes the abscissa and ordinate of the <b><i>Player</i></b> depending on the given direction. Before making a move, it 
	 * detects if there will be a collision for the tile to move through the <b><i>CollisionDetection</i></b>. 
	 * After moving, it increases the moveCounter until it reaches the MOVE_LIMIT, where it calls <i>restart()</i>.
	 * 
	 * @see #restart()
	 */
	public void update() { //changes the position of the character when the controls are moved
		
		if(CharacterControls.upButton || CharacterControls.downButton 
		        || CharacterControls.leftButton || CharacterControls.rightButton == true 
				|| CharacterControls.spaceButton == true) {	/*we dont want our player to walk stationarily.*/
			
			if(CharacterControls.upButton == true) {
				direction = "up";
			} else if(CharacterControls.downButton == true) {
				direction = "down";
			} else if(CharacterControls.rightButton == true) {
				direction = "right";
			} else if(CharacterControls.leftButton == true) {
				direction = "left";
			} else if(CharacterControls.spaceButton == true) {
				direction = "halt";
			}
			spriteNumber = (spriteNumber == 1)? 2: 1; 		/*creates a walking animation by alternating 2 images.*/
			gamePanel.collisionDetect.checkTile(this);
			
			if(isCollide == false) {
				
				switch(direction) {
				case "up":									/*ordinate increases as you go down.*/	
					ordinate -= speed; 	
					break;
				case "down":	
					ordinate += speed;	
					break;
				case "right":	
					abscissa += speed;	
					break;
				case "left":	
					abscissa -= speed;	
					break;
				case "halt":
					break;
				default:	
					break;
				}
				moveCounter++;
				
				if(moveCounter == MOVE_LIMIT) { 
					restart();
				}
			} 
		}
	}
	
	/**
	 * Redraws the <b><i>Player</i></b> from its updated information from <i>update()</i>. We also call 
	 * <i>getPLayerImage()</i> to consider instance when color has been changed.
	 * @param g2 - A Graphics2D that allows several methods.
	 * 
	 * @see #getPlayerImage()
	 */
	public void draw(Graphics2D g2) { 
		BufferedImage characterImage = null;	
		getPlayerImage();

		if(direction == "up") {
			characterImage = (spriteNumber == 1)? up1: up2;
		} else if(direction == "down") {
			characterImage = (spriteNumber == 1)? down1: down2;
		} else if(direction == "left") {
			characterImage = (spriteNumber == 1)? left1: left2;
		} else if(direction == "right") {
			characterImage = (spriteNumber == 1)? right1: right2;
		} else if(direction == "halt") {
			characterImage = (spriteNumber == 1)? down1: down2;
		}
		g2.drawImage(characterImage, abscissa, ordinate, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null); 
	}
	
	/**
	 * Restarts the moveCounter of the <b><i>Player</i></b> and calls <i>scramble()</i> to change the 
	 * <b><i>ObjectItem</i></b> positions.
	 */
	public void restart() {
		moveCounter = 0;
		gamePanel.objectSet.scramble();
	}
}
