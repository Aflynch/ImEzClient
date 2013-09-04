package com.LynchSoftwareEngineering.ImEzClient;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
/** NetWorkObject.java
 * 	This class is and {@link SocketInListener} handle all of the technical ends of sending and 
 * 	receiving data via Sockets. {@link SocketInListener} is coupled with this class for ease
 * 	of access.
 * 
 * @author Andrew F. Lynch
 *
 */

public class NetWorkObject {
	private ImEzClient imEzClient;
	private LogInJFrame logInJFrame;
	private TimeStampBufferedWriter timeStampBufferedWriter;
	private ImEzClientChatJFrame imEzClientChatJFrame;
	private SocketInListener socketInListener;
	private ArrayList<String> chatReadyUsersArrayList;
	private ClientSideConnectoinValidatorThread clientSideConnectoinValidatorThread;

	/**
	 * @param args
	 */
	public NetWorkObject(ImEzClient imEzClient) {	
		this.imEzClient = imEzClient;
		try {
			Socket socket = new Socket("johnny-be-good.net", 9902); // "localhost" or "54.244.116.111"
			timeStampBufferedWriter = new TimeStampBufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
			socketInListener = new SocketInListener(socket, this);
			chatReadyUsersArrayList = new ArrayList<String>();
			socketInListener.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		
	}

	public void sendText(String text) {
		if(timeStampBufferedWriter == null){
			System.out.println("bufferedWriter Null!!");
		}
		try {
			timeStampBufferedWriter.write(text+"\n");
			timeStampBufferedWriter.flush();
			System.out.println(this + " sendText "+text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void startClientConntectionValidatorIfNotEqulNull(){
		if (clientSideConnectoinValidatorThread == null){
			clientSideConnectoinValidatorThread = new ClientSideConnectoinValidatorThread(timeStampBufferedWriter);
			clientSideConnectoinValidatorThread.start();
		}
	}

	public void addImEzClientChatFrame(ImEzClientChatJFrame imEzClientChatJFrame) {
		this.imEzClientChatJFrame = imEzClientChatJFrame;
		socketInListener.addImEzClientFrame(imEzClientChatJFrame);
	}

	public void checkCurrentUserNameAndPassWord(String userName, String password) {
		sendText("#oldUser");
		sendText(userName);
		sendText(password);	
		startClientConntectionValidatorIfNotEqulNull();
	}

	public void checkNewUser(String userName, String password) {
		sendText("#newUser");
		sendText(userName);
		sendText(password);	
		startClientConntectionValidatorIfNotEqulNull();
	}
	
	public void setLogInFrame(LogInJFrame logInJFrame) {
		this.logInJFrame = logInJFrame;
	}
	
	public LogInJFrame getLogInJFrame(){
		return logInJFrame;
	}

	public void addToUserArrayList(ArrayList<String> namesOfOnLineUsersArrayList) {
		for(String userName: namesOfOnLineUsersArrayList){
			chatReadyUsersArrayList.add(userName);
		}
	}

	public void removeFromUserArrayList(ArrayList<String> namesOfOnLineUsersArrayList) {
		for(String userName: namesOfOnLineUsersArrayList){
			chatReadyUsersArrayList.remove(userName);
		}		
	}

	public ImEzClient getImEzClient() {
		return imEzClient;
	}

	public void setImEzClient(ImEzClient imEzClient) {
		this.imEzClient = imEzClient;
	}

	public ImEzClientChatJFrame getImEzClientChatFrame() {
		return imEzClientChatJFrame;
	}

	public void setImEzClientChatFrame(ImEzClientChatJFrame imEzClientChatJFrame) {
		this.imEzClientChatJFrame = imEzClientChatJFrame;
	}

	public ArrayList<String> getChatReadyUsersArrayList() {
		return chatReadyUsersArrayList;
	}

	public void setChatReadyUsersArrayList(ArrayList<String> chatReadyUsersArrayList) {
		this.chatReadyUsersArrayList = chatReadyUsersArrayList;
	}
	
	
}
