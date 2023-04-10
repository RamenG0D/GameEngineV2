import Renders.Camera2D;
import helper.App;

public class Main {
    //
    public Main() {
        //
        new App("App", 800, 600, new Camera2D(0,0,800,600));
        //
    }
    //
    public static void main(String[] args) {
        //
        new Main();
        //
    }
}
