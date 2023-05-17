package com.primitives;

import com.primitives.Shapes.Cube;

public class BlockChunk {
    Cube[] blocks = new Cube[256];

    public void GenChunck() {
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                if(y < 4) blocks[x+y] = new Cube(x, y, (x+y)); // default is null
            }
        }
    }

    public Cube[] getChunk() {
        return blocks;
    }
}
