package com.LynchSoftwareEngineering.ImEzClient;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
/**TimeStampBufferedWriter.java
*  This class extends {@link BufferedWriter} and adds the ability to find out when the last 
*  time the flush method was called.
*
*@author Andrew F. Lynch 
*/


public class TimeStampBufferedWriter extends BufferedWriter{
	
	long timeOfLastFlush;

	public TimeStampBufferedWriter(Writer out) {
		super(out);
	}
	
	@Override
	public void flush() throws IOException {
		super.flush();
		timeOfLastFlush = System.currentTimeMillis();
	}

	public long getTimeFromLastFlush() {
		return timeOfLastFlush;
	}

	
	
}
