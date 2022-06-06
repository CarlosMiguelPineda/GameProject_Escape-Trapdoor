package com.trapdoor_escape.src.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


/**
 * <b><i>Display</i></b> controls the area of the game field that the user can see and review.
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 05 JUN 2022
 */
public class Display { 
	private static GamePanel gamePanel;
	private static Area unsearchedDisplay;
	private static Area totalSearchedDisplay;
	private static Area currentSearchedDisplay;
	
	/**
	 * Constructor for instantiating <b><i>Display</i></b>. unsearchedDisplay is by default the area of the game field,
	 * while the totalSeachedDisplay depends on the previous value of currentSearchedDisplay.
	 * @param gamePanel - serves as the communication channel for other classes. 
	 */
	public Display(GamePanel gamePanel) {
		Display.gamePanel = gamePanel;
		unsearchedDisplay = new Area(new Rectangle2D.Double(GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,
				GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 9));
		totalSearchedDisplay = new Area();
		
	}
	
	/**
	 * draws the area of unsearchedDisplay, totalSearchedDisplay, and currentSearchedDisplay. unsearchedDisplay is
	 * the area of the game field that the user has not been explored, totalSearchedDisplays shows the area 
	 * the user has previously explored but any <b><i>ObjectItem</i></b> on it will remain invisible, and
	 * currentSearchedDisplay shows all of the objectItem that are covered in the range. The range is a circle
	 * with a diameter of GamePanel.TILE_SIZE * 3.
	 * 
	 * @param g2 - A Graphics2D that allows several methods.
	 */
	public void draw(Graphics2D g2) {
		currentSearchedDisplay = new Area(new Ellipse2D.Double(gamePanel.player.getAbscissa() - GamePanel.TILE_SIZE,
				gamePanel.player.getOrdinate() - GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE * 3));
		
		g2.setColor(new Color(12, 9, 10, 200));
		
		/*We need to subtract since the currentSearchedDisplay should on top of the other two Areas*/
		totalSearchedDisplay.subtract(currentSearchedDisplay); 
		
		g2.fill(totalSearchedDisplay);
		
		/*unsearchedDisplay should be bottom of the other two Areas*/
		unsearchedDisplay.subtract(currentSearchedDisplay);
		unsearchedDisplay.subtract(totalSearchedDisplay); 
		
		g2.setColor(new Color(12, 9, 10));
		g2.fill(unsearchedDisplay);
		
		for(int index = 0; index < gamePanel.objectItem.length; index++) { 
			/*changes the visibility of the objectItem when the objectItem is within the currentSeachedDisplay.*/
			if(currentSearchedDisplay.intersects(gamePanel.objectItem[index].getAbscissa(), 
					gamePanel.objectItem[index].getOrdinate(), GamePanel.TILE_SIZE, GamePanel.TILE_SIZE)) { 
				gamePanel.objectItem[index].setVisible(true);
			} else {
				gamePanel.objectItem[index].setVisible(false);
			}
		}
		totalSearchedDisplay.add(currentSearchedDisplay); /*totalSearchedDisplay acts like a post-increment not pre-increment*/
	}

}
