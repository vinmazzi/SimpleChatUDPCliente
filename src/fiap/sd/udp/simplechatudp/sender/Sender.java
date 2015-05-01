package fiap.sd.udp.simplechatudp.sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import fiap.sd.udp.simplechatudp.receiver.Receiver;
import fiap.sd.udp.simplechatudp.receiver.RunReceiver;

/**
 * Implementa o lado "Falador" de nosso chat UDP simples
 * @author fm
 *
 */
public class Sender {
	private DatagramSocket speakSocket;
	private InetAddress destAddress;
	private int destPort;
	private static final BufferedReader console = new BufferedReader(new InputStreamReader(
			System.in));

	public Sender(String destAddr, int destPort) {
		try {
			this.speakSocket = new DatagramSocket();
			this.destAddress = InetAddress.getByName(destAddr);
			this.destPort = destPort;
			System.out.println("** Preparando para enviar mensagens para "
					+ this.destAddress + ":" + this.destPort);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	
	public boolean fecharConexao(){
		String message = "Connect123456CodeConnection";
		RunReceiver rRec = new RunReceiver();
		sendMessage(message);
		boolean conexao = rRec.receberConexao();
		if(!conexao){
			System.out.println("Aguardando conexão com o servidor....");
		}else{
			System.out.println("Conexão Fechada!");
		}
		return conexao;
	}
	
	public void run() {
		String message = null;
		while (speakSocket != null) {
			try {
				message = readMessage();
			} catch (IOException e) {
				System.out.println("Erro na entrada do teclado.");
				e.printStackTrace();
				System.exit(-1);
			}
			sendMessage(message);
		}
	}

	private String readMessage() throws IOException {
		System.out.print("Mensagem a ser enviada > ");
		return console.readLine();
	}

	private void sendMessage(String command) {
		DatagramPacket packet = new DatagramPacket(command.getBytes(),
				command.length(), this.destAddress, this.destPort);
		try {
			this.speakSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
