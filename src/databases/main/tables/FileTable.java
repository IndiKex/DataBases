package databases.main.tables;

import java.io.File;
import java.util.List;

import databases.main.data.Data;
import databases.main.interfaces.Table;
import databases.main.interfaces.View;

public class FileTable implements Table {
	private String name;

	public String getName() {
		return name;
	}
	
	public int getSize() {
		
	}
	
	public void loadCSV(File sourceFile, String[] dataTypes) {
		
	}
	
	public void loadCSV(File sourceFile, String[] dataTypes, String keyField) {
		
	}
	
	public int getFieldSize() {
		
	}
	
	public List<String> getFields() {
		
	}
	
	
	public Data getFieldFromKey(String field, Data key) {
		
	}
	
	public List<Data> getFieldFromFieldData(String resField, String searchField, Data searchData) {
		
	}
	
	public View getViewFromFieldData(String searchField, Data searchData) {
		
	}
	
}
