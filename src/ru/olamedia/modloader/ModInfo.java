package ru.olamedia.modloader;

public class ModInfo {
	private String filename;
	private String name;
	private String uid;
	private ModDictionary mods;

	private boolean isLoaded = false;

	public ModInfo(ModDictionary mods) {
		this.mods = mods;
	}

	public void addDependency(String modName, String version) throws ModDependencyException {
		if (!mods.containsName(modName)) {
			System.out.println("Depends on: " + modName);
			throw new ModDependencyException("Mod " + modName + " was not loaded yet");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUID() {
		return uid;
	}

	public void setUID(String uid) {
		this.uid = uid;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
