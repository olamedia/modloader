package ru.olamedia.modloader;

public class MyMod extends ModBase {
	private static final String name = "My Mod";

	public MyMod(ModInfo info) {
		super(info);
		info.setName(name);
	}

	@Override
	public void register(ModInfo info) {
		// TODO Auto-generated method stub
		
	}
}
