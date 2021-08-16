package entity;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

public class ExceptionWriter {

	public static PrintStream getStream() {

		PrintStream stream = null;
		try {
			
			stream = new PrintStream("programErrors.txt");
			stream.append(new Date().toString()+ ":\n");
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return stream;
		
	}
}
