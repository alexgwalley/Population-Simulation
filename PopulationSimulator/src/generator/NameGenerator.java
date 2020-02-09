package generator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class NameGenerator {
	
	private static Random r = new Random();
	
	public static String newName() {
		String name = "Jean";
		try {
			BufferedReader br = new BufferedReader(new FileReader("res\\french_names.txt"));
			Object[] names = br.lines().toArray();
			name = names[r.nextInt(274)].toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

}
