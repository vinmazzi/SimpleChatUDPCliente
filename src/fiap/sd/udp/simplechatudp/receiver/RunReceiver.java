package fiap.sd.udp.simplechatudp.receiver;

import fiap.sd.udp.simplechatudp.util.Console;

/**
 * Coloca no ar o lado "Ouvidor" do chat UDP simples
 * @author fm
 *
 */
public class RunReceiver {
	
	public boolean receberConexao(){
		int port = 4545;
//		String addr = "127.0.0.1";
		Receiver rec = new Receiver(port);
		boolean ret = rec.runConfirmacao();
		return ret;
	}
	
	public static void main(String[] args) 
	{
		Console console = Console.getConsole();
		console.println("RECEIVER");
		int port = 3321;
		Receiver rec = new Receiver(port);
		rec.run();
	}
	


}
