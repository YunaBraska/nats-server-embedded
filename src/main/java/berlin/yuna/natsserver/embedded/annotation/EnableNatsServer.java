package berlin.yuna.natsserver.embedded.annotation;

import berlin.yuna.natsserver.embedded.logic.NatsServer;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation that can be specified on a test class that runs Nats based tests.
 * Provides the following features over and above the regular <em>Spring {@link org.springframework.test.context.TestContext}
 * Framework</em>:
 * <ul>
 * <li>Registers a {@link NatsServer} bean with the {@link NatsServer} bean name.
 * </li>
 * </ul>
 * <p>
 * The typical usage of this annotation is like:
 * <pre class="code">
 * &#064;{@link org.springframework.boot.test.context.SpringBootTest}
 * &#064;{@link EnableNatsServer}
 * public class MyNatsTests {
 *
 *    &#064;{@link org.springframework.beans.factory.annotation.Autowired}
 *    private {@link NatsServer} natsServer;
 *
 * }
 * </pre>
 */
@Retention(RUNTIME)
@Target(TYPE)
@Documented
@Inherited
public @interface EnableNatsServer {

    /**
     * Sets nats port
     * -1 means random port
     */
    int port() default 4222;

    /**
     * Defines the startup and teardown timeout
     */
    long timeoutMs() default 10000;

    /**
     * Config file
     */
    String configFile() default "";

    /**
     * Custom download URL
     */
    String downloadUrl() default "";

    /**
     * File to nats server binary so no download will be needed
     */
    String binaryFile() default "";

    /**
     * Passes the original parameters to {@link NatsServer#config(String...)} for startup
     * {@link berlin.yuna.natsserver.config.NatsConfig}
     */
    String[] config() default {};

    /**
     * Sets the version for the {@link NatsServer}
     */
    String version() default "";
}
