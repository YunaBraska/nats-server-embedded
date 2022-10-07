package berlin.yuna.natsserver.embedded.helper;

import com.vdurmont.semver4j.Semver;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static berlin.yuna.natsserver.config.NatsConfig.NATS_VERSION;

@Tag("UnitTest")
class VersionTextUpdaterTest {

    @Test
    void updateVersionTxtTest() throws IOException {
        final Path versionFile = Paths.get(System.getProperty("user.dir"), "version.txt");
        final Semver versionText = semverOf(readFile(versionFile).trim());
        final Semver natsVersion = semverOf(NATS_VERSION.value());
        if (natsVersion.compareTo(versionText) > 0) {
            Files.write(versionFile, natsVersion.getOriginalValue().getBytes());
        }
    }

    private static Semver semverOf(final String semver) {
        return new Semver(semver.startsWith("v") ? semver.substring(1) : semver);
    }

    private static String readFile(final Path versionFile) {
        try {
            return "v" + new String(Files.readAllBytes(versionFile));
        } catch (IOException e) {
            return "";
        }
    }
}
