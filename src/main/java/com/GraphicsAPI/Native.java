package com.GraphicsAPI;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Platform;

@Platform(include="Native.hpp")
@Namespace("NativeGraphics")
public class Native {  // NOT FUNCTIONAL YET

    public static class nativeTest extends Pointer {
        static { Loader.load(); } 
        protected nativeTest() { allocate(); }
        public native void allocate();

        public @Name("display") void display() {
            System.out.println("display WORKS!");
        }

        public @Name("keyUp") void keyUp(char key, int a, int b) {
            System.out.println("keyUp WORKS!");
        }
    
        public @Name("keyDown") void keyDown(char key, int a, int b) {
            System.out.println("keyDown WORKS!");
        }
    }

    public native @Name("init") 
    void callback(
        CharPointer title, 
        int amountOfProgramArgs, 
        PointerPointer<CharPointer> args, 
        int windowWidth, 
        int windowHeight, 
        @ByVal nativeTest KeyUPcallback, 
        @ByVal nativeTest KeyDowncallback, 
        @ByVal nativeTest displayFunc
    );

    public Native(String[] args) {nativeTest n = new nativeTest();
        this.callback(new CharPointer("App"), args.length, new PointerPointer<>(args), 600, 600, n, n, n);
    }

    public static void main(String[] args) {
        new Native(args);        
    }

}
