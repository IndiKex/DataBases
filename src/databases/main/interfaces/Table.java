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
	
}
