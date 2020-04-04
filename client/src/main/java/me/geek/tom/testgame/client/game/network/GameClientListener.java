package me.geek.tom.testgame.client.game.network;

import com.github.steveice10.packetlib.event.session.*;
import me.geek.tom.testgame.client.game.Game;
import me.geek.tom.testgame.common.networking.client.ClientLoginPacket;
import me.geek.tom.testgame.common.networking.server.ServerBeginGamePacket;
import me.geek.tom.testgame.common.networking.server.ServerChunkDataPacket;

import java.util.Random;

public class GameClientListener implements SessionListener {
    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if (event.getPacket() instanceof ServerBeginGamePacket)
            System.out.println("Server OK with join request!");

        if (event.getPacket() instanceof ServerChunkDataPacket) {
            ServerChunkDataPacket packet = event.getPacket();
            Game.INSTANCE.getChunkManager().onChunkRecieved(packet.getChunkData());
        }
    }

    @Override
    public void packetSending(PacketSendingEvent event) { }

    @Override
    public void packetSent(PacketSentEvent event) { }

    @Override
    public void connected(ConnectedEvent event) {
        System.out.println("Connected to server at " + event.getSession().getHost() + ":" + event.getSession().getPort());
        event.getSession().send(new ClientLoginPacket("Dev"+new Random().nextInt()));
    }

    @Override
    public void disconnecting(DisconnectingEvent event) { }

    @Override
    public void disconnected(DisconnectedEvent event) {
        System.out.println("Disconnected!");
    }
}
