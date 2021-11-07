package berlin.yuna.natsserver.embedded.util;


import berlin.yuna.configmetadata.model.ConfigurationMetadata;
import berlin.yuna.configmetadata.model.Groups;
import berlin.yuna.natsserver.config.NatsConfig;
import berlin.yuna.natsserver.config.NatsSourceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static berlin.yuna.clu.logic.SystemUtil.OS;
import static berlin.yuna.clu.logic.SystemUtil.OS_ARCH;
import static berlin.yuna.clu.logic.SystemUtil.OS_ARCH_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag("UnitTest")
class ConfigMetadataIntegrationTest {

    @Test
    @DisplayName("Generate spring boot autocompletion")
    void generate() throws IOException {
        ConfigurationMetadata metadata = new ConfigurationMetadata("nats.server", NatsConfig.class);
        for (NatsConfig config : NatsConfig.values()) {
            String name = config.name().toLowerCase();
            String desc = config.getDescription();
            metadata.newProperties().name(name).description(parseDesc(desc)).type(parseType(desc)).defaultValue(config.getDefaultValue());
        }

        Groups groups = metadata.newGroups("nats.source", NatsSourceConfig.class);
        for (NatsSourceConfig config : NatsSourceConfig.values()) {
            String name = config.name().toLowerCase();
            String desc = config.getDescription();
            metadata.newProperties().name(groups, name).description(parseDesc(desc)).type(parseType(desc)).defaultValue(config.getDefaultValue(OS, OS_ARCH, OS_ARCH_TYPE));
        }

        Path generated = metadata.generate();
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
