package leader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import leader.bean.MyDemoEvent;
import leader.bean.MyDemoEvent2;

@Component
public class MySpringEvtSender {
@Autowired
private  ApplicationEventPublisher publisher;
    
  public void createDemoEvent()
  {
	  MyDemoEvent evnt=new MyDemoEvent();
	  evnt.setId(4);
	  evnt.setMessage("xxxxxxxxxxxxx");
	  publisher.publishEvent(evnt);
	  System.out.println("published event "+evnt);
	  MyDemoEvent2 evnt2=new MyDemoEvent2();
	  evnt2.setId(111111);
	  evnt2.setMessage("xxxxxxxxxxxxx");
	  publisher.publishEvent(evnt2);
	  System.out.println("published event "+evnt2);
	  
  }
}
