package com.trapdoor_escape.src.object_item;


import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import com.trapdoor_escape.src.main.GamePanel;


/**
 * <b><i>Trapdoor</i></b> is a subclass of <b><i>ObjectItem</i></b>. It can be only open when the <b><i>Player</i></b>
 * color is the same with the trapdoor's color. Its color is also randomized every after the TIME_LIMIT is reached.
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 05 JUN 2022
 */
public class Trapdoor extends ObjectItem {
	private static boolean isLocked;
	private static String trapdoorColor;
	private static GamePanel gamePanel;

	/**
	 * Constructor to create new instance of <b><i>Trapdoor</i></b> that is locked and not visible by default. 
	 * @param gamePanel - serves as the communication channel for other classes. 
	 * @see #setColor()
	 * @see #randomize(ObjectItem, GamePanel)
	 */
	public Trapdoor(GamePanel gamePanel) {
		Trapdoor.gamePanel = gamePanel;
		isLocked = true;
		setColor();
		this.randomize(this, gamePanel);
		visible = false;
	}
	
	/**
	 * Method overriding that defines a random color to the <b><i>Trapdoor</i></b>. 
	 * @see #setObjectDefault()
	 */
	@Override
	public void setColor() {
		Random randomColor = new Random();
		int convertedColor = randomColor.nextInt(3); //only 3 colors
		
		switch(convertedColor) { //choosing a random color
		case 0:
			trapdoorColor = "Red";
			break;
		case 1:
			trapdoorColor = "Blue";
			break;
		case 2:
			trapdoorColor = "Yellow";
			break;
		default:
			break;
		}
		setObjectDefault();
	}
	
	/**
	 * Sets value for the image and objectName inherited property of <b><i>Trapdoor</i></b> 
	 * accordingly to its color.
	 */
	public void setObjectDefault() {  //instantiate the image property of the ObjectItem.
		
		try {
			String folderDirectory = "/com/trapdoor_escape/drawings/trapdoor/";
			if(isLocked == true) {
				switch(trapdoorColor) {
				case "Red": 
					image = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "locked_red.png")); 
					break;
				case "Blue": 
					image = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "locked_blue.png")); 
					break;	
				case "Yellow": 
					image = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "locked_yellow.png")); 
					break;
				default: 
					break;
				}
				objectName = "trapdoor" + trapdoorColor;
			} else {
				image = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "unlocked_trapdoor.png"));
				objectName = "trapdoorUnlocked"; 
			}		
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method overriding to lock again and reset the <b><i>Trapdoor</i></b>.
	 *  @see #setObjectDefault()
	 */
	@Override
	public void update() {
		gamePanel.userInterface.showMessage("Player opens the trapdoor!");
		Trapdoor.isLocked = false;
		gamePanel.playSoundEffect(5);
		setObjectDefault();
	}
}
