package berlin.yuna.natsserver.embedded.annotation;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;

import java.util.List;

class EnableNatsServerContextCustomizerFactory implements ContextCustomizerFactory {

    /**
     * @param testClass        current test class with {@link EnableNatsServer} annotation
     * @param configAttributes {@link ContextConfigurationAttributes} not in use
     * @return {@link EnableNatsServerContextCustomizer}
     */
    @Override
    public ContextCustomizer createContextCustomizer(Class<?> testClass, List<ContextConfigurationAttributes> configAttributes) {
        EnableNatsServer enableNatsServer = AnnotatedElementUtils.findMergedAnnotation(testClass, EnableNatsServer.class);
        return new EnableNatsServerContextCustomizer(enableNatsServer);
    }

}