package com.raycaster.IO;

public interface IResource<T> {
	/**
	 * Load resource from a file
	 * 
	 * @param filename
	 * @return
	 */
	public T loadFromFile(String filename);
}
