package databases.main.databases;

import java.util.ArrayList;
import java.util.List;

import databases.main.interfaces.DataBase;
import databases.main.interfaces.Table;

public class NaiveDataBase implements DataBase {
	private List<Table> tableList = new ArrayList<>();
	String name;
	
	public NaiveDataBase(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addTable(Table table) {
		tableList.add(table);
	}
	
	public List<String> getTableNames() {
		ArrayList<String> names = new ArrayList<>();
		names.ensureCapacity(tableList.size());
		for (Table t : tableList)
			names.add(t.getName());
		return names;
	}
	
	public Table getTable(String tableName) {
		for (Table t : tableList)
			if (t.getName().equals(tableName))
				return t;
		return null;
	}
}
