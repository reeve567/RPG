package me.imunsmart.rpg.util.json;

import java.util.HashMap;
import java.util.Map;

public class Info {
	Map<String, SubInfo> info = new HashMap<>();

	public Map<String, SubInfo> getInfo() {
		return info;
	}

	public void setInfo(Map<String, SubInfo> info) {
		this.info = info;
	}
}
