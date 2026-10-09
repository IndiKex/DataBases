package databases.test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import databases.main.data.Data;
import databases.main.data.DataType;
import databases.main.interfaces.Table;
import databases.main.tables.FileTable;

public class DataBaseTest {

	public static void main(String[] args) {
		try {
			//String[] stopDataTypes = {"int", "int", "str", "str", "float", "float", "int", "str", "int", "str", "str", "int"};
			DataType[] stopDataTypes = {
					DataType.INTEGER,
					DataType.INTEGER,
					DataType.STRING,
					DataType.STRING,
					DataType.FLOAT,
					DataType.FLOAT,
					DataType.INTEGER,
					DataType.STRING,
					DataType.INTEGER,
					DataType.STRING,
					DataType.STRING,
					DataType.INTEGER
			};
			String[] agencyDataTypes = {"int", "str", "str", "str", "str", "str", "str"};
			
			String projectPath = System.getProperty("user.dir");
			String tablePath = projectPath + "/testDatabase/testTable";
			Table t = new FileTable(tablePath);
			
			//t.loadCSV(new File(projectPath + "/testFiles/csv/stops.txt"), Arrays.asList(stopDataTypes));
			
			System.out.println(t.getRecordFromKey(new Data(0)));
			
			t.close();
			
			// TEST 1
			String[] expectedFields = {"DefaultKeyID", "stop_id", "stop_code", "stop_name", "stop_desc", "stop_lat", "stop_lon", "zone_id", "stop_url", "location_type", "parent_station", "stop_timezone", "wheelchair_boarding"};
			
			
			// TEST 2
			String[] expectedZeroRowData = {"0", "70", "", "Búcsúszentlászló", "", "46.793056", "16.932778", "", "", "0", "", "", "2"};
			
			
			// TEST 3
			String[] expected1193RowData = {"1193", "152470", "", "Szeged, Rókus vasútállomás", "", "46.266107", "20.125686", "", "", "0", "", "", "2"};
			
			
			// TEST 4
			String[][] expected = {
					{"0", "1", "2"},
					{"134", "198", "1743"},
					{"GYSEV", "MÁV", "Gyermekvasút"},
					{"http://www2.gysev.hu/", "http://www.mav-start.hu"},
					{"Europe/Budapest"},
					{"hu"},
					{"+36 (99) 577-212", "+36 (1) 3-49-49-49"},
					{"https://jegy.mav.hu/"}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
