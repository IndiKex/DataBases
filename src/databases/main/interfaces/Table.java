package databases.main.interfaces;

import java.io.File;
import java.util.List;

import databases.main.data.Data;

public interface Table {
	public String getName();
	public int getSize();
	public void loadCSV(File sourceFile, String[] dataTypes);
	public void loadCSV(File sourceFile, String[] dataTypes, String keyField);
	public int getFieldSize();
	public List<String> getFields();
	
	public Data getFieldFromKey(String field, Data key);
	public List<Data> getFieldFromFieldData(String resField, String searchField, Data searchData);
	public View getViewFromFieldData(String searchField, Data searchData);
}
