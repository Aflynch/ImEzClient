package com.LynchSoftwareEngineering.ImEzClient;
/** SplashScreenJWindowRunnable.java 
 * 	This is extends {@link JWindow} and implements {@link Runnable} so that it can 
 *	show an image with out a menu bar and be shown of a set amount of time. The Splash Screen
 *	is only for show nothing else is being load at this time. This project was not made as 
 *	release to the public but as a demonstration of competence. As such sometime constraint 
 *	were put on this project to limit scope creep.  
 * 
 * @author Andrew F. Lynch
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class SplashScreenJWindowRunnable extends JWindow implements Runnable {
	private ImEzClient imEzClient;
	private Image splashImgae;
	private Rectangle splashImageRectangle;
	public SplashScreenJWindowRunnable(ImEzClient imEzClient) {
		super();
		this.imEzClient = imEzClient;
	}
	
	public void setUpGUI(){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		try {
			splashImgae = ImageIO.read( getClass().getResource("ImEzLogo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		splashImageRectangle = new Rectangle(dimension.width/3,dimension.height/3, dimension.width/3,dimension.height/3 );
		setLocation(splashImageRectangle.x, splashImageRectangle.y);
		setSize(splashImageRectangle.width, splashImageRectangle.height);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.drawImage(splashImgae, 0, 0,splashImageRectangle.width , splashImageRectangle.height, null);

	}

	@Override
	public void run() {
		setUpGUI();
		try{
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setVisible(false);
		imEzClient.nullSplashScreen();
	}
}
