package com.bit.semiproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LierServer extends Thread{
	Socket sock;
	static ArrayList<BufferedWriter> list=new ArrayList<>();
	
	
	public LierServer(Socket sock) {
		this.sock=sock;
	}
	
	public void allUser(String id, String msg) throws IOException {
		for(int i=0; i<list.size(); i++) {
			BufferedWriter bw=list.get(i);	
//			System.out.println(id);
		try {
		bw.write(id+">"+msg);
		bw.newLine();
		bw.flush();
		}catch(IOException e){
			list.remove(bw);
		}
		}
	}
	
	
	@Override
	public void run() {
		InetAddress inet= sock.getInetAddress();
		String ip=inet.getHostAddress();
		System.out.println(ip+"입장");
		InputStream is=null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		
		OutputStream os=null;
		OutputStreamWriter osw=null;
		BufferedWriter bw=null;
		try {
			is=sock.getInputStream();
			os=sock.getOutputStream();
			isr=new InputStreamReader(is);
			br=new BufferedReader(isr);
			osw=new OutputStreamWriter(os);
			bw=new BufferedWriter(osw);
			list.add(bw);
			
			String msg=null;
			String id=null;
			String random=null;
			id=br.readLine();
			random=id.substring(id.length()-1,id.length());
			id=id.substring(0,id.length()-1);
			
			System.out.println(id);
			System.out.println(random);
				
			while((msg=br.readLine())!=null ) {
				allUser(id,msg);
//				if(br.readLine().equals("@bye")) {
//					break;
//					}
				}
			
			
		} catch (IOException e) {
			System.out.println(ip+"나감");
		}finally {
			try {
				if(os!=null) {os.close();}
				if(is!=null) {is.close();}
				if(sock!=null) {sock.close();}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	
	public static void main(String[] args) {
		int port = 7777;

		ServerSocket serv = null;

		try {
			serv = new ServerSocket(port);// 서버소켓 생성해서 포트 삽입
			while (true) {
				System.out.println("대기중...");
				Socket sock = serv.accept();
				LierServer server=new LierServer(sock);
				server.start();
				
			}
		} catch (IOException e) {
		} finally {
			try {
				if (serv != null) {
					serv.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
