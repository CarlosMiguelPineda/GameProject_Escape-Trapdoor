package com.trapdoor_escape.src.main;


import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * <b><i>Audio</i></b> class sets and controls the background music and special effect that are
 * used throughout the game.
 * 
 * @author CARLOS MIGUEL PINEDA
 * @version 0.0.1
 * @since 05 JUN 2022
 *
 */
public class Audio {
	private static Clip clip; 
	private static URL audioURL[] = new URL [10]; 
	
	/**
	 * Reads and stores the audio from a .wav form into a file(audioURL).
	 */
	public Audio() {
		String folderDirectory = "/com/trapdoor_escape/audio/";
		audioURL[0] = getClass().getResource(folderDirectory + "intro.wav"); 		/*background music*/
		audioURL[1] = getClass().getResource(folderDirectory + "on-game.wav");		/*background musics*/
		audioURL[2] = getClass().getResource(folderDirectory + "spirit.wav"); 		/*special effect*/
		audioURL[3] = getClass().getResource(folderDirectory + "stair.wav"); 		/*special effect*/
		audioURL[4] = getClass().getResource(folderDirectory + "incorrect.wav");	/*special effect*/
		audioURL[5] = getClass().getResource(folderDirectory + "unlock.wav");		/*special effect*/
		audioURL[6] = getClass().getResource(folderDirectory + "gameOver.wav"); 	/*background music*/
	}
	
	/**
	 * Formats the audioURL as a Clip for setting-up the background music or special effect.
	 * @param index - identifies the specific audio that will be played, looped, or stopped.
	 */
	public void setFile(int index) { 
		try {
			AudioInputStream track = AudioSystem.getAudioInputStream(audioURL[index]);
			clip = AudioSystem.getClip();
			clip.open(track);
		}catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	/**
	 * Plays the background music or special effect.
	 */
	public void playAudio() {
		clip.start();
	}
	
	/**
	 * Repeat playing the background music until stopped. Applicable only to the background music and not
	 * to special effects.
	 */
	public void loopAudio() { 
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * Stops the background music or special effect.
	 */
	public void stopAudio() {
		clip.stop(); 
	}
	
}
