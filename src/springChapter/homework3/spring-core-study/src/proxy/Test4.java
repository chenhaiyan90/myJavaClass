package proxy;

import java.lang.reflect.InvocationTargetException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

public class Test4 {
public static void main(String[] args) throws CannotCompileException, NotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
{
	 ClassPool classPool = ClassPool.getDefault();  
     CtClass ctClass = classPool.makeClass("leaderus.mysuperclass");  
	    CtField field = new CtField(classPool.get("java.lang.String"), "name", ctClass);  
	    field.setModifiers(Modifier.PRIVATE); 
	    ctClass.addField(field);  
	    ctClass.addMethod(CtNewMethod.setter("setName", field));  
	    ctClass.addMethod(CtNewMethod.getter("getName", field));  
	   
	    CtMethod method = new CtMethod(CtClass.voidType, "hello", null, ctClass);  
	    method.setModifiers(Modifier.PUBLIC);  
	    method.setBody("{System.out.println(\"Hello\" + this.name);}");  
	    ctClass.addMethod(method);  
	    
	    CtConstructor constructor = new CtConstructor(null, ctClass);  
	    constructor.setModifiers(Modifier.PUBLIC);  
	    constructor.setBody("{System.out.println(\"created \"+this); }");  
	    ctClass.addConstructor(constructor);  
	   
	    Class<?> clazz = ctClass.toClass();  
	    Object obj = clazz.newInstance();  
	    clazz.getMethod("setName", String.class).invoke(obj, " Super Man");  
	    clazz.getMethod("hello").invoke(obj);  
	    

}
}
