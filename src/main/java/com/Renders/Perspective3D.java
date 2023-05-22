package com.Renders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.demos.Util;
import com.demos.Util.Matrix4;
import com.demos.Util.returnClip;
import com.primitives.ITransform;
import com.primitives.Mesh;
import com.primitives.Transform3D;
import com.primitives.Vector3;
import com.primitives.World;
import com.primitives.Shapes.Triangle;

public class Perspective3D extends Camera {
    private Comparator<Triangle> sort = (Triangle t1, Triangle t2) -> {
		double z1 = (t1.p[0].z + t1.p[1].z + t1.p[2].z) / 3.0;
		double z2 = (t2.p[0].z + t2.p[1].z + t2.p[2].z) / 3.0;
		return Double.compare(z1, z2);
	};
	private int width = 100, height = 100;
	private Matrix4 projection;
	private Transform3D transform;

    public Perspective3D(float x, float y, float z, int width, int height) {
		Vector3 pos = new Vector3(x, y, z);
		init(70f, 0.1f, 1000f, 0f, 0f, width, height, pos);
    }

	private void init(float fov, float near, float far, float yaw, float rotation, int width, int height, Vector3 pos) {
		this.transform = new Transform3D(
			pos, 
			new Vector3(0, 0, 1), 
			1000f, 
			0.1f, 
			75f, 
			0f, 
			0f
		);
		this.width = width;
		this.height = height;
	}

	public Perspective3D(float fov, float near, float far, float yaw, float rot, int width, int height, Vector3 pos) {
		init(fov, near, far, yaw, rot, width, height, pos);
	}

	public float getRotation() {
		return transform.getRotation();
	}

	public float getNearPlane() {
		return transform.getNear();
	}

	public float getFarPlane() {
		return transform.getFar();
	}

	public void setNearPlane(float near) {
		this.transform.setNear(near);
	}

	public void setFarPlane(float far) {
		this.transform.setFar(far);
	}

	public void setYaw(float yaw) {
		this.transform.setYaw(yaw);
	}

	public float getYaw() {
		return transform.getYaw();
	}

	public void setRotation(float rotation) {
		this.transform.setRotation(rotation);
	}

	public void setPosition(float x, float y, float z) {
		this.transform.getPosition().x = x;
		this.transform.getPosition().y = y;
		this.transform.getPosition().z = z;
	}

	public float getFOV() {
		return transform.getFOV();
	}

	public Matrix4 getProjectionMatrix() {
		return projection;
	}

	public Vector3 getPosition() {
		return transform.getPosition();
	}

	public float getX() {
		return transform.getPosition().x;
	}

	public float getY() {
		return transform.getPosition().y;
	}

    public float getZ() {
        return transform.getPosition().z;
    }

	public void setSize(int size) {
        this.width = size;
		this.height = size;
    }

	public void setSize(int w, int h) {
        this.width = w;
		this.height = h;
    }

	/** Renderers all Objects in the World */
	public void render(Graphics g, World world) {
		projection = Util.MatrixMakeProjection(transform.getFOV(), (float)height/(float)width, transform.getNear(), transform.getFar());
		for(Mesh m : world.getAllMeshes()) {
			this.renderObject(m, g);
		}
	}

	public void invertPos(Triangle triProjected) {
		triProjected.p[0].x *= -1.0f;
		triProjected.p[1].x *= -1.0f;
		triProjected.p[2].x *= -1.0f;
		triProjected.p[0].y *= -1.0f;
		triProjected.p[1].y *= -1.0f;
		triProjected.p[2].y *= -1.0f;
	}

	public void scaleIntoView(Triangle triProjected) {
		triProjected.p[0].x *= (float)(width/2);
		triProjected.p[0].y *= (float)(height/2);
		triProjected.p[1].x *= (float)(width/2);
		triProjected.p[1].y *= (float)(height/2);
		triProjected.p[2].x *= (float)(width/2);
		triProjected.p[2].y *= (float)(height/2);
	}

	public void offsetVerts(Triangle triProjected) {
		Vector3 vOffsetView = new Vector3(1, 1, 0);
		triProjected.p[0] = Util.VectorAdd(triProjected.p[0], vOffsetView);
		triProjected.p[1] = Util.VectorAdd(triProjected.p[1], vOffsetView);
		triProjected.p[2] = Util.VectorAdd(triProjected.p[2], vOffsetView);
	}

