import java.io.*;
import java.net.*;

/**
 * Created by Fuad Huseynov on 17.11.2016.
 */
public class UDPClient {

    private InetAddress serverIPAddress;    //IP address of the server to connect
    private int serverPortNumber;       //port number of the server to connect
    private String userMessage;     //message typed by user to send to server
    private String messageFromServer;   //message received from server

    private byte[] sendData;
    private byte[] receiveData;

    //Constructor
    public UDPClient(String hostname, int portNumber) {
        this.sendData = new byte[1024];
        this.receiveData = new byte[1024];

        this.serverPortNumber = portNumber;
        try {
            this.serverIPAddress = InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host Exception: " + e.getMessage());
        }
        connect(serverIPAddress);
    }

    public void connect(InetAddress serverAddress) {
        //Input stream - inFromUser
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        //Create client socket
        try {
            DatagramSocket clientSocket = new DatagramSocket();

            //Get message from the user:
            System.out.println("Please enter the message to the server: ");
            userMessage = inFromUser.readLine();
            //Convert the message to byte sequence
            sendData = userMessage.getBytes();

            //Create packet to send
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverPortNumber);
            //Send the packet to the server
            clientSocket.send(sendPacket);
            System.out.println("Message is sent!");

            //Create packet to receive
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            messageFromServer = new String(receivePacket.getData());

            //Show the message to user
            System.out.println("Message from server:\n" + messageFromServer);

            //close the socket
            clientSocket.close();
        } catch (SocketException e) {
            System.out.println("Socket Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

}
