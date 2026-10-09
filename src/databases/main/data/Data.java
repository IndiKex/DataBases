package databases.main.data;



public class Data {
	private int iData;
	private float fData;
	private String sData;
	
	private DataType dataType;
	
	public Data(int data) {
		this.iData = data;
		this.fData = 0.0f;
		this.sData = "";
		dataType = DataType.INTEGER;
	}
	
	public Data(float data) {
		this.iData = 0;
		this.fData = data;
		this.sData = "";
		dataType = DataType.FLOAT;
	}
	
	public Data(String data) {
		this.iData = 0;
		this.fData = 0.0f;
		this.sData = data;
		dataType = DataType.STRING;
	}
	
	public Data(DataType type) {
		this.iData = 0;
		this.fData = 0.0f;
		this.sData = "";
		dataType = type;
	}
	
	public int toInt() {
		return iData;
	}
	
	public String toString() {
		switch (dataType) {
		case DataType.INTEGER:
			return Integer.toString(iData);
		case DataType.FLOAT:
			return Float.toString(fData);
		}
		return sData;
	}
	
	public float toFloat() {
		return fData;
	}
	
	public DataType getDataType() {
		return dataType;
	}
	
	public boolean equals(Data d) {
		if (dataType.equals(d.dataType)) {
			switch (d.dataType) {
			case DataType.INTEGER:
				if (d.iData == iData)
					return true;
				break;
			case DataType.FLOAT:
				if (d.fData == fData)
					return true;
				break;
			case DataType.STRING:
				if (d.sData.equals(sData))
					return true;
				break;
			}
		}
		
		return false;
	}
	
	public static Data parseData(String s) {
		try {
			int iData = Integer.parseInt(s);
			return new Data(iData);
		} catch (Exception ignored) {}
		try {
			float fData = Float.parseFloat(s);
			return new Data(fData);
		} catch (Exception ignored) {}
		return new Data(s);
	}
	
	public static DataType parseDataType(String s) {
		if (s.equals("INTEGER"))
			return DataType.INTEGER;
		if (s.equals("FLOAT"))
			return DataType.FLOAT;
		if (s.equals("STRING"))
			return DataType.STRING;
		return null;
	}
}
