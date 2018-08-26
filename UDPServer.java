import java.io.*; 
import java.net.*; 

class UDPServer { 
    public static void main(String args[]) throws Exception 
    { 
        try
        { 
            DatagramSocket serverSocket = new DatagramSocket(6969);

            byte[] receiveData = new byte[1024]; 
            byte[] sendData  = new byte[1024]; 

            while(true) 
            { 

                receiveData = new byte[1024]; 

                DatagramPacket receivePacket = 
                new DatagramPacket(receiveData, receiveData.length); 

                System.out.println ("Aguardando por um pacote");

                serverSocket.receive(receivePacket); 

                String sentence = new String(receivePacket.getData()); 

                InetAddress IPAddress = receivePacket.getAddress(); 

                int port = receivePacket.getPort(); 

                System.out.println ("De: " + IPAddress + ":" + port);
                System.out.println ("Mensagem: " + sentence);

                String capitalizedSentence = sentence.toUpperCase(); 

                sendData = capitalizedSentence.getBytes(); 

                DatagramPacket sendPacket = 
                new DatagramPacket(sendData, sendData.length, IPAddress, 
                 port); 

                serverSocket.send(sendPacket); 

            } 

        }
        catch (SocketException ex) {
            System.out.println("Porta UDP 6969 está ocupada.");
            System.exit(1);
        }

    } 
}  
