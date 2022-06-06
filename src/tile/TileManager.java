package com.trapdoor_escape.src.tile;


import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.trapdoor_escape.src.main.GamePanel;
import com.trapdoor_escape.src.main.UserInterface;


/**
 * <b><i>TileManager</i></b> facilitates and automates the rendering of the tiles in the playing field.
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 06 JUN 2022
 *
 */
public class TileManager {
	
	/**
	 * stores the different tile types each having its own image.
	 */
	public Tile tileType[];
	
	/**
	 * stores the map data at every tile.
	 */
	public int floorTileNumber[][]; 
	
	/**
	 * Sets the size of tileTypes and the floorTileNumber accordingly to the size of the game field.
	 * @see #getImage()
	 * @see #loadMap()
	 */
	public TileManager() {
		tileType = new Tile[4]; 
		
		/* There are 11-by-11 tiles for our game field out of 11-by-13 tiles for the panel, hence, 121 tiles */
		floorTileNumber = new int[GamePanel.SCREEN_COLUMNS][GamePanel.SCREEN_ROWS - 2];
		
		getImage();
		loadMap();
	}
	
	/**
	 * Instantiates the four distinct tileType and sets their corresponding image.
	 */
	private void getImage() { 
		try {
			tileType[0] = new Tile();
			tileType[0].image = ImageIO.read(getClass().getResourceAsStream("/com/trapdoor_escape/drawings/tile/main.png"));
			
			tileType[1] = new Tile();
			tileType[1].image = ImageIO.read(getClass().getResourceAsStream("/com/trapdoor_escape/drawings/tile/conjunction.png"));
			
			tileType[2] = new Tile();
			tileType[2].image = ImageIO.read(getClass().getResourceAsStream("/com/trapdoor_escape/drawings/tile/hole.png"));
			tileType[2].walkable = false;
			
			tileType[3] = new Tile();
			tileType[3].image = ImageIO.read(getClass().getResourceAsStream("/com/trapdoor_escape/drawings/tile/wall.png"));
			tileType[3].walkable = false;	

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Automates the tile drawing process by following the floor "blueprint" from the floor.txt that 
	 * depends on the floorLevel.
	 */
	private void loadMap() { 
		try {
			String floorNumber = String.valueOf(UserInterface.floorLevel); 
			InputStream inputStream = getClass().getResourceAsStream("/com/trapdoor_escape/drawings/"
					+ "floor/floor" + floorNumber +".txt"); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); 
			
			int column = 0;
			int row = 0;
			
			/* There are 11-by-11 tiles for our game field out of 11-by-13 tiles for the panel. */
			while(column < GamePanel.SCREEN_COLUMNS && row < (GamePanel.SCREEN_ROWS - 2)){
				String lineInputFloor = reader.readLine();
				String numbers[] = lineInputFloor.split(" "); /*stores the individual tile number from our lineInputFloor*/

				for( ; column < GamePanel.SCREEN_COLUMNS; column++) {
					int convertedNum = Integer.parseInt(numbers[column]);
					floorTileNumber[column][row] = convertedNum;
				}

				if(column == GamePanel.SCREEN_COLUMNS) { 		/*reads the nextLine after filling 1 complete row*/
					column = 0;
					row++;
				}
			}
			reader.close(); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Reset the existing tiles in the current floor level to create a new one for the next floor level.
	 * @see #loadMap()
	 */
	public void reset() {
		floorTileNumber =null;
		loadMap();
	}
	
	/**
	 * Draws the tiles for the game field in the current floor number.
	 * Each tile consumes one TILE_SIZE of area. Draws by row starting at the top-leftmost of the panel.
	 * @param g2 - serves as the communication channel for other classes. 
	 */
	public void draw(Graphics2D g2) { 
		int abscissa = 0, ordinate = 0;
		int column = 0, row = 0;
		
			while(column < GamePanel.SCREEN_COLUMNS && row < GamePanel.SCREEN_COLUMNS) {
				int tileNum = floorTileNumber[column][row]; 
				
				g2.drawImage(tileType[tileNum].image, abscissa, ordinate, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
				column++; 
				abscissa += GamePanel.TILE_SIZE; 
															
				if(column == GamePanel.SCREEN_COLUMNS) {
					column = 0;
					abscissa = 0;
					row++;
					ordinate += GamePanel.TILE_SIZE;
				}
			}
		

	}
}
