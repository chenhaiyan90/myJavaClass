package leader.domain;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Configurable;

import leader.service.HelloWorldBean2;

@Configurable
public class MyOrder {

@Resource	
private HelloWorldBean2 hellBean;

public HelloWorldBean2 getHellBean() {
	return hellBean;
}

}
