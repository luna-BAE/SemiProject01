package com.bit.semiproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class GameState implements WindowListener {
	static TextField tf;
	static TextArea ta;

	public GameState() {
		ta = new TextArea();

		tf = new TextField();
	}

	public static void main(String[] args) {
		Frame frame = new Frame();
		// FlowLayout fl=new FlowLayout();
		// GridLayout grid=new GridLayout(2,1);
		BorderLayout border = new BorderLayout();

		Panel p1 = new Panel();
		p1.setLayout(border);

		p1.add(tf, border.SOUTH);
		p1.add(ta, border.CENTER);

		// frame.addWindowListener();
		frame.add(p1);
		frame.setBounds(100, 100, 800, 600);
		frame.setVisible(true);

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
