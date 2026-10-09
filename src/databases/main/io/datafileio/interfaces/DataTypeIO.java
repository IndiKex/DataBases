package databases.main.io.datafileio.interfaces;

import java.io.IOException;

import databases.main.data.Data;

public interface DataTypeIO {
	public void write(Data data) throws IOException;
	public void writeAt(int idx, Data data) throws IOException;
	public Data read(int idx) throws IOException;
	public void close() throws IOException;
	public void clear();
}
