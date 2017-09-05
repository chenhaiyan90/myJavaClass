package leader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import leader.bean.MyDataSourceParamBean;

@Component
public class MyDataSource {
@Autowired
private MyDataSourceParamBean dsPramBean;
public MyDataSourceParamBean getDsPramBean() {
	return dsPramBean;
}

}
