package uk.co.idv.otp.app.spring;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

@Builder
@Slf4j
public class PortReady implements Callable<Boolean> {

    @Builder.Default
    private final String hostname = "localhost";

    @Builder.Default
    private final int port = 8080;

    public static PortReady local(int port) {
        return PortReady.builder()
                .port(port)
                .build();
    }

    @Override
    public Boolean call() {
        try {
            SocketAddress address = new InetSocketAddress(hostname, port);
            SocketChannel socketChannel = SocketChannel.open();
            try (socketChannel) {
                socketChannel.configureBlocking(true);
                socketChannel.connect(address);
                log.info("successfully connected to {} on port {}", hostname, port);
                return true;
            }
        } catch (IOException e) {
            log.warn("failed to connect to {} on port {}", hostname, port);
            return false;
        }
    }

}
