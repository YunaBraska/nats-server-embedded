package berlin.yuna.natsserver.embedded.model.exception;

public class NatsStreamingStartException extends RuntimeException {

    public NatsStreamingStartException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
