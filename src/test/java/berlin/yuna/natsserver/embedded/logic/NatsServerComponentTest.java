package berlin.yuna.natsserver.embedded.logic;

import berlin.yuna.natsserver.embedded.annotation.EnableNatsServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.Socket;

import static berlin.yuna.natsserver.config.NatsConfig.PORT;
import static berlin.yuna.natsserver.config.NatsOptions.natsBuilder;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@EnableNatsServer(port = -1)
@Tag("IntegrationTest")
@DisplayName("NatsServerComponentTest")
class NatsServerComponentTest {

    @Autowired
    private NatsServer natsServer;

    @Test
    @DisplayName("Start server on a random port")
    void natsServer_shouldStartOnARandomPort() {
        assertThat(natsServer, is(notNullValue()));
        assertThat(natsServer.port(), is(greaterThan((int) PORT.defaultValue())));
        assertThat(natsServer.pid(), is(greaterThan(-1)));
    }

    @Test
    @DisplayName("Port config with double dash")
    void secondNatsServer_withDoubleDotSeparatedProperty_shouldStartSuccessful() {
        assertNatsServerStart(4225, "--port", "4225");
    }

    @Test
    @DisplayName("Port config without dashes")
    void secondNatsServer_withOutMinusProperty_shouldStartSuccessful() {
        assertNatsServerStart(4226, "port", "4226");
    }

    @Test
    @DisplayName("Invalid config [FAIL]")
    void secondNatsServer_withInvalidProperty_shouldFailToStart() {
        assertThrows(
                IllegalArgumentException.class,
                () -> assertNatsServerStart(4228, "p", "4228"),
                "No enum constant"
        );
    }

    @Test
    @DisplayName("ToString")
    void toString_shouldPrintPortAndOs() {
        final String serverString = natsServer.toString();
        assertThat(serverString, containsString(String.valueOf(natsServer.port())));
    }

    private void assertNatsServerStart(final int port, final String... config) {
        try (final NatsServer natsServer = new NatsServer(natsBuilder().timeoutMs(10000).config(config).build())) {
            new Socket("localhost", port).close();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
