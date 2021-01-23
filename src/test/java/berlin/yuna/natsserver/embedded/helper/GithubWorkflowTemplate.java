package berlin.yuna.natsserver.embedded.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Tag("UnitTest")
class GithubWorkflowTemplate {

    private final Map<String, String> variables = new HashMap<>();

    @Test
    @DisplayName("Generate Workflows")
    void generateWorkflows() throws IOException {
        final Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir"), ".github", "workflows");
        assertThat(Files.exists(path), is(true));
        assertThat(Files.isDirectory(path), is(true));
        final Set<Path> workflows = Files.walk(path, 1).filter(Files::isRegularFile).collect(Collectors.toSet());
        workflows.forEach(this::readVariables);
        workflows.forEach(this::replacePlaceHolder);
    }

    private void replacePlaceHolder(final Path file) {
        AtomicBoolean replaceMode = new AtomicBoolean(false);
        final StringBuilder newContent = new StringBuilder();
        readLines(file, line -> {
            if (!replaceMode.get()) {
                newContent.append(line);
            }
            if (line != null && line.trim().startsWith("#START_FILL_WITH")) {
                final String key = line.trim().split("\\s")[1];
                System.out.println("Replacing content [" + key + "] at [" + file.getFileName().toString() + "]");
                replaceMode.set(true);
                String variable = variables.get(key);
                if (variable != null) {
                    newContent.append(variable);
                }
            } else if (line != null && line.trim().startsWith("#END_FILL_WITH")) {
                newContent.append(line);
                replaceMode.set(false);
            }
        });
        try {
            Files.write(file, newContent.toString().getBytes(UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readVariables(final Path file) {
        final StringBuilder content = new StringBuilder();
        final AtomicReference<String> reusableName = new AtomicReference<>(null);
        readLines(file, line -> {
            if (line != null && line.trim().startsWith("#START_TEMPLATE")) {
                content.setLength(0);
                reusableName.set(line.trim().split("\\s")[1]);
            } else if (line != null && line.trim().startsWith("#END_TEMPLATE")) {
                if (reusableName.get() != null) {
                    System.out.println("Found placeholder [" + reusableName.get() + "] at [" + file.getFileName().toString() + "]");
                    variables.put(reusableName.get(), content.toString());
                }
                content.setLength(0);
                reusableName.set(null);
            } else if (reusableName.get() != null) {
                content.append(line);
            }
        });
    }

    private void readLines(final Path file, final Consumer<String> consumer) {
        try {
            Scanner scanner = new Scanner(file, UTF_8.name()).useDelimiter("(?<=\n)|(?!\n)(?<=\r)");
            while (scanner.hasNext()) {
                final String line = scanner.next();
                if (line != null) {
                    consumer.accept(line);
                }
            }
        } catch (IOException e) {
            System.err.println(file + ": " + e.getMessage());
        }
    }
}
