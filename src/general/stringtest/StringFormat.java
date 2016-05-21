package general.stringtest;

/**
 * String.format() 을 사용하여 character 등 찍어보기임. 
 * @author sjoon
 *
 */
public class StringFormat {
	public static void main(String[] args) {
		String result = String.format("%s | %c | 0x%X", "스트링", 'K', -31111);
		
		System.out.println(result);
	}
}
