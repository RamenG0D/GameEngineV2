/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.geom;

/**
 * @author Philip DeCamp  
 */
public interface Volume {
    public boolean contains( float x, float y, float z );
    public Aabb bounds();
}
