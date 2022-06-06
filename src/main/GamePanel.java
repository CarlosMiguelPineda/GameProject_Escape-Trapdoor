package com.trapdoor_escape.src.main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import com.trapdoor_escape.src.character.Player;
import com.trapdoor_escape.src.object_item.ObjectItem;
import com.trapdoor_escape.src.tile.TileManager;


/**
 * This controls the in-game flow of the game including the screen settings, system interaction, and all entities.
 * It adapts the mediator behavioral design pattern. It implements runnable interface.
 *
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 05 JUN 2022
 *
 */
public class GamePanel extends JPanel implements Runnable{
	/*SCREEN or PANEL SETTINGS*/
	
	/**
	 * stores how much pixel in one tile.
	 */
	public static final int TILE_SIZE = 48;
	
	/**
	 * stores the panel total columns.
	 */
	public static final int SCREEN_COLUMNS = 11; 
	
	/**
	 * stores the panel total rows.
	 */
	public static final int SCREEN_ROWS = 13;
	
	/**
	 * stores the total pixel for the panel's width.
	 */
	public static final int SCREEN_WIDTH = TILE_SIZE * SCREEN_COLUMNS; 
	
	/**
	 * stores the total pixel for the panel's height
	 */
	public static final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROWS;
	
	private static final int FPS = 7;
	
	/*GAME SYSTEM*/
	
	/**
	 * facilitates the communication between the user and the game
	 */
	public UserInterface userInterface = new UserInterface(this); 
	
	/**
	 * facilitates the rendering and automating the tiles
	 */
	public TileManager tileManager = new TileManager(); 
	
	/**
	 * facilitates if the targeted tile for the player to move is walkable.
	 */
	public CollisionDetection collisionDetect = new CollisionDetection (this); 
	
	/**
	 * facilitates the interaction and allowable moves of the player.
	 */
	private CharacterControls moves = new CharacterControls(this); 
	
	/**
	 * facilitates the area of the game field that the user can see and review.
	 */
	private Display display = new Display(this); 
	
	/**
	 * facilitates the background music which is an object of Audio.
	 */
	private Audio background = new Audio(); 
	
	/**
	 * facilitates the special effect which is an object of Audio.
	 */
	private Audio specialEffect = new Audio(); 
	
	/**
	 * stores and responsible for the game clock and loop of the game
	 */
	Thread gameThread; 			
	
	/*OBJECT AND CHARACTER*/
	
	/**
	 * facilitates the static entities
	 */
	public ObjectItem objectItem[] = new ObjectItem[4]; 
	
	/**
	 * facilitates the instantiating objects and their positions
	 */
	public ObjectSetter objectSet = new ObjectSetter(this); 
	
	/**
	 * facilitates the player of the user.
	 */
	public Player player = new Player(this); 
	
	/**
	 * Sets up the screen settings.
	 */
	GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); 	/*for better game rendering*/
		this.addKeyListener(moves);
		this.setFocusable(true);
	}
	
	/**
	 * Starts the game clock for the game loop. Enables the in-game background music.
	 * @see #playBackgroundMusic(int)
	 */
	void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		playBackgroundMusic(1); 
	}
	
	/**
	 * Method found in the runnable interface. It facilitates the game loop.
	 * @see #update()
	 * @see #repaint()
	 */
	@Override
	public void run() { 
		double updateRepaintIntervalTime = 1000000000/FPS; 
		double waitTime = 0;
		long oldTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) { 	/*creates game loop*/
			currentTime = System.nanoTime();
			waitTime += (currentTime - oldTime) / updateRepaintIntervalTime;
			oldTime = currentTime;
			
			if(waitTime >= 1) { 		/*we want our system to call update 7times persecond or 7FPS*/
				update();
				repaint(); 		
				waitTime--;
			}
		}
	}
	
	/**
	 * Changes the position of the character
	 */
	private void update() { 
		player.update();
	}
	
	/**
	 * Draws the character with the current position from update(). Order of drawing the layer is important - 
	 * the 1st layer to be drawn will be placed in most bottom layer.
	 */
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;  /*we need to convert it to Graphics2D for more allowable methods.*/
		
		/*drawing tiles*/
		tileManager.draw(g2); 
		
		/*drawing objectItem*/
		for (int index = 0; index < objectItem.length; index++) {
			if(objectItem[index] != null) {
				objectItem[index].draw(g2);
			}
		}

		/*drawing display*/
		display.draw(g2);

		/*drawing player*/
		player.draw(g2);
		
		/*drawing UI*/
		userInterface.draw(g2);
		g2.dispose();
	}
	
	/**
	 * Plays the background music and is continuously looped
	 * @param index - identifier for the specific audio.
	 */
	public void playBackgroundMusic(int index){
		background.setFile(index);
		background.playAudio();
		background.loopAudio();
	}
	
	/**
	 * Plays the sound effect for a short time only.
	 * @param index - identifier for the specific audio.
	 */
	public void playSoundEffect(int index) { 
		specialEffect.setFile(index);
		specialEffect.playAudio();
	}
	
	/**
	 * Stops the audio.
	 */
	public void stopAudio() {
		background.stopAudio();
	}
	
	/**
	 * Resets and creates new floor when the floor level is increased. 
	 * Recycle display, tileManager, player, objectItem, and other objects that requires the former objects.
	 * Paused the gameThread to control the draw() method since some variable becomes temporarily null.
	 * 
	 */
	public void reset() {
		gameThread = null; 
		display = null;
		tileManager = null;
		collisionDetect = null;
		objectSet = null;
		player = null;
		
		/*this is the recycle portion*/
		display = new Display(this);
		tileManager = new TileManager();
		collisionDetect = new CollisionDetection (this);

		objectSet = new ObjectSetter(this);
		player = new Player(this);
		gameThread = new Thread(this);
		
		gameThread.start(); 
	}
}
