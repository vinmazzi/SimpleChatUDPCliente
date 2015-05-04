package fiap.sd.udp.simplechatudp.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import fiap.sd.udp.simplechatudp.receiver.Receiver;
import fiap.sd.udp.simplechatudp.receiver.RunReceiver;
import fiap.sd.udp.simplechatudp.sender.RunSender;
import fiap.sd.udp.simplechatudp.sender.Sender;

public class Cliente {
	
	static String splitter = "%%%Cod3%%%";

	
	public static boolean fecharConexao(int port, String addr){
		String message = "Connect123456CodeConnection"+splitter;
		RunReceiver rRec = new RunReceiver();
		Sender send = new Sender(addr,port);
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
	
	public static void rodarServer(int lPort, String dest, int dPort){
		Receiver rc = new Receiver(lPort);
		Sender send = new Sender(dest,dPort);
		DatagramSocket dS;
		dS = rc.getListenSocket();
		while(dS != null){
			String msg = rc.runServer();
			String tmp[] = msg.split(splitter);
			String code = tmp[0];
			String data = null;
			if(tmp.length > 1)
				data = tmp[1];
			switch(code){
				case "1234UsernameQuest4321":
					send.runServer("Informe seu usuário", code);
					break;
				case "1234MenuSelect4321":
					System.out.println(data);
					send.runServer("Escolha uma opção", code);
				default:
					break;
			
			}
		}	
	}
	
	public static void main(String args[]){
		Console console = Console.getConsole();
		console.println("Configurando uma nova conexão....");
		String host = console.readLine("Informe o endereço do servidor > ");
		int port = Integer.parseInt(console.readLine("Informe a porta de conexão com o servidor > "));
		boolean con = fecharConexao(port, host);
		if(con){
			rodarServer(3322,host,port);
		}
		

	}

}
