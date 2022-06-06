package com.trapdoor_escape.src.object_item;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.trapdoor_escape.src.main.GamePanel;


/**
 * This is a superclass for all static entities. For this version, there are two subclasses implemented; <b><i>Spirit</i></b>
 * and <b><i>Trapdoor</i></b>.
 * 
 * <p>
 * <b><i>ObjectItem</i></b> is a superclass that allows <b><i>Player</i></b> to interact with them to help clear the floor.
 * It only changes their positive when MOVE_LIMIT is reached. Properties are encapsulated. 
 * Every <b><i>ObjectItem</i></b> consumes one tile of area.
 * </p>
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 06 June 2022
 */
public class ObjectItem {
	
	/**
	 * stores the previous block number so that every block number is unique
	 */
	public static List <Integer> blockNumberPool = new ArrayList <Integer>();
	
	protected int tileTypeOfObjectItem;
	protected BufferedImage image;
	protected String objectName;
	
	protected int abscissa; 
	protected int ordinate;
	protected boolean visible;
	
	/**
	 * sets to be overriden in the subclasses <b><i>Trapdoor</i></b>. It will only be called
	 * once in <b><i>CharacterContorls</i></b> when the player opens the trapdoor.
	 */
	public void update() {}
	
	/**
	 * sets to be overrided in the subclass <b><i>Trapdoor</i></b>.
	 */
	public void setColor() {}
	
	/**
	 * Gives access for getting the tileTypeOfObjectTime.
	 * @return tileTypeOfObjectItem - type of tile on top of the instance of <b><i>ObjectItem</i></b>.
	 */
	public int getTileTypeOfObjectItem() {
		return tileTypeOfObjectItem;
	}
	
	/**
	 * Gives access for getting the objectName of the <b><i>ObjectItem</i></b>.
	 * @return objectName - a string that defines the instance of <b><i>ObjectItem</i></b>.
	 */
	public String getObjectName() {
		return objectName;
	}
	
	/**
	 * Gives access for getting the abscissa of the instance of <b><i>ObjectItem</i></b>.
	 * @return the x-component of the ObjectItem's position from the leftmost of the panel in terms of pixels.
	 */
	public int getAbscissa() {
		return abscissa;
	}
	
	/**
	 * Gives access for getting the ordinate of the instance of <b><i>ObjectItem</i></b>.
	 * @return the y-component of the ObjectItem's position from the topmost of the panel in terms of pixels.
	 */
	public int getOrdinate() {
		return ordinate;
	}
	
	/**
	 * Gives access for getting the visibility of the instance of <b><i>ObjectItem</i></b>.
	 * @return visible of the ObjectItem.
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Gives access for setting the updated visibility of the instance of <b><i>ObjectItem</i></b>.
	 * @param visible - true or false.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Sets a random abscissa and ordinate for the tile of instance of <b><i>ObjectItem</i></b> 
	 * that is unique from the past instance of <b><i>ObjectItem</i></b>. It makes sure that the random 
	 * tile is walkable.
	 * @param objectItem - static entity either a trapdoor or spirit.
	 * @param gamePanel - serves as the communication channel for other classes
	 */
	public void randomize(ObjectItem objectItem, GamePanel gamePanel) { 
		boolean loopCheck; 			/* decides if the loop will continue*/
		
		/*The 9-by-9 tiles playing field will be divided into 9 blocks, starts at 0 to 8, similar in suduko.*/
		Random randomBlockNumber;
		int convertedBlockNumber; 	/* converts the generated block number from Randon to int*/
		int prevBlockNumber;
		
		/*Each block has three rows and three columns, which starts at 1 to 3, similar in suduko.*/
		Random randomColumn; 
		Random randomRow;
		int convertedColumn; 
		int convertedRow;
		
		/* Each block has corresponding factor that will be multiplied to the SCALE(3) to get the absolute tile 
		 * with respect to the 9-by-9 tiles playing field.
		 */
		int columnFactor; 
		int rowFactor;
		final int SCALE = 3;
		int scaledColumn; 		/*column component of the absolute tile*/
		int scaledRow; 			/*row component of the absolute tile*/
		
		do {
			loopCheck = true;
			randomBlockNumber = new Random();
			convertedBlockNumber = randomBlockNumber.nextInt(9);
			
			/*we dont want our objectItem to pop in the plaer's spawn block.*/
			BLOCK_CHECKER: if(convertedBlockNumber != 4) { 
				
				for(int index = 0 ; index < blockNumberPool.size() ; index++) { 
					prevBlockNumber = blockNumberPool.get(index);
					
					 /*reset if convertedBlockNumber is not unique*/
					if(prevBlockNumber == convertedBlockNumber) {
						break BLOCK_CHECKER;
					}
				} 
				/*
				 * If it finishes completely, then convertedBlockNumber is unique but it doesn't automatically mean 
				 * it is acceptable. We will check if the tile on the convertedBlockNumber is walkable before we store
				 * in blockNumberPool.
				 */
				
				randomColumn = new Random();
				randomRow = new Random();
				convertedColumn = randomColumn.nextInt(3) + 1; 
				convertedRow = randomRow.nextInt(3) + 1; 
				
				columnFactor = convertedBlockNumber % SCALE; 
				rowFactor = convertedBlockNumber / SCALE;
				scaledColumn = (SCALE * columnFactor) + convertedColumn; 
				scaledRow = (SCALE * rowFactor) + convertedRow; 
				
				tileTypeOfObjectItem = gamePanel.tileManager.floorTileNumber[scaledColumn][scaledRow];   
				loopCheck = (gamePanel.tileManager.tileType[tileTypeOfObjectItem].walkable == true)? false: true; 
		
				if(loopCheck == false) {
					objectItem.abscissa = scaledColumn * GamePanel.TILE_SIZE;
					objectItem.ordinate = scaledRow * GamePanel.TILE_SIZE;
					blockNumberPool.add(convertedBlockNumber); 
				}
			}
		}while(loopCheck);
	}
			
	/**
	 * Draws the instance of <b><i>ObjectItem</i></b> on a random tile.
	 * @param g2 - A Graphics2D that allows several methods.
	 */
	public void draw(Graphics2D g2) {
		
		if(this.visible == true) {
			g2.drawImage(image, abscissa, ordinate, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null); 
		}
	} 
	
}
