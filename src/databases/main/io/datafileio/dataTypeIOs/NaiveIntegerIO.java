package databases.main.io.datafileio.dataTypeIOs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import databases.main.data.Data;
import databases.main.io.datafileio.interfaces.IntegerIO;

public class NaiveIntegerIO implements IntegerIO {
	private String dataPath = "/datafile.txt";
	private String metaPath = "/metafile.txt";
	
	private File dataFile;
	private File metaFile;
	private File fieldDirFile;
	
	private int nRecords = 0;
	
	private RandomAccessFile raf;
	private FileChannel channel;
	private MappedByteBuffer buffer;
	
	private static long MAX_FILE_SIZE = 2 * 1024 * 1024;
	
	public NaiveIntegerIO(String fieldDir) throws IOException {
		openFiles(fieldDir);
	}
	
	private void openFiles(String fieldDir) throws IOException {
		fieldDirFile = new File(fieldDir);
		dataFile = new File(fieldDirFile, dataPath);
		metaFile = new File(fieldDirFile, metaPath);
		
		if (!(fieldDirFile.exists() && fieldDirFile.isDirectory()))
			throw new IOException("Field Directory does not exist.");
		
		if (!metaFile.exists()) {
			nRecords = 0;
			metaFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(metaFile));
			bw.write(Integer.toString(nRecords));
			bw.write("\nINTEGER");
			bw.close();
		} else {
			FileReader fr = new FileReader(metaFile);
			BufferedReader br = new BufferedReader(fr);
			nRecords = Integer.parseInt(br.readAllLines().get(0));
			br.close();
		}
		
		raf = new RandomAccessFile(dataFile, "rw");
		channel = raf.getChannel();
		
		buffer = channel.map(MapMode.READ_WRITE, 0, MAX_FILE_SIZE);
		buffer.position(nRecords * 4);
		
	}
	
	public void write(Data data) throws IOException {
		buffer.putInt(data.toInt());
		
		nRecords++;
	}
	
	public void writeAt(int idx, Data data) throws IOException {
		if (idx >= nRecords)
			throw new IOException("Index Overflow. Tried to read at invalid index.");
		
		buffer.putInt(idx * 4, data.toInt());
	}
	
	public Data read(int idx) throws IOException {
		if (idx >= nRecords)
			throw new IOException("Index Overflow. Tried to read at invalid index.");
		
		int iData = buffer.getInt(idx * 4);
		return new Data(iData);
	}
	
	public void clear() {
		nRecords = 0;
		buffer.position(nRecords);
	}
	
	public void close() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(metaFile));
		bw.write(Integer.toString(nRecords));
		bw.write("\nINTEGER");
		bw.close();
		
		buffer.force();
		channel.close();
		raf.close();
	}
	
}
