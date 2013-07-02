package ru.olamedia.modloader;

import java.io.File;
import java.lang.reflect.Method;

public class ModInfo {
	private File file;
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

	public void onStart(Object obj, Method method) {
		this.mods.onStart(obj, method);
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
