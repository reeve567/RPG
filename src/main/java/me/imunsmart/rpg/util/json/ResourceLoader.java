package me.imunsmart.rpg.util.json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class ResourceLoader {

	private Info npcs = new Info();

	public ResourceLoader() {
		try {
			Gson gson = new Gson();
			URL location = ResourceLoader.class.getProtectionDomain().getCodeSource().getLocation();
			JsonReader reader;
			reader = new JsonReader(new FileReader(new File(location.getFile() + "json", "test.json")));
			npcs = gson.fromJson(reader, Info.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public Info getNpcsInfo() {
		return npcs;
	}


}
