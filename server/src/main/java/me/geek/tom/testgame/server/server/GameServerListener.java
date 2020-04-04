package me.geek.tom.testgame.server.server;

import com.github.steveice10.packetlib.event.server.*;
import me.geek.tom.testgame.common.networking.ProtocolDefinition;

import javax.crypto.SecretKey;
import java.util.logging.Logger;

public class GameServerListener implements ServerListener {

    private SecretKey key;
    private static Logger LOGGER = Logger.getLogger("ServerListener");

    public GameServerListener(SecretKey key) {
        this.key = key;
    }

    @Override
    public void serverBound(ServerBoundEvent event) {
        LOGGER.info("Server running on " + event.getServer().getHost() + ":" + event.getServer().getPort());
    }

    @Override
    public void serverClosing(ServerClosingEvent event) {
        LOGGER.info("Server shutting down...");
    }

    @Override
    public void serverClosed(ServerClosedEvent event) {
        LOGGER.info("Done. Goodbye!");
    }

    @Override
    public void sessionAdded(SessionAddedEvent event) {
        ProtocolDefinition definition = (ProtocolDefinition) event.getSession().getPacketProtocol();
        definition.setKey(key);
        event.getSession().addListener(new GameClientListener());
        LOGGER.info("New connection from " + event.getSession().getHost() + ":" + event.getSession().getPort());
    }

    @Override
    public void sessionRemoved(SessionRemovedEvent event) {
        LOGGER.info("Client from " + event.getSession().getHost() + ":" + event.getSession().getPort() + " left!");
    }
}
