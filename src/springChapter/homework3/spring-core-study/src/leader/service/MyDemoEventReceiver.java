package leader.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import leader.bean.MyDemoEvent;
import leader.bean.MyDemoEvent2;

@Component
public class MyDemoEventReceiver {

	@EventListener(value={MyDemoEvent.class,MyDemoEvent2.class},condition = "#demoEvnt.id > 4")
	public void handlerEvent(Object demoEvnt)
	{
		System.out.println("received event "+demoEvnt);
	}
	
//	@EventListener
//	public void handlerEvent(MyDemoEvent2 demoEvnt)
//	{
//		System.out.println("received event2 "+demoEvnt);
//	}
}
