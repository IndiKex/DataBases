package databases.main.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.List;

import databases.main.data.Data;
import databases.main.data.DataType;

public interface Table {
	public String getName();
	public int getSize();
	public void loadCSV(File sourceFile, List<DataType> dataTypes_) throws IOException;
	public void loadCSV(File sourceFile, List<DataType> dataTypes_, String keyField) throws IOException;
	public int getFieldSize();
	public List<String> getFields();
	
	public void addField(String fieldName, DataType type) throws IOException;
	
	public Data getFieldFromKey(String field, Data key) throws IOException;
	public List<Data> getRecordFromKey(Data key) throws IOException;
	public List<Data> getFieldFromFieldData(String resField, String searchField, Data searchData) throws IOException;
	public View getViewFromFieldData(String searchField, Data searchData) throws IOException;
	
	public void addRecord(List<Data> recordData) throws IOException;
	public void writeRecord(Data key, List<Data> recordData) throws IOException;
	public void writeData(Data key, String fieldName, Data data) throws IOException;
	
	public void close() throws IOException;
}
