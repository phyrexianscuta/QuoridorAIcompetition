package Quoridor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class logRecord {
	
	public static PrintWriter f;
	
	public static void write(String string) {
		f.println(string);		
	}
	
	public static void close() {
		f.close();
	}

	public static void main(String filename) throws FileNotFoundException {
		PrintWriter f= new PrintWriter(filename+".txt");
		}
	}
