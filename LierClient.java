package com.bit.semiproject;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;

import com.bit.LiarGame.LierServer;

public class LierClient extends Frame implements ActionListener, MouseListener, WindowListener, KeyListener{

	static BufferedWriter bw=null;
	static OutputStreamWriter osw=null;
	//static ArrayList<String> list2=new ArrayList();
	static BufferedReader br;
	static BgmSc gamestate;

	public LierClient() {
		GridLayout grid=new GridLayout(5,1);
		setLayout(grid);

		Panel p1=new Panel();
		Panel p2=new Panel();
		Panel p3=new Panel();
		Panel p4=new Panel();
		Panel p5=new Panel();
		Label la1=new Label("ID :");
		TextField tf=new TextField(20);
		//nickname=tf.getText();
		
		
		JButton btn1=new JButton("입장");
		btn1.setBackground(Color.white);
		
		
				//.size()==4)
		
		btn1.addMouseListener(this);
	
		tf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id=((TextField)e.getSource()).getText();
				id+=(int)(Math.random()*4);
				try {
					bw.write(id);
					bw.newLine();
					bw.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
//				try {
//					bw.write(nickname);
//					bw.newLine();
//					bw.flush();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				((TextField)e.getSource()).setText("");
//				
//			}
//	});
		
//		tf.addActionListener(this);
		p4.add(la1);
		p4.add(tf);
		p4.add(btn1);


		add(p1);
		add(p2);
		add(p3);
		add(p4);
		add(p5);
		addWindowListener(this);
		setBounds(100,100,600,400);
		setVisible(true);
	}

	//	public void gamestate() {
	//		
	//		
	//		setBounds(100,100,800,600);
	//		setVisible(true);
	//	}

	public static void main	(String[] args) {
		
		LierClient me=new LierClient();
		gamestate=new BgmSc();
		

		String ip="192.168.0.19";
		int port=7777;

		Socket sock=null;
		InputStream is=null;
		InputStreamReader isr=null;
		OutputStream os=null;
		


		try {
			sock=new Socket(ip,port);
			os=sock.getOutputStream();
			is=sock.getInputStream();
			osw=new OutputStreamWriter(os);
			isr=new InputStreamReader(is);
			bw=new BufferedWriter(osw);
			br=new BufferedReader(isr);
			while(true){
				try {

					gamestate.ta.append(br.readLine()+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}finally {
			try {

				if(sock!=null) {sock.close();}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String msg=((TextField)e.getSource()).getText();
		try {
			bw.write(msg);
			bw.newLine();
			bw.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		((TextField)e.getSource()).setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton bt=((JButton)e.getSource());
		String la=bt.getLabel();
		if(la=="입장") {
			//			gamestate.main(null);
			//			gamestate.tf.addActionListener(this);

			
			gamestate.setVisible(true);
			gamestate.tf.addActionListener(this);
			gamestate.gameStart();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String msg=((TextField)e.getSource()).getText();
		try {
			bw.write(msg);
			bw.newLine();
			bw.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		((TextField)e.getSource()).setText("");
	}

}
