package com.raycaster.Bitmap;

import com.raycaster.utils.Tuple;

public class VerticalFirstBitmapCursor<T> extends BitmapCursor<T> {

	public VerticalFirstBitmapCursor(IBitmap<T> bmp, int x, int y) {
		super(bmp, x, y);
	}

	public VerticalFirstBitmapCursor(IBitmap<T> bmp) {
		super(bmp);
	}

	@Override
	public void nextX() {
		m_posIndex += m_bitmap.getHeight();
	}

	@Override
	public void nextY() {
		++m_posIndex;
	}

	@Override
	public T next() {
		fwd();
		return get();
	}

	@Override
	public ICursor1D<T> copy() {
		return null;
	}

	@Override
	public void bck() {
		--m_posIndex;
	}

	@Override
	public T prev() {
		--m_posIndex;
		return get();
	}

	@Override
	public Tuple<Integer, Integer> getPositionXY() {
		return null;
	}
}
