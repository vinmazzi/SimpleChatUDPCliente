package fiap.sd.udp.simplechatudp.sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import fiap.sd.udp.simplachatudp.beans.ClienteLocal;
import fiap.sd.udp.simplachatudp.beans.Sala;
import fiap.sd.udp.simplachatudp.beans.Servidor;
import fiap.sd.udp.simplachatudp.beans.Usuario;
import fiap.sd.udp.simplechatudp.receiver.Receiver;
import fiap.sd.udp.simplechatudp.receiver.RunReceiver;

/**
 * Implementa o lado "Falador" de nosso chat UDP simples
 * @author fm
 *
 */
public class Sender {
	private InetAddress destAddress;
	static String splitter = "%%%Cod3%%%";
	private int destPort;
	private static final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	private DatagramSocket speakSocket;
	ClienteLocal cL;
	
	public void setSpeakSocket(DatagramSocket sSocket){
		
		this.speakSocket = sSocket;
	}

	public DatagramSocket getSpeakSocket() {
		return speakSocket;
	}

	public Sender(ClienteLocal clienteLocal) {
		try {
			this.cL = clienteLocal;
			this.speakSocket = new DatagramSocket();
			Servidor server = clienteLocal.getServidor();
			this.destAddress = InetAddress.getByName(server.getIp());
			this.destPort = server.getPort();
			System.out.println("** Preparando para enviar mensagens para "
					+ this.destAddress + ":" + this.destPort);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void runServer(String title, String code) {
		String message = null;
		String read = null;
		String splitterCode = null;
		try {
			switch(code){
				case "1234UsernameQuest4321":
					read = readMessage(title);
					splitterCode = "1234UsernameAsw4321" + splitter;
					Usuario user = new Usuario();
					user.setNickName(read);
					cL.setUsuario(user);
					break;
				case "1234MenuSelect4321":
					read = readMessage(title);
					splitterCode = "1234MenuSelectAsw4321" + splitter;
					break;
				case "1234SalaIndisponivel4321":
					read = readMessage(title);
					splitterCode = "1234SalaIndisponivelAsw4321" + splitter;
					break;
				case "1234CriaNovaSalaNome4321":
					read = readMessage(title);
					Sala sala = new Sala();
					sala.setNome(read);
					cL.setSala(sala);
					splitterCode = "1234CriaNovaSalaNomeAsw4321" + splitter;
					break;
				case "1234InSala4321":
					Sala s;
					Usuario u;
					s = cL.getSala();
					u = cL.getUsuario();
					title = s.getNome();
					read = readMessage(title);
					splitterCode = "1234InSalaAsw4321" + splitter + "5678Sala8765" + splitter + s.getNome() + splitter + u.getNickName() + splitter;
					break;
					
			}
			message = splitterCode + read;
		} catch (IOException e) {
			System.out.println("Erro na entrada do teclado.");
			e.printStackTrace();
			System.exit(-1);
		}
		sendMessage(message);
	}
	
	public void run() {
		String message = null;
		while (speakSocket != null) {
			try {
				
				message = readMessage("default");
			} catch (IOException e) {
				System.out.println("Erro na entrada do teclado.");
				e.printStackTrace();
				System.exit(-1);
			}
			sendMessage(message);
		}
	}

	private String readMessage(String title) throws IOException {
		if(title.equals("default")){
			System.out.print("\nMensagem a ser enviada > ");
		}else{
			
			System.out.print("\n" + title.trim() + " > ");
		}
		return console.readLine();
	}

	public void sendMessage(String command) {
		DatagramPacket packet = new DatagramPacket(command.getBytes(),
				command.length(), this.destAddress, this.destPort);
		try {
			this.speakSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
