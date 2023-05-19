package com.primitives;

import java.util.ArrayList;

public class World {
    private ArrayList<Mesh> objects = new ArrayList<>();
    
    public World() {}

	public Mesh getMesh(int i) {
		return objects.get(i);
	}

	public void setMesh(int i, Mesh m) {
		objects.set(i, m);
	}

	public void removeMesh(int i) {
		objects.remove(i);
	}

	public void addMesh(Mesh m) {
		objects.add(m);
	}

	public ArrayList<Mesh> getAllMeshes() {
		return objects;
	}

}
