package berlin.yuna.natsserver.embedded.logic;

import berlin.yuna.natsserver.embedded.annotation.EnableNatsServer;
import berlin.yuna.natsserver.logic.NatsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
@Tag("IntegrationTest")
@EnableNatsServer(port = 4227 ,config = {"port", "4235"})
@DisplayName("NatsServer AutoConfig port from map test")
class NatsServerWithPortMapComponentTest {

    @Autowired
    private NatsServer natsServer;

    @Test
    @DisplayName("Overwrite port on config map")
    void natsServer_withCustomPortInMap_shouldStartWithCustomPort() {
        NatsUtils.waitForPort(4235, 5000, false);
        assertThat(natsServer, is(notNullValue()));
        assertThat(natsServer.port(), is(4235));
        assertThat(natsServer.pid(), is(greaterThan(-1)));
        natsServer.close();
    }
}
