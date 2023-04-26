package com.utils;

import java.io.IOException;

public interface Saveable<T> {

    public boolean saveToFile(GameFile gameFile) throws IOException;

	public boolean loadFromFile(GameFile gameFile) throws IOException;

	public String getTag();

}
