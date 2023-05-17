/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.func;

import com.Math3D.Vector3;


/**
 * @author Philip DeCamp
 */
public interface Func1v3 {
    public void apply( float t, Vector3 out );
}
