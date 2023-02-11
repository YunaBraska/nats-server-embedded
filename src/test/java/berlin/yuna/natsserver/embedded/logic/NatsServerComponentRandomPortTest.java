package berlin.yuna.natsserver.embedded.logic;

import berlin.yuna.natsserver.embedded.annotation.EnableNatsServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;

import static berlin.yuna.natsserver.config.NatsConfig.PORT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
@EnableNatsServer(port = -1, timeoutMs = 5000)
@Tag("IntegrationTest")
@DisplayName("NatsServerRandomPortComponentTestTest")
class NatsServerComponentRandomPortTest {

    @Autowired
    private NatsServer natsServer;

    @Test
    @DisplayName("Download and start server")
    void natsServer_shouldDownloadUnzipAndStart() throws IOException {
        Files.deleteIfExists(natsServer.binary());
        assertThat(natsServer, is(notNullValue()));
        System.out.println("Port: " + natsServer.port());
        assertThat(natsServer.port(), is(greaterThan(((int) PORT.defaultValue()))));
    }
}
