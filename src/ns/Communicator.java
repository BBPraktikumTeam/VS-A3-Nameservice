package ns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

final class Communicator extends Thread {
	private PrintWriter out;
	private BufferedReader in;
	private String inputLine;
	private String[] inputTokens;

	Communicator(Socket socket) {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while ((inputLine = in.readLine()) != null) {
				inputTokens = inputLine.split(",");
				if (inputTokens[0].equals("rebind")) {
					rebind(inputTokens);
				} else if (inputTokens[0].equals("resolve")) {
					resolve(inputTokens);
				} else {
					error("unknown command: " + inputTokens[0]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void rebind(String[] tokens) {
		if (tokens.length!=5) {
			error("rebind: wrong number of params");
		} else {
			String name = tokens[1];
			Class<?> type=null;
			try {
				 type = Class.forName(tokens[2]);
			} catch (ClassNotFoundException e) {
				error("rebind: class not found: " + tokens[2]);
			}
			String host = tokens[3];
			int port = Integer.parseInt(tokens[4]);
			Nameservice.rebind(new ObjectInfo(name, type, new InetSocketAddress(host, port)));
			System.out.println("rebind: " + name + "," + type + "," + host + "," + port);
			out.println("ok");
		}
	}

	private void resolve(String[] tokens) {
		if (tokens.length!=2) {
			error("resolve: wrong number of params");
		} else {
			String name = tokens[2];
			ObjectInfo obj = Nameservice.resolve(name);
			if (obj==null) {
				System.out.println("resolve: name not found: " + name);
				out.println("nameNotFound");
			} else {
				Class<?> type = obj.type();
				InetSocketAddress address = obj.address();
				System.out.println("resolve: " + name + "," + type + "," + address.getHostName() + "," + address.getPort());
				out.println("result," + name + "," + type + "," + address.getHostName() + "," + address.getPort());
			}
		}
	}

	private void error(String message) {
		System.out.println("ERROR: " + message);
		out.println("ERROR: " + message);
	}
}
