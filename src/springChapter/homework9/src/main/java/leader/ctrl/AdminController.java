package leader.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import leader.bean.AssignClsBean;
import leader.bean.KeyValueBean;
import leader.bean.LdClassSummary;

@RestController

public class AdminController {
	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping(value = "/assignclass", method = RequestMethod.POST)
	public String assginClasst(AssignClsBean assginClsBean) {
		logger.info("assgin students " + assginClsBean);
		try {
			// ldClassService.assignLdClass(assginClsBean);
			return "Success";
		} catch (Exception e) {
			return "失败:" + e;
		}
	}

	@RequestMapping("/ldclassmap")
	public List<leader.bean.KeyValueBean<Integer, String>> getLdClassMap() {
		List<leader.bean.KeyValueBean<Integer, String>> arrays = IntStream.range(1, 20)
				.mapToObj(i -> new leader.bean.KeyValueBean<Integer, String>(i, "Class " + i))
				.collect(Collectors.toList());
		return arrays;
	}

	@RequestMapping("/ldclasslist")
	public List<LdClassSummary> getLdClassSummary() {
		List<LdClassSummary> arrays = IntStream.range(1, 20).mapToObj(i -> {
			LdClassSummary sumr = new LdClassSummary();
			sumr.setClassId(i);
			sumr.setClassName("class " + i);
			sumr.setCurNormalStudents(System.currentTimeMillis() % 30);
			sumr.setDropedStudents(System.currentTimeMillis() % 10);
			return sumr;
		}).collect(Collectors.toList());
		return arrays;
	}
}
