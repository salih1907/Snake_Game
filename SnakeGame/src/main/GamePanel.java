package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 60;
	static final int x[] = new int[GAME_UNITS];
	static final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	JLabel label;
	boolean gameOver = false;
	boolean musicON = true;
	Font customFont;
	boolean isStarted = false;
	public int highScore;
	

	File file = new File("C:\\Users\\gencs\\Desktop\\SnakeGame\\Music\\piano.wav");

	AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

	Clip clip = AudioSystem.getClip();

	public GamePanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.addMouseListener(new MouseAdapter());
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		PlayMusic();
		readHighScore();
		startGame();

	}
	
	public void readHighScore() {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("C:\\Users\\gencs\\Desktop\\SnakeGame\\HighestScore.txt"));

			highScore = reader.read();

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void PlayMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		clip.open(audioStream);
		clip.start();
		clip.loop(clip.LOOP_CONTINUOUSLY);

	}

	public void startGame() {
		bodyParts = 6;
		x[0] = 0;
		y[0] = 0;
		direction = 'R';
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		newFont();
		timer.start();
		

	}

	public void backGroundImage(Graphics g) {
		Image backgroundImage;
		try {
			backgroundImage = ImageIO
					.read(new File("C:\\Users\\gencs\\Desktop\\SnakeGame\\backgrounds\\background2.jpg"));
			g.drawImage(backgroundImage, 0, 0, null);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	public void Head(Graphics g, int i) {
		Image Head;
		try {
			Head = ImageIO.read(new File("C:\\Users\\gencs\\Desktop\\SnakeGame\\backgrounds\\Head4.png"));
			g.drawImage(Head, x[i], y[i], null);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void Apple(Graphics g) {
		Image Apple;
		try {
			Apple = ImageIO.read(new File("C:\\Users\\gencs\\Desktop\\SnakeGame\\backgrounds\\art2.png"));
			g.drawImage(Apple, appleX, appleY, null);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void Tail(Graphics g, int i) {
		Image Tail;
		try {
			Tail = ImageIO.read(new File("C:\\Users\\gencs\\Desktop\\SnakeGame\\backgrounds\\tail2.png"));
			g.drawImage(Tail, x[i], y[i], null);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		draw(g);
		if (isStarted) {
			MusicOption(g);
		}
		

	}

	public void draw(Graphics g) {
		if (!isStarted) {
			backGroundImage(g);
			startScreen(g);
		} else if (running) {
			/*
			 * for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) { g.drawLine(i *
			 * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); g.drawLine(0, i * UNIT_SIZE,
			 * SCREEN_WIDTH, i * UNIT_SIZE); }
			 */
			backGroundImage(g);
			g.setColor(Color.red);
			// g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			Apple(g);

			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					g.setColor(Color.green);
					// g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					Head(g, i);
				} else {
					g.setColor(new Color(40, 180, 0));
					// g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					// g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					Tail(g, i);
				}
			}

			g.setColor(Color.red);
			g.setFont(customFont.deriveFont(50f));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score " + applesEaten)) / 2,
					g.getFont().getSize());
			if ((highScore-applesEaten)>0) {
				g.setColor(Color.yellow);
				g.setFont(customFont.deriveFont(25f));
				FontMetrics metrics2 = getFontMetrics(g.getFont());
				g.drawString("To new record " + (highScore-applesEaten), (SCREEN_WIDTH - metrics2.stringWidth("to new record " +(highScore-applesEaten))) / 2,
						g.getFont().getSize()+40);
			}else {
				g.setColor(Color.yellow);
				g.setFont(customFont.deriveFont(25f));
				FontMetrics metrics2 = getFontMetrics(g.getFont());
				g.drawString("you passed " + (applesEaten-highScore), (SCREEN_WIDTH - metrics2.stringWidth("you passed " +(applesEaten-highScore))) / 2,
						g.getFont().getSize()+40);
			}
			

		} else {
			backGroundImage(g);
			gameOver(g);

		}

	}

	public void newApple() {
		appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	public void MusicOption(Graphics g) {

		g.setColor(Color.red);
		g.setFont(customFont.deriveFont(30f));
		FontMetrics metrics3 = getFontMetrics(g.getFont());
		if (musicON) {
			g.drawString("Music ON", (SCREEN_WIDTH - metrics3.stringWidth("Music OFF") - 10), 30);
		} else {
			g.drawString("Music OFF", (SCREEN_WIDTH - metrics3.stringWidth("Music OFF") - 10), 30);
		}

	}

	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}

	public void chechkApple() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (x[0] == appleX && y[0] == appleY) {
			applesEaten++;
			bodyParts++;
			File file = new File("C:\\Users\\gencs\\Desktop\\SnakeGame\\Music\\Score Sound Effect.wav");

			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);

			clip.start();
			newApple();
		}
	}

	public void newFont() {
		try {
			customFont = Font
					.createFont(Font.TRUETYPE_FONT,
							new File("C:\\Users\\gencs\\Desktop\\SnakeGame\\backgrounds\\DebugFreeTrial-MVdYB.otf"))
					.deriveFont(50f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);
		} catch (FontFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void checkCollisions() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
				this.gameOver = true;
				clip.stop();
				File file2 = new File("C:\\Users\\gencs\\Desktop\\SnakeGame\\Music\\Game Over.wav");
				AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(file2);
				Clip clip2 = AudioSystem.getClip();
				clip2.open(audioStream2);
				clip2.start();
			}
		}
		if (x[0] < 0) {
			x[0] = SCREEN_WIDTH;
		}
		if (x[0] > SCREEN_WIDTH) {
			x[0] = 0;
		}
		if (y[0] < 0) {
			y[0] = SCREEN_HEIGHT;
		}
		if (y[0] > SCREEN_HEIGHT) {
			y[0] = 0;
		}
		if (!running) {
			timer.stop();
		}

	}

	public void startScreen(Graphics g) {
		newFont();
		Color customColor = new Color(211, 79, 30);
		Color customColor2 = new Color(236, 161, 6);
		g.setColor(customColor);
		g.setFont(customFont.deriveFont(140f));
		
		g.drawString("Start",30,280);
		
		g.setColor(customColor);
		g.setFont(customFont.deriveFont(140f));
		g.drawString("Game", 310,380);
		
		
		
		for (int i = 60; i < 600; i += 30) {
			g.setColor(customColor2);
			g.setFont(customFont.deriveFont(40f));
			//FontMetrics metrics5 = getFontMetrics(g.getFont());
			g.drawString("I", 10, i);
			
			g.setColor(customColor2);
			g.setFont(customFont.deriveFont(40f));
			//FontMetrics metrics5 = getFontMetrics(g.getFont());
			g.drawString("I", SCREEN_WIDTH-20, i);
		}
		
		for (int i = 30; i < 300; i += 20) {
			g.setColor(customColor2);
			g.setFont(customFont.deriveFont(40f));
			//FontMetrics metrics5 = getFontMetrics(g.getFont());
			g.drawString(".", i, 350);

		}
		for (int i = 310; i < 580; i += 20) {
			g.setColor(customColor2);
			g.setFont(customFont.deriveFont(40f));
			//FontMetrics metrics5 = getFontMetrics(g.getFont());
			g.drawString(".", i, 250);

		}
		
	

		g.setColor(new Color(241, 202, 137));
		g.setFont(customFont.deriveFont(30f));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Designed By Salih", (SCREEN_WIDTH - metrics2.stringWidth("Designed By Salih")) / 2,
				SCREEN_HEIGHT - 40);
		
		

	}

	public void gameOver(Graphics g) {

		try {

			if (applesEaten > highScore) {
				BufferedWriter writer = new BufferedWriter(
						new FileWriter("C:\\Users\\gencs\\Desktop\\SnakeGame\\HighestScore.txt"));
				writer.write(applesEaten);
				highScore = applesEaten;

				g.setColor(Color.yellow);
				g.setFont(customFont.deriveFont(30f));
				FontMetrics metrics2 = getFontMetrics(g.getFont());
				g.drawString("new Highest score", (SCREEN_WIDTH - metrics2.stringWidth("new Highest score")) / 2, 80);
				writer.close();
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		}

		newFont();
		g.setColor(Color.red);
		g.setFont(customFont.deriveFont(150f));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

		g.setColor(Color.red);
		g.setFont(customFont.deriveFont(30));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score " + applesEaten)) / 2,
				g.getFont().getSize());

		g.setColor(Color.yellow);
		g.setFont(customFont.deriveFont(50f));
		FontMetrics metrics4 = getFontMetrics(g.getFont());
		g.drawString("Highest Score " + highScore,
				(SCREEN_WIDTH - metrics4.stringWidth("Highest Score " + highScore)) / 2, SCREEN_HEIGHT - 170);

		if (gameOver) {
			newFont();
			g.setColor(Color.red);
			g.setFont(customFont.deriveFont(50f));
			FontMetrics metrics3 = getFontMetrics(g.getFont());
			g.drawString("Try Again", (SCREEN_WIDTH - metrics3.stringWidth("Try Again")) / 2, SCREEN_HEIGHT - 50);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (running) {
			move();

			try {
				chechkApple();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				
				e1.printStackTrace();
			}

			try {
				try {
					checkCollisions();
				} catch (UnsupportedAudioFileException e1) {
					
					e1.printStackTrace();
				}
			} catch (LineUnavailableException e1) {
				
				e1.printStackTrace();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		repaint();

	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R' && y[0] < SCREEN_HEIGHT && y[0] >= 0) {

					direction = 'L';

				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L' && y[0] < SCREEN_HEIGHT && y[0] >= 0) {

					direction = 'R';

				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D' && x[0] < SCREEN_WIDTH && x[0] >= 0) {

					direction = 'U';

				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U' && x[0] < SCREEN_WIDTH && x[0] >= 0) {

					direction = 'D';

				}
				break;

			}
		}
	}

	public class MouseAdapter implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isStarted) {
				isStarted = true;

			} else if (gameOver) {
				applesEaten = 0;
				if (musicON) {
					clip.start();
					clip.loop(clip.LOOP_CONTINUOUSLY);
				}
				gameOver = false;
				startGame();
			} else {
				if (musicON) {
					clip.stop();
					musicON = false;
				} else {
					clip.start();
					clip.loop(clip.LOOP_CONTINUOUSLY);
					musicON = true;
				}
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
			

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			

		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

	}

}
