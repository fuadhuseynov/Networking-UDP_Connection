/**
 * Created by Fuad Huseynov on 17.11.2016.
 *
 * Basic implementation of UDP network connection between client and server
 * Server listens on the given port (inputs are given from the command line)
 * and accepts one client at a time.
 * Client connects to the server with the given hostname and port number, enters
 * the message to be sent to the server.
 * Server replies with client's IP address and port number
 */

public class UDP {

    public static void main(String[] args) {
        if (args.length == 1) {     //Server is called (portNumber)
            int portNumber = Integer.parseInt(args[0]);
            UDPServer server = new UDPServer(portNumber);
        }
        else if (args.length == 2) {    //Client is called  (hostname, portnumber)
            int portNumber = Integer.parseInt(args[1]);
            UDPClient client = new UDPClient(args[0], portNumber);
        }
    }

}
