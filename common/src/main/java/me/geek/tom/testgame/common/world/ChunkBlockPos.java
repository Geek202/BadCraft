package me.geek.tom.testgame.common.world;

public class ChunkBlockPos {
    public int x, y, z;

    public ChunkBlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ChunkBlockPos(ChunkBlockPos other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    @Override
    public String toString() {
        return x + " / " + y + " / " + z;
    }

    public ChunkBlockPos inDirection(Direction direction) {
        ChunkBlockPos n = new ChunkBlockPos(this);
        switch (direction) {
            case POSX:
                n.x++;
                break;
            case UP:
                n.y++;
                break;
            case POSZ:
                n.z++;
                break;
            case NEGX:
                n.x--;
                break;
            case DOWN:
                n.y--;
                break;
            case NEGZ:
                n.z--;
                break;
        }
        return n;
    }
}
