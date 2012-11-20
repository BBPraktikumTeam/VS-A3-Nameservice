package ns;

import java.net.InetSocketAddress;

final class ObjectInfo {

	private final String name;
	private final Class<?> type;
	private final InetSocketAddress address;

	ObjectInfo(String name, Class<?> type, InetSocketAddress address) {
		this.name = name;
		this.type = type;
		this.address = address;
	}
	
	String name() {
		return name;
	}
	
	Class<?> type() {
		return type;
	}
	
	InetSocketAddress address() {
		return address;
	}
}
