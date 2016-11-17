import java.io.*;
import java.net.*;

/**
 * Created by Fuad Huseynov on 17.11.2016.
 */
public class UDPServer {

    InetAddress clientIPAddress;    //Saves connected client's IP address
    int clientPort;     //Saves connected client's port number
    String receivedSentence;    //Message received from the client
    String sendSentence;        //Message to be sent to the client by server

    byte[] receivedData;   //bytes from client
    byte[] sendData;       //bytes from server

    //Constructor
    public UDPServer(int serverPortNumber) {
        //Initialize
        clientIPAddress = null;
        clientPort = 0;
        receivedSentence = "";
        sendSentence = "";

        receivedData = new byte[1024];
        sendData = new byte[1024];

        startListening(serverPortNumber);
    }

    //Method UDPServer.startListening(int serverPortNumber)
    //Starts listening on the given port
    //Gets message from client
    //Responds to the client with client's IP address and port number
    public void startListening(int serverPortNumber) {
        try {
            //Create datagram socket at port given by user
            DatagramSocket datagramSocket = new DatagramSocket(serverPortNumber);
            System.out.println("Listening on " + serverPortNumber + "\n");

            while(true) {
                //Create space in the memory for received packet
                DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);

                //Receive packet
                datagramSocket.receive(receivePacket);
                //Get the IP address of the sender
                clientIPAddress = receivePacket.getAddress();
                //Get the port number of the sender
                clientPort = receivePacket.getPort();

                //Print information about connected client
                System.out.println("Client connected!\nIP Address: " + clientIPAddress + "\nPort: " + clientPort);

                //Get the message from the client and print it
                receivedSentence = new String(receivePacket.getData());
                System.out.println("Message from client: " + receivedSentence);

                //Message to send to the client
                sendSentence = "Your IP Address: " + clientIPAddress + "\nYour Port Number: " + serverPortNumber;
                //"Pack" the String object as a byte packet
                sendData = sendSentence.getBytes();

                //Create a packet to send to the client
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, clientPort);
                //Send the packet
                datagramSocket.send(sendPacket);
            }
        } catch (SocketException e) {
            System.out.println("Socket Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

}
