package ns;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestObjectInfo {

	@Test
	public void create() {
		ObjectInfo obj = new ObjectInfo("name", "java.lang.Object",
				"localhost", 1234);
		assertFalse(obj == null);
	}

	@Test
	public void name() {
		ObjectInfo obj = new ObjectInfo("name", "java.lang.Object",
				"localhost", 1234);
		assertEquals("name", obj.name());
	}

	@Test
	public void type() {
		ObjectInfo obj = new ObjectInfo("name", "java.lang.Object",
				"localhost", 1234);
		assertEquals("java.lang.Object", obj.type());
	}

	@Test
	public void host() {
		ObjectInfo obj = new ObjectInfo("name", "java.lang.Object",
				"localhost", 1234);
		assertEquals("localhost", obj.host());
	}

	@Test
	public void port() {
		ObjectInfo obj = new ObjectInfo("name", "java.lang.Object",
				"localhost", 1234);
		assertEquals(1234, obj.port());
	}

	@Test(expected = IllegalArgumentException.class)
	public void nameNull() {
		new ObjectInfo(null, "java.lang.Object", "localhost", 1234);
	}

	@Test(expected = IllegalArgumentException.class)
	public void typeNull() {
		new ObjectInfo("name", null, "localhost", 1234);
	}

	@Test(expected = IllegalArgumentException.class)
	public void hostNull() {
		new ObjectInfo("name", "java.lang.Object", null, 1234);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nameEmpty() {
		new ObjectInfo("   ", "java.lang.Object", "localhost", 1234);
	}

	@Test(expected = IllegalArgumentException.class)
	public void typeEmpty() {
		new ObjectInfo("name", "   ", "localhost", 1234);
	}

	@Test(expected = IllegalArgumentException.class)
	public void hostEmpty() {
		new ObjectInfo("name", "java.lang.Object", "   ", 1234);
	}

	@Test(expected = IllegalArgumentException.class)
	public void portTooSmall() {
		new ObjectInfo("name", "   ", "localhost", -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void portTooBig() {
		new ObjectInfo("name", "java.lang.Object", "   ", 65536);
	}
}
