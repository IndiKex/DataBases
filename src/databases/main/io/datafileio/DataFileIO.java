package databases.main.io.datafileio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import databases.main.data.Data;
import databases.main.io.datafileio.interfaces.DataTypeIO;
import databases.main.io.datafileio.interfaces.DataTypeIOPackage;

public class DataFileIO {
	public static void write(Data data, DataTypeIO io) {
		try {
			io.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeAt(int idx, Data data, DataTypeIO io) {
		try {
			io.writeAt(idx, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeRecord(List<Data> recordData, DataTypeIOPackage dtp) {
		try {
			for (int i = 0; i < dtp.size(); i++) {
				DataTypeIO io = dtp.get(i);
				io.write(recordData.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeRecordAt(int idx, List<Data> recordData, DataTypeIOPackage dtp) {
		try {
			for (int i = 0; i < dtp.size(); i++) {
				DataTypeIO io = dtp.get(i);
				io.writeAt(idx, recordData.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Data read(int idx, DataTypeIO io) {
		Data d = null;
		try {
			d = io.read(idx);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public static List<Data> readRecord(int idx, DataTypeIOPackage dtp) {
		List<Data> res = new ArrayList<>();
		try {
			for (int i = 0; i < dtp.size(); i++) {
				DataTypeIO io = dtp.get(i);
				Data d = io.read(idx);
				res.add(d);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
}
