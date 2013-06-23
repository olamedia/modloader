package ru.olamedia.modloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

public class ModLoader {
	public String cwd;
	public String modsPath;
	protected ModDictionary mods = new ModDictionary();
	private ArrayList<URL> urls = new ArrayList<URL>(); // url list is common
														// for all mods

	public ModLoader() {

	}

	public boolean loadModClass(Class<?> modClass, String filename) throws java.lang.ClassNotFoundException {
		ModInfo info = new ModInfo(mods);
		info.setUID(modClass.getName());
		info.setFilename(filename);
		try {
			Constructor<?> modConstructor = modClass.getConstructor(ModInfo.class);
			ModBase mod = (ModBase) modConstructor.newInstance(info);
			mods.add(info, mod);
			System.out.println("ADDED Mod name: " + info.getName() + " " + info.getUID());
			mods.register();
			info.setLoaded(true);
			return true;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void loadModURL(URL modURL) {
		System.out.println(modURL.toString());

	}

	public void loadModsFromPath(String path, boolean tryLoadClasses) {
		// ArrayList<URL> urls = new ArrayList<URL>();
		try {
			urls.add(new URL("file://" + new File(path).getCanonicalPath()));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<File> files = new ArrayList<File>();
		try {
			cwd = new File("").getCanonicalPath();
			if (!new File(path).isDirectory()) {
				System.out.println("ModPath: " + path + " not exists");
				return;
			}
			String mpath = new File(path).getCanonicalPath();
			// System.out.println("AppPath: " + cwd);
			System.out.println("ModPath: " + mpath);
			File[] listOfFiles = (new File(mpath)).listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					File file = listOfFiles[i];
					String name = file.getName();
					if (name.endsWith(".jar")) {
						URL modURL = new URL("jar:file://" + file.getCanonicalFile() + "!/");
						urls.add(modURL);
						URL modURL2 = new URL("file://" + file.getCanonicalFile() + "");
						urls.add(modURL2);
						files.add(file);
					}
				}
			}
			URL[] urlArray = new URL[urls.size()];
			int i = 0;
			for (URL u : urls) {
				urlArray[i] = u;
				i++;
			}
			URLClassLoader cl = URLClassLoader.newInstance(urlArray, this.getClass().getClassLoader());
			Class<?> modClass = null;
			// System.out.println(file.getCanonicalFile());
			if (tryLoadClasses) {
				int classCounter = 0;
				for (File file : files) {
					JarFile jarFile = new JarFile(file.getCanonicalFile());
					Enumeration<JarEntry> en = jarFile.entries();
					while (en.hasMoreElements()) {
						JarEntry entry = en.nextElement();
						if (entry.getName().endsWith(".class") && !entry.getName().endsWith("/package-info.class")
								&& !entry.isDirectory()) {
							// String[] a = entry.getName().split("/");
							String fname = entry.getName().replace('/', '.').replaceFirst(".class", "");
							// System.out.println(fname);
							// System.out.println("Opening " + entry.getName());
							try {
								modClass = Class.forName(fname, true, cl);
								classCounter++;
								if (ModBase.class.isAssignableFrom(modClass)) {
									loadModClass(modClass, jarFile.getName() + "!/" + entry.getName());
								} else {

								}
							} catch (java.lang.UnsatisfiedLinkError e) {
								System.out.println("Error while loading " + modClass.getName() + ": not found "
										+ e.getCause());
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								System.out.println("Error while loading " + modClass.getName() + ": not found "
										+ e.getCause());
								e.printStackTrace();
							}
						}
					}
					System.out.println("Found " + classCounter + " class(es) in " + file.getName());
				}
			} else {
				for (File file : files) {
					System.out.println(file.getName());
				}
			}
		} catch (ZipException e) {
			System.out.println("Not a zip?");
			// e.printStackTrace();
			// not a zip or something else
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMods() {
		try {
			cwd = new java.io.File("").getCanonicalPath();
			loadModsFromPath("lib", false); // do not load, because some
											// libraries have a lot of external
											// dependencies
			loadModsFromPath("coremods", true);
			loadModsFromPath("mods", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * URLClassLoader child = new URLClassLoader (myJar.toURL(),
	 * this.getClass().getClassLoader());
	 * Class classToLoad = Class.forName ("com.MyClass", true, child);
	 * Method method = classToLoad.getDeclaredMethod ("myMethod");
	 * Object instance = classToLoad.newInstance ();
	 * Object result = method.invoke (instance);
	 */
}
