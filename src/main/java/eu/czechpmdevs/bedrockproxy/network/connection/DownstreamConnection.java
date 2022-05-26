package eu.czechpmdevs.bedrockproxy.network.connection;

import java.net.InetSocketAddress;

import eu.czechpmdevs.bedrockproxy.utils.Logger;
import io.netty.channel.socket.DatagramPacket;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.novatech.jbprotocol.util.ServerConnectInfo;

public class DownstreamConnection extends Thread {

    @Setter
    private boolean isRunning = true;

    @Getter
    private final String ip;
    @Getter
    private final int port;

    @Getter(AccessLevel.PRIVATE)
    private UDPServerSocket socket;

    public DownstreamConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    
    public ServerConnectInfo toServerInfo() {
    	return new ServerConnectInfo(new InetSocketAddress(ip, port));
    }

    @Override
    public void run() {
        Thread.currentThread().setName("FakeServer thread");

        Logger logger = new Logger();
        this.socket = new UDPServerSocket(logger, ip, port);

        while (this.isRunning) {
            this.receivePacket();
        }
    }

    public void receivePacket() {
        DatagramPacket packet = this.getSocket().readPacket();
        if(packet == null) {
            return;
        }

        String source = packet.sender().getHostName();
    }
}