	public void divByW(Triangle triProjected) {
		triProjected.t[0].x /= triProjected.p[0].w;
		triProjected.t[1].x /= triProjected.p[1].w;
		triProjected.t[2].x /= triProjected.p[2].w;

		triProjected.t[0].y /= triProjected.p[0].w;
		triProjected.t[1].y /= triProjected.p[1].w;
		triProjected.t[2].y /= triProjected.p[2].w;

		triProjected.t[0].w = 1.0f / triProjected.p[0].w;
		triProjected.t[1].w = 1.0f / triProjected.p[1].w;
		triProjected.t[2].w = 1.0f / triProjected.p[2].w;

		// Scale into view, we moved the normalising into cartesian space
		// out of the matrix.yector function from the previous video, so
		// do this manually
		triProjected.p[0] = Util.VectorDiv(triProjected.p[0], triProjected.p[0].w);
		triProjected.p[1] = Util.VectorDiv(triProjected.p[1], triProjected.p[1].w);
		triProjected.p[2] = Util.VectorDiv(triProjected.p[2], triProjected.p[2].w);
	}

    /**
     * Renders one specified object in the world
     * @param g
     * @param rot z and x rotation in radians
     * @param yaw y rotation in radians
     * @param projection
     * @param camera
     */
    public void renderObject(Mesh mesh, Graphics g) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		projection = Util.MatrixMakeProjection(transform.getFOV(), (float)width/(float)height, transform.getNear(), transform.getFar());
		Graphics2D g2 = img.createGraphics();
	
		Matrix4 matWorld;
		{
			Matrix4 matRotZ, matRotX;
			matRotZ = Util.MatrixMakeRotationZ(transform.getRotation() * 0.5f);
			matRotX = Util.MatrixMakeRotationX(transform.getRotation());

			Matrix4 matTrans;
			matTrans = Util.MatrixMakeTranslation(0.0f, 0.0f, 5.0f);
			
			// matWorld = MatrixMakeIdentity();
			matWorld = Util.MatrixMultiplyMatrix(matRotZ, matRotX);
			matWorld = Util.MatrixMultiplyMatrix(matWorld, matTrans);
		}

		Vector3 vUp = new Vector3(0, 1, 0);
		Vector3 vTarget = new Vector3(0, 0, 1);
		Matrix4 matCameraRot = Util.MatrixMakeRotationY(transform.getYaw());
		transform.setLookDir(Util.MatrixMultiplyVector(matCameraRot, vTarget));
		vTarget = Util.VectorAdd(transform.getPosition(), transform.getLookDir());

		Matrix4 matCamera = Util.MatrixPointAt(transform.getPosition(), vTarget, vUp);

		// Make matrix view from camera
		Matrix4 matView = Util.MatrixQuickInverse(matCamera);

		// Store Triangles for Rastering Later
		ArrayList<Triangle> trianglesToRaster = new ArrayList<>();

		// Draw Triangles
		for (Triangle tri : mesh.getTris()) {
			Triangle triProjected = new Triangle(), triTransformed = new Triangle(), triViewed = new Triangle();

			triTransformed.p[0] = Util.MatrixMultiplyVector(matWorld, tri.p[0]);
			triTransformed.p[1] = Util.MatrixMultiplyVector(matWorld, tri.p[1]);
			triTransformed.p[2] = Util.MatrixMultiplyVector(matWorld, tri.p[2]);
			triTransformed.t = tri.clone().t;

			// Calculate Triangle Normal
			Vector3 normal;

			{
				Vector3 line1, line2;
				// Get lines either side of triangle
				line1 = Util.VectorSub(triTransformed.p[1], triTransformed.p[0]);
				line2 = Util.VectorSub(triTransformed.p[2], triTransformed.p[0]);

				// Take Cross Product of lines to get normal to triangle surface
				normal = Util.VectorCrossProduct(line1, line2);

				// You Normally need to Normalise a Normal!
				normal = Util.VectorNormalise(normal);
			}

			// Get Ray from Triangle to Camera
			Vector3 cameraRay = Util.VectorSub(triTransformed.p[0], transform.getPosition());

			// If ray is aligned with normal, then triangle is visible
			if(Util.VectorDotProduct(normal, cameraRay) < 0.0f) {

				// Illumination
				Vector3 lightDirection = new Vector3(0, 1, -1);
				lightDirection = Util.VectorNormalise(lightDirection);

				// How "aligned" are light direction and triangle surface normal?
				float dp = Math.max(0.1f, Util.VectorDotProduct(lightDirection, normal));
				// Choose Colors as Required
				triTransformed.col = new Color(dp, dp, dp);

				// Convert World Space --> View Space
				triViewed.p[0] = Util.MatrixMultiplyVector(matView, triTransformed.p[0]);
				triViewed.p[1] = Util.MatrixMultiplyVector(matView, triTransformed.p[1]);
				triViewed.p[2] = Util.MatrixMultiplyVector(matView, triTransformed.p[2]);
				triViewed.col = triTransformed.col;
				triViewed.t = triTransformed.t;

				// Clip Viewed Triangle against near plane, this could form two additional
				// triangles
				returnClip clipResult = Util.TriangleClipAgainstPlane(new Vector3(0.0f, 0.0f, 0.1f), new Vector3(0.0f, 0.0f, 1.0f), triViewed);
				int nClippedTriangles = clipResult.numTris;
				Triangle[] clipped = clipResult.tris;

				for (int n = 0; n < nClippedTriangles; n++) {

					// Project Triangles from 3D --> 2D
					triProjected.p[0] = Util.MatrixMultiplyVector(projection, clipped[n].p[0]);
					triProjected.p[1] = Util.MatrixMultiplyVector(projection, clipped[n].p[1]);
					triProjected.p[2] = Util.MatrixMultiplyVector(projection, clipped[n].p[2]);
					triProjected.col = clipped[n].col;
					triProjected.t = clipped[n].t;

					Thread t = new Thread(()->{
						divByW(triProjected);

						// X/Y are inverted so put them back
						invertPos(triProjected);

						// Offset verts into visible normalised space
						offsetVerts(triProjected);
						
						scaleIntoView(triProjected);
					});
					t.start();

					try {t.join();}
					catch(InterruptedException e)
					{e.printStackTrace();}

					// Store Triangles for sorting
					trianglesToRaster.add(triProjected);
				}
			}
		}

