package databases.main.io.datafileio.interfaces;

import java.io.IOException;

import databases.main.data.DataType;

public interface DataTypeIOPackage {
	public DataTypeIO get(int idx);
	public void add(DataType dataType, String fieldDir) throws IOException;
	public void clear();
	public void close();
	public int size();
}
