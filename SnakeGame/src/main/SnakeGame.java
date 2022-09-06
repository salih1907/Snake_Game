package main;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SnakeGame {

	public static void main(String[] args)  throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		
		
		new GameFrame();
	}

}
