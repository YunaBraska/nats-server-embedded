package berlin.yuna.natsserver.embedded.util;


import berlin.yuna.configmetadata.model.ConfigurationMetadata;
import berlin.yuna.natsserver.config.NatsConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag("UnitTest")
class ConfigMetadataIntegrationTest {

    @Test
    @DisplayName("Generate spring boot autocompletion")
    void generate() throws IOException {
        final ConfigurationMetadata metadata = new ConfigurationMetadata("nats.server", NatsConfig.class);
        for (NatsConfig config : NatsConfig.values()) {
            final String name = config.name().toLowerCase();
            final String desc = config.description();
            final Class<?> type = config.type();
            final Object defaultValue = config.defaultValue();
            metadata.newProperties().name(name).description(desc).type(type == NatsConfig.SilentBoolean.class ? Boolean.class : type).defaultValue(defaultValue);
        }

        final Path generated = metadata.generate();
        assertThat(generated, is(notNullValue()));
    }
}
