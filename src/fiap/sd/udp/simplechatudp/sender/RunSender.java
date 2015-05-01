package fiap.sd.udp.simplechatudp.sender;

import fiap.sd.udp.simplechatudp.receiver.RunReceiver;
import fiap.sd.udp.simplechatudp.util.Console;

/**
 * Coloca no ar o lado "Falador" do chat UDP simples
 * @author fm
 *
 */
public class RunSender {
	
	public static void main(String[] args) {
		Console console = Console.getConsole();
		console.println("SENDER");
		console.println("Configurando uma nova conexão....");
		String host = console.readLine("Informe o endereço do servidor > ");
		int port = Integer.parseInt(console.readLine("Informe a porta de conexão com o servidor > "));
		Sender sender = new Sender(host,port);
		sender.run();
	}

}
