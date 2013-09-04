package com.LynchSoftwareEngineering.ImEzClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/** SocketInListener.java 
 * 	This class operates as an NetWork Controller. Ideally in the MVC scheme there would only
 * 	be one class that was a controller. However having a GUI inputs and NetWork inputs that
 *  need to be processed.I felt that the data was best organized at the point of contact 
 *  for the respective areas.  
 * 
 * @author Andrew F. Lynch
 *
 */

public class SocketInListener extends Thread {

	private Socket socket;
	private ImEzClientChatJFrame imEzClientChatJFrame;
	private BufferedReader bufferedReader;
	private NetWorkObject netWorkObject;

	public SocketInListener(Socket socket, NetWorkObject netWorkObject) {
		this.socket = socket;
		this.netWorkObject = netWorkObject;
	}

	@Override
	public void run() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			try {
				String buffer = bufferedReader.readLine();
				
				System.out.println("---------"+buffer);
				if (buffer.equals("#UserListAdd") || buffer.equals("#UserListRemove")) {
					handleAddOrRemoveChatReadyUsers(buffer);
				} else if (buffer.equals("#LoggedIn")) {
					netWorkObject.getLogInJFrame().kill();
					netWorkObject.getImEzClient().startImEzClientChatFrame();
				} else {
					sendString(buffer);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void handleAddOrRemoveChatReadyUsers(String buffer)
			throws NumberFormatException, IOException {
		int numberOfUsers = Integer.parseInt(bufferedReader.readLine());
		ArrayList<String> namesOfOnLineUsersArrayList = new ArrayList<String>();
		for (int i = numberOfUsers; i > 0; i--) {
			namesOfOnLineUsersArrayList.add(bufferedReader.readLine());
		}
		if (netWorkObject.getImEzClient().getUserName() == null) {
			System.out.println("CLIENT USER NAME OUT OUT OF SYNC");
		}
		namesOfOnLineUsersArrayList.remove(netWorkObject.getImEzClient().getUserName());
		if (buffer.endsWith("#UserListAdd")) {
			System.out.println("Add: " + namesOfOnLineUsersArrayList);
			if (imEzClientChatJFrame == null) {
				netWorkObject.addToUserArrayList(namesOfOnLineUsersArrayList);
			} else {
				imEzClientChatJFrame.addToOnLineJTabel(namesOfOnLineUsersArrayList);
			}
			System.out.println("add: " + namesOfOnLineUsersArrayList);
		} else if (buffer.endsWith("#UserListRemove")) {
			System.out.println("Remove: " + namesOfOnLineUsersArrayList);
			if (imEzClientChatJFrame == null) {
				netWorkObject.removeFromUserArrayList(namesOfOnLineUsersArrayList);
			} else {
				imEzClientChatJFrame.removeFromChatReadyUsers(namesOfOnLineUsersArrayList);
			}
			System.out.println("remove: " + namesOfOnLineUsersArrayList);
		} else {
			System.out.println("SocketInListener no flage case for" + buffer);
		}
	}

	private void sendString(String buffer) {
		if (buffer.endsWith("#BadLongIn")) {
			netWorkObject.getImEzClient().getTestFrame().getLblWellcomeToImez().setText("   Invalid User Name &Or PassWord");
		}
		if (imEzClientChatJFrame != null){
			imEzClientChatJFrame.getTextArea().setText(buffer +"\n"+imEzClientChatJFrame.getTextArea().getText());
		}
		System.out.println(this + " imEzClientChatJFrame equaled null "+ buffer);
	}

	public void addImEzClientFrame(ImEzClientChatJFrame imEzClientChatJFrame) {
		this.imEzClientChatJFrame = imEzClientChatJFrame;
	}

}
