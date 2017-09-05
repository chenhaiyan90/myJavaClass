package util;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.springframework.core.NamedThreadLocal;

public class ThreadLocalUtil {
	public static void dumphreadLocals() {
		try {
			// Get a reference to the thread locals table of the current thread
			Thread thread = Thread.currentThread();
			Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
			threadLocalsField.setAccessible(true);
			Object threadLocalTable = threadLocalsField.get(thread);

			// Get a reference to the array holding the thread local variables
			// inside the
			// ThreadLocalMap of the current thread
			@SuppressWarnings("rawtypes")
			Class threadLocalMapClass = Class.forName("java.lang.ThreadLocal$ThreadLocalMap");
			Field tableField = threadLocalMapClass.getDeclaredField("table");
			tableField.setAccessible(true);
			Object[] table = (Object[]) tableField.get(threadLocalTable);

			@SuppressWarnings("rawtypes")
			Class threadLocalMapEntryClass = Class.forName("java.lang.ThreadLocal$ThreadLocalMap$Entry");
			Field entryValueField = threadLocalMapEntryClass.getDeclaredField("value");
			entryValueField.setAccessible(true);
			// The key to the ThreadLocalMap is a WeakReference object. The
			// referent field of this object
			// is a reference to the actual ThreadLocal variable
			Field referentField = Reference.class.getDeclaredField("referent");
			referentField.setAccessible(true);

			for (Object entry : table) {
				// Each entry in the table array of ThreadLocalMap is an Entry
				// object
				// representing the thread local reference and its value
				if (entry != null) {

					Object tlcValue = entryValueField.get(entry);

					ThreadLocal threadLocal = (ThreadLocal) referentField.get(entry);

					// System.out.println("thread local value "+tlcValue);
					// if(threadLocal)
					printObject(threadLocal, tlcValue);
				}
			}
			System.out.println("__________________________");
		} catch (Exception e) {
			// We will tolerate an exception here and just log it
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	private static void printObject(ThreadLocal threadLocal, Object obj) {
		String threadLocalCls=threadLocal.getClass().getName();
		if (threadLocal instanceof NamedThreadLocal) {
			System.out.print("......Spring threadlocal var: "+threadLocal+"=");
		} else if(obj==null || threadLocalCls.startsWith("java") ||threadLocalCls.startsWith("sun")){
			return;
		}
		if (obj instanceof Object[]) {
			System.out.println(Arrays.deepToString((Object[]) obj));
		} else if (obj instanceof java.lang.ref.Reference) {
			java.lang.ref.Reference ref = (Reference) obj;
			System.out.println(" ref " + ref.getClass().getName() + " ref to " + ref.get());
		} else if (obj instanceof ThreadLocal) {
			ThreadLocal threadLc = (ThreadLocal) obj;
			String clssName = threadLc.getClass().getName();
			System.out.println(threadLc.getClass().getName() + "=" + threadLc.get());
		} else {
			System.out.println(obj);
		}
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {

		dumphreadLocals();
	}
}
