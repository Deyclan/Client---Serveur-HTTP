package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Brandon on 24/05/2017.
 */
public class Client {

    Socket clientSocket;
    InetAddress address;
    String fileName;
    String request;
    String connexionType;

    public String fileContent;
    public String header;
    public String fileType;

    public Client() {
        try {
            connexionType = "Connection: keep-alive";
            getFromClavier();
            address = InetAddress.getByName("127.0.0.1");
            clientSocket = new Socket(address,12345);
            runClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(String request, String address, String fileName)
    {
        fileType = "";
        fileContent = "";
        header = "";
        connexionType = "Connection: keep-alive";
        try {
            this.address = InetAddress.getByName(address);
            this.fileName = fileName;
            this.request = request;
            clientSocket = new Socket(this.address,2046);
            runClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getFromClavier() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Entrez votre requête :");
        request = scanner.nextLine();

        System.out.println("Rester connecter ? y/n");
        connexionType = scanner.nextLine();
        if (connexionType.equals("y"))
            connexionType = "Connection: keep-alive";
        else
            connexionType = "Connection: close";

        scanner.close();
    }

    @SuppressWarnings("deprecation")
    private void runClient() throws IOException {

        // Création des flux entrant et sortant
        DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream fromServer = new DataInputStream(clientSocket.getInputStream());

        //Ecriture de la request GET dans le flux
        toServer.writeBytes(request +" "+ connexionType +"\n");
        toServer.flush();

        String temp;
        String getResponse="";
        String length ="";

        String getServerResponse = fromServer.readLine();
        String[] splitedResponse = getServerResponse.split(" ");

        //Si la réponse serveur est OK, on lit la suite de l'header
        if(splitedResponse.length > 1) {
            if (splitedResponse[1].equals("200")) {
                getResponse = getServerResponse+"\n";

                while (!(temp = fromServer.readLine()).equals("")) {

                    if (temp.contains("Length"))
                        if(temp.split(": ").length > 1)
                            length = (temp.split(": "))[1];

                    getResponse += temp + "\n";
                }
                header = getResponse;

                System.out.println("Received: \n" + getResponse);

                //CREATION DU BUFFER DE RECEP
                byte data[] =new byte[Integer.parseInt(length)];

                //RECEPTION DU FICHIER
                for(int i =0; i<data.length;i++)
                    data[i] = fromServer.readByte();
                fromServer.close();

                //CREATION DU FICHIER A LA RACINE
                String nomFichierSplit = "inconnu.html";
                if(request.split("/").length > 2)
                    nomFichierSplit = request.split("/")[2].split(" ")[0];
                File file = new File("RECU"+nomFichierSplit);
                file.createNewFile();

                fileType = nomFichierSplit;

                //ECRITURE DU CONTENU RECU DANS LE FICHIER
                FileOutputStream fop = new FileOutputStream(file);
                fop.write(data);
                fileContent = new String(data);
                fop.close();

                System.out.println("TRANSFERT REUSSI");
                clientSocket.close();
            }
            else if (splitedResponse[1].equals("404")) {
                System.out.println("404 NOT FOUND : "+getServerResponse);
                byte data[] = new byte[133];

                for(int i=0;i<data.length;i++)
                    data[i] = fromServer.readByte();
                fromServer.close();

                File file = new File("404.html");
                file.createNewFile();

                FileOutputStream fop = new FileOutputStream(file);
                fop.write(data);
                fileContent = new String(data);
                fop.close();
                clientSocket.close();

            }
        }
    }



}
