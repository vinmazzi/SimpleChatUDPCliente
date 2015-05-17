package fiap.sd.udp.simplechatudp.util;

import fiap.sd.udp.simplachatudp.beans.ClienteLocal;
import fiap.sd.udp.simplachatudp.beans.Usuario;
import fiap.sd.udp.simplechatudp.sender.Sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class CreateThreadSend extends Thread{


	private ClienteLocal cl;

	public ClienteLocal getCl() {
		return cl;
	}

	public void setCl(ClienteLocal cl) {
		this.cl = cl;
	}

	public CreateThreadSend(ClienteLocal clienteLocal){
		setCl(clienteLocal);
	}

	public void runSender(){
		Sender send = new Sender(getCl());
		String msg = null;
		String splitter = "%%%Cod3%%%";
		String cMsg = null;
		while(true){
			try {
				msg = send.readMessage(cl.getSala().getNome());
				if(!msg.isEmpty()){
					cMsg = "1234InSalaAsw4321" + splitter + "5678Sala8765" + splitter + cl.getSala().getNome() + splitter + cl.getUsuario().getNickName() + splitter + msg;
					send.sendMessage(cMsg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}


	@Override
	public void run() {
		runSender();
	}
}
