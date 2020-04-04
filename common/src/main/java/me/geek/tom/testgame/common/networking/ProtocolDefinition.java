package me.geek.tom.testgame.common.networking;

import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.crypt.AESEncryption;
import com.github.steveice10.packetlib.crypt.PacketEncryption;
import com.github.steveice10.packetlib.packet.DefaultPacketHeader;
import com.github.steveice10.packetlib.packet.PacketHeader;
import com.github.steveice10.packetlib.packet.PacketProtocol;
import me.geek.tom.testgame.common.networking.client.ClientLoginPacket;
import me.geek.tom.testgame.common.networking.client.ClientRequestChunkPacket;
import me.geek.tom.testgame.common.networking.server.ServerChunkDataPacket;
import me.geek.tom.testgame.common.networking.server.ServerBeginGamePacket;

import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;

public class ProtocolDefinition extends PacketProtocol {

    private PacketHeader header = new DefaultPacketHeader();
    private AESEncryption encrypt;

    public ProtocolDefinition() {
    }

    public ProtocolDefinition(SecretKey key) {
        setKey(key);
    }

    private void registerPackets() {
        this.register(0x00, ClientLoginPacket.class);
        this.register(0x01, ServerBeginGamePacket.class);
        this.register(0x02, ClientRequestChunkPacket.class);
        this.register(0x03, ServerChunkDataPacket.class);
    }

    public void setKey(SecretKey key) {
        this.registerPackets();

        try {
            this.encrypt = new AESEncryption(key);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSRVRecordPrefix() {
        return "_badcraft";
    }

    @Override
    public PacketHeader getPacketHeader() {
        return this.header;
    }

    @Override
    public PacketEncryption getEncryption() {
        return encrypt;
    }

    @Override
    public void newClientSession(Client client, Session session) { }

    @Override
    public void newServerSession(Server server, Session session) { }
}
