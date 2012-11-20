package ns;

import java.util.HashMap;
import java.util.Map;

final class Nameservice {

	private static Map<String, ObjectInfo> bindings = new HashMap<String, ObjectInfo>();
	
	synchronized static void rebind(ObjectInfo obj) {
		bindings.put(obj.name(), obj);
	}
	
	static ObjectInfo resolve(String name) {
		return bindings.get(name);
	}
}
