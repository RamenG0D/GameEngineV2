package com.primitives;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import com.primitives.Shapes.Triangle;

public class Mesh {
    private ArrayList<Triangle> tris;
    private Texture texture;

    public Mesh() {}

    public Mesh(ArrayList<Triangle> tris) {
        this.tris = tris;
        this.texture = new Texture("noTexture.png");
    }

    public Mesh(ArrayList<Triangle> tris, Texture t) {
        this.tris = tris;
        this.texture = t;
    }

    public Texture getTexture() {
        return texture;
    }

    public Mesh(String path) {
        try {
            Scanner in = new Scanner(new File(path));
            ArrayList<Vector3> vecList = new ArrayList<>();
            ArrayList<Triangle> triList = new ArrayList<>();
            while (in.hasNextLine()) {
                String[] line = in.nextLine().split(" ");
                switch (line[0]) {
                    case "v":
                        float x = Float.parseFloat(line[1]);
                        float y = Float.parseFloat(line[2]);
                        float z = Float.parseFloat(line[3]);
                        vecList.add(new Vector3(x, y, z));
                        break;

                    case "f":
                        int p0 = Integer.parseInt(line[1]) - 1;
                        int p1 = Integer.parseInt(line[2]) - 1;
                        int p2 = Integer.parseInt(line[3]) - 1;
                        Triangle tempTri = new Triangle(vecList.get(p0), vecList.get(p1), vecList.get(p2));
                        triList.add(tempTri);
                        break;
                }
            }
            in.close();
            this.tris = triList;
            this.texture = new Texture("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Triangle> getTris() {
        return tris;
    }

    public void setTris(ArrayList<Triangle> tris) {
        this.tris = tris;
    }
}
