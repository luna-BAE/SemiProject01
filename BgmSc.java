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
   
   // "완료" 생성자 - 클래스가 실행됨과 동시에 Frame이 실행.
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
   
   // "완료" 메인 메소드
   public static void main(String[] args) {
      
//	   BgmSc me = new BgmSc();
//      thr = new Thread(me);
//      thr.start();
      
   }
   
   // "완료" 게임 스타트 메소드.
   public void gameStart() {
      thr = new Thread(this);
      thr.start();
   }
   
   // "완료" 제한 시간 쓰레드.
   @Override
   public void run() {
      while(true) {
         try {
            thr.sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         cnt--;
         timer.setText("제한 시간 : " + cnt + "초");
         if ( cnt == 0 ) { break; }
      }
      userVote.setEnabled(true); // 투표창 활성화.
      tf.setEnabled(false); // 채팅창 비활성화.
      tf.setText("입력 시간이 종료되었습니다.");
   }
   
   // "완료" 게임 메인 패널 메소드(이미지)
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
   
   // "완료" 카운트 패널 메소드(시간 제한)
   public Panel getCountPanel() {

      Panel countP = new Panel();

      GridLayout time = new GridLayout(1,2);
      countP.setLayout(time);
      
      timer = new Label("제한 시간 : " +cnt + "초");
      countP.add(timer);
      
      return countP;
   }
   
   // 게임 패널 메소드(투표, 채팅)
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
      
      Label explain = new Label("당신이 생각하는 라이어는?");
      userVote = new Button("투표");
      userVote.setEnabled(false);
      userVote.addActionListener(new ActionListener() {
         @Override 
         public void actionPerformed(ActionEvent e) {
            
            userVote.setEnabled(false); // 투표 후 버튼 비활성화
            String userVoteResult = voteSection.getSelectedCheckbox().getLabel(); // 체크박스그룹에서 체크된 항목의 라벨을 result에 대입.
            
            // 통신 결과 확인용 소켓 생성.
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
               System.out.println(voteResultToServer); // 달구 확인용.
               
               if ( voteResultToServer.equals("win") ) { // 클라이언트가 접속했을 당시 저장되는 자료구조 형태에 따라 바귈 수 있음.
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
            } // 클라이언트가 서버로 투표 결과를 보냄.
            
         } // 서버에서 투표 결과를 토대로 결과를 도출.
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
   
   // "완료" 라이어가 이긴 경우 결과 다이얼로그를 클라이언트에게 전달.
   public void liarWinResult() {
      
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            dispose();
         }
      });
      
      setEnabled(false); // 메인 프레임 비활성화.
      
      Dialog result = new Dialog(this,"결과");
      
      BorderLayout resultLayout = new BorderLayout(2,1);
      result.setLayout(resultLayout);
      
      Button confirm = new Button("확인");
      confirm.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });
      
      Label LiarLose = new Label("라이어가 이겼습니다.");

      result.add(LiarLose, BorderLayout.CENTER);
      result.add(confirm, BorderLayout.SOUTH);
      result.setBounds(200,250,500,400);
      result.setVisible(true);
      
   }
   
   // "완료" 라이어가 진 경우 결과 다이얼로그를 클라이언트에게 전달.
   public void liarLoseResult() {
      
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            dispose();
         }
      });
      
      setEnabled(false); // 메인 프레임 비활성화.
      
      Dialog result = new Dialog(this,"결과");
      
      BorderLayout resultLayout = new BorderLayout(2,1);
      result.setLayout(resultLayout);
      
      Button confirm = new Button("확인");
      confirm.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });
      
      Label LiarLose = new Label("라이어가 졌습니다.");
      
      result.add(LiarLose, BorderLayout.CENTER);
      result.add(confirm, BorderLayout.SOUTH);
      result.setBounds(200,250,500,400);
      result.setVisible(true);
      
   }

}