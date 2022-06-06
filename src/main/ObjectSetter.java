package com.trapdoor_escape.src.main;


import com.trapdoor_escape.src.object_item.ObjectItem;
import com.trapdoor_escape.src.object_item.Spirit;
import com.trapdoor_escape.src.object_item.Trapdoor;


/**
 * <b><i>ObjectSetter</i></b> facilitates the positioning of any <b><i>ObjectItem</i></b>. It is a separate class
 * to control the number of lines in <b><i>GamePanel</i></b> especially for future version where there will
 * be a lot of objects/items. 
 * 
 * @author CARLOS MIGUEL PINEDA
 * version 0.0.1
 * @since 05 JUN 2022
 */
public class ObjectSetter {
	GamePanel gamePanel; 
	
	/**
	 * Constructor for instantiating four <b><i>ObjectItem</i></b>, in the current version.
	 * @param gamePanel - serves as the communication channel for other classes. 
	 */
	public ObjectSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		gamePanel.objectItem[0] = new Spirit(gamePanel, "Red");
		gamePanel.objectItem[1] = new Spirit(gamePanel, "Blue");
		gamePanel.objectItem[2] = new Spirit(gamePanel, "Yellow");
		gamePanel.objectItem[3] = new Trapdoor(gamePanel);
		ObjectItem.blockNumberPool.clear();  /*blockNumberPool needs to be reset since we have only limited 9 blocks*/
	}
	
	/**
	 * Changes the position of the existing objectItem and the color of the trapdoor.  
	 * This will be called when movesCounter ==  MOVE_LIMIT. 
	 * It erases the existing data in blockNUmberPool since we have only limited blocks.
	 */
	public void scramble() {
		ObjectItem.blockNumberPool.clear(); 
		gamePanel.objectItem[0].randomize(gamePanel.objectItem[0], gamePanel);
		gamePanel.objectItem[1].randomize(gamePanel.objectItem[1], gamePanel);
		gamePanel.objectItem[2].randomize(gamePanel.objectItem[2], gamePanel);
		gamePanel.objectItem[3].randomize(gamePanel.objectItem[3], gamePanel);
		gamePanel.objectItem[3].setColor();
	}

}