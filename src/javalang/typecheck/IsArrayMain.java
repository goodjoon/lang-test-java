package javalang.typecheck;

import java.lang.reflect.Array;

public class IsArrayMain {
	public static void main(String[] args) {
		
		String[] strs1 = {"하나", "둘", "셋"};
		int[] ints1 = {1,2,3};
		Object nullObject = null;
		
		
		Object o = strs1;
		System.out.println("T1 : " + (o instanceof Array));
		System.out.println("T1 : " + (o instanceof String[]));
		System.out.println("T1 : " + (o instanceof Object[]));
		
		o = ints1;
		System.out.println("T1 : " + (o instanceof Array));
		System.out.println("T1 : " + (o instanceof String[]));
		System.out.println("T1 : " + (o instanceof Object[]));
		
		
	}
}
