package databases.main.io.parsers;

import databases.main.io.parsers.dataparsers.interfaces.FloatParser;
import databases.main.io.parsers.dataparsers.interfaces.IntegerParser;
import databases.main.io.parsers.dataparsers.interfaces.StringParser;

public class DataParserPackage {
	public StringParser stringParser;
	public IntegerParser integerParser;
	public FloatParser floatParser;
	
	DataParserPackage(StringParser stringParser, IntegerParser integerParser, FloatParser floatParser) {
		this.stringParser = stringParser;
		this.integerParser = integerParser;
		this.floatParser = floatParser;
	}
}
