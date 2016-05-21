package general.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {
	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			File file = new File("Joon.txt");
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String read = "";
			while ((read = br.readLine()) != null) {
				if (read.trim().startsWith("#")) {
					System.out.println("[COMMENT] " + read);
				} else
					System.out.println("[ " + read + " ]");
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			if (br == null) {
				System.out.println("BufferedReader 는 NULL 이었닷~!");
			} else
				System.out.println("BR 은 꼭 닫아야 하겠네~ File 못찾았어도~");
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
}
