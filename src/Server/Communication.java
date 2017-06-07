package Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Brandon on 24/05/2017.
 */
public class Communication implements Runnable {

    private Socket connectedSocket;

    public Communication(Socket socket) {
        this.connectedSocket = socket;
    }

    @Override
    public void run() {

        try {
            DataInputStream inputStream = new DataInputStream(connectedSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(connectedSocket.getOutputStream());

            receiveGET(inputStream, outputStream);

        } catch (IOException e) {
            System.out.println("Unable to open input or output streams");
            e.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    public boolean receiveGET(DataInputStream fromClient, DataOutputStream toClient) {
        try {

            // On reçoit la requête
            String[] inputs = fromClient.readLine().split(" ");
            String[] requestedPath = inputs[1].split("/");
            String path = "";
            for (int i = 2; i < requestedPath.length; i++) {
                if (i != 2) {
                    path += "/";
                }
                path += requestedPath[i];
            }

            // Si la requête est un GET
            if (inputs[0].equals("GET")) {

                // On vérifie si l'adresse est bien celle du serveur
                if (requestedPath.length > 1 && (requestedPath[1].equals(InetAddress.getLocalHost().getAddress())
                        || requestedPath[1].equals(InetAddress.getLoopbackAddress().getHostAddress()))) {

                    System.out.println("requête GET reçue");

                    // Construction du GET de réponse
                    String command = inputs[2] + " 200 OK";

                    Date currentDate = new Date();

                    String date = "Date: " + currentDate;
                    String connexion = inputs[3] + " " + inputs[4];
                    String serverAddress = ("Server: " + this.connectedSocket.getInetAddress());
                    String fileType = "unknow";

                    if (path.split("\\.").length > 1)
                        fileType = path.split("\\.")[1];
                    String contentType = "Content-Type: " + fileType;
                    System.out.println(fileType);

                    System.out.println("débug : " + path);
                    File file = new File(path);

                    // Si le chemin d'accès n'est pas rentrée correctement (le fichier ne peut pas être récupéré)
                    if (!file.exists()) {
                        command = inputs[2] + " 404";

                        toClient.writeBytes(command + "\n"); // Envoi du 404 not found
                        toClient.flush();

                        FileInputStream html404 = new FileInputStream("404.html");

                        byte sendingBuffer[] = new byte[133];
                        html404.read(sendingBuffer);
                        html404.close();
                        toClient.write(sendingBuffer);

                        System.out.println("Page 404 envoyée.");
                        connectedSocket.close();
                        return false;
                    }
                    int size = (int) file.length(); //On prend la longueur du fichier à envoyer pour le header

                    FileInputStream htmlFile = new FileInputStream(path);
                    byte sendingBuffer[] = new byte[size];

                    // lecture du fichier
                    htmlFile.read(sendingBuffer);
                    htmlFile.close();

                    String contentLength = "Content-Length: " + size;

                    // Requête GET reponse
                    String getResp = command + "\n" + date + "\n" + connexion
                            + "\n" + serverAddress + "\n" + contentType + "\n"
                            + contentLength;

                    // Envoi du fichier
                    toClient.writeBytes(getResp + "\n\n");
                    toClient.flush();

                    toClient.write(sendingBuffer);
                    toClient.close();

                }
            } else {

                /////// SI LA COMMANDE N'EST PAS UN GET ///////
                String command = inputs[2] + " 504";
                toClient.writeChars(command);
                toClient.flush();


                FileInputStream html504 = new FileInputStream("504.html");
                byte bufferEnvoi[] = new byte[133];
                html504.read(bufferEnvoi);
                html504.close();
                toClient.write(bufferEnvoi);

                System.out.println("Page 504 envoyée.");

                connectedSocket.close();
            }
            connectedSocket.close();

            return true;


        } catch (IOException e) {
            System.out.println("Unable to read inputStream");
            e.printStackTrace();
            return false;
        }

    }
}
