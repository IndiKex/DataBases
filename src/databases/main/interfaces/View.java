package databases.main.interfaces;

import java.util.List;

import databases.main.data.Data;

public interface View {
	public String getName();
	public int getSize();

	public int getFieldSize();
	public List<String> getFields();
	
	public Data getFieldFromKey(String field, Data key);
	
	public Table createNewTable(boolean temporary);
}
