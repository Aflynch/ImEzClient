package com.LynchSoftwareEngineering.ImEzClient;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/** ImEzClientChatJFrame.java
 * 	This class is the View for the client to chat with. This class has custom GUI not stalk 
 * 	layouts were use. I set up my own lay out in the layoutJPanels method it holds the left 
 * 	and right JPanels at constant width of 200 and lets the middle grow and or shrink when 
 * 	JFrame is resized.
 * 
 * @author Andrew F. Lynch
 *
 */

public class ImEzClientChatJFrame extends JFrame {
	private ArrayList<String> chatReadyUsersArrayList, chatBuddiesArrayList;
	private NetWorkObject netWorkObject;
	private ImEzClient imEzClient;
	private BackGroundImageJPanel leftJPanel, centerJPanel, rightJPanel;
	private JTextField userInPutJTextField;
	private JTextArea jTextArea;
	private String userName;
	private JList chatReadyJList, chatBuddiesJList;
	private Image backGroundImage;

	/**
	 * Create the frame.
	 */
	public ImEzClientChatJFrame(NetWorkObject netWorkObject, ImEzClient imEzClient, String userName) {
		chatReadyUsersArrayList = new ArrayList<String>();
		chatBuddiesArrayList = new ArrayList<String>();
		ImEzClintChatFrameMouseAdapter imEzClintChatFrameMouseAdapter = new ImEzClintChatFrameMouseAdapter();
		this.netWorkObject = netWorkObject;
		this.imEzClient = new ImEzClient();
		this.userName = userName;
		netWorkObject.addImEzClientChatFrame(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 500);
		setupGUI(imEzClintChatFrameMouseAdapter);
	}

	private void setupGUI(ImEzClintChatFrameMouseAdapter imEzClintChatFrameMouseAdapter) {
		setLayout(null);
		initBackGroundImageJPanels();	
		setLayoutNullAndAddJPanels();
		layoutJPanels();
		setupJComponents(imEzClintChatFrameMouseAdapter);
		setVisible(true);
	}

	private void setupJComponents(ImEzClintChatFrameMouseAdapter imEzClintChatFrameMouseAdapter) {
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		Font userInPutJTextFieldFont = new Font("Dialog", Font.BOLD, 29);
		Font jTextAreaFont = new Font("Dialog", Font.BOLD, 22);
		Color backGroundColor = new Color(.8f, .8f, .8f, 1);
		
		userInPutJTextField = new JTextField();
		userInPutJTextField.setText("Say hi");
		userInPutJTextField.setFont(userInPutJTextFieldFont);
				
		userInPutJTextField.setBackground(backGroundColor);
		centerJPanel.add(userInPutJTextField);
		
		userInPutJTextField.setSelectedTextColor(Color.WHITE);
		userInPutJTextField.addActionListener(new ImEzClientChatFrameActionListener());
		userInPutJTextField.setBorder(border);
		
		jTextArea = new JTextArea();
		jTextArea.setWrapStyleWord(true);
		jTextArea.setLineWrap(true);
		jTextArea.setEditable(false);
		jTextArea.setBorder(border);
		jTextArea.setFont(jTextAreaFont);
		jTextArea.setOpaque(true);
		jTextArea.setBackground(backGroundColor);
		centerJPanel.add(jTextArea);

		chatReadyJList = new JList();
		rightJPanel.add(chatReadyJList);
		chatReadyJList.addMouseListener(imEzClintChatFrameMouseAdapter);
		chatReadyJList.setBorder(border);

		chatBuddiesJList = new JList();
		leftJPanel.add(chatBuddiesJList);
		chatBuddiesJList.addMouseListener(imEzClintChatFrameMouseAdapter);
		chatBuddiesJList.setBorder(border);
	}

	private void setLayoutNullAndAddJPanels() {
		leftJPanel.setLayout(null);
		centerJPanel.setLayout(null);
		rightJPanel.setLayout(null);

		add(leftJPanel);
		add(rightJPanel);
		add(centerJPanel);
	}

	private void initBackGroundImageJPanels() {
		leftJPanel = new BackGroundImageJPanel(getClass().getResource("ImEzTalking.png"));
		centerJPanel = new BackGroundImageJPanel(getClass().getResource("ImEzChatCenter.png"));
		rightJPanel = new BackGroundImageJPanel(getClass().getResource("ImEzOnLine.png"));
	}

