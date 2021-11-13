package berlin.yuna.natsserver.embedded.annotation;

import berlin.yuna.natsserver.embedded.model.exception.NatsStartException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.test.context.MergedContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static berlin.yuna.natsserver.config.NatsConfig.ADDR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@DisplayName("Configuration unit test")
class NatsServerComponentRandomPortTest {

    private EnableNatsServer annotation;
    private AnnotationConfigApplicationContext ctx;
    private MergedContextConfiguration mCtx;
    private EnableNatsServerContextCustomizer contextCustomizer;


    @BeforeEach
    void setUp() {
        ctx = new AnnotationConfigApplicationContext();
        mCtx = mock(MergedContextConfiguration.class);
        annotation = mock(EnableNatsServer.class);
        when(annotation.port()).thenReturn(4222);
        when(annotation.timeoutMs()).thenReturn(512L);
        contextCustomizer = new EnableNatsServerContextCustomizer(annotation);
        final MutablePropertySources propertySources = ctx.getEnvironment().getPropertySources();
        final Map<String, Object> map = new HashMap<>();
        map.put("nats.server.nats_log_name", "NatsTest");
        propertySources.addFirst(new MapPropertySource("nats.server.nats_log_name", map));
    }

    @Test
    @DisplayName("Download and start server")
    void natsServer_configureForCovWithWrongPort_shouldFailToStart() {
        when(annotation.config()).thenReturn(new String[]{ADDR.name(), "invalid"});
        when(annotation.configFile()).thenReturn("invalid/path");
        assertThrows(NatsStartException.class, () -> contextCustomizer.customizeContext(ctx, mCtx));
    }
}
