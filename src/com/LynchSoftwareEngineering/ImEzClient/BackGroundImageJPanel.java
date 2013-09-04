package com.LynchSoftwareEngineering.ImEzClient;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/** BackGroundImageJPanel.java
 * 	This class acts like a custom JComponent as wall a JPanel. It was designed to help 
 * 	with the process of putting in background images in GUIs. It can be set to be a custom 
 * 	six Via Rectangle. Without a Rectangle Object the Image will fill the size of the usable
 * 	,JFame.getHeight() - heightOfMuneBarInt,JPanel.
 * 
 * @author Andrew F. Lynch
 *
 */

public class BackGroundImageJPanel extends JPanel {
	private Rectangle rectangle;
	private Image backGroundImage;
	private Insets insets;
	public BackGroundImageJPanel( URL url) {
		try {
			backGroundImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BackGroundImageJPanel(Rectangle rectangle, File file) {
		this.rectangle = rectangle;
		try {
			backGroundImage = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	public int getHeightOfBanner(){ 
		if (insets== null){
			insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		} 
		return (int) insets.top;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(rectangle == null){
			g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight()-getHeightOfBanner(), null);
		}else{
			g.drawImage(backGroundImage, rectangle.x,rectangle.y,(int)rectangle.getWidth(),(int)rectangle.getHeight(), null);
		}
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	public Image getBackGroundImage() {
		return backGroundImage;
	}
	public void setBackGroundImage(Image backGroundImage) {
		this.backGroundImage = backGroundImage;
	}
	public Insets getInsets() {
		return insets;
	}
	public void setInsets(Insets insets) {
		this.insets = insets;
	}


}