	private void layoutWidgets() {
		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int leftWidthInt = (int) leftJPanel.getBounds().getWidth();
		int leftHeightInt = (int) leftJPanel.getBounds().getHeight() - insets.top;
		int centerWidthInt = (int) centerJPanel.getBounds().getWidth();
		int centerHeightInt = (int) centerJPanel.getBounds().getHeight() - insets.top;
		int rightWidthInt = (int) rightJPanel.getBounds().getWidth();
		int rightHeightInt = (int) rightJPanel.getBounds().getHeight() - insets.top;

		jTextArea.setBounds((int)(centerWidthInt*.05), (int)(centerHeightInt*.15),(int)(centerWidthInt*.90),(int) (centerHeightInt*.65));
		userInPutJTextField.setBounds((int)(centerWidthInt*.05), (int)(centerHeightInt*.80),(int)( centerWidthInt*.90),(int) (centerHeightInt*.15));
		chatReadyJList.setBounds((int) (leftWidthInt * .05), (int) (leftHeightInt * .15),(int) (leftWidthInt * .90), (int) (leftHeightInt * .80));
		chatBuddiesJList.setBounds((int) (rightWidthInt * .05), (int) (rightHeightInt * .15),(int) (rightWidthInt * .90), (int) (rightHeightInt * .80));
	}

	private void layoutJPanels() {
		Rectangle parentRectangel = getBounds();
		System.out.println("From Panel " + parentRectangel.width + " : " + parentRectangel.height+ " leftJPanel is null: " + (leftJPanel == null));
		leftJPanel.setBounds(0, 0, 200, parentRectangel.height);
		centerJPanel.setBounds(199, 0, parentRectangel.width - 398, parentRectangel.height);
		rightJPanel.setBounds(parentRectangel.width - 200, 0, 200, parentRectangel.height);
	}

	@Override
	public void paint(Graphics grahics) {
		layoutJPanels();// The layout needs to be done before the drawing can be done.
		layoutWidgets();
		super.paint(grahics);
		grahics.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), null);

	}

	public void addToOnLineJTabel(ArrayList<String> namesOfOnLineUsersArrayList) {
		for (String userName : namesOfOnLineUsersArrayList) {
			chatReadyUsersArrayList.add(userName);
		}
		chatReadyJList.setListData(chatReadyUsersArrayList.toArray());
	}

	public void removeFromChatReadyUsers(ArrayList<String> namesOfOnLineUsersArrayList) {
		for (String userName : namesOfOnLineUsersArrayList) {
			if (chatReadyUsersArrayList.contains(userName)) {
				chatReadyUsersArrayList.remove(userName);
				chatReadyJList.setListData(chatReadyUsersArrayList.toArray());
			} else {
				chatBuddiesArrayList.remove(userName);
				chatBuddiesJList.setListData(chatBuddiesArrayList.toArray());
				netWorkObject.sendText("#ChatBuddy");
				netWorkObject.sendText(userName);
			}
		}
	}

	public JTextArea getTextArea() {
		return jTextArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.jTextArea = textArea;
	}

	class ImEzClientChatFrameActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(userInPutJTextField)) {
				String buffer = userInPutJTextField.getText();
				netWorkObject.sendText(buffer);
				jTextArea.setText("You(" + userName + ") " + userInPutJTextField.getText() + "\n"
						+ jTextArea.getText());
				userInPutJTextField.setText("");
			}
		}
	}

	private void plusMinusChatBuddies(String userName) {
		if (chatBuddiesArrayList.contains(userName)) {// add to chatReady
			chatBuddiesArrayList.remove(userName);
			chatBuddiesJList.setListData(chatBuddiesArrayList.toArray());
			chatReadyUsersArrayList.add(userName);
			chatReadyJList.setListData(chatReadyUsersArrayList.toArray());
		} else {// add to chatBuddies
			chatBuddiesArrayList.add(userName);
			chatBuddiesJList.setListData(chatBuddiesArrayList.toArray());
		}
	}

	class ImEzClintChatFrameMouseAdapter implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(chatReadyJList)) {
				if (e.getClickCount() == 2) {// add chat buddy
					int index = chatReadyJList.locationToIndex(e.getPoint());
					if (index > -1) {
						System.out.println("Double clicked on Item" + index + " "+ chatReadyUsersArrayList.get(index)+" :time "+e.getWhen());
						netWorkObject.sendText("#ChatBuddy");
						String userName = chatReadyUsersArrayList.get(index);
						netWorkObject.sendText(userName);// add to list here
						chatReadyUsersArrayList.remove(userName);
						chatReadyJList.setListData(chatReadyUsersArrayList.toArray());
						chatBuddiesArrayList.add(userName);
						chatBuddiesJList.setListData(chatBuddiesArrayList.toArray());
					}
				}
			} else {
				if (e.getClickCount() == 2) {// remove chat buddy
					int index = chatBuddiesJList.locationToIndex(e.getPoint());
					if (index > -1) {
						System.out.println("Double clicked on Item " + index + " "
								+ chatBuddiesArrayList.get(index)+" : time "+e.getWhen());
						String userName = (chatBuddiesArrayList.get(index));
						netWorkObject.sendText("#ChatBuddy");
						netWorkObject.sendText(userName);
						chatBuddiesArrayList.remove(userName);
						chatBuddiesJList.setListData(chatBuddiesArrayList.toArray());
						chatReadyUsersArrayList.add(userName);
						chatReadyJList.setListData(chatReadyUsersArrayList.toArray());
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
