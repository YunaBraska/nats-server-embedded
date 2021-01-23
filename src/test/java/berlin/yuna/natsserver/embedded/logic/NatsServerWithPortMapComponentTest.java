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
@EnableNatsServer(config = "port:4235")
@DisplayName("NatsServer AutoConfig port from map test")
class NatsServerWithPortMapComponentTest {

    @Autowired
    private NatsServer natsServer;

    @Test
    @DisplayName("Overwrite port on config map")
    void natsServer_withCustomPortInMap_shouldStartWithCustomPort() throws IOException {
        new Socket("localhost", 4235).close();
        assertThat(natsServer, is(notNullValue()));
        assertThat(natsServer.port(), is(4235));
        natsServer.stop();
    }
}
