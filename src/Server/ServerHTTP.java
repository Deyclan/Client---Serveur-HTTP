package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Brandon on 24/05/2017.
 */
public class ServerHTTP implements Runnable {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    int port = 1026;

    public ServerHTTP(){
        /*
        byte[] portByte = new byte[100];
        System.in.read(portByte);
        this.port = portByte;
        */

        System.out.println("Server started.");
        System.out.println("Initializing server...");

        try{
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            System.out.println("Server stopped, unable to launch server");
            e.printStackTrace();
        }

        System.out.println("Server initialized !");
    }

    @Override
    public void run() {
        System.out.println("Server is now running");

        try{
            while (!serverSocket.isClosed()){
                clientSocket = serverSocket.accept();
                Communication enteringCommunication = new Communication(clientSocket);
                new Thread(enteringCommunication).start();
            }
        }catch (IOException e){
            System.out.println("Error, unable to start communication");
            e.printStackTrace();
        }
    }
}
