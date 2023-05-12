package com.demos;

public class Native {

    public native void hello();
    
    static {
        System.loadLibrary("hello");
    }

    public static void main(String[] args) {
        Native n = new Native();
        n.hello();
    }

}
