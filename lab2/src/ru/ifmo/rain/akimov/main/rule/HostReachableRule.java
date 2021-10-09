package ru.ifmo.rain.akimov.main.rule;

import org.junit.Assume;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

public class HostReachableRule implements TestRule {
    private final static int TIMEOUT = 1_000;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface HostReachable {
        String value();
    }

    private static class SkipStatement extends Statement {
        private final String host;

        public SkipStatement(final String host) {
            this.host = host;
        }

        @Override
        public void evaluate() throws Throwable {
            Assume.assumeTrue("Skipped, because of unreachable host " + host, false);
        }
    }

    @Override
    public Statement apply(final Statement statement, final Description description) {
        final HostReachable hostReachable = description.getAnnotation(HostReachable.class);
        if (hostReachable == null) {
            return new SkipStatement("<no host>");
        } else if (!checkHost(hostReachable.value())) {
            return new SkipStatement(hostReachable.value());
        } else {
            return statement;
        }
    }

    private static boolean checkHost(final String host) {
        return nativePing(host);
    }

    private static boolean nativePing(final String host) {
        try {
            final Process pingProcess = new ProcessBuilder("ping", "-n", "1", host).start();
            if (!pingProcess.waitFor(TIMEOUT, TimeUnit.MILLISECONDS)) {
                return false;
            }
            return pingProcess.exitValue() == 0;
        } catch (final IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
