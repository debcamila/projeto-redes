
package game;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SecondWindowController implements Initializable {
    private ClientGame jogo = new ClientGame();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

 @FXML
    public void btnContinue(ActionEvent event) throws Exception { //direciona para a pagina do jogo, PLAY
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        jogo.start(stage);
        
    }    

}
