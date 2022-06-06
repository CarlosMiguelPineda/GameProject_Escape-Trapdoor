package com.trapdoor_escape.src.tile;


import java.awt.image.BufferedImage;


/**
 * <b><i>Tile</i></b> class holds the properties for creating tiles in the <b><i>TileManager</i></b>. 
 * It has a corresponding image that dictates if the player can move to it.
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 05 JUN 2022 
 */
public class Tile {
	protected BufferedImage image;
	
	/**
	 * stores true if the tile is walkable depending on its image.
	 */
	public boolean walkable = true;
}
