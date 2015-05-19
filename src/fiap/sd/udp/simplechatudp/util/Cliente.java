package fiap.sd.udp.simplechatudp.util;

import java.io.IOException;
import java.net.DatagramSocket;

import fiap.sd.udp.simplachatudp.beans.Servidor;
import fiap.sd.udp.simplechatudp.receiver.Receiver;
import fiap.sd.udp.simplechatudp.receiver.RunReceiver;
import fiap.sd.udp.simplechatudp.sender.Sender;
import fiap.sd.udp.simplachatudp.beans.ClienteLocal;


public class Cliente {

	static String splitter = "%%%Cod3%%%";


	public static boolean fecharConexao(ClienteLocal cL) {
		String message = "Connect123456CodeConnection" + splitter;
		RunReceiver rRec = new RunReceiver();
		Sender send = new Sender(cL);
		send.sendMessage(message);
		boolean conexao = rRec.receberConexao();
		System.out.println("Estou no receber conexao");

		if (!conexao) {
			System.out.println("Aguardando conexão com o servidor....");
		} else {
			System.out.println("Conexão Fechada!");
			send.setSpeakSocket(null);
		}
		return conexao;
	}

	public static void rodarServer(int lPort, ClienteLocal cL, Console c) {
		Receiver rc = new Receiver(lPort);
		Sender send = new Sender(cL);
		DatagramSocket dS;
		dS = rc.getListenSocket();
		final Console console = c;
		boolean threadStatusListen = false;
		boolean threadStatusSend = false;

		while (dS != null) {
			String msg = rc.runServer();
			String tmp[] = msg.split(splitter);
			String code = tmp[0];
			String data = null;
			String sala = null;
			String usuario = null;
			Thread thread = null;
			if (tmp.length > 1) {
				if (code.equals("1234InSalaMsg4321")) {
					data = tmp[2];
					usuario = tmp[1];
				} else {
					data = tmp[1];
				}
			}
			switch (code) {
				case "1234UsernameQuest4321":
					send.runServer("Informe seu usuário", code);
					break;
				case "1234MenuSelect4321":
					System.out.println(data);
					send.runServer("Escolha uma opção", code);
					break;
				case "1234SalaIndisponivel4321":
					System.out.println(data);
					send.runServer("Criar uma nova sala ( S: sim | N: não ) ", code);
					break;
				case "1234CriaNovaSalaNome4321":
					System.out.println(data);
					send.runServer("Informe o nome para a nova sala", code);
					break;
				case "1234Message4321":
					console.println(data);
					break;
				case "1234InicioListaSalas4321":
					console.println("Essas são as salas disponiveis: ");
					break;
				case "1234Sala4321":
					System.out.println(data);
					break;
				case "1234FimListaDeSalas":
					send.runServer("Informe o nome da sala para entrar ", code);
					break;
				case "1234InSalaMsg4321":
					System.out.print(cL.getUsuario().getNickName() + " > " + data.trim());
					try {
						String messageTmp = send.readMessage(cL.getSala().getNome());
						String message = "1234InSalaAsw4321" + splitter + "5678Sala8765" + splitter + cL.getSala().getNome() + splitter + cL.getUsuario().getNickName() + splitter + messageTmp;
						send.sendMessage(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case "1234InSala4321":
					boolean on = true;
					//while (on) {
						try {
							String messageTmp = send.readMessage(cL.getSala().getNome());
							String message = "1234InSalaAsw4321" + splitter + "5678Sala8765" + splitter + cL.getSala().getNome() + splitter + cL.getUsuario().getNickName() + splitter + messageTmp;
							send.sendMessage(message);
						} catch (IOException e) {
							e.printStackTrace();
						}

					//}
					break;

				default:
					try {
						String messageTmp = send.readMessage(cL.getSala().getNome());
						String message = "1234InSalaAsw4321" + splitter + "5678Sala8765" + splitter + cL.getSala().getNome() + splitter + cL.getUsuario().getNickName() + splitter + messageTmp;
						send.sendMessage(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;

			}
			code = "1234InSala4321";
		}
		System.out.println(dS);
	}

	public static void main(String args[]){
		ClienteLocal cL = new ClienteLocal();
		Console console = Console.getConsole();
		console.println("Configurando uma nova conexão....");
		String host = console.readLine("Informe o endereço do servidor > ");
		int port = Integer.parseInt(console.readLine("Informe a porta de conexão com o servidor > "));
		Servidor server = new Servidor();
		server.setIp(host);
		server.setPort(port);
		cL.setServidor(server);
		boolean con = fecharConexao(cL);
		if(con){
			rodarServer(3322,cL,console);
		}
		

	}

}
