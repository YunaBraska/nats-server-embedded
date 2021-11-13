package berlin.yuna.natsserver.embedded.logic;

import berlin.yuna.natsserver.config.NatsConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static berlin.yuna.natsserver.config.NatsConfig.PORT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

@Tag("UnitTest")
@DisplayName("Configuration unit test")
class NatsServerTest {

    private NatsServer natsServer;

    @BeforeEach
    void setUp() {
        natsServer = new NatsServer(5000);
    }

    @AfterEach
    void tearDown() {
        natsServer.destroy();
    }

    @Test
    void runNatsServer() throws Exception {
        natsServer.config(PORT, "-1").start();
        assertThat(natsServer.pid(), is(greaterThan(-1)));
        natsServer.destroy();
    }
}