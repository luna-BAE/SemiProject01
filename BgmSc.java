package com.bit.semiproject;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.awt.Dialog;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BgmSc extends Frame implements Runnable{

   static int cnt = 10;
   Label timer;
   
   CheckboxGroup voteSection;
   Button userVote;
   
   TextField tf;
   TextArea ta;
   Checkbox vote1;
   Checkbox vote2;
   Checkbox vote3;
   Checkbox vote4;
   Panel p1;

   static Thread thr;
   
   // "�Ϸ�" ������ - Ŭ������ ����ʰ� ���ÿ� Frame�� ����.
   public BgmSc() {

      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            dispose();
         }
      });
      
      BorderLayout mainLayout = new BorderLayout(10,0);
      setLayout(mainLayout);
      
      add(getGamePanel(), BorderLayout.EAST);
      add(getCountPanel(), BorderLayout.NORTH);
      add(getMainPanel(), BorderLayout.CENTER);
      
      setBounds(100,100,1000,800);
      setVisible(false);
      
   }
   
   // "�Ϸ�" ���� �޼ҵ�
   public static void main(String[] args) {
      
//	   BgmSc me = new BgmSc();
//      thr = new Thread(me);
//      thr.start();
      
   }
   
   // "�Ϸ�" ���� ��ŸƮ �޼ҵ�.
   public void gameStart() {
      thr = new Thread(this);
      thr.start();
   }
   
   // "�Ϸ�" ���� �ð� ������.
   @Override
   public void run() {
      while(true) {
         try {
            thr.sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         cnt--;
         timer.setText("���� �ð� : " + cnt + "��");
         if ( cnt == 0 ) { break; }
      }
      userVote.setEnabled(true); // ��ǥâ Ȱ��ȭ.
      tf.setEnabled(false); // ä��â ��Ȱ��ȭ.
      tf.setText("�Է� �ð��� ����Ǿ����ϴ�.");
   }
   
   // "�Ϸ�" ���� ���� �г� �޼ҵ�(�̹���)
   public Panel getMainPanel() {

      Panel mainP = new Panel();

      GridLayout mainlaLayout = new GridLayout(2,2);
      mainP.setLayout(mainlaLayout);

      Icon icon = new ImageIcon("userIcon.png");
      
      JButton user1 = new JButton(icon);
      JButton user2 = new JButton(icon);
      JButton user3 = new JButton(icon);
      JButton user4 = new JButton(icon);
      
      user1.setBackground(Color.white);
      user2.setBackground(Color.white);
      user3.setBackground(Color.white);
      user4.setBackground(Color.white);
      
      user1.setLabel("user1");
      user2.setLabel("user2");
      user3.setLabel("user3");
      user4.setLabel("user4");
      
      mainP.add(user1);
      mainP.add(user2);
      mainP.add(user3);
      mainP.add(user4);
      
      return mainP;
   }
   
   // "�Ϸ�" ī��Ʈ �г� �޼ҵ�(�ð� ����)
   public Panel getCountPanel() {

      Panel countP = new Panel();

      GridLayout time = new GridLayout(1,2);
      countP.setLayout(time);
      
      timer = new Label("���� �ð� : " +cnt + "��");
      countP.add(timer);
      
      return countP;
   }
   
   // ���� �г� �޼ҵ�(��ǥ, ä��)
   public Panel getGamePanel() {
      
      Panel gameP = new Panel();
      GridLayout gameLayout = new GridLayout(2,1);
      gameP.setLayout(gameLayout);
      
      voteSection = new CheckboxGroup();
      vote1 = new Checkbox("user1", false, voteSection);
      vote2 = new Checkbox("user2", false, voteSection);
      vote3 = new Checkbox("user3", false, voteSection);
      vote4 = new Checkbox("user4", false, voteSection);      
            
      Panel vote = new Panel();
      GridLayout voteLayout = new GridLayout(6,1);
      vote.setLayout(voteLayout);
      
      Label explain = new Label("����� �����ϴ� ���̾��?");
      userVote = new Button("��ǥ");
      userVote.setEnabled(false);
      userVote.addActionListener(new ActionListener() {
         @Override 
         public void actionPerformed(ActionEvent e) {
            
            userVote.setEnabled(false); // ��ǥ �� ��ư ��Ȱ��ȭ
            String userVoteResult = voteSection.getSelectedCheckbox().getLabel(); // üũ�ڽ��׷쿡�� üũ�� �׸��� ���� result�� ����.
            
            // ��� ��� Ȯ�ο� ���� ����.
            String ip = "192.168.35.53";
            int port = 7777;
            SocketAddress endpoint = null;
            endpoint = new InetSocketAddress(ip, port);
            
            Socket sock = new Socket();
            
            OutputStream os = null;
            OutputStreamWriter osw = null;
            BufferedWriter bw = null;
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            
            try {
               sock.connect(endpoint);
               os = sock.getOutputStream();
               osw = new OutputStreamWriter(os);
               bw = new BufferedWriter(osw);
               
               is = sock.getInputStream();
               isr = new InputStreamReader(is);
               br = new BufferedReader(isr);
               
               bw.write(userVoteResult);
               bw.flush();
               
               String voteResultToServer = br.readLine();
               System.out.println(voteResultToServer); // �ޱ� Ȯ�ο�.
               
               if ( voteResultToServer.equals("win") ) { // Ŭ���̾�Ʈ�� �������� ��� ����Ǵ� �ڷᱸ�� ���¿� ���� �ٱ� �� ����.
                  liarWinResult();
               } else {
                  liarLoseResult();
               }
               
               
            } catch (IOException e1) {
               e1.printStackTrace();
            } finally {
               try {
                  if ( isr != null ) { isr.close(); };
                  if ( is != null ) { is.close(); };
                  if ( osw != null ) { osw.close(); };
                  if ( os != null ) { os.close(); };
                  if ( sock != null ) { sock.close(); };
               } catch (IOException e1) {
                  e1.printStackTrace();
               }
            } // Ŭ���̾�Ʈ�� ������ ��ǥ ����� ����.
            
         } // �������� ��ǥ ����� ���� ����� ����.
      });
      
      vote.add(explain);
      vote.add(vote1);
      vote.add(vote2);
      vote.add(vote3);
      vote.add(vote4);
      vote.add(userVote);
      
       BorderLayout border=new BorderLayout();
         
       p1=new Panel();
       p1.setLayout(border);
         
       tf=new TextField();
       ta=new TextArea(30,50);
       p1.add(tf, border.SOUTH);
       p1.add(ta, border.CENTER);
      
      gameP.add(vote);
      gameP.add(p1);
      return gameP;
   }
   
   // "�Ϸ�" ���̾ �̱� ��� ��� ���̾�α׸� Ŭ���̾�Ʈ���� ����.
   public void liarWinResult() {
      
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            dispose();
         }
      });
      
      setEnabled(false); // ���� ������ ��Ȱ��ȭ.
      
      Dialog result = new Dialog(this,"���");
      
      BorderLayout resultLayout = new BorderLayout(2,1);
      result.setLayout(resultLayout);
      
      Button confirm = new Button("Ȯ��");
      confirm.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });
      
      Label LiarLose = new Label("���̾ �̰���ϴ�.");

      result.add(LiarLose, BorderLayout.CENTER);
      result.add(confirm, BorderLayout.SOUTH);
      result.setBounds(200,250,500,400);
      result.setVisible(true);
      
   }
   
   // "�Ϸ�" ���̾ �� ��� ��� ���̾�α׸� Ŭ���̾�Ʈ���� ����.
   public void liarLoseResult() {
      
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            dispose();
         }
      });
      
      setEnabled(false); // ���� ������ ��Ȱ��ȭ.
      
      Dialog result = new Dialog(this,"���");
      
      BorderLayout resultLayout = new BorderLayout(2,1);
      result.setLayout(resultLayout);
      
      Button confirm = new Button("Ȯ��");
      confirm.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });
      
      Label LiarLose = new Label("���̾ �����ϴ�.");
      
      result.add(LiarLose, BorderLayout.CENTER);
      result.add(confirm, BorderLayout.SOUTH);
      result.setBounds(200,250,500,400);
      result.setVisible(true);
      
   }

}