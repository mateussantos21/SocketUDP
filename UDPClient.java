import java.io.*; 
import java.net.*;
import java.util.GregorianCalendar;

class UDPClient { 
	public static void main(String args[]) throws Exception 
	{ 
		try {
			String serverHostname = new String ("localhost");

			if (args.length > 0)
				serverHostname = args[0];

			BufferedReader inFromUser = 
			new BufferedReader(new InputStreamReader(System.in)); 

			DatagramSocket clientSocket = new DatagramSocket(); 

			InetAddress IPAddress = InetAddress.getByName(serverHostname);
			System.out.println ("Tentando se conectar em " + IPAddress + 
				") via porta UDP 6969");

			byte[] sendData = new byte[1024]; 
			byte[] receiveData = new byte[1024]; 

			System.out.print("Digite a mensagem: ");
			String sentence = inFromUser.readLine(); 
			sendData = sentence.getBytes();         

			System.out.println ("Enviando " + sendData.length + 
				" bytes de dados para o servidor.");
			DatagramPacket sendPacket = 
			new DatagramPacket(sendData, sendData.length, IPAddress, 6969);

			clientSocket.send(sendPacket); 

			DatagramPacket receivePacket = 
			new DatagramPacket(receiveData, receiveData.length); 

			System.out.println ("Aguardando pelo retorno do pacote");
			clientSocket.setSoTimeout(10000);

			try {

				clientSocket.receive(receivePacket); 
				String modifiedSentence = 
				new String(receivePacket.getData());

				InetAddress returnIPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				System.out.println ("Do servidor: " + returnIPAddress + ":" + port);

				long tempoFinal = 0;
				long tempoInicial = new GregorianCalendar().getTimeInMillis();

				if (IPAddress.isReachable(5000)){
					tempoFinal = new GregorianCalendar().getTimeInMillis();
					System.out.println("RTT: " + (tempoFinal - tempoInicial + "ms"));
				} else {
					System.out.println(IPAddress + " não acessível.");
				}

				System.out.println("Mensagem: " + modifiedSentence); 

			}
			catch (SocketTimeoutException ste)
			{
				System.out.println ("Tempo limite ocorreu: pacote perdido");
			}

			clientSocket.close(); 
		}
		catch (UnknownHostException ex) { 
			System.err.println(ex);
		}
		catch (IOException ex) {
			System.err.println(ex);
		}
	} 
}
