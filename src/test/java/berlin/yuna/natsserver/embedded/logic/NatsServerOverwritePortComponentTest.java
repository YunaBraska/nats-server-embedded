package berlin.yuna.natsserver.embedded.logic;

import berlin.yuna.natsserver.embedded.annotation.EnableNatsServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@Tag("IntegrationTest")
@EnableNatsServer(port = 4247, config = "port:4246")
@DisplayName("NatsServer overwrite AutoConfig port test")
class NatsServerOverwritePortComponentTest {

    @Autowired
    private NatsServer natsServer;

    @Test
    @DisplayName("Custom port overwrites map")
    void natsServer_customPorts_shouldOverwritePortMap() throws IOException {
        new Socket("localhost", 4247).close();
        assertThat(natsServer, is(notNullValue()));
        assertThat(natsServer.port(), is(4247));
        natsServer.stop();
    }
}
