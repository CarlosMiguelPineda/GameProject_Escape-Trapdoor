package com.trapdoor_escape.src.main;


import com.trapdoor_escape.src.character.Character;


/**
 * <b><i>CollisionDectection</i></b> facilitates if the targeted tile for the player to move is walkable.
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 05 JUN 2022
 */
public class CollisionDetection {
	private static GamePanel gamePanel;
	
	/**
	 * Sets an access medium to other classes once instantiating <b><i>CollisionDectection</i></b>.
	 * @param gamePanel - serves as the communication channel for other classes. 
	 */
	public CollisionDetection(GamePanel gamePanel) {
		CollisionDetection.gamePanel = gamePanel;
	}
	
	/**
	 * Checks if the targeted tile is walkable.
	 * @param character - the entity that is about to move.
	 */
	public void checkTile(Character character) {
		int objectColumn = (character.getAbscissa()) / GamePanel.TILE_SIZE;
		int objectRow = (character.getOrdinate()) / GamePanel.TILE_SIZE;
		int tileTypeToMove;
		
		switch(character.getDirection()) {
		case "up":
			tileTypeToMove = gamePanel.tileManager.floorTileNumber[objectColumn][objectRow - 1]; 
			character.isCollide = (gamePanel.tileManager.tileType[tileTypeToMove].walkable == true)	? false: true;
			break;
		case "down":
			tileTypeToMove = gamePanel.tileManager.floorTileNumber[objectColumn][objectRow + 1]; 
			character.isCollide = (gamePanel.tileManager.tileType[tileTypeToMove].walkable == true)	? false: true;
			break;
		case "right":
			tileTypeToMove = gamePanel.tileManager.floorTileNumber[objectColumn + 1][objectRow];
			character.isCollide = (gamePanel.tileManager.tileType[tileTypeToMove].walkable == true)	? false: true;
			break;
		case "left":
			tileTypeToMove = gamePanel.tileManager.floorTileNumber[objectColumn - 1][objectRow];
			character.isCollide = (gamePanel.tileManager.tileType[tileTypeToMove].walkable == true)	? false: true;
			break;	
		default:
			break;
		}
	}
	
}
