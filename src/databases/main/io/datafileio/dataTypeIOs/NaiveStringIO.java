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
import databases.main.io.datafileio.interfaces.StringIO;

public class NaiveStringIO implements StringIO {
	private String dataPath = "/datafile.txt";
	private String indexPath = "/indexfile.txt";
	private String metaPath = "/metafile.txt";
	
	private File dataFile;
	private File indexFile;
	private File metaFile;
	private File fieldDirFile;
	
	private int nRecords;
	
	private RandomAccessFile rafData;
	private FileChannel channelData;
	private MappedByteBuffer bufferData;
	
	private RandomAccessFile rafIndex;
	private FileChannel channelIndex;
	private MappedByteBuffer bufferIndex;
	
	private static long MAX_FILE_SIZE = 2 * 1024 * 1024;
	private static long MAX_INDEX_SIZE = 4 * 1024 * 1024;
	
	public NaiveStringIO(String fieldDir) throws IOException {
		openFiles(fieldDir);
	}
	
	private void openFiles(String fieldDir) throws IOException {
		fieldDirFile = new File(fieldDir);
		dataFile = new File(fieldDirFile, dataPath);
		indexFile = new File(fieldDirFile, indexPath);
		metaFile = new File(fieldDirFile, metaPath);
		
		if (!(fieldDirFile.exists() && fieldDirFile.isDirectory()))
			throw new IOException("Field Directory does not exist.");
		
		if (!metaFile.exists()) {
			nRecords = 0;
			metaFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(metaFile));
			bw.write(Integer.toString(nRecords));
			bw.write("\nSTRING");
			bw.close();
		} else {
			FileReader fr = new FileReader(metaFile);
			BufferedReader br = new BufferedReader(fr);
			nRecords = Integer.parseInt(br.readAllLines().get(0));
			br.close();
		}
		
		rafData = new RandomAccessFile(dataFile, "rw");
		channelData = rafData.getChannel();
		
		rafIndex = new RandomAccessFile(indexFile, "rw");
		channelIndex = rafIndex.getChannel();
		
		bufferData = channelData.map(MapMode.READ_WRITE, 0, MAX_FILE_SIZE);
		bufferIndex = channelIndex.map(MapMode.READ_WRITE, 0, MAX_INDEX_SIZE);
		
		bufferData.position(nRecords);
		bufferIndex.position(nRecords * 8);
	}
	
	public void write(Data data) throws IOException {
		int bufferPos = bufferData.position();
		bufferIndex.putInt(bufferPos);
		for (char c : data.toString().toCharArray()) {
			bufferData.put((byte)c);
		}
		bufferPos = bufferData.position();
		bufferIndex.putInt(bufferPos);
		
		nRecords++;
	}
	
	public void writeAt(int idx, Data data) throws IOException {
		if (idx >= nRecords)
			throw new IOException("Index Overflow. Tried to read at invalid index.");
		
		int bufferPos = bufferData.position();
		bufferIndex.putInt(idx * 4, bufferPos);
		for (char c : data.toString().toCharArray()) {
			bufferData.put((byte) c);
		}
		bufferPos = bufferData.position();
		bufferIndex.putInt((idx + 1) * 4, bufferPos);
	}
	
	public Data read(int idx) throws IOException {
		if (idx >= nRecords)
			throw new IOException("Index Overflow. Tried to read at invalid index.");
		
		String sData = "";
		int dataStartIdx = bufferIndex.getInt(idx * 4);
		int dataEndIdx = bufferIndex.getInt((idx + 1) * 4);
		for (int i = dataStartIdx; i < dataEndIdx; i++) {
			sData += (char) (bufferData.get(i) & 0xFF);
		}
		return new Data(sData);
	}
	
	public void clear() {
		nRecords = 0;
		bufferData.position(nRecords);
		bufferIndex.position(nRecords);
	}
	
	public void close() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(metaFile));
		bw.write(Integer.toString(nRecords));
		bw.write("\nSTRING");
		bw.close();
		
		bufferData.force();
		channelData.close();
		rafData.close();
		
		bufferIndex.force();
		channelIndex.close();
		rafIndex.close();
	}
}
