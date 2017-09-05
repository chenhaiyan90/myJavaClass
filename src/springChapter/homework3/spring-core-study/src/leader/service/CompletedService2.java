package leader.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import leader.dao.AbstractDAO;

@Component
public class CompletedService2 {
@Autowired	
private Collection<AbstractDAO> allDaos;

public Collection<AbstractDAO> getAllDaos() {
	return allDaos;
}

 

}
