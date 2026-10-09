package databases.main.io.datafileio.dataTypeIOs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import databases.main.data.DataType;
import databases.main.io.datafileio.interfaces.DataTypeIO;
import databases.main.io.datafileio.interfaces.DataTypeIOPackage;

public class NaiveDataTypeIOPackage implements DataTypeIOPackage {
	private ArrayList<DataTypeIO> ioList = new ArrayList<>();
	
	public NaiveDataTypeIOPackage(List<DataType> typeList, List<String> fieldDirs) throws IOException {
		ioList.ensureCapacity(fieldDirs.size());
		for (int i = 0; i < typeList.size(); i++) {
			ioList.add(getIOFromType(typeList.get(i), fieldDirs.get(i)));
		}
	}
	
	private DataTypeIO getIOFromType(DataType dataType, String fieldDir) throws IOException {
		switch (dataType) {
		case DataType.INTEGER:
			return new NaiveIntegerIO(fieldDir);
		case DataType.FLOAT:
			return new NaiveFloatIO(fieldDir);
		case DataType.STRING:
			return new NaiveStringIO(fieldDir);
		}
		return null;
	}
	
	public DataTypeIO get(int idx) {
		return ioList.get(idx);
	}
	
	public void add(DataType dataType, String fieldDir) throws IOException {
		ioList.add(getIOFromType(dataType, fieldDir));
	}
	
	public void clear() {
		for (DataTypeIO io : ioList)
			io.clear();
	}
	
	public void close() {
		try {
			for (DataTypeIO io : ioList)
				io.close();
		} catch (IOException ignored) {}
	}
	
	public int size() {
		return ioList.size();
	}
}
