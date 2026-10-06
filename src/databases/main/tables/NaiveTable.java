package databases.main.tables;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import databases.main.interfaces.DataBase;
import databases.main.interfaces.Table;
import databases.main.parsers.CSVParser;
import databases.main.parsers.Container;

public class NaiveTable implements Table {
	private String name;
	private HashMap<String, List<String>> tableData = new HashMap<>();
	public ArrayList<String> fieldNames = new ArrayList<>();
	String DEFAULT_KEY_NAME = "DefaultKeyID"; 
	String key = "default";
	
	DataBase parentDB;
	
	public NaiveTable(String name, DataBase parentDB) {
		this.name = name;
		this.parentDB = parentDB;
		this.parentDB.addTable(this);
	}
	
	public String getName() {
		return name;
	}

	public int getSize() {
		return tableData.size();
	}
	
	public void loadCSV(File sourceFile, String[] dataTypes) {
		CSVParser parser = new CSVParser();
		parser.parse(sourceFile, new Container() {
			public void addRow(List<String> data) {
				addRecord(data);
			}
			
			public void addFields(List<String> fields) {
				int i = 1;
				String defKey = DEFAULT_KEY_NAME;
				while (fields.contains(key)) {
					defKey = DEFAULT_KEY_NAME + "(" + i + ")";
					i++;
				}
				if (key.equals("default"))
					key = defKey;
				
				fieldNames.add(defKey);
				fieldNames.ensureCapacity(fields.size() + 1);
				for (String field : fields)
					fieldNames.add(field);
			}
		});
	}
	
	public void loadCSV(File sourceFile, String[] dataTypes, String keyField) {
		key = keyField;
		loadCSV(sourceFile, dataTypes);
	}
	
	public void addRecord(List<String> data) {
		int keyIdx = fieldNames.indexOf(key);
		String keyData = data.get(keyIdx);
		tableData.put(keyData, data);
	}
	
	public int getFieldSize() {
		return fieldNames.size();
	}
	
	public List<String> getFields() {
		return fieldNames;
	}
	
	public List<String> searchKeysByField(String Field, String data) {
		return new ArrayList<>();
	}
	
	public String searchFieldByKey(String field, String keyData) {
		List<String> rowData = tableData.get(keyData);
		int fieldIdx = fieldNames.indexOf(field);
		return rowData.get(fieldIdx);
	}
	
	public HashMap<String, String> searchRowByKey(String keyData) {
		List<String> rowData = tableData.get(keyData);
		HashMap<String, String> rowHash = new HashMap<>();
		for (int i = 0; i < fieldNames.size(); i++)
			rowHash.put(fieldNames.get(i), rowData.get(i));
		return rowHash;
	}
	
	public List<String> getRange(String field) {
		ArrayList<String> ranges = new ArrayList<String>();
		ranges.ensureCapacity(tableData.size() / 2);
		int fieldID = fieldNames.indexOf(field);
		for (int i = 0; i < tableData.size(); i++) {
			String s = tableData.get("" + i).get(fieldID);
			if (!ranges.contains(s))
				ranges.add(s);
		}
		ranges.trimToSize();
		return ranges;
		
	}
}
