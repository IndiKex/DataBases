package databases.main.tables;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import databases.main.data.Data;
import databases.main.data.DataType;
import databases.main.interfaces.Table;
import databases.main.interfaces.View;
import databases.main.io.datafileio.dataTypeIOs.NaiveDataTypeIOPackage;
import databases.main.io.datafileio.interfaces.DataTypeIO;
import databases.main.io.datafileio.interfaces.DataTypeIOPackage;
import databases.main.io.parsers.CSVParser;
import databases.main.io.parsers.Container;
import databases.main.views.NaiveView;

public class FileTable implements Table {
	private String name;
	private int nRecords;
	private String tablePath;
	
	private ArrayList<String> fieldNames = new ArrayList<>();
	private ArrayList<DataType> dataTypes = new ArrayList<>();
	private int keyFieldID = 0;
	private String defaultFieldName = "id";
	
	private DataTypeIOPackage dtp;
	

	public FileTable(String tablePath) {
		this.tablePath = tablePath;
		
		String[] tablePathSplit = tablePath.split("/");
		this.name = tablePathSplit[tablePathSplit.length - 1];
		
		try {
			dtp = new NaiveDataTypeIOPackage(dataTypes, fieldNames);
			
			if (existsTableDir()) {
				readTableDir();
			} else {
				clearTableDir();
				addField(defaultFieldName, DataType.INTEGER);
			}
		} catch (IOException ignored) {}
		
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return nRecords;
	}
	
