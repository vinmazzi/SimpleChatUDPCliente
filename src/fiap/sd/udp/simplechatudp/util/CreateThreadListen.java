package fiap.sd.udp.simplechatudp.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import fiap.sd.udp.simplachatudp.beans.Usuario;



public class CreateThreadListen extends Thread{


	private String msg;
	private Usuario u;
	private Console c;
	private DatagramSocket ds;
	
	
	
	
	public DatagramSocket getDs() {
		return ds;
	}

	public void setDs(DatagramSocket ds) {
		this.ds = ds;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Usuario getU() {
		return u;
	}

	public void setU(Usuario u) {
		this.u = u;
	}

	public Console getC() {
		return c;
	}

	public void setC(Console c) {
		this.c = c;
	}
	
	public CreateThreadListen(Console c, DatagramSocket ds){
		
		setC(c);
		setDs(ds);
	}
	
	@Override
	public void run() {
				
		String user = null;
		String msgem = null;
		String splitter = "%%%Cod3%%%";
		String[] tmp = null;
		String msg = null;
		String data = null;
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			byte[] buffer = new byte[5000];
			DatagramPacket packet = new DatagramPacket(buffer,5000);
			try {
				ds.receive(packet);
				data = new String(packet.getData()).trim();
				tmp = data.split(splitter);
				msg = tmp[2];
				user = tmp[1];
				if(!msg.isEmpty())
					c.println("\n\n" + user + " diz > " + msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			buffer = null;
			packet = null;
		}
		
	}
}
