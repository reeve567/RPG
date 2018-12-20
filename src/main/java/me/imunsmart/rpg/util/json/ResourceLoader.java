package me.imunsmart.rpg.util.json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class ResourceLoader {

	public static void main(String[] args) throws IOException {
		Gson gson = new Gson();
		URL location = ResourceLoader.class.getProtectionDomain().getCodeSource().getLocation();
		JsonReader reader = new JsonReader(new FileReader(new File(location.getFile() + "json", "test.json")));
		Info info = gson.fromJson(reader, Info.class);



	}

}
