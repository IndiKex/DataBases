package databases.main.data;

public class Data {
	private int iData;
	private float fData;
	private String sData;
	
	public Data(int data) {
		this.iData = data;
		this.fData = 0.0f;
		this.sData = "";
	}
	
	public Data(float data) {
		this.iData = 0;
		this.fData = data;
		this.sData = "";
	}
	
	public Data(String data) {
		this.iData = 0;
		this.fData = 0.0f;
		this.sData = data;
	}
	
	public int toInt() {
		return iData;
	}
	
	public String toString() {
		return sData;
	}
	
	public float toFloat() {
		return fData;
	}
}
