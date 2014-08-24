package br.usp.each.saeg.jaguar.jacoco;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.jacoco.core.data.AbstractExecutionDataStore;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.data.dua.DataflowExecutionDataStore;
import org.jacoco.core.runtime.RemoteControlReader;
import org.jacoco.core.runtime.RemoteControlWriter;

/**
 * This example connects to a coverage agent that run in output mode
 * <code>tcpserver</code> and requests execution data. The collected data is
 * returned.
 */
public final class JacocoTCPClient {

	private static final String ADDRESS = "localhost";
	private static final int PORT = 6300;
	private final Socket socket;
	private final RemoteControlWriter writer;
	private final RemoteControlReader reader;
	private final Boolean isDataflow;
	
	public JacocoTCPClient(Boolean isDataflow) throws UnknownHostException, IOException{
		socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
		writer = new RemoteControlWriter(socket.getOutputStream());
		reader = new RemoteControlReader(socket.getInputStream());
		this.isDataflow = isDataflow;
	}
	
	public JacocoTCPClient() throws UnknownHostException, IOException{
		this(false);
	}
	
	public AbstractExecutionDataStore read() throws IOException {
		SessionInfoStore sessionInfo = new SessionInfoStore();
		AbstractExecutionDataStore executionData = isDataflow ? new DataflowExecutionDataStore() : new ExecutionDataStore();
		reader.setSessionInfoVisitor(sessionInfo);
		reader.setExecutionDataVisitor(executionData);

		// Send a dump and reset command and read the response:
		writer.visitDumpCommand(true, true);
		reader.read();

		return executionData;
	}
	
	public void closeSocket() throws IOException{
		socket.close();
	}

}
