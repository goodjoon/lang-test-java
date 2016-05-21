package general.exception;

public class ExceptionTester1 {
	public static void main(String[] args) {
		try {
			ExceptionTester1 tester1 = new ExceptionTester1();
			tester1.catchAndThrowMethodTest();
		} catch (ChildException e) {
			System.out.println("ChildException 잡았음~~");
			e.printStackTrace();
		}
	}
	
	public void method1() throws ParentException {
		throw new ParentException();
	}
	
	public void method2() throws ChildException {
		throw new ChildException();
	}
	
	public void catchAndThrowMethodTest() throws ChildException {
		try {
			method2();
		} catch (ChildException e) {
			e.printStackTrace();
			System.out.println("ChildException으로 캐치한놈 : " + e.getClass());
			throw e;
		} catch (ParentException e) {
			e.printStackTrace();
			System.out.println("ParentException으로 캐치한놈 : " + e.getClass());
		}
	}
}
