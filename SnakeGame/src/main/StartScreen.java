package main;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import main.GamePanel.MouseAdapter;

public class StartScreen extends JPanel {
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	public boolean isClicked = false;
	
	public StartScreen() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.addMouseListener(new MouseAdapter());
		this.setFocusable(true);
	}
	
	public class MouseAdapter implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			isClicked = true;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
