package com.arcadia.editor.application;

import java.awt.image.BufferedImage;

public interface IRotatable {

    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    void rotateRight();

    void rotateLeft();

    void rotate180();
}
