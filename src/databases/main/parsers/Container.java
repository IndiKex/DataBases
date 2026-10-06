package databases.main.parsers;

import java.util.List;

public interface Container {
	public void addRow(List<String> data);
	public void addFields(List<String> fields);
}
