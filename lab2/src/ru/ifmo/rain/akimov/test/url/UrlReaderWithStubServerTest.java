package ru.ifmo.rain.akimov.test.url;

import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;

import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import ru.ifmo.rain.akimov.main.http.UrlReader;

import java.io.IOException;
import java.util.function.Consumer;

public class UrlReaderWithStubServerTest {
    private static final int PORT = 34381;
    private final UrlReader urlReader = new UrlReader();

    private void withStubServer(final int port, final Consumer<StubServer> callback) {
        StubServer server = null;
        try {
            server = new StubServer(port).run();
            callback.accept(server);
        } finally {
            if (server != null) {
                server.stop();
            }
        }
    }

    @Test
    public void correctServer() {
        withStubServer(PORT, server -> {
            whenHttp(server)
                    .match(Condition.method(Method.GET), Condition.startsWithUri("/ping"))
                    .then(stringContent("pong"));
            try {
                final String result = urlReader.getAsText("http://localhost:" + PORT + "/ping");
                Assert.assertEquals("pong", result);
            } catch (final Exception ignored) {
                Assert.fail();
            }
        });
    }

    @Test
    public void notFoundError() {
        withStubServer(PORT, server -> {
            whenHttp(server)
                    .match(Condition.method(Method.GET), Condition.startsWithUri("/ping"))
                    .then(status(HttpStatus.NOT_FOUND_404));
            Assert.assertThrows(IOException.class, () -> urlReader.getAsText("http://localhost:" + PORT + "/ping"));
        });
    }
}
