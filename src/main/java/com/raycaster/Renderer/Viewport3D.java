package com.raycaster.Renderer;

import com.raycaster.Bitmap.FastIntBitmap;
import com.raycaster.Entities.IEntity;
import com.raycaster.utils.IWorld;

public class Viewport3D extends Screen {

	public Viewport3D (IWorld p_world, IEntity camEntity, int width, int height){ 
		super(width, height);
		
		renderer = new EpicRayRenderer(p_world, camEntity, m_width, m_height);
	}
	
	@Override
	public final void present() {
		renderer.render();
		final FastIntBitmap result = (FastIntBitmap) renderer.getRenderResult();
		
		int i2 = 0;
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < m_length; ++i) {
				putPixel(i2, result.m_pixels[i]);
				
				i2 += m_width;
				++y;
				
				if (y == m_height) {
					i2 = ++x;
					y = 0;
				}
		}
	}

}
