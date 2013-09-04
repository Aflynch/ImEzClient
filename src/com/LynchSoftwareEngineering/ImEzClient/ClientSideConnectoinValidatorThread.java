package com.LynchSoftwareEngineering.ImEzClient;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * ClientSideConnectoinValidatorThread.java
 * 
 * @author Andrew F. Lynch
 * 	This class sends a validation signal to the server to let it
 * 	server the that client is still connected.
 */
public class ClientSideConnectoinValidatorThread extends Thread {
	TimeStampBufferedWriter timeStampBufferedWriter;
	final String checkIn = "#checkIn";
	final long timeInterval = 14900;
	long timeToNextInterval;

	public ClientSideConnectoinValidatorThread(TimeStampBufferedWriter timeStampBufferedWriter) {
		this.timeStampBufferedWriter = timeStampBufferedWriter;
	}

	@Override
	public void run() {
		super.run();
		while(true){
			validateClient();
			try {
				sleep(timeToNextInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void validateClient() {
		long timeDifference = System.currentTimeMillis() - timeStampBufferedWriter.getTimeFromLastFlush();
		if (timeDifference > timeInterval) {
			try {
				timeStampBufferedWriter.write(checkIn + "\n");
				timeStampBufferedWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				timeToNextInterval = timeInterval;
			}
			
		} else {
			timeToNextInterval  = timeInterval - timeDifference;
		}

	}
}
