package com.trapdoor_escape.src.object_item;


import java.io.IOException;
import javax.imageio.ImageIO;
import com.trapdoor_escape.src.main.GamePanel;


/**
 * <b><i>Spirit</i></b> is a subclass of <b><i>ObjectItem</i></b>. Once the <b><i>Player</i></b> interacts with them,
 * <b><i>Player</i></b> changes it suit color that corresponds to the <b><i>Spirit</i></b> color.
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 05 JUN 2022
 *
 */
public class Spirit extends ObjectItem{
	private String spiritType;
	
	/**
	 * Constructor to create instance of <b><i>Spirit</i></b> that has a color accordingly to the passed color. 
	 * @param gamePanel - serves as the communication channel for other classes. 
	 * @param spiritType - determines the color of the spirit
	 * 
	 * @see #setObjectItemDefault()
	 * @see #randomize(ObjectItem, GamePanel)
	 */
	public Spirit(GamePanel gamePanel, String spiritType) {
		this.spiritType = spiritType;
		setObjectItemDefault();
		this.randomize(this, gamePanel);
		visible = false;
	}
	
	/**
	 * Defines the image and objectName inherited property of the <b><i>Spirit</i></b> 
	 * accordingly to its color. 
	 */
	public void setObjectItemDefault() { //instantiate the image property of the ObjectItem.
		
		try {
			String folderDirectory = "/com/trapdoor_escape/drawings/spirit/";
			
			if(spiritType == "Red") {
				image = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "redSpirit.png"));
			} else if(spiritType == "Blue"){
				image = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "blueSpirit.png"));
			} else if(spiritType == "Yellow") {
				image = ImageIO.read(getClass().getResourceAsStream(folderDirectory + "yellowSpirit.png"));
			}
			objectName = "spirit" + spiritType;
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
