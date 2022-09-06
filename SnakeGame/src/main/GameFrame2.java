package main;

import java.awt.Toolkit;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class GameFrame2 extends JFrame{
	public GameFrame2() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\gencs\\Downloads\\icon.png.png"));
		this.add(new StartScreen());
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
