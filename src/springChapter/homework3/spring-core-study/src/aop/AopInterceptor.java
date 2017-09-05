package aop;
  
public class AopInterceptor {
	public static void afterInvoke() {
		System.err.println("After...");
	}

	public static void beforeInvoke() {
		System.err.println("Before...");
	}
}