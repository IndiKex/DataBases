package databases.main.interfaces;

import java.util.List;

public interface DataBase {
	public void addTable(Table table);
	public List<String> getTableNames();
	public Table getTable(String tableName);
	public String getName();
}
