package com.LynchSoftwareEngineering.ImEzClient;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/** LogInJFrame.java 
 * 	This class is the view for log in process. The GUI is also laid out in a custom scalable way
 * 	so the window my be reside.No stalk Layouts were used in this project. 
 * 
 * @author Andrew F. Lynch 
 *
 */

public class LogInJFrame extends JFrame {
	private NetWorkObject netWorkObject;
	private ImEzClient imEzClient;
	private JTextField newUserJTextField, userNameJTextField;
	private JPasswordField newUserJPassword, userJPasswordFeild;
	private JButton newUserJButton, logInJButton;
	private Image backGroundImage;
	private JLabel currentUserJLabel, userNameJLabel, passwordJLabel, newUserNameJLabel, newPasswordJLabel ,wellcomeToImezJLabel;
	private double[] column = new double[10]; 
	private double[] row = new double[10];

	/**
	 * Create the frame.
	 */
	public LogInJFrame( NetWorkObject netWorkObject, ImEzClient imEzClient) {	
		this.netWorkObject = netWorkObject;
		this.imEzClient = imEzClient;	
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		setBounds((int)(dimension.width*.25), (int)(dimension.height*.2),(int) (dimension.width*.5),(int) (dimension.height*.5));
		LogInJPanel logInJPanel = new LogInJPanel();
		add(logInJPanel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
		
	public JLabel getLblWellcomeToImez() {
		return wellcomeToImezJLabel;
	}
	public void setLblWellcomeToImez(JLabel lblWellcomeToImez) {
		this.wellcomeToImezJLabel = lblWellcomeToImez;
	}
	public void kill() {
		setVisible(false);
		dispose();
	}
	
public class LogInJPanel extends JPanel{
	
	public LogInJPanel(){
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		try { 
			backGroundImage = ImageIO.read( getClass().getResource("ImEzThree.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		column[0] = 0;
		row[0] = 0;
		Dimension minAndStartScreenDimension = new Dimension(550,250);
		setSize(minAndStartScreenDimension);
		Dimension imageDimension = new Dimension();
		imageDimension.setSize(getWidth(), getHeight());
		ClientActionListener clientActionListener = new ClientActionListener();
		logInJButton = new JButton("Log In");
		
		currentUserJLabel = new JLabel("Current User");
		add(currentUserJLabel);
		
		newUserJTextField = new JTextField();
		newUserJTextField.setColumns(10);
		newUserJTextField.setText("New User Name");
		add(newUserJTextField);
		
		userNameJTextField = new JTextField();
		userNameJTextField.setText("User Name");
		add(userNameJTextField);
		userNameJTextField.setColumns(10);
		
		userJPasswordFeild = new JPasswordField();
		userJPasswordFeild.setText("Password");
		add(userJPasswordFeild);
		
		userNameJLabel = new JLabel("User Name");
		add(userNameJLabel);
		
		passwordJLabel = new JLabel("Password");
		add(passwordJLabel);

		logInJButton.addActionListener(clientActionListener);
		add(logInJButton);
		
		newUserJButton = new JButton("NewUser");
		newUserJButton.addActionListener(clientActionListener);
		add(newUserJButton);
		
		newUserJPassword = new JPasswordField();
		newUserJPassword.setText("NewUserPassword");
		add(newUserJPassword);
		
		newUserNameJLabel = new JLabel("New User name ");
		add(newUserNameJLabel);
		
		newPasswordJLabel = new JLabel("New Password");
		add(newPasswordJLabel);

		wellcomeToImezJLabel = new JLabel("Wellcome to ImEz - You are not yet logged in");		
		add(wellcomeToImezJLabel);

		setVisible(true);

	}
	
	public void upDataRowsAndColumns(){
		int width = getParent().getWidth();
		int height = getParent().getHeight();
		for(int i=1; i<10; i++){
			column[i] = (double) (width* (i/10.0));
		}
		for (int i=1; i<10; i++){
			row[i] = (double) (height* (i/10.0)); 
		}
	}
	
	@Override
	public void paintComponent(Graphics arg0) {
		super.paintComponents(arg0);
		arg0.drawImage(backGroundImage,0,0,getParent().getWidth(),getParent().getHeight(),null);
		//System.out.println("paintComponet was called "+column[9]+" : "+row[9] );
		upDataRowsAndColumns();
		currentUserJLabel.setBounds((int)column[5]-(100/2), (int) row[6], 113, 16);
		userNameJTextField.setBounds( (int)column[9]-134, (int) row[6], 134, 28);
		userJPasswordFeild.setBounds((int)column[9]-134, (int) row[7], 134, 28);
		userNameJLabel.setBounds((int)column[1], (int) row[6], 120, 16);
		passwordJLabel.setBounds((int)column[1], (int) row[7], 101, 16);
		logInJButton.setBounds((int)column[5]-(117/2), (int) row[7], 117, 29);
		newUserJButton.setBounds((int)column[5]-(117/2), (int)row[3], 117, 29);
		newUserJTextField.setBounds((int)column[9]-134, (int)row[2], 134, 28);
		newUserJPassword.setBounds((int)column[9]-134, (int)row[3], 134, 28);
		newUserNameJLabel.setBounds((int)column[1], (int)row[2], 101, 16);
		newPasswordJLabel.setBounds((int)column[1], (int)row[3], 101, 16);
		wellcomeToImezJLabel.setBounds((int)(column[5])-(wellcomeToImezJLabel.getBounds().width)/2, (int)row[0], 304, 16);
	}
		
	}
	
	class  ClientActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource().equals(logInJButton)){
				String userName = userNameJTextField.getText();
				netWorkObject.checkCurrentUserNameAndPassWord(userName, ""+userJPasswordFeild.getPassword());
				netWorkObject.getImEzClient().setUserName(userName);// user name changed
			}else if (arg0.getSource().equals(newUserJButton)){
				String userName = newUserJTextField.getText();
				netWorkObject.checkNewUser(userName, ""+newUserJPassword.getPassword());
				netWorkObject.getImEzClient().setUserName(userName);// user name changed
			}
		}
		
	}


}
