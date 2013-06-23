package ru.olamedia.launcher;

import ru.olamedia.modloader.ModLoader;


public class Launcher {
	private static ModLoader modLoader;
	public static void main(String[] args) {
		modLoader = new ModLoader();
		modLoader.loadMods();
	}
}
