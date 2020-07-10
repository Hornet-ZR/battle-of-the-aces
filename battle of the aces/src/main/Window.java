package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame{
	public Window(main m, int width, int height, String title, boolean resizable) {
		Dimension size = new Dimension(width,height);
		this.setSize(size);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(title);
		this.setResizable(resizable);
		this.setLocationRelativeTo(null);
		this.add(m);
		this.setVisible(true);
	}
}
