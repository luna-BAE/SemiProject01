package com.bit.semiproject;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.sound.midi.Receiver;

/* 채팅 클라이언트 프로그램
 * GUI 환경
 * 1. 서버접속, 사용자 정보도 함께 보내기
 * 2. 내용을 작성 후 서버에 전송
 * 3. 서버로부터 메시지 수신기능 쓰레드 구현해야 함
 * 4. 채팅 종료시 /bye 문자를 전송하면 됨.
 * */
public class ClientSun extends Frame implements ActionListener{
  
 public static void main(String[] args) {
  
  new ClientSun();
 }
 
 final static int serverPort = 7777;
 Socket client = null;
 ObjectOutputStream oos ;
 static ObjectInputStream ois;
 String userId;
 Button btn_exit,btn_send,btn_connect;
 static TextArea txt_list;
 TextField txt_ip, txt_name, txt_input;
 CardLayout cl;
 public ClientSun(){
  super("채팅 프로그램");
  
    
  cl = new CardLayout();
  setLayout(cl);
   // 접속창
  Panel connect = new Panel();
  connect.setLayout(new BorderLayout());
  connect.add("North",new Label("다중채팅접속화면",Label.CENTER));
  Panel conn_sub = new Panel();
  conn_sub.add(new Label("서버 아이피 : "));
  txt_ip = new TextField("localhost",15);
  conn_sub.add(txt_ip);
  conn_sub.add(new Label("대화명 : "));
  txt_name = new TextField("홍길동",15);
  conn_sub.add(txt_name);
  connect.add("Center",conn_sub);
  btn_connect = new Button("서버접속");
  btn_connect.addActionListener(this);
  conn_sub.add(btn_connect);
  Panel chat = new Panel();
  chat.setLayout(new BorderLayout());
  chat.add("North",new Label("채팅프로그램 ver1.0", Label.CENTER));
  txt_list = new TextArea();
  chat.add("Center",txt_list);
  
  Panel chat_sub = new Panel();
  txt_input = new TextField("",25);
  btn_exit = new Button("종료");
  btn_send = new Button("전송");
  chat_sub.add(txt_input);
  chat_sub.add(btn_exit);
  chat_sub.add(btn_send);
  txt_input.addActionListener(this);
  btn_exit.addActionListener(this);
  btn_send.addActionListener(this);
  chat.add("South",chat_sub);
  add(connect,"접속창");
  add(chat, "채팅창");
  cl.show(this, "접속창");
  setSize(300,300);
  setVisible(true);
  addWindowListener(new WindowAdapter() {
   public void windowClosing(WindowEvent e){
    System.exit(0);
   }   
  });
 }
 
 
 @Override
 public void actionPerformed(ActionEvent e) {
  try{
   Object obj = e.getSource();
   if( obj == btn_connect )
    init();
   else if( obj == btn_exit )
    System.exit(0);
   else if(obj == btn_send || obj == txt_input){
    String sendData = txt_input.getText();
    oos.writeObject(sendData);
    oos.flush();
    txt_input.setText("");
    txt_input.requestFocus();
   }    
  }catch(Exception e1){ txt_list.append(e1.getMessage()+"\n");}
 }


 private void init() throws IOException{
  String ipAddr = txt_ip.getText();
  client = new Socket(ipAddr,serverPort);
  oos = new ObjectOutputStream
       (client.getOutputStream());
  ois = new ObjectInputStream
       (client.getInputStream());
  userId = txt_name.getText();
  oos.writeObject(userId);
  oos.flush();
  ReceiveDataThread rt = new ReceiveDataThread();
  Thread t = new Thread( rt );
  t.start();
  cl.show(this, "채팅창");
  setTitle(userId + "채팅창");
  txt_input.requestFocus();
 }
 
 static class ReceiveDataThread implements Runnable{
  String recvData;
  
  @Override
  public void run() {  
   try {
    while(true){
     recvData = (String)ois.readObject();
     txt_list.append(recvData + "\n");
    }    
   }catch(Exception e){}
  }
 }
}