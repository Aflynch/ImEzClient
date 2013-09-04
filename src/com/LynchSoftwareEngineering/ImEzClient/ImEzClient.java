package com.LynchSoftwareEngineering.ImEzClient;
/** ImEzClient.java 
 * 	This class operates roughly as model, in the MVC scheme, it dispatches splash screens and JFrames
 * 	as there are need. It also plays a role in the coupling that is used to accomplish ISP in this 
 * 	project. 
 * 
 * @author Andrew F. Lynch
 *
 */

public class ImEzClient extends Thread {
	private String userName;
	private LogInJFrame logInJFrame;
	private NetWorkObject netWorkObject;
	private SplashScreenJWindowRunnable splashScreenJWindowRunnable;
	private Thread splashScreenThread;
	@Override
	public void run() {
		loadSlashScreen(); 
		System.out.println("Splash screen was loaded. ");
		netWorkObject = new NetWorkObject(this);
		logInJFrame = new LogInJFrame(netWorkObject, this);
		netWorkObject.setLogInFrame(logInJFrame);
		System.out.println("Here");
		//This shutDownHook is sends a sends the '#kill' to server to end the connection. 
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		    	netWorkObject.sendText("#kill");
		    	System.out.println("closing");
		    }
		}));
	}
	public void loadSlashScreen() {
		splashScreenJWindowRunnable = new SplashScreenJWindowRunnable(this);
		splashScreenThread = new Thread(splashScreenJWindowRunnable);
		splashScreenThread.run();
	}
	public void nullSplashScreen(){
		splashScreenJWindowRunnable = null;
		splashScreenThread = null;
	}
	
	public void startImEzClientChatFrame(){
		ImEzClientChatJFrame imEzClientChatJFrame = new ImEzClientChatJFrame(netWorkObject, this, userName);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		System.out.println("USERNAME HAS BEEN CHANGED ===>"+ this.userName);
	}

	public LogInJFrame getTestFrame() {
		return logInJFrame;
	}

	public void setTestFrame(LogInJFrame logInJFrame) {
		this.logInJFrame = logInJFrame;
	}

	public NetWorkObject getNetWorkObject() {
		return netWorkObject;
	}

	public void setNetWorkObject(NetWorkObject netWorkObject) {
		this.netWorkObject = netWorkObject;
	}
	
}
