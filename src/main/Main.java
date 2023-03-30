import Renders.Camera;
import Renders.Camera2D;
import helper.App;

public class Main {
    //
    public Main() {
        //
        Camera cam = new Camera2D(0, 0);
        new App("App", 800, 600, cam, null, null);
        //
    }
    //
    public static void main(String[] args) {
        //
        new Main();
        //
    }
}
