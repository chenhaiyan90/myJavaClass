package leader.reflect;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class TestAnnotation {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
		Annotation[] annos=MyService.class.getAnnotations();
		Map<String,Object> myBeanMap=new HashMap<String,Object>();
		for(Annotation ann:annos)
		{
			Class<? extends Annotation> annCls=ann.annotationType();
			System.out.println(annCls);
			if(annCls==LeaderComonent.class)
			{
				LeaderComonent leaderAnn=(LeaderComonent) ann;
				System.out.println("find leader annotation "+leaderAnn);
				Object myserviceObj=MyService.class.newInstance();
				myBeanMap.put(leaderAnn.name(), myserviceObj);
				
			}
		}

	}

}
