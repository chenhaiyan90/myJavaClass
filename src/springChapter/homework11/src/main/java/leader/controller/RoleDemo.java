package leader.controller;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleDemo {

	@RequestMapping("/super/adminrole")
	@PreAuthorize("hasAnyRole('admin', 'user')")
	public String show() {
		return "admin Role Page";
	}

	@RequestMapping("/manager/index.html")
	@PreAuthorize("hasAnyRole('admin', 'user')")
	public String showManIndex() {
		return "manager Role Page";
	}

	@RequestMapping("/manager/{resource}/index.html")
	@PreAuthorize("isAuthenticated() and hasPermission(#resource, 'read')")
	public String showResourceIndex(@PathVariable("resource") @P("resource") String resouce) {
		return "manager resource  Page for resource " + resouce;
	}

}
