package ru.olamedia.modloader;

public interface IModLoadListener {
	public void onLibraryLoad(ModInfo info);
	public void onModLoad(ModInfo info);
}
