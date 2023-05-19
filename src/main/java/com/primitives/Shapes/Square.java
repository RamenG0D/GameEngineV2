package com.primitives.Shapes;

import java.util.ArrayList;
import java.util.Arrays;

import com.primitives.Mesh;

public class Square extends Mesh {
    
    public Square() {
        super(new ArrayList<Triangle>(
            Arrays.asList(
                new Triangle(new float[]{1, 0, 1, 1, 1, 1, 0, 1, 1}, new float[]{0, 1, 0, 0, 1, 0}),
                new Triangle(new float[]{1, 0, 1, 0, 1, 1, 0, 0, 1}, new float[]{0, 1, 1, 0, 1, 1})
            )
        ));
    }

}
