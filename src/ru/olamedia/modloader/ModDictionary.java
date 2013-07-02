package ru.olamedia.modloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

public class ModDictionary {
	protected HashMap<String, ModBase> mods = new HashMap<String, ModBase>();
	protected HashMap<String, ModInfo> modsInfo = new HashMap<String, ModInfo>();
	protected HashMap<String, String> uids = new HashMap<String, String>();
	protected ArrayList<String> unregistered = new ArrayList<String>();
	protected ArrayList<Object> onStart = new ArrayList<Object>();
	protected ArrayList<Method> onStartMethods = new ArrayList<Method>();

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
				System.out.println("Registered: " + k + " (" + modsInfo.get(uids.get(k)).getUID() + " at "
						+ modsInfo.get(uids.get(k)).getFilename() + ")");
				unregistered.remove(k);
			} catch (ModDependencyException e) {
				System.out.println("Still not registered: " + k);
			}
		}
	}

	public void start(JFrame frame) {
		for (int i = 0; i < onStart.size(); i++) {
			Method m = onStartMethods.get(i);
			Object obj = onStart.get(i);
			try {
				m.invoke(obj, frame);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public void onStart(Object obj, Method method) {
		onStart.add(obj);
		onStartMethods.add(method);
	}
}
