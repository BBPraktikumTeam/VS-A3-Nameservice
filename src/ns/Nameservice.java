package ns;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public final class Nameservice {

	private static Map<String, ObjectInfo> bindings = new HashMap<String, ObjectInfo>();

	private static int port;

	public static void main(String[] args) {
		port = Integer.parseInt(args[0]);
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				Socket socket = serverSocket.accept();
				Communicator comm = new Communicator(socket);
				comm.setDaemon(true);
				System.out.println("starting new ns thread");
				comm.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	synchronized static void rebind(ObjectInfo obj) {
		bindings.put(obj.name(), obj);
	}
	
	static ObjectInfo resolve(String name) {
		return bindings.get(name);
	}
}