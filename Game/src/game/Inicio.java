package game;

import java.util.LinkedList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Inicio extends Application {
    
    public static LinkedList<Jogador> lista; 
    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception { //CRIO A LISTA DE JOGADORES AQUI
        
        stage = primaryStage;
        stage.close();
        StackPane root = new StackPane();
        lista = new LinkedList<>();
        ManipulacaoArquivo mani = new ManipulacaoArquivo();
        OperacaoArquivo op = new OperacaoArquivo(mani,"Arquivo");
        op.LerArqJogador();
        FXLMDocument menu = new FXLMDocument();
        menu.start(new Stage());
//        Scene scene = new Scene(root, 300, 250);
//        stage.setTitle("");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
