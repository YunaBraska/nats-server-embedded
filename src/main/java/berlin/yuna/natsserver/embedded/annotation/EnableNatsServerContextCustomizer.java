package berlin.yuna.natsserver.embedded.annotation;

import berlin.yuna.clu.logic.SystemUtil;
import berlin.yuna.natsserver.config.NatsConfig;
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

import java.util.HashMap;
import java.util.Map;

import static berlin.yuna.natsserver.config.NatsConfig.PORT;
import static berlin.yuna.natsserver.embedded.logic.NatsServer.BEAN_NAME;
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
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Assert.isInstanceOf(DefaultSingletonBeanRegistry.class, beanFactory);
        ConfigurableEnvironment environment = context.getEnvironment();

        if (enableNatsServer == null) {
            LOG.debug("Skipping [{}] cause its not defined", EnableNatsServer.class.getSimpleName());
            return;
        }

        final NatsServer natsServerBean = new NatsServer(enableNatsServer.timeoutMs());
        natsServerBean.setConfig(enableNatsServer.config());
        natsServerBean.port(overwritePort(natsServerBean));
        String sourceUrl = overwriteSourceUrl(environment, natsServerBean.source());
        natsServerBean.source(!hasText(sourceUrl) ? natsServerBean.source() : sourceUrl);
        natsServerBean.setConfig(mergeConfig(environment, natsServerBean.getConfig()));

        try {
            natsServerBean.start(enableNatsServer.timeoutMs());
        } catch (Exception e) {
            natsServerBean.stop(enableNatsServer.timeoutMs());
            throw new NatsStartException("Failed to initialise " + EnableNatsServer.class.getSimpleName(), e);
        }

        beanFactory.initializeBean(natsServerBean, BEAN_NAME);
        beanFactory.registerSingleton(BEAN_NAME, natsServerBean);
        ((DefaultSingletonBeanRegistry) beanFactory).registerDisposableBean(BEAN_NAME, natsServerBean);
    }

    private String overwriteSourceUrl(final ConfigurableEnvironment environment, final String fallback) {
        return environment.getProperty("nats.source.default", environment.getProperty("nats.source." + SystemUtil.getOsType().toString().toLowerCase(), fallback));
    }

    private int overwritePort(final NatsServer natsServerBean) {
        if (enableNatsServer.randomPort()) {
            return -1;
        }
        return enableNatsServer.port() > 0 && enableNatsServer.port() != (Integer) PORT.getDefaultValue() ? enableNatsServer.port() : natsServerBean.port();
    }

    private Map<NatsConfig, String> mergeConfig(final ConfigurableEnvironment environment, final Map<NatsConfig, String> originalConfig) {
        Map<NatsConfig, String> mergedConfig = new HashMap<>(originalConfig);
        for (NatsConfig NatsConfig : NatsConfig.values()) {
            String key = "nats.server." + NatsConfig.name().toLowerCase();
            String value = environment.getProperty(key);
            if (hasText(value) && !mergedConfig.containsKey(NatsConfig)) {
                mergedConfig.put(NatsConfig, value);
            }
        }
        return mergedConfig;
    }
}
