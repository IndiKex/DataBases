package databases.main.views;

import java.util.HashMap;
import java.util.List;

import databases.main.data.Data;
import databases.main.interfaces.Table;
import databases.main.interfaces.View;
import databases.main.tables.FileTable;

public class NaiveView implements View {
	private String name;
	private List<String> fields;
	private HashMap<Data, List<Data>> dataHash = new HashMap<>();
	boolean canAdd = true;
	
	public NaiveView(String name, List<String> fields, HashMap<Data, List<Data>> dataHash) {
		this.name = name;
		this.fields = fields;
		this.dataHash = dataHash;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return dataHash.size();
	}
	

	public int getFieldSize() {
		return fields.size();
	}
	
	public List<String> getFields() {
		return fields;
	}
	

	public void addRecord(Data key, List<Data> recordData) {
		if (!canAdd) return;
		
		dataHash.put(key, recordData);
	}
	
	public void disableAdd() {
		canAdd = false;
	}
	
	
	public Data getFieldFromKey(String field, Data key) {
		return dataHash.get(key).get(fields.indexOf(field));
	}
	
	
	// TODO
	public Table createNewTable(boolean temporary) {
		return new FileTable();
	}
}
