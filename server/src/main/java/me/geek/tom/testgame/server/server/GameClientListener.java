package me.geek.tom.testgame.server.server;

import com.github.steveice10.packetlib.event.session.*;
import com.github.steveice10.packetlib.packet.Packet;
import me.geek.tom.testgame.common.networking.ChunkData;
import me.geek.tom.testgame.common.networking.client.ClientLoginPacket;
import me.geek.tom.testgame.common.networking.client.ClientRequestChunkPacket;
import me.geek.tom.testgame.common.networking.server.ServerBeginGamePacket;
import me.geek.tom.testgame.common.networking.server.ServerChunkDataPacket;
import me.geek.tom.testgame.common.world.ChunkPos;
import me.geek.tom.testgame.server.TestGameServer;

import java.util.logging.Logger;

public class GameClientListener implements SessionListener {

    private static Logger LOGGER = Logger.getLogger("ClientListener");

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        Packet packet = event.getPacket();
        if (packet instanceof ClientLoginPacket) {
            LOGGER.info("Got a login packet for user: " + ((ClientLoginPacket)packet).getUsername());
            event.getSession().send(new ServerBeginGamePacket());
        }

        if (packet instanceof ClientRequestChunkPacket) {
            ChunkPos pos = ((ClientRequestChunkPacket)packet).getPos();

            LOGGER.info("Requested: " + pos);

            ChunkData data = new ChunkData(TestGameServer.INSTANCE.getChunkManager().getChunk(pos));
            ServerChunkDataPacket chunkDataPacket = new ServerChunkDataPacket(data);
            event.getSession().send(chunkDataPacket);
        }
    }

    @Override
    public void packetSending(PacketSendingEvent event) {

    }

    @Override
    public void packetSent(PacketSentEvent event) {

    }

    @Override
    public void connected(ConnectedEvent event) {

    }

    @Override
    public void disconnecting(DisconnectingEvent event) {

    }

    @Override
    public void disconnected(DisconnectedEvent event) {

    }
}
