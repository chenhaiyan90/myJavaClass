package leader.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.springframework.core.ResolvableType;

public class TestReflect1 {

	@SuppressWarnings({ "rawtypes" })
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		MyCompBean<String> comp = new MyCompBean<String>();
		Util.analyseFields(MyCompBean.class, comp);
		TypeVariable<Class<MyCompBean>> typeVariable = MyCompBean.class.getTypeParameters()[0];
		System.out.println("type name " + typeVariable.getName() + " type " + typeVariable.getTypeName());

		ParameterizedType parameterizedType = (ParameterizedType) MyChildCompBean.class.getGenericSuperclass();
		Type genericType = parameterizedType.getActualTypeArguments()[0];
		System.out.println("type is " + genericType + " is java.util.Date ? " + (genericType==java.util.Date.class));
		
		
		 parameterizedType = (ParameterizedType) MyChildCompBean2.class.getGenericSuperclass();
		 genericType = parameterizedType.getActualTypeArguments()[0];
		 System.out.println("type is " + genericType);
		 ParameterizedType realType=(ParameterizedType) genericType;
		 System.out.println("raw type "+realType.getRawType()+ ",realType is " + realType.getActualTypeArguments()[0]);
		 
		 //spring code
		 System.out.println("spring api ");
		 ResolvableType resolvableType1 = ResolvableType.forClass(MyCompBean.class);  
		 System.out.println("type name "+ resolvableType1.getGeneric(0).resolve());
		 
		 resolvableType1 = ResolvableType.forClass(MyChildCompBean.class).getSuperType();
		 System.out.println("type name "+ resolvableType1.getGeneric(0).resolve());
		 
		 resolvableType1 = ResolvableType.forClass(MyChildCompBean2.class).getSuperType();
		 Type theType=resolvableType1.getGeneric(0).resolve();
		 System.out.println("raw name "+ theType);
		 // MyCompBean<Collection<java.util.Date>>
		 theType=resolvableType1.getGeneric(0,0).resolve();
		 System.out.println("realType name "+ theType);

	}

}
