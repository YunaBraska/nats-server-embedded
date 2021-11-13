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
@EnableNatsServer(port = 4247, config = {"port", "4246"})
@DisplayName("NatsServer overwrite AutoConfig port test")
class NatsServerOverwritePortComponentTest {

    @Autowired
    private NatsServer natsServer;

    @Test
    @DisplayName("Custom port overwritten by map")
    void natsServer_customPorts_shouldOverwritePortMap() {
        NatsUtils.waitForPort(4246, 5000, false);
        assertThat(natsServer, is(notNullValue()));
        assertThat(natsServer.port(), is(4246));
        assertThat(natsServer.pid(), is(greaterThan(-1)));
        natsServer.stop();
    }
}
