package com.raycaster.utils;

public interface IType<T> {

	public T[] createArray(int size);
	
	public T get();
	public void set(T value);
}
