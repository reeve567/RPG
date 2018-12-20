package me.imunsmart.rpg.util.json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class ResourceLoader {

	public static Info npcs;

	public static void main(String[] args) {
		
	}

	public ResourceLoader() {
		try {
			Gson gson = new Gson();
			JsonReader reader;
			reader = new JsonReader(new FileReader(new File("json", "npcs.json")));
			npcs = gson.fromJson(reader, Info.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