		trianglesToRaster.sort(sort.reversed());

		for (Triangle triToRaster: trianglesToRaster) {

			// Clip triangles against all four screen edges, this could yield
			// a bunch of triangles
			Triangle[] clipped;
			ArrayList<Triangle> listTriangles = new ArrayList<>();

			// Add initial triangle
			listTriangles.add(triToRaster);
			int nNewTriangles = 1;

			for (int p = 0; p < 4; p++) {

				int nTrisToAdd;
				while (nNewTriangles > 0) {

					// Take triangle from front of queue
					Triangle test = listTriangles.get(0);
					listTriangles.remove(0);
					nNewTriangles--;

					// Clip it against a plane. We only need to test each
					// subsequent plane, against subsequent new triangles
					// as all triangles after a plane clip are guaranteed
					// to lie on the inside of the plane. I like how this
					// comment is almost completely and utterly justified
					returnClip clip = null;

					switch (p) {
						case 0: clip = Util.TriangleClipAgainstPlane(new Vector3(0, 0, 0), new Vector3(0, 1, 0), test); break;
						case 1: clip = Util.TriangleClipAgainstPlane(new Vector3(0, height - 1, 0), new Vector3(0, -1, 0), test); break;
						case 2: clip = Util.TriangleClipAgainstPlane(new Vector3(0, 0, 0), new Vector3(1, 0, 0), test); break;
						case 3: clip = Util.TriangleClipAgainstPlane(new Vector3(width - 1, 0, 0), new Vector3(-1, 0, 0), test); break;
						default: break;
					}
					nTrisToAdd = clip.numTris;
					clipped = clip.tris;

					// Clipping may yield a variable number of triangles, so
					// add these new ones to the back of the queue for subsequent
					// clipping against next planes
					listTriangles.addAll(Arrays.asList(clipped).subList(0, nTrisToAdd));
				}
				nNewTriangles = listTriangles.size();
			}

			
			// Draw the transformed, viewed, clipped, projected, sorted, clipped triangles
			for (Triangle t : listTriangles) {
				Util.TexturedTriangle(
					new ArrayList<>(Arrays.asList((int)t.p[0].x, (int)t.p[1].x, (int)t.p[2].x)),
					new ArrayList<>(Arrays.asList((int)t.p[0].y, (int)t.p[1].y, (int)t.p[2].y)),
					new ArrayList<>(Arrays.asList(t.t[0].x, t.t[1].x, t.t[2].x)),
					new ArrayList<>(Arrays.asList(t.t[0].y, t.t[1].y, t.t[2].y)),
					new ArrayList<>(Arrays.asList(t.t[0].w, t.t[1].w, t.t[2].w)),
					g2, mesh.getTexture().getImage()
				);

				g2.setColor(Color.WHITE);
				g2.drawLine((int)t.p[0].x, (int)t.p[0].y, (int)t.p[1].x, (int)t.p[1].y);
				g2.drawLine((int)t.p[1].x, (int)t.p[1].y, (int)t.p[2].x, (int)t.p[2].y);
				g2.drawLine((int)t.p[2].x, (int)t.p[2].y, (int)t.p[0].x, (int)t.p[0].y);

				g.drawImage(img, 0, 0, null);
				g.drawImage(mesh.getTexture().getImage(), 0, 0, 16, 16,  null);
			}
		}
    }

    @Override
    public CameraType getType() {
        return CameraType.Perspective3D;
    }

	@Override
	public ITransform getTransform() {
		return transform;
	}
}
