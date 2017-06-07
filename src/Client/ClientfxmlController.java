/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nejko
 */
public class ClientfxmlController implements Initializable {

    @FXML
    Button getFileBtn;
    @FXML
    TextField destIP, fileName;
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
        
    }
    

}
