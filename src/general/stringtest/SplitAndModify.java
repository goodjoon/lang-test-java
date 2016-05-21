package general.stringtest;

public class SplitAndModify {
	public static void main(String[] args) {
		String statement = "serviceManager.queryName";
		
		String[] tokens = statement.split("\\.");
		for (int i = 0 ; i < tokens.length ; i++) {
			System.out.println(i + " : " + tokens[i]);
		}
		
		System.out.println(statement.substring(0, statement.lastIndexOf(".")) );
	}
}
