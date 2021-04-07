package com.bit.semiproject;

import java.awt.Color;
import java.awt.Font;
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


public class LierClient2 extends Frame implements ActionListener, MouseListener, WindowListener, KeyListener{

   static BufferedWriter bw=null;
   static ArrayList<String> list2=new ArrayList();
   static BufferedReader br;
   static MainGame gamestate;


   static TextField nameBox;
   static JButton startbtn;
   static byte[] nameArr;
   
   public LierClient2() {
         
         GridLayout grid=new GridLayout(3,1);
         setLayout(grid);
         
         Panel p0=new Panel(); //��� �ִ� �г� ���� Layout�� ����
         Panel p1=new Panel(); //���� �̸� ����ִ� �г�
         Panel p2=new Panel(); //�г��� ���� Textfield, ���� ��ư �ִ� �г�
         
         String title="���̾� ����(ver 0.1.0)"; 
         Label laTitle=new Label(title);
         Font font=new Font(Font.SANS_SERIF,Font.BOLD,45);
         laTitle.setFont(font);
         p1.add(laTitle); //���� ����
         
         
         nameBox=new TextField(20);
         
         startbtn=new JButton("����");
         startbtn.setEnabled(false);
         nameBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
               nameArr=nameBox.getText().getBytes();

                  if(nameArr.length>=5){
                     startbtn.setEnabled(true);
                     
                  }else{
                     startbtn.setEnabled(false);
                  }
          
            }
         }); //textfield�� �г��� �Է��ϸ� ���� ��ư Ȱ��ȭ
         //�ߺ��� �˻� �ʿ�
         
         
         
         startbtn.addMouseListener(this);
//         .addActionListener(new ActionListener() {
//            
//            @Override
//            public void actionPerformed(ActionEvent e) {
//              
//               dispose();
//               
//            }
//         }); //���� ��ư�� ������ project01 class ��ü ���� -> ���� ���� Frame ������ 
         p2.add(nameBox);
         p2.add(startbtn);
         
         
         add(p0);
         add(p1);
         add(p2);
         setBounds(100,100,1000,800);
         setVisible(true);
   }

 

   public static void main (String[] args) {
      LierClient2 me=new LierClient2();
      gamestate=new MainGame();

      String ip="192.168.0.19";
      int port=7777;

      Socket sock=null;
      InputStream is=null;
      InputStreamReader isr=null;
      OutputStream os=null;
      OutputStreamWriter osw=null;


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
      if(la=="����") {
         //         gamestate.main(null);
         //         gamestate.tf.addActionListener(this);

        
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
      
   }

}