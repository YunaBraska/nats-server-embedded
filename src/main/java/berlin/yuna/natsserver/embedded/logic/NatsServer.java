package berlin.yuna.natsserver.embedded.logic;

import berlin.yuna.clu.logic.SystemUtil;
import berlin.yuna.clu.model.OsArch;
import berlin.yuna.clu.model.OsArchType;
import berlin.yuna.clu.model.OsType;
import berlin.yuna.natsserver.config.NatsSourceConfig;
import berlin.yuna.natsserver.logic.Nats;
import org.springframework.beans.factory.DisposableBean;

import java.net.ConnectException;
import java.nio.file.Path;

/**
 * {@link NatsServer}
 */
public class NatsServer extends Nats implements DisposableBean {

    public static final String BEAN_NAME = NatsServer.class.getSimpleName();
    private final long timeoutMs;

    /**
     * Create custom {@link NatsServer} with simplest configuration {@link NatsServer#config(String...)}
     *
     * @param timeoutMs        tear down timeout
     * @param natsServerConfig passes the original parameters to the server. example: port:4222, user:admin, password:admin
     */
    public NatsServer(final long timeoutMs, final String... natsServerConfig) {
        super(natsServerConfig);
        this.timeoutMs = timeoutMs;
    }

    /**
     * Create {@link NatsServer} with simplest start able configuration
     *
     * @param timeoutMs tear down timeout
     */
    public NatsServer(final long timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    /**
     * Sets the port out of the configuration
     *
     * @param port {@code -1} for random port
     * @return {@link NatsServer}
     * @throws RuntimeException with {@link ConnectException} when there is no port configured
     */
    public NatsServer port(final int port) {
        super.port(port);
        return this;
    }

    /**
     * Url to find nats server source
     *
     * @param natsServerUrl url of the source {@link NatsSourceConfig}
     * @return {@link NatsServer}
     */
    public NatsServer source(final String natsServerUrl) {
        super.source(natsServerUrl);
        return this;
    }

    protected Path getDefaultPath() {
        return super.getDefaultPath();
    }

    protected Path getNatsServerPath(final OsType os, final OsArch arch, final OsArchType archType) {
        return super.getNatsServerPath(os, arch, archType);
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