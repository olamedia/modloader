package ru.olamedia.modloader;

import java.util.ArrayList;
import java.util.HashMap;

public class ModDictionary {
	protected HashMap<String, ModBase> mods = new HashMap<String, ModBase>();
	protected HashMap<String, ModInfo> modsInfo = new HashMap<String, ModInfo>();
	protected HashMap<String, String> uids = new HashMap<String, String>();
	protected ArrayList<String> unregistered = new ArrayList<String>();

	public void add(ModInfo info, ModBase mod) {
		mods.put(info.getUID(), mod);
		modsInfo.put(info.getUID(), info);
		uids.put(info.getName(), info.getUID());
		unregistered.add(info.getName());
	}

	public boolean containsName(String modName) {
		if (uids.containsKey(modName)) {
			return true;
		}
		return false;
	}

	public void register() {
		@SuppressWarnings("unchecked")
		ArrayList<String> un = (ArrayList<String>) unregistered.clone();
		for (String k : un) {
			try {
				ModBase mod = mods.get(uids.get(k));
				ModInfo info = modsInfo.get(uids.get(k));
				mod.register(info);
				System.out.println("Registered: " + k + " (" + modsInfo.get(uids.get(k)).getUID() + " at " + modsInfo.get(uids.get(k)).getFilename() + ")");
				unregistered.remove(k);
			} catch (ModDependencyException e) {
				System.out.println("Still not registered: " + k);
			}
		}
	}
}
