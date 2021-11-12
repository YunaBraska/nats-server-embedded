package berlin.yuna.natsserver.embedded.logic;

import berlin.yuna.natsserver.logic.Nats;
import org.springframework.beans.factory.DisposableBean;

/**
 * {@link NatsServer}
 */
public class NatsServer extends Nats implements DisposableBean {

    public static final String BEAN_NAME = NatsServer.class.getSimpleName();
    private final long timeoutMs;

    /**
     * Create {@link NatsServer} with simplest start able configuration
     *
     * @param timeoutMs tear down timeout
     */
    public NatsServer(final long timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    /**
     * Simply stops the {@link NatsServer}
     *
     * @see NatsServer#stop()
     */
    @Override
    public void destroy() {
        stop(timeoutMs);
    }
}