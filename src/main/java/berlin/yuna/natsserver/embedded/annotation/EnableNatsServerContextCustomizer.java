package berlin.yuna.natsserver.embedded.annotation;

import berlin.yuna.natsserver.config.NatsConfig;
import berlin.yuna.natsserver.config.NatsOptionsBuilder;
import berlin.yuna.natsserver.embedded.logic.NatsServer;
import berlin.yuna.natsserver.embedded.model.exception.NatsStartException;
import org.slf4j.Logger;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.util.Assert;

import static berlin.yuna.natsserver.config.NatsConfig.NATS_BINARY_PATH;
import static berlin.yuna.natsserver.config.NatsConfig.NATS_DOWNLOAD_URL;
import static berlin.yuna.natsserver.config.NatsConfig.NATS_PROPERTY_FILE;
import static berlin.yuna.natsserver.config.NatsConfig.PORT;
import static berlin.yuna.natsserver.config.NatsOptions.natsBuilder;
import static berlin.yuna.natsserver.embedded.logic.NatsServer.BEAN_NAME;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.StringUtils.hasText;

class EnableNatsServerContextCustomizer implements ContextCustomizer {

    private final EnableNatsServer enableNatsServer;
    private static final Logger LOG = getLogger(EnableNatsServerContextCustomizer.class);

    /**
     * Sets the source with parameter {@link EnableNatsServer} {@link EnableNatsServerContextCustomizer#customizeContext(ConfigurableApplicationContext, MergedContextConfiguration)}
     *
     * @param enableNatsServer {@link EnableNatsServer} annotation class
     */
    EnableNatsServerContextCustomizer(final EnableNatsServer enableNatsServer) {
        this.enableNatsServer = enableNatsServer;
    }

    /**
     * customizeContext will start register {@link NatsServer} with bean name {@link NatsServer#BEAN_NAME} to the spring test context
     *
     * @param context      {@link ConfigurableApplicationContext}
     * @param mergedConfig {@link MergedContextConfiguration} is not in use
     */
    @Override
    public void customizeContext(final ConfigurableApplicationContext context, final MergedContextConfiguration mergedConfig) {
        final ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Assert.isInstanceOf(DefaultSingletonBeanRegistry.class, beanFactory);
        final ConfigurableEnvironment environment = context.getEnvironment();

        if (enableNatsServer == null) {
            LOG.debug("Skipping [{}] cause its not defined", EnableNatsServer.class.getSimpleName());
            return;
        }

        NatsServer natsServer = null;
        final NatsOptionsBuilder options = natsBuilder().timeoutMs(enableNatsServer.timeoutMs());
        setEnvConfig(options, environment);
        if (enableNatsServer.port() != (Integer) PORT.defaultValue()) {
            options.port(enableNatsServer.port());
        }
        options.config(enableNatsServer.config());
        configure(options, NATS_PROPERTY_FILE, enableNatsServer.configFile());
        configure(options, NATS_BINARY_PATH, enableNatsServer.binaryFile());
        configure(options, NATS_DOWNLOAD_URL, enableNatsServer.downloadUrl());

        try {
            natsServer = new NatsServer(options.build());
        } catch (Exception e) {
            ofNullable(natsServer).ifPresent(NatsServer::close);
            throw new NatsStartException(
                    "Failed to initialise"
                            + " name [" + EnableNatsServer.class.getSimpleName() + "]"
                            + " port [" + options.port() + "]"
                            + " timeoutMs [" + options.timeoutMs() + "]"
                            + " logLevel [" + options.logLevel() + "]"
                            + " jetStream [" + options.jetStream() + "]"
                            + " autostart [" + options.autostart() + "]"
                            + " configFile [" + options.configFile() + "]"
                            + " downloadUrl [" + options.configMap().get(NATS_DOWNLOAD_URL) + "]"
                    , e
            );
        }

        beanFactory.initializeBean(natsServer, BEAN_NAME);
        beanFactory.registerSingleton(BEAN_NAME, natsServer);
        ((DefaultSingletonBeanRegistry) beanFactory).registerDisposableBean(BEAN_NAME, natsServer);

    }

    private void configure(final NatsOptionsBuilder options, final NatsConfig key, final String value) {
        if (hasText(value)) {
            options.config(key, value);
        }
    }

    private void setEnvConfig(final NatsOptionsBuilder options, final ConfigurableEnvironment environment) {
        for (NatsConfig natsConfig : NatsConfig.values()) {
            final String key = "nats.server." + natsConfig.name().toLowerCase();
            final String value = environment.getProperty(key);
            if (hasText(value)) {
                options.config(natsConfig, value);
            }
        }
    }
}
