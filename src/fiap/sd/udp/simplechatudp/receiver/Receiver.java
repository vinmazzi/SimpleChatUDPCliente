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
	
	public Receiver(int port) {
		try {
			listenSocket = new DatagramSocket(port);
			System.out.println("** Ouvindo mensagens em "+listenSocket.getLocalSocketAddress());
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
				DatagramPacket packet = new DatagramPacket(buffer,BUFSIZE);
				listenSocket.receive(packet);
				String data = new String(packet.getData()).trim();
				if(data == "Connect123456CodeConnection-Closed"){
					ret = true;
				}else{
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
