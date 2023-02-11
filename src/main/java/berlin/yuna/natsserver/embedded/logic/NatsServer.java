package berlin.yuna.natsserver.embedded.logic;

import berlin.yuna.natsserver.config.NatsOptions;
import berlin.yuna.natsserver.logic.Nats;
import org.springframework.beans.factory.DisposableBean;

/**
 * {@link NatsServer}
 */
public class NatsServer extends Nats implements DisposableBean {

    public static final String BEAN_NAME = NatsServer.class.getSimpleName();

    /**
     * Create {@link NatsServer} with the simplest start able configuration
     */
    public NatsServer(final NatsOptions options) {
        super(options);
    }

    /**
     * Simply stops the {@link NatsServer}
     *
     * @see NatsServer#close()
     */
    @Override
    public void destroy() {
        close();
    }
}
