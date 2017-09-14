package com.spring.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Model;

/**
 * @author chenhaiyan
 *
 */
public class ModelListUtil {
	public static List<Map<String, Object>> toMap(List<Model> list) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(list.size());
		for (Model m : list) {
			result.add(m.toMap());
		}
		return result;
	}
}
