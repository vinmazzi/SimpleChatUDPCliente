package fiap.sd.udp.simplechatudp.util;

import fiap.sd.udp.simplechatudp.receiver.RunReceiver;
import fiap.sd.udp.simplechatudp.sender.RunSender;
import fiap.sd.udp.simplechatudp.sender.Sender;

public class Cliente {
	
	public static void main(String args[]){
		Console console = Console.getConsole();
		console.println("SENDER");
		console.println("Configurando uma nova conexão....");
		RunReceiver r = new RunReceiver();
		String host = console.readLine("Informe o endereço do servidor > ");
		int port = Integer.parseInt(console.readLine("Informe a porta de conexão com o servidor > "));
		Sender rSend = new Sender(host,port);
		boolean con = rSend.fecharConexao();
		if(con){
			rSend.run();			
		}
		

	}

}
