package me.imunsmart.rpg.util.json;

public class SubInfo {
	String name;
	String[] messages;
	String[] quests;
	double[][] locations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[][] getLocations() {
		return locations;
	}

	public void setLocations(double[][] locations) {
		this.locations = locations;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public String[] getQuests() {
		return quests;
	}

	public void setQuests(String[] quests) {
		this.quests = quests;
	}
}
