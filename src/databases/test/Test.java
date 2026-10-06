package databases.test;

public class Test {
	String name;
	
	public Test(String name) {
		this.name = name;
	}
	
	public boolean run() {
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	public static void addTest(Test t) {
		boolean success = t.run();
		if (success)
			System.out.println("TEST " + t.getName() + " successful");
		else {
			System.out.println("TEST " + t.getName() + " unsuccessful");
			System.out.println("\n\tTESTS FAILED");
			System.exit(0);
		}
	}
}
