package leader.security;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import leader.dao.DemoDao;

@Component
public class HelloPermissionEvaluator implements PermissionEvaluator {

	@Autowired
	private DemoDao demoDao;
	Logger log = LoggerFactory.getLogger(HelloPermissionEvaluator.class);

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		log.info("hasPermission(Authentication, Object, Object) called ,target obj " + targetDomainObject
				+ " permission " + permission);
		String userName=authentication.getName();
		log.info("user name "+userName );
		return demoDao.hasAuthority(userName, targetDomainObject);
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		log.error("hasPermission(Authentication, Serializable, String, Object) called");
		throw new RuntimeException("ID based permission evaluation currently not supported.");
	}
}