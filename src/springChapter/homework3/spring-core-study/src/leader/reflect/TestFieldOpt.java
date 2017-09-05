package leader.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

import org.springframework.beans.DirectFieldAccessor;

public class TestFieldOpt {

	public static void main(String[] args) throws Throwable {
		MyCompBean<String> comp = new MyCompBean<String>();
		Field field = MyCompBean.class.getDeclaredField("level");
		field.setAccessible(true);
		field.set(comp, 5);
		System.out.println(comp.getLevel());
		// MethodHandle ,level field must be public
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		// MethodHandle fieldHandle = lookup.findSetter(MyCompBean.class,
		// "level", Integer.class);
		MethodHandle fieldHandle = lookup.findVirtual(MyCompBean.class, "setLevel",
				MethodType.methodType(void.class, Integer.class));
		fieldHandle.invoke(comp, 15);
		System.out.println(comp.getLevel());

		// Spring 4
		DirectFieldAccessor accessor = new DirectFieldAccessor(comp);
		accessor.setPropertyValue("level", 20);
		System.out.println(accessor.getPropertyValue("level"));

	}

}
