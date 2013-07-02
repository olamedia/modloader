package ru.olamedia.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import ru.olamedia.modloader.IModLoadListener;
import ru.olamedia.modloader.ModInfo;
import ru.olamedia.modloader.ModLoader;

public class Preloader extends JFrame implements IModLoadListener {
	private static ModLoader modLoader;
	private static final long serialVersionUID = -3343785819609648664L;
	private int width = 854;
	private int height = 480;
	private JLabel label = new JLabel();
	private static ArrayList<IDisposable> disposables = new ArrayList<IDisposable>();
	private JEditorPane html = new JEditorPane();
	private JTextArea textArea = new JTextArea();
	private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea, "Test");

	public Preloader() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.getHSBColor(68f/360f, 6f/100f, 25f/100f));
		final BorderLayout layout = new BorderLayout();
		setLayout(layout);
		setMinimumSize(new Dimension(320, 240));
		setSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		getContentPane().setSize(new Dimension(width, height));
		label.setHorizontalAlignment(JLabel.CENTER);
		setLocationRelativeTo(null);
		setTitle("Loading...");
		label.setText("Loading...");
		label.setForeground(Color.getHSBColor(68/360f, 11/100f, 93/100f));
		final JLabel label2 = new JLabel();
		label2.setText("Loading...");
		label2.setForeground(Color.white);
		label2.setHorizontalAlignment(JLabel.CENTER);
		layout.setVgap(10);
		//System.setOut(new PrintStream(taOutputStream));
		// layout.addLayoutComponent(label2, BorderLayout.CENTER);
		// layout.addLayoutComponent(label, BorderLayout.SOUTH);
		getContentPane().add(label);
		// getContentPane().add(label2);
		pack();
		modLoader = new ModLoader();
		modLoader.addListener(this);
	}

	public static void addDisposable(IDisposable disposable) {
		disposables.add(disposable);
	}

	public void loadMods() {
		modLoader.setManualStart(true);
		modLoader.loadMods();
		modLoader.setFrame(this);
		getContentPane().removeAll();
		pack();
		repaint();
		modLoader.start();
	}

	@Override
	public void dispose() {
		super.dispose();
		for (IDisposable disposable : disposables) {
			disposable.dispose();
		}
	}

	@Override
	public void onModLoad(ModInfo info) {
		label.setText("Loading " + info.getName() + "...");
		html.setText(html.getText() + "\r\n" + "Loading " + info.getName() + "...");
	}

	@Override
	public void onLibraryLoad(ModInfo info) {
		label.setText("Loading " + info.getName() + "...");
		html.setText(html.getText() + "\r\n" + "Loading " + info.getName() + "...");
	}

}
