package me.geek.tom.testgame.common.networking;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

@SuppressWarnings("unused")
public class EmptyPacket implements Packet {

    @Override
    public void read(NetInput in) throws IOException { }

    @Override
    public void write(NetOutput out) throws IOException { }

    @Override
    public boolean isPriority() {
        return true;
    }
}
