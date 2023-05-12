package com.primitives;

import com.primitives.Shapes.Cube;

public class BlockChunk {
    Cube[] blocks = new Cube[256];

    public void GenChunck() {
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < blocks.length; y++) {
                if(y < 4) blocks[x*blocks.length+y] = new Cube(); // default is null
            }
        }
    }

    public Cube[] getChunk() {
        return blocks;
    }
}
