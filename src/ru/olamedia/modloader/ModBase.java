package ru.olamedia.modloader;

abstract public class ModBase {

	private static final String name = "Mod Base";

	public ModBase(ModInfo info) {
		info.setName(name);
	}

	abstract public void register(ModInfo info) throws ModDependencyException;
}
