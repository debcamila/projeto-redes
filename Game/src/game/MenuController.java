package game;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MenuController implements Initializable {

    @FXML
    private Button btnStart;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnPontuation;

    @FXML
    private ImageView bruxaref;

    private LinkedList<Jogador> lista;

    @FXML
    private void btnStart(ActionEvent e) throws Exception { //vai direcionar para a segunda janela da mensagem        
        Parent root = FXMLLoader.load(getClass().getResource("SecondWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Jogo da Forca");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void btnExitAction(ActionEvent e) { //botao de sair, antes de sair tenho que gravar os dados dos jogadores
        ManipulacaoArquivo mani = new ManipulacaoArquivo();
        OperacaoArquivo op = new OperacaoArquivo(mani, "Arquivo");
        op.GravarJogadores();
        System.exit(0);
    }
    
    @FXML
    public void IrPontuacao() { //botao de placar
        ScoreBoard ta = new ScoreBoard();
        FXLMDocument.stage.close();
        ta.start(new Stage());
    }
    
    // primeira função a ser chamada quando o menu abre
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        MediaPlayer player = new MediaPlayer(new Media(this.getClass().getResource("/game/musica.mp3").toExternalForm()));
        player.play();
        player.setOnEndOfMedia(() -> player.play());
        
        
    }
}
