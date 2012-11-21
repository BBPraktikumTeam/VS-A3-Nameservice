package ns;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCommunicator {

	private final static int SERVER_PORT = 15000;

	private static Communicator comm;
	private static Socket clientSocket;
	private static PrintWriter out = null;
	private static BufferedReader in = null;
	private static String inLine = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		new Thread() {
			public void run() {
				try {
					ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
					comm = new Communicator(serverSocket.accept());
					comm.start();
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		clientSocket = new Socket("localhost", SERVER_PORT);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		in.close();
		out.close();
		clientSocket.close();
	}

	@Test
	public void rebindWithWrongParameterNumber() {

		out.println("rebind");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,name01");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,name02,java.lang.String");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,name03,java.lang.String,nowhere.com");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,name05,java.lang.String,nowhere.com,12345,bullshit");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));
	}

	@Test
	public void rebindWithEmptyName() {
		out.println("rebind,,java.lang.String,nowhere.com,12345");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,   ,java.lang.String,nowhere.com,12345");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));
	}

	@Test
	public void rebindWithEmptyType() {
		out.println("rebind,name06,,nowhere.com,12345");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,name07,   ,nowhere.com,12345");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));
	}

	@Test
	public void rebindWithEmptyHost() {
		out.println("rebind,name08,java.lang.String,,12345");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,name09,java.lang.String,   ,12345");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));
	}

	@Test
	public void rebindWithIllegalPort() {
		out.println("rebind,name08,java.lang.String,nowhere.com,-1");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,name08,java.lang.String,nowhere.com,65536");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,name08,java.lang.String,nowhere.com,2.0");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,name08,java.lang.String,nowhere.com,null");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));
	}

	@Test
	public void resolveWithWrongParameterNumber() {

		out.println("resolve");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebind,name00,bullshit");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));
	}

	@Test
	public void resolveWithEmptyName() {
		out.println("resolve,,");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("resolve,   ,");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));
	}

	@Test
	public void rebindAndResolve() {
		out.println("resolve,name42");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.equals("nameNotFound"));

		out.println("rebind,name42,java.lang.Object,nowhere.com,12345");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.equals("ok"));
		out.println("resolve,name42");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine
				.equals("result,name42,java.lang.Object,nowhere.com,12345"));
	}

	@Test
	public void unknownCommand() {
		out.println("rebin,name50,java.lang.Object,nowhere.com,12345");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("rebinde,name51,java.lang.Object,nowhere.com,12345");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("resolv,name");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("resolver,name");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));

		out.println("bullshit");
		try {
			inLine = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine.startsWith("Exception"));
	}
}
