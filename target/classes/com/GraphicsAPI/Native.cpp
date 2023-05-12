#include "Native.hpp"

#define res        1                        //0=160x120 1=360x240 4=640x480
#define SW         160*res                  //screen width
#define SH         120*res                  //screen height
#define SW2        (SW/2)                   //half of screen width
#define SH2        (SH/2)                   //half of screen height
#define pixelScale 4/res                    //OpenGL pixel scale
#define GLSW       (SW*pixelScale)          //OpenGL window width
#define GLSH       (SH*pixelScale)          //OpenGL window height

void init(){}

void KeysUp(unsigned char a, int b, int c) {}

void KeysDown(unsigned char a, int b, int c) {}

void pixel(int x,int y, int c) {
    int rgb[3] = { 0 };
    if(c==0){ rgb[0]=255; rgb[1]=255; rgb[2]=  0;} //Yellow	
    if(c==1){ rgb[0]=160; rgb[1]=160; rgb[2]=  0;} //Yellow darker	
    if(c==2){ rgb[0]=  0; rgb[1]=255; rgb[2]=  0;} //Green	
    if(c==3){ rgb[0]=  0; rgb[1]=160; rgb[2]=  0;} //Green darker	
    if(c==4){ rgb[0]=  0; rgb[1]=255; rgb[2]=255;} //Cyan	
    if(c==5){ rgb[0]=  0; rgb[1]=160; rgb[2]=160;} //Cyan darker
    if(c==6){ rgb[0]=160; rgb[1]=100; rgb[2]=  0;} //brown	
    if(c==7){ rgb[0]=110; rgb[1]= 50; rgb[2]=  0;} //brown darker
    if(c==8){ rgb[0]=  0; rgb[1]= 60; rgb[2]=130;} //background 
    glColor3ub(rgb[0],rgb[1],rgb[2]); 
    glBegin(GL_POINTS);
    glVertex2i(x*pixelScale+2,y*pixelScale+2);
    glEnd();
}

void clearBackground() {
    int x,y;
    for(y=0;y<SH;y++)
    { 
        for(x=0;x<SW;x++){ pixel(x,y,8); } //clear background color
    }
}

int ltick;
void draw3D() {
    int x,y,c=0;
    for(y=0;y<SH2;y++) {
        for(x=0;x<SW2;x++) {
            pixel(x,y,c); 
            c+=1; if(c>8){ c=0;}
        }
    }
    //frame rate
    ltick+=1; if(ltick>20){ ltick=0;} pixel(SW2,SH2+ltick,0);
}

void display() {
    clearBackground();
    draw3D();
    glutSwapBuffers();
    glutPostRedisplay();
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowPosition(GLSW/2,GLSH/2);
    glutInitWindowSize(GLSW,GLSH);
    glutCreateWindow("Test App"); 
    glPointSize(pixelScale);                        //pixel size
    gluOrtho2D(0,GLSW,0,GLSH);                      //origin bottom left
    init();
    glutDisplayFunc(display);
    glutKeyboardFunc(KeysDown);
    glutKeyboardUpFunc(KeysUp);
    glutMainLoop();
    return 0;
}
