package game;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScoreBoard extends Application {

    private GridPane tabela;
    private Label placar;
    private Label nome;
    private Label pontuacao;
    private ImageView fundo;
    private VBox local;
    private int qtd;
    private AnimationTimer loop;


    @Override
    public void start(Stage stage) {
        Pane visu = new Pane();
        Scene scene = new Scene(visu);
        visu.setPrefSize(890, 520);
        stage.initStyle(StageStyle.UNDECORATED);
        qtd = Inicio.lista.size();
        System.out.println(qtd);
        local = new VBox();
        Button btn = new Button();
        btn.setText("Voltar");
        btn.setPrefSize(50, 50);
        btn.setLayoutX(10);
        btn.setLayoutY(10);
        fundo = new ImageView(new Image(""));
        visu.getChildren().addAll(fundo, btn);
        criarTitulo(visu);
        criarCategoria(visu);
        local.setPrefWidth(463);
        criarPlacar(local);
        preencherPlacar(tabela);
        ScrollPane scrollPane = new ScrollPane(local);
        scrollPane.setPrefHeight(255);
        scrollPane.setLayoutX(250);
        scrollPane.setLayoutY(190);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        visu.getChildren().add(scrollPane);

        loop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                btn.setOnMouseClicked((MouseEvent e) -> {
                    loop.stop();
                    stage.close();
                    FXLMDocument menu = new FXLMDocument();
                    try {
                        menu.start(new Stage());
                    } catch (Exception ex) {
                        Logger.getLogger(ClientGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        };
        loop.start();
        stage.setTitle("Placar");
        stage.setScene(scene);
        stage.show();
    }

    private void criarTitulo(Pane root) {
        placar = new Label();
        placar.setPrefSize(360, 103);
        placar.setLayoutX(280);
        placar.setLayoutY(51);
        placar.setText("Placar");
        placar.setTextFill(Color.CORNSILK);
        placar.setAlignment(Pos.CENTER);
        placar.setFont(Font.font("Franklin Gothic Heavy", 70));
        root.getChildren().add(placar);
    }

    private void criarCategoria(Pane root) {
        nome = new Label();
        nome.setPrefSize(90, 36);
        nome.setLayoutX(250);
        nome.setLayoutY(157);
        nome.setText("Nome");
        nome.setTextFill(Color.CORNSILK);
        nome.setAlignment(Pos.BASELINE_LEFT);
        nome.setFont(Font.font("Franklin Gothic Heavy", 26));
        // ----------------------------------------------- CATEGORIA NOME
        pontuacao = new Label();
        pontuacao.setPrefSize(90, 36);
        pontuacao.setLayoutX(580);
        pontuacao.setLayoutY(157);
        pontuacao.setText("Pontos");
        pontuacao.setTextFill(Color.RED);
        pontuacao.setAlignment(Pos.BASELINE_LEFT);
        pontuacao.setFont(Font.font("Franklin Gothic Heavy", 26));
        root.getChildren().addAll(nome, pontuacao);
    }

    private void criarPlacar(VBox caixa) { // PEGAR O QTD DA LISTA ENCADEADA E CRIAR O GRID PANE 
        tabela = new GridPane();
        tabela.setPrefSize(463, qtd * 51);

        int i;

        ColumnConstraints numero = new ColumnConstraints();
        ColumnConstraints nome = new ColumnConstraints();
        RowConstraints linha = new RowConstraints();
        numero.setHgrow(Priority.NEVER);
        numero.setMaxWidth(143);
        numero.setPrefWidth(143);
        nome.setHgrow(Priority.NEVER);
        nome.setMaxWidth(320); // maior nome que pode ter
        nome.setPrefWidth(320);
        tabela.getColumnConstraints().addAll(nome, numero);
        linha.setPrefHeight(51);
        linha.setVgrow(Priority.NEVER);
        linha.setMaxHeight(51);
        for (i = 0; i < qtd; i++) { // CRIAR A TABELA DE PONTUAÇÃO
            tabela.getRowConstraints().add(i, linha);
        }
        tabela.setGridLinesVisible(true);
        caixa.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        caixa.getChildren().add(tabela);
    }

    private void preencherPlacar(GridPane tabela) {

        int i = 0; // LINHA
        int j; // COLUNA
        int valor;
        Label dado;
        String num, nome;
        Color cor;

        for (Jogador jogo : Inicio.lista) { //jogo criado na classe SecondWindowController

            nome = jogo.getNome();

            for (j = 0; j < 2; j++) {
                if (j == 0) {                // NOME
                    cor = Color.DARKORANGE;
                    dado = criarDado(nome, 55, 317);
                    dado.setTextFill(cor);
                    tabela.add(dado, j, i);
                } else if (j == 1) {
                    cor = Color.GREEN;
                    valor = jogo.getTotalPontos();// valor inteiro 
                    num = Integer.toString(valor); // so add no label se for string 
                    dado = criarDado(num, 52, 92);
                    dado.setTextFill(cor);
                    tabela.add(dado, j, i);
                }
            }
            i++;
        }

    }

    private Label criarDado(String dado, double altura, double comprimento) {

        Label inf = new Label(dado);
        inf.setPrefSize(comprimento, altura);

        if (comprimento == 92) { // FONTE DOS NUMERO
            inf.setFont(Font.font("Berlin Sans FB", 36));
            inf.setAlignment(Pos.CENTER);
        } else {
            inf.setFont(Font.font("Bell MT", 36)); // FONTE DO NOME            
            inf.setAlignment(Pos.CENTER_LEFT);
        }
        return inf;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
