#define NDEBUG 0
#include "glut.h"

#define res        1                        //0=160x120 1=360x240 4=640x480
#define SW         160*res                  //screen width
#define SH         120*res                  //screen height
#define SW2        (SW/2)                   //half of screen width
#define SH2        (SH/2)                   //half of screen height
#define pixelScale 4/res                    //OpenGL pixel scale
#define GLSW       (SW*pixelScale)          //OpenGL window width
#define GLSH       (SH*pixelScale)          //OpenGL window height

namespace NativeGraphics {

	class nativeRenderer {
        public:
		    void pixel(int x, int y, int c) {
                int rgb[3] = { 0 };
                if (c == 0) { rgb[0] = 255; rgb[1] = 255; rgb[2] = 0; } //Yellow	
                if (c == 1) { rgb[0] = 160; rgb[1] = 160; rgb[2] = 0; } //Yellow darker	
                if (c == 2) { rgb[0] = 0; rgb[1] = 255; rgb[2] = 0; } //Green	
                if (c == 3) { rgb[0] = 0; rgb[1] = 160; rgb[2] = 0; } //Green darker	
                if (c == 4) { rgb[0] = 0; rgb[1] = 255; rgb[2] = 255; } //Cyan	
                if (c == 5) { rgb[0] = 0; rgb[1] = 160; rgb[2] = 160; } //Cyan darker
                if (c == 6) { rgb[0] = 160; rgb[1] = 100; rgb[2] = 0; } //brown	
                if (c == 7) { rgb[0] = 110; rgb[1] = 50; rgb[2] = 0; } //brown darker
                if (c == 8) { rgb[0] = 0; rgb[1] = 60; rgb[2] = 130; } //background 
                glColor3ub(rgb[0], rgb[1], rgb[2]);
                glBegin(GL_POINTS);
                glVertex2i(x * pixelScale + 2, y * pixelScale + 2);
                glEnd();
            }

            void init(
                char* title, 
                int amountOfProgramArgs, 
                char** args, 
                int windowWidth, 
                int windowHeight, 
                void (KeyUPcallback)(unsigned char, int, int),
                void (KeyDowncallback)(unsigned char, int, int),
                void (displayFunc)()
            ) {
                glutInit(&amountOfProgramArgs, args);
                glutCreateWindow(title);
                glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
                glutInitWindowPosition(GLSW / 2, GLSH / 2);
                glutInitWindowSize(windowWidth, windowHeight);
                glutKeyboardFunc(KeyDowncallback);
                glutKeyboardUpFunc(KeyUPcallback);
                glPointSize(pixelScale);
                gluOrtho2D(0, GLSW, 0, GLSH);
                glutDisplayFunc(displayFunc);
                glutMainLoop();
            }
	};

}