package be.nexios.projectBootcamp;

//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.IMongoConfig;
//import de.flapdoodle.embed.mongo.config.IMongodConfig;
//import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
//import de.flapdoodle.embed.mongo.config.Net;
//import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.process.runtime.Network;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.UnknownHostException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectBootcampApplicationTests {

//    @Value("${spring.data.mongodb.port}")
//    private int port;
//
//    private MongodExecutable executable;
//
//	@Before
//    public void setUp() throws IOException {
//        IMongodConfig mongoConfig = new MongodConfigBuilder()
//                .version(Version.Main.PRODUCTION)
//                .net(new Net("localhost", port, Network.localhostIsIPv6()))
//                .build();
//        MongodStarter starter = MongodStarter.getDefaultInstance();
//        executable = starter.prepare(mongoConfig);
//        executable.start();
//    }
//
//    @After
//    public void clean() {
//	    executable.stop();
//    }

	@Test
	public void contextLoads() {
	}

}
