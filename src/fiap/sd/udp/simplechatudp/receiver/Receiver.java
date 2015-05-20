package fiap.sd.udp.simplechatudp.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**
 * Implementa o lado "Ouvidor" de nosso chat UDP simples
 * @author fm
 *
 */
public class Receiver {
	
	// PARA PESQUISAR: Qual o tamanho maximo do buffer?
	private static int BUFSIZE = 4096;

	private DatagramSocket listenSocket;
	
	
	public DatagramSocket getListenSocket() {
		return listenSocket;
	}

	
	public Receiver(int port) {
		try {
			listenSocket = new DatagramSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public boolean runConfirmacao() {
		byte[] buffer = new byte[BUFSIZE];
		boolean ret = false;
		while(listenSocket!=null) {
			try {
				Arrays.fill(buffer, (byte) ' ');
				DatagramPacket packet = null;
				packet = new DatagramPacket(buffer,BUFSIZE);
				listenSocket.receive(packet);
				String splitter = "%%%Cod3%%%";
				String data = new String(packet.getData()).trim();
				if(data.equals("Connect123456CodeConnection-Closed"+splitter)){
					System.out.println("Retornou true no runConfirmaçao!!!!!");
					ret = true;
					listenSocket.close();
					listenSocket = null;
				}else{
					System.out.println("Retornou False no runConfirmaçao");
					System.out.println("Dados: " + data);
					ret = false;
				}
				Thread.yield();
			} catch (IOException e) {
				e.printStackTrace();
				ret = false;
				}
		}
		return ret;
	}



	public String runServer() {
		byte[] buffer = new byte[BUFSIZE];
		String msg = null;
		try {
			Arrays.fill(buffer, (byte) ' ');
			DatagramPacket packet = new DatagramPacket(buffer,BUFSIZE);
			listenSocket.receive(packet);
			msg = new String(packet.getData());
			Thread.yield();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}

	
	public void run() {
		byte[] buffer = new byte[BUFSIZE];
		while(listenSocket!=null) {
			try {
				Arrays.fill(buffer, (byte) ' ');
				DatagramPacket packet = new DatagramPacket(buffer,BUFSIZE);
				listenSocket.receive(packet);
				System.out.println(new String(packet.getData()));
				Thread.yield();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
