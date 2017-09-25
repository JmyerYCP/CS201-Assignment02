package edu.ycp.cs201.disks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class DisksPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	Disk disk[]= new Disk[100];
	private Random rand = new Random();
	Disk mouseDisk;
	private Timer timer;
	int randRadius;
	Disk[] diskArray= new Disk[500];
	private int count, color;
	boolean loose= false;
	// TODO: add any other fields you need to represent the state of the game
	
	
	public DisksPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.GRAY);
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				handleMouseClick(e);
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				handleMouseMove(e);
			}
		});
		
		// Schedule timer events to fire every 100 milliseconds (0.1 seconds)
		this.timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleTimerEvent(e);
			}
		});
	}

	// You can call this method to convert a DiskColor value into
	// a java.awt.Color object.
	public Color colorOf(DiskColor dc) {
		return new Color(dc.red(), dc.green(), dc.blue());
	}

	// This method is called whenever the mouse is moved
	protected void handleMouseMove(MouseEvent e) {
		// TODO\
		
		mouseDisk =new Disk((double) e.getX(),(double) e.getY(),(double)randRadius,DiskColor.BLACK);
		repaint();

	}
	
	// This method is called whenever the mouse is clicked
	protected void handleMouseClick(MouseEvent e) {
		// TODO
		if (e.getButton() ==MouseEvent.BUTTON1){
			randRadius=rand.nextInt(34)+10;
			diskArray[count]=new Disk(mouseDisk.getX(),mouseDisk.getY(),mouseDisk.getRadius(),DiskColor.values()[color]);
			for (int i = 0;i<count;i++){
				loose = diskArray[count].overlaps(diskArray[i]);
				}
			
			count++;
			color++;
			if (color>=15){
				color =0;
			}
		}
		repaint();
}
	
	// This method is called whenever a timer event fires
	protected void handleTimerEvent(ActionEvent e) {
		// TODO
	}
	
	private static final Font FONT = new Font("Dialog", Font.BOLD, 36);

	// This method is called automatically whenever the contents of
	// the window need to be redrawn.
	@Override
	public void paintComponent(Graphics g) {
		// Paint the window background
		super.paintComponent(g);
		if (loose){
			gameOver(g,FONT);
		}
			
		
		paintCircle(g,mouseDisk);
		for (int i = 0;i < count; i++){		
			paintCircle(g,diskArray[i]);
		}
		
		// TODO: draw everything that needs to be drawn
	}
	public void paintCircle(Graphics g,Disk d){
        g.setColor(colorOf(d.getColor()));
        g.fillOval((int)d.getX()-(int)d.getRadius(),(int) d.getY()-(int)d.getRadius(),(int) d.getRadius() * 2,(int) d.getRadius() * 2);
    }
	public void gameOver(Graphics g,Font f){
		g.setColor(Color.BLACK);
		g.setFont(f);
		g.drawString("Game Over", 150, 200);
	}
}
