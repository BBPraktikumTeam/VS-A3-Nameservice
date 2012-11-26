package ns;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestNameservice {

	private static ObjectInfo obj1, obj2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 obj1 = new ObjectInfo("name1", "java.lang.Object", "localhost", 1234);
		 obj2 = new ObjectInfo("name2", "java.lang.Object", "localhost", 1234);
	}

	@Test
	public void rebind() {
		NameserviceDb.rebind(obj1);
	}

	@Test
	public void resolve() {
		assertNull(NameserviceDb.resolve(obj2.name()));
		NameserviceDb.rebind(obj2);
		assertEquals(obj2, NameserviceDb.resolve(obj2.name()));
	}
}
