package com.spring.common.util;

import java.util.ArrayList;

public class SignList extends ArrayList<String> {

	public SignList(int size) {
		super(size);
	}

	private static final long serialVersionUID = 2621444383666420433L;

	public String toString() {
		return this.get(0) + this.get(1) + this.get(2);
	}

}
