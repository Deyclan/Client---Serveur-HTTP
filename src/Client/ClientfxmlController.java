/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import sun.plugin.javascript.navig.Anchor;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author Nejko
 */
public class ClientfxmlController implements Initializable {

    @FXML Button getFileBtn;
    @FXML TextField destIP, fileName;
    @FXML TextArea zoneHeader, zoneFichier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert getFileBtn != null : "fx:id=\"getFileBtn\" was not injected: check your FXML file 'clientfxml.fxml'.";
        assert destIP != null : "fx:id=\"destIP\" was not injected: check your FXML file 'clientfxml.fxml'.";
        assert fileName != null : "fx:id=\"fileName\" was not injected: check your FXML file 'clientfxml.fxml'.";
        assert zoneHeader != null : "fx:id=\"zoneHeader\" was not injected: check your FXML file 'clientfxml.fxml'.";
        assert zoneFichier != null : "fx:id=\"zoneFichier\" was not injected: check your FXML file 'clientfxml.fxml'.";

    }
    
    
    public void getFile(){

        String command = "GET /";
        command+=destIP.getText();
        command+="/";
        command+=fileName.getText();
        command+=" HTTP/1.1";

        Client client = new Client(command,destIP.getText(),fileName.getText());

        if (!client.error.equals("")){
            zoneHeader.setText(client.error);
        }

        else if(client.fileType.contains("jpg") || client.fileType.contains("png")){
            zoneFichier.setText("Image reçue, \n     => Voir onglet Image");

            ImageIcon imageIcon = new ImageIcon(client.fileType);
            JDialog dialog = new JDialog();
            dialog.setTitle(client.fileName);
            JLabel label = new JLabel(imageIcon);
            dialog.add(label);
            dialog.pack();
            dialog.setVisible(true);

        }
        else if(client.fileType.contains("html")) {
            zoneHeader.setText(client.header);
            zoneFichier.setText(client.fileContent);
            try {
                Desktop.getDesktop().browse(client.file.toURI());
            } catch (IOException e1) {
                zoneFichier.setText("Unable to lauch html page in browser");
                e1.printStackTrace();
            }
        }
        else{
            zoneHeader.setText(client.header);
            zoneFichier.setText(client.fileContent);
        }
    }
}
