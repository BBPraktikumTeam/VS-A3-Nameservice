package ns;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestServer {

	private final static int SERVER_PORT = 15001;
	private static Socket clientSocket1, clientSocket2;
	private static PrintWriter out1 = null;
	private static BufferedReader in1 = null;
	private static String inLine1 = null;
	private static PrintWriter out2 = null;
	private static BufferedReader in2 = null;
	private static String inLine2 = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final String[] args = { String.valueOf(SERVER_PORT) };
		new Thread() {
			public void run() {
				Server.main(args);
			}
		}.start();

		clientSocket1 = new Socket("localhost", SERVER_PORT);
		out1 = new PrintWriter(clientSocket1.getOutputStream(), true);
		in1 = new BufferedReader(new InputStreamReader(
				clientSocket1.getInputStream()));

		clientSocket2 = new Socket("localhost", SERVER_PORT);
		out2 = new PrintWriter(clientSocket2.getOutputStream(), true);
		in2 = new BufferedReader(new InputStreamReader(
				clientSocket2.getInputStream()));

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		in1.close();
		out1.close();
		clientSocket1.close();

		in2.close();
		out2.close();
		clientSocket2.close();

	}

	@Test
	public void multipleConnections() {
		out1.println("rebind");
		try {
			inLine1 = in1.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine1.startsWith("Exception"));

		out2.println("rebind");
		try {
			inLine2 = in2.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine2.startsWith("Exception"));
	}

	@Test
	public void rebindAndResolveWithMultipleConnection() {
		out1.println("resolve,name43");
		try {
			inLine1 = in1.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine1.equals("nameNotFound"));

		out2.println("resolve,name43");
		try {
			inLine2 = in2.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine2.equals("nameNotFound"));

		out1.println("rebind,name43,java.lang.Object,nowhere.com,12345");
		try {
			inLine1 = in1.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine1.equals("ok"));

		out1.println("resolve,name43");
		try {
			inLine1 = in1.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine1
				.equals("result,name43,java.lang.Object,nowhere.com,12345"));

		out2.println("resolve,name43");
		try {
			inLine2 = in2.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(inLine2
				.equals("result,name43,java.lang.Object,nowhere.com,12345"));
	}
}
