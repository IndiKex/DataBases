package databases.main.data;

public class Data {
	private int iData;
	private float fData;
	private String sData;
	
	private String dataType;
	
	public Data(int data) {
		this.iData = data;
		this.fData = 0.0f;
		this.sData = "";
		dataType = "i";
	}
	
	public Data(float data) {
		this.iData = 0;
		this.fData = data;
		this.sData = "";
		dataType = "f";
	}
	
	public Data(String data) {
		this.iData = 0;
		this.fData = 0.0f;
		this.sData = data;
		dataType = "s";
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
	
	public boolean equals(Data d) {
		if (dataType.equals(d.dataType)) {
			switch (d.dataType) {
			case "i":
				if (d.iData == iData)
					return true;
				break;
			case "f":
				if (d.fData == fData)
					return true;
				break;
			case "s":
				if (d.sData.equals(sData))
					return true;
				break;
			}
		}
		
		return false;
	}
}