	public void loadCSV(File sourceFile, List<DataType> dataTypes_) {
		CSVParser parser = new CSVParser();
		
		parser.parse(sourceFile, new Container() {
			public void addRow(List<String> data) {
				ArrayList<Data> recordData = new ArrayList<>();
				recordData.ensureCapacity(data.size());
				for (int i = 1; i < data.size(); i++) {
					recordData.add(Data.parseData(data.get(i)));
				}
				try {
					addRecord(recordData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			public void addFields(List<String> fields) {
				fieldNames = new ArrayList<>();
				fieldNames.ensureCapacity(fields.size() + 1);
				fieldNames.add(defaultFieldName);
				dataTypes = new ArrayList<>();
				dataTypes.ensureCapacity(fields.size() + 1);
				dataTypes.add(DataType.INTEGER);
				
				for (int i = 0; i < fields.size(); i++) {
					fieldNames.add(fields.get(i));
					dataTypes.add(dataTypes_.get(i));
				}
				
				try {
					clearTableDir();
					createTableDir();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void loadCSV(File sourceFile, List<DataType> dataTypes_, String keyField) {
		
	}
	
	public int getFieldSize() {
		return fieldNames.size();
	}
	
	public List<String> getFields() {
		return fieldNames;
	}
	
	public void addField(String fieldName, DataType type) throws IOException {
		fieldNames.add(fieldName);
		dataTypes.add(type);
		createTableDir();
		
		DataTypeIO io = dtp.get(fieldNames.size() - 1);
		Data d = new Data(type);
		for (int i = 0; i < nRecords; i++) {
			io.write(d);
		}
	}
	
	private void readTableDir() throws IOException {
		File tableDir = new File(tablePath);
		if (!tableDir.exists())
			return;
		
		File[] files = tableDir.listFiles();
		String[] fileNames = tableDir.list();
		int idID = Arrays.asList(fileNames).indexOf("id");
		readField(files[idID]);
		
		for (File file : files) {
			if (file.isDirectory() && !file.getName().equals("id")) {
				readField(file);
			}
		}
	}
	
	private void readField(File fieldDir) throws IOException {
		String[] fileNames = fieldDir.list();
		int metaID = Arrays.asList(fileNames).indexOf("metafile.txt");
		DataType dataType;
		if (metaID > 0) {
			File[] fileContents = fieldDir.listFiles();
			FileReader fr = new FileReader(fileContents[metaID]);
			List<String> metadata = fr.readAllLines();
			fr.close();
			
			dataType = Data.parseDataType(metadata.get(1));
			
			fieldNames.add(fieldDir.getName());
			dataTypes.add(dataType);
			
			String fieldPath = tablePath + "/" + fieldDir.getName();
			dtp.add(dataType, fieldPath);
		}
		
	}
	
	private void createTableDir() throws IOException {
		File tableDir = new File(tablePath);
		
		if (!tableDir.exists())
			tableDir.mkdir();
		
		for (int i = 0; i < fieldNames.size(); i++) {
			String fieldName = fieldNames.get(i);
			File fieldDir = new File(tableDir, "/" + fieldName);
			if (!fieldDir.exists()) {
				fieldDir.mkdir();
			}
			if (i >= dtp.size()) {
				String fieldPath = tablePath + "/" + fieldName;
				dtp.add(dataTypes.get(i), fieldPath);
			}
		}
	}
	
	public boolean existsTableDir() {
		File tableDir = new File(tablePath);
		
		if (!tableDir.exists())
			return false;
		
		File[] files = tableDir.listFiles();
		String[] fileNames = tableDir.list();
		int idID = Arrays.asList(fileNames).indexOf("id");
		if (idID == -1 || !files[idID].isDirectory())
			return false;
		
		return true;
		
	}
	
	public void clearTableDir() throws IOException {
		File tableDir = new File(tablePath);
		if (!tableDir.exists())
			return;
		
		deleteFile(tableDir);
		tableDir.mkdir();
		
		dtp = new NaiveDataTypeIOPackage(new ArrayList<>(), new ArrayList<>());
	}
	
	private void deleteFile(File f) {
		if (f.isDirectory()) {
			File[] contents = f.listFiles();
			for (File cf : contents)
				deleteFile(cf);
		}
		f.delete();
	}
	
	public Data getFieldFromKey(String field, Data key) throws IOException {
		int fieldID = fieldNames.lastIndexOf(field);
		int idx = key2idx(key);
		DataTypeIO io = dtp.get(fieldID);
		return io.read(idx);
	}

	public List<Data> getRecordFromKey(Data key) throws IOException {
		int idx = key2idx(key);
		ArrayList<Data> recordData = new ArrayList<>();
		recordData.ensureCapacity(fieldNames.size());
		for (int i = 0; i < fieldNames.size(); i++) {
			DataTypeIO io = dtp.get(i);
			recordData.add(io.read(idx));
		}
		return recordData;
	}
	
	public List<Data> getFieldFromFieldData(String resField, String searchField, Data searchData) throws IOException {
		return new ArrayList<>();
	}
	
	public View getViewFromFieldData(String searchField, Data searchData) throws IOException {
		return new NaiveView("", fieldNames, new HashMap<>());
	}
	
	
	public void addRecord(List<Data> recordData) throws IOException {
		DataTypeIO idIO = dtp.get(0);
		idIO.write(new Data(nRecords));
		
		for (int i = 1; i < dtp.size(); i++) {
			DataTypeIO io = dtp.get(i);
			io.write(recordData.get(i-1));
		}
		nRecords++;
	}
	
	public void writeRecord(Data key, List<Data> recordData) throws IOException {
		int idx = key2idx(key);
		for (int i = 1; i < dtp.size(); i++) {
			DataTypeIO io = dtp.get(i);
			io.writeAt(idx, recordData.get(i-1));
		}
	}
	
	public void writeData(Data key, String fieldName, Data data) throws IOException {
		int fieldID = fieldNames.lastIndexOf(fieldName);
		if (fieldID == 0)
			return;
		
		int idx = key2idx(key);
		DataTypeIO io = dtp.get(fieldID);
		io.writeAt(idx, data);
	}
	
	private int key2idx(Data key) {
		return key.toInt();
	}
	
	public void close() throws IOException {
		dtp.close();
	}
}
