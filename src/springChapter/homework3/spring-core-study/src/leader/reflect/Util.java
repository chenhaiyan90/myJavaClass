package leader.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class Util {

	public static void analyseFields(Class<?> cls,Object theObj) throws IllegalArgumentException, IllegalAccessException
	{
		Field[] fields =cls.getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			fields[j].setAccessible(true);
			 
			System.out.print(fields[j].getName() + ",");
			Type fieldType = fields[j].getGenericType();
			System.out.print(fieldType.getTypeName()+",");
			if (fieldType.equals(String.class)) {
				System.out.print(fields[j].get(theObj));
				
			} else if (fieldType.equals(java.lang.Integer.class)) {
				System.out.println(fields[j].get(theObj));
			} else if (fieldType.getTypeName().equals("int")) {
				
				System.out.println(fields[j].getInt(theObj));
			}
			else if (fieldType instanceof java.lang.reflect.GenericArrayType) 
			{
				GenericArrayType genType=(GenericArrayType) fieldType;
				System.out.println("  GenericArrayType :" +genType.getGenericComponentType().getTypeName());
			}
			else if (fieldType instanceof java.lang.reflect.ParameterizedType) 
			{
				ParameterizedType parType=(ParameterizedType) fieldType;
				System.out.println("  ParameterizedType : raw "+parType.getRawType()+" actuals:"+Arrays.toString(parType.getActualTypeArguments()));
			}else
			{
				System.out.println(" complix type " );
			}
			//
		}
		System.out.println();
	}
}
