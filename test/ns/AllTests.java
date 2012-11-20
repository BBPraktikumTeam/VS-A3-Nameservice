package ns;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCommunicator.class, TestNameservice.class,
		TestObjectInfo.class, TestServer.class })
public class AllTests {

}
