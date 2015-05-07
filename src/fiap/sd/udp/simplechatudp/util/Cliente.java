package fiap.sd.udp.simplechatudp.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import fiap.sd.udp.simplachatudp.beans.Servidor;
import fiap.sd.udp.simplechatudp.receiver.Receiver;
import fiap.sd.udp.simplechatudp.receiver.RunReceiver;
import fiap.sd.udp.simplechatudp.sender.RunSender;
import fiap.sd.udp.simplechatudp.sender.Sender;
import fiap.sd.udp.simplachatudp.beans.ClienteLocal;

public class Cliente {
	
	static String splitter = "%%%Cod3%%%";

	
	public static boolean fecharConexao(ClienteLocal cL){
		String message = "Connect123456CodeConnection"+splitter;
		RunReceiver rRec = new RunReceiver();
		Sender send = new Sender(cL);
		send.sendMessage(message);
		boolean conexao = rRec.receberConexao();
		if(!conexao){
			System.out.println("Aguardando conexão com o servidor....");
		}else{
			System.out.println("Conexão Fechada!");
			send.setSpeakSocket(null);
		}
		return conexao;
	}
	
	public static void rodarServer(int lPort, ClienteLocal cL){
		Receiver rc = new Receiver(lPort);
		Sender send = new Sender(cL);
		DatagramSocket dS;
		dS = rc.getListenSocket();
		while(dS != null){
			String msg = rc.runServer();
			String tmp[] = msg.split(splitter);
			String code = tmp[0];
			String data = null;
			String sala = null;
			String usuario = null;
			if(tmp.length > 1){
				if(code.equals("1234InSalaMsg4321")){
					System.out.println("cai no code equals!!!!!");
					data = tmp[2];
					System.out.println("Este é o conteudo do data " + data);
					usuario = tmp[1];
				}else{
					data = tmp[1];
				}
			}
			switch(code){
				case "1234UsernameQuest4321":
					send.runServer("Informe seu usuário", code);
					break;
				case "1234MenuSelect4321":
					System.out.println(data);
					send.runServer("Escolha uma opção", code);
					break;
				case "1234SalaIndisponivel4321":
					System.out.println(data);
					send.runServer("Criar uma nova sala ( S: sim | N: não ): ", code);
					break;
				case "1234CriaNovaSalaNome4321":
					System.out.println(data);
					send.runServer("Informe o nome para a nova sala", code);
					break;
				case "1234Message4321":
					System.out.println(data);
					break;
				case "1234InSala4321":
					send.runServer(data, code);
					break;
				case "1234InSalaMsg4321":
					System.out.println(usuario + " diz > " + data);
					code = "1234InSala4321";
					send.runServer(data, code);
					break;
				default:
					break;
			
			}
		}	
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
			rodarServer(3322,cL);
		}
		

	}

}
