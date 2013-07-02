package ru.olamedia.launcher;

public class Launcher {
	// performance settings.

	static {
		//System.setProperty("sun.java2d.transaccel", "True");
		// System.setProperty("sun.java2d.trace", "timestamp,log,count");
		//System.setProperty("sun.java2d.opengl", "True");
		//System.setProperty("sun.java2d.noddraw", "True");
		//System.setProperty("sun.java2d.d3d", "True");
		//System.setProperty("sun.java2d.ddforcevram", "True");
		// Avoid erasing the background to avoid flickering
		// for some OpenGL rendering backends
		//System.setProperty("sun.awt.noerasebackground", "true");
	}

	public static void main(String[] args) {
		Preloader preloader = new Preloader();
		preloader.setVisible(true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		preloader.loadMods();
	}
}
