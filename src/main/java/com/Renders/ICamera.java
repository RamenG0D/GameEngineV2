package com.Renders;

import com.primitives.ITransform;

public interface ICamera {

    public static enum CameraType {
        Perspective2D,
        Perspective3D,
        OrthoGraphic2D, // NOT IMPLEMENTED
        OrthoGraphic3D // NOT IMPLEMENTED
    }

    public CameraType getType();

    public ITransform getTransform();

}
