package com.Resources;

import java.io.IOException;

public interface SaveData {

    public String readString() throws IOException;

	public boolean writeString(String s) throws IOException;

	public int readInt() throws IOException;

	public boolean writeInt(int i) throws IOException;

	public float readFloat() throws IOException;

	public boolean writeFloat(float f) throws IOException;

	public char readChar() throws IOException;

	public boolean writeChar(char c) throws IOException;

	public boolean readBoolean() throws IOException;

	public boolean writeBoolean(boolean b) throws IOException;

}
