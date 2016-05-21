package javalang.uuid;

import java.util.UUID;

public class GenerateUuidMain {
	public static void main(String[] args) {
		long nStart = System.currentTimeMillis();
		for (int i = 0 ; i < 1000 ; i++) {
			UUID uuid = UUID.randomUUID();
			System.out.println(uuid.toString().replace("-","").toUpperCase() + " / " + uuid.toString().replace("-","").toUpperCase().length());
		}
		System.out.println("Elapsed : " + (System.currentTimeMillis() - nStart) + " ms");
	}
}
