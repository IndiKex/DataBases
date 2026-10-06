package databases.main.interfaces;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public interface Table {
	public String getName();
	public int getSize();
	public void loadCSV(File sourceFile, String[] dataTypes);
	public void loadCSV(File sourceFile, String[] dataTypes, String keyField);
	public int getFieldSize();
	public List<String> getFields();
	public List<String> searchKeysByField(String field, String data);
	public String searchFieldByKey(String field, String keyData);
	public HashMap<String, String> searchRowByKey(String keyData);
	public List<String> getRange(String field);
	public void addRecord(List<String> data);
}
