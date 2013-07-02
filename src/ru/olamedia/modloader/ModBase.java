package ru.olamedia.modloader;

abstract public class ModBase {

	private static final String name = "Mod Base";

	/**
	 * Set some basic static information: module name, id, description etc
	 * 
	 * @param info
	 */
	public ModBase(ModInfo info) {
		info.setName(name);
	}

	/**
	 * Proceed to initialize:
	 * 1. register dependencies to other mods via info.addDependency 
	 * (ModDependencyException will be thrown and captured automagically)
	 * ...
	 * load configuration, register with other mods etc
	 * 
	 * @param info
	 * @throws ModDependencyException
	 */
	abstract public void register(ModInfo info) throws ModDependencyException;
}
