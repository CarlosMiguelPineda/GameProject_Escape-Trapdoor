package com.trapdoor_escape.src.main;


import javax.swing.JFrame;


/**
 * The main class of the game project responsible for creating a new instance for the game as well as to start-up the game.
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 05 JUN 2022
 */
public class Main {

	/**
	 * The main method for the game
	 * @param args - default
	 */
	public static void main(String[] args) {
		JFrame window =new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	//close the window, when 'x' button is clicked.
		window.setResizable(false);
		window.setTitle("Moving Trapdoor Escape");
		
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel); 									/*to apply the gamePanel configurization to the window*/
		
		window.pack(); 											/*to fit the size and layouts of the gamePanel to the window*/
		window.setLocationRelativeTo(null); 					/*window appears at the center of the screen*/
		window.setVisible(true);
		
		gamePanel.startGameThread();
	}

}
