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
            final String desc = config.desc();
            metadata.newProperties().name(name).description(parseDesc(desc)).type(parseType(desc)).defaultValue(config.valueRaw());
        }

        final Path generated = metadata.generate();
        assertThat(generated, is(notNullValue()));
    }

    private String parseDesc(final String description) {
        return description.substring(description.indexOf(']') + 1).trim();
    }

    private Class<?> parseType(final String description) {
        String goType = description.replace("-", "");
        goType = goType.substring(1, goType.indexOf(']')).trim();
        switch (goType) {
            case "INT":
                return Integer.class;
            case "SIZE":
                return Long.class;
            case "BOOL":
                return Boolean.class;
            default:
                return String.class;
        }
    }
}
