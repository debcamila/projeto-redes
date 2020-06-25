package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import palavras.PalavraRandom;

class mousePos { // criar posiçoes do mouse x e y

    public int x;
    public int y;
}

public class ClientGame extends Application {

    ArrayList<Potion> pocoes;
    ArrayList<Letra> acertos;
    
    PalavraRandom palavraRandom;
    Palavra pal;
    
    ArrayList<Boolean> displayPalavra;
    public static MediaPlayer player;
    public static Media media;
    boolean acertou = false;
    private Label nomeCategoria;
    private Label nomeDica;
    
    private Label pontuUm;
    private Label pontuDois;
    private boolean pontoUmDisplay = false;
    private boolean pontoDoisDisplay = false;
    
    private Button btnTryAgain;
    private Button btnEncerrar;
    private Button btnNao;
    
    int ponto = 0;
    private Jogador jogador;
    
    private int erros = 0;
    
    static Socket client = null;
    ObjectInputStream inObject;
    ObjectOutputStream outObject;
    String[] categorias = {"comida", "objeto", "esporte"};
    String[] comida = {"biscoito", "feijao", "arroz"};
    String[] objeto = {"espelho", "panela", "bola"};
    String[] esporte = {"futebol", "volei", "futsal"};
    
    Dados dadosDoJogo = new Dados(true, 0, 0, "", false);
    
    public void Client() throws IOException, ClassNotFoundException{
        InetAddress server = InetAddress.getLocalHost();
        client = new Socket(server, 16868);
        
    
        System.out.println("Aguardando Conexao.");
       
        outObject = new ObjectOutputStream(client.getOutputStream());
        inObject = new ObjectInputStream(client.getInputStream());
        
        dadosDoJogo = (Dados) inObject.readObject();
        
        dadosDoJogo.setPlayerClientName(jogador.getNome());
        outObject.writeObject(dadosDoJogo);
        outObject.flush();
        
        SecureRandom sorteio = new SecureRandom();
        int randomCategoria = java.util.Arrays.asList(categorias).indexOf(dadosDoJogo.getCategoria());
        
        palavraRandom = new PalavraRandom();
        pal = palavraRandom.gerarPalavra(randomCategoria);
        displayPalavra = pal.getAcertos();
        
        new Thread() {
                   
            @Override
            public void run() {
                Dados threadDados;
                while (true) {
                    
                    try {
                        threadDados = (Dados) inObject.readObject();
                        dadosDoJogo.setPlaying(threadDados.getisPlaying());
                        dadosDoJogo.setIsServerTurn(threadDados.getisIsServerTurn()); 
                        dadosDoJogo.setPlayerClientScore(threadDados.getPlayerClientScore()); 
                        dadosDoJogo.setPlayerServerScore(threadDados.getPlayerServerScore());
                        System.out.println("cliente");
                        System.out.println("dados do jogo: "+dadosDoJogo.getisIsServerTurn());
                        System.out.println("client score: "+dadosDoJogo.getPlayerClientScore());
                        System.out.println("server score: "+dadosDoJogo.getPlayerServerScore());

                    } catch (IOException ex) {
                        
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ClientGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }

    // MAIN DO JavaFx
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Jogo da Forca - Client");
        Group raiz = new Group();
        Scene s = new Scene(raiz, 1024, 640);
        primaryStage.setScene(s);       
        Image telaPrincipal = new Image("img/tela-principal.jpg");
        ImageView iv = new ImageView();
        iv.setImage(telaPrincipal);
        iv.setFitWidth(1024);
        iv.setFitHeight(640);
        raiz.getChildren().add(iv);

        Canvas canvas = new Canvas(s.getWidth(), s.getHeight());
        raiz.getChildren().add(canvas);
        
        GraphicsContext gc = canvas.getGraphicsContext2D(); // para pintar no canvas
        
        pocoes = criarPocoes();// cria a lista de poçoes
        acertos = new ArrayList<>();
        mousePos mouse = new mousePos();
        criarJogador();
 
        btnTryAgain = new Button("SIM");
        btnTryAgain.setPrefSize(96, 37);
        btnTryAgain.setLayoutX(464);
        btnTryAgain.setLayoutY(500);
        btnTryAgain.setStyle("-fx-background-color: YELLOW; -fx-background-radius: 50;");
        btnTryAgain.setVisible(false);
        btnTryAgain.setDisable(true);
        
        raiz.getChildren().add(btnTryAgain);
        
        btnEncerrar = new Button("NÃO");
        btnEncerrar.setPrefSize(96, 37);
        btnEncerrar.setLayoutX(664);
        btnEncerrar.setLayoutY(500);
        btnEncerrar.setStyle("-fx-background-color: YELLOW; -fx-background-radius: 50;");
        btnEncerrar.setVisible(false);
        btnEncerrar.setDisable(true);
        
        raiz.getChildren().add(btnEncerrar);
        
        btnNao = new Button("ENCERRAR");
        btnNao.setPrefSize(96, 37);
        btnNao.setLayoutX(664);
        btnNao.setLayoutY(500);
        btnNao.setStyle("-fx-background-color: YELLOW; -fx-background-radius: 50;");
        btnNao.setVisible(false);
        btnNao.setDisable(true);
        
        raiz.getChildren().add(btnNao);
        
        Client();

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() { //javafx precisa saber quem é o no que vai receber os eventos, so temos um no: canvas
            @Override
            public void handle(MouseEvent event) {
                mouse.x = (int) event.getX();
                mouse.y = (int) event.getY();
            }
        });

        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {//quando o solta o botao do mouse
            @Override                               // passa pra proxima palavra e soma os pontos
            public void handle(MouseEvent event) {
                for (Potion p : pocoes) {
                    if (p.isSelecionado == true) {
                        p.isSelecionado = false;
                        p.resetar();
                    }
                }
                if (acertou == true) {
                    System.out.println("acertou a palavra");
                    
                    try {
                        dadosDoJogo.setPlaying(false);
                        dadosDoJogo.setPlayerClientScore(dadosDoJogo.getPlayerClientScore() + 20);
                        outObject.writeObject(new Dados(dadosDoJogo.getisPlaying(), dadosDoJogo.getPlayerServerScore(), dadosDoJogo.getPlayerClientScore(), dadosDoJogo.getCategoria(), dadosDoJogo.getisIsServerTurn()));
                        outObject.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(ClientGame.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                }
            }
        });

        canvas.setOnMousePressed(new EventHandler<MouseEvent>() { // checa qual pocao ta selecionada e qual posicao o mouse ta tocando
            @Override
            public void handle(MouseEvent event) {
                for (Potion p : pocoes) {
                    if (p.checarMouse((int) event.getX(), (int) event.getY()) == true) {
                        if(!pal.tentaLetra(p.getAcento())){
                            dadosDoJogo.setIsServerTurn(true);
                            if(dadosDoJogo.getPlayerClientScore() >= 5){
                                dadosDoJogo.setPlayerClientScore(dadosDoJogo.getPlayerClientScore() - 5);
                            }
                            System.out.println("ERREI.MUDANDO!");
                            erros++;
                            try {
                                System.out.println("enviando serverturn como: "+dadosDoJogo.getisIsServerTurn());
                                outObject.writeObject(new Dados(dadosDoJogo.getisPlaying(), dadosDoJogo.getPlayerServerScore(), dadosDoJogo.getPlayerClientScore(), dadosDoJogo.getCategoria(), dadosDoJogo.getisIsServerTurn()));
                                outObject.flush();
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{
                            dadosDoJogo.setPlayerClientScore(dadosDoJogo.getPlayerClientScore() + 5);
                            if(pal.checarAcerto()){
                                dadosDoJogo.setPlaying(false);
                                dadosDoJogo.setPlayerClientScore(dadosDoJogo.getPlayerClientScore() + 20);
                            }
                            System.out.println("ACERTEI!");
                            try {
                                System.out.println("enviando client points como: "+dadosDoJogo.getPlayerClientScore());
                                System.out.println("playing: "+dadosDoJogo.getisPlaying());
                                outObject.writeObject(new Dados(dadosDoJogo.getisPlaying(), dadosDoJogo.getPlayerServerScore(), dadosDoJogo.getPlayerClientScore(), dadosDoJogo.getCategoria(), dadosDoJogo.getisIsServerTurn()));
                                outObject.flush();
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGame.class.getName()).log(Level.SEVERE, null, ex);
                            }  
                        }
                        p.setUsado(true);
                        System.out.println("testando letra: "+p.getAcento());
                    }
                }
            }
        });

        mousePos bruxaPos = new mousePos();
        
        
        new AnimationTimer() { //loop principal do jogo(main loop) que roda 60x por segundo de forma otimizada, desenho tudo
            int x = 0;
            int dir = 1;

            int bdirx = 0;
            int bdiry = 0;      
                
            
            // N realidade, QUEM vai rodar 60x por segundo é este método HANDLE
            
            
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, 840, 520);
                if(!dadosDoJogo.getisPlaying()){
                    if(dadosDoJogo.getPlayerClientScore() < dadosDoJogo.getPlayerServerScore()){
                        gc.drawImage(new Image("img/perdeu.jpg"), 0, 0, 1024, 640);
                        btnEncerrar.setDisable(false);
                        btnEncerrar.setVisible(true);
                        
                        btnTryAgain.setDisable(false);
                        btnTryAgain.setVisible(true);
                        return;
                    }
                    else{
                        gc.drawImage(new Image("img/ganhou.jpg"), 0, 0, 1024, 640);
                        btnNao.setDisable(false);
                        btnNao.setVisible(true);
                        return;
                    }
                }
                
                btnNao.setOnMouseClicked((MouseEvent e) -> {
                    try {
                        outObject.close();
                        inObject.close();
                        client.close();
                        System.exit(0);
                        
                    } catch (IOException ex) {
                        Logger.getLogger(ClientGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                btnEncerrar.setOnMouseClicked((MouseEvent e) -> {
                    try {
                        outObject.close();
                        inObject.close();
                        client.close();
                        System.exit(0);
                        
                    } catch (IOException ex) {
                        Logger.getLogger(ClientGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                        
                btnTryAgain.setOnMouseClicked((MouseEvent e) -> {
                    SecureRandom sorteio = new SecureRandom();
                    int randomCategoria = sorteio.nextInt(3);
                    int randomTurn = sorteio.nextInt(2);

                    palavraRandom = new PalavraRandom();
                    pal = palavraRandom.gerarPalavra(randomCategoria);
                    displayPalavra = pal.getAcertos();
                  
                    String novoNomeClient = dadosDoJogo.getPlayerClientName();
                    String novoNomeServer = dadosDoJogo.getPlayerServerName();

                    String categoriaSelected = categorias[randomCategoria];
                    boolean isServerTurn;

                    if(randomTurn == 0){
                        isServerTurn = true;
                    }else{
                        isServerTurn = false;
                    }
                    boolean playing = true;
                    int playerOneScore = 0;
                    int playerTwoScore = 0;
                    dadosDoJogo = new Dados(playing, playerOneScore , playerTwoScore, categoriaSelected, isServerTurn);
                    displayPalavra = pal.getAcertos();
                    dadosDoJogo.setPlayerClientName(novoNomeClient);
                    dadosDoJogo.setPlayerServerName(novoNomeServer);
                    
                    Image telaPrincipal = new Image("img/tela-principal.jpg");
                    iv.setImage(telaPrincipal);
                    
                    pontuUm.setText(dadosDoJogo.getPlayerClientName()+": 0");
                    pontuDois.setText(dadosDoJogo.getPlayerServerName()+": 0");
                   
                    btnTryAgain.setDisable(true);
                    btnTryAgain.setVisible(false);
                    btnEncerrar.setVisible(false);
                    btnEncerrar.setDisable(true);
                        
                    try {
                        outObject.writeObject(dadosDoJogo);
                    } catch (IOException ex) {
                        Logger.getLogger(ClientGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
                
                if(dadosDoJogo.getisIsServerTurn()){
                    gc.clearRect(0, 0, 1024, 640);
                    gc.drawImage(new Image("img/tela-espera.jpg"), 0, 0, 1024, 640);
                    forca(gc);
                    return;
                }
                
                if (x > canvas.getWidth() - 100) {
                    dir = -1;
                } else if (x < 0) {
                    dir = 1;
                }

                x += dir;

                if (bruxaPos.x > mouse.x) {
                    bdirx = -5;
                } else if (bruxaPos.x < mouse.x) {
                    bdirx = 5;
                }

                bruxaPos.x += bdirx;
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFont(new Font(48));
                gc.setFill(Color.WHITE);
                for (Potion p : pocoes) {
                    gc.drawImage(p.getImg(), p.getX(), p.getY());
                }
                gc.setFont(new Font(48));
                gc.setFill(Color.WHITE);
                
                for (int i = 0; i < pal.getLetras().size(); i++) {
                    if(displayPalavra.get(i)){
                        pal.getLetras().get(i).setX(i * 64 + 64);
                        pal.getLetras().get(i).setY(200);
                        pal.getLetras().get(i).render(gc);
                    }else{
                        pal.getBlank().setX(i * 64 + 64);
                        pal.getBlank().setY(200);
                        pal.getBlank().render(gc);
                    }

                }
                String cat = dadosDoJogo.getCategoria();
                nomeCategoria = new Label("Categoria: " + cat);
                nomeCategoria.setLayoutX(30);
                nomeCategoria.setLayoutY(30);
                nomeCategoria.setAlignment(Pos.CENTER);
                nomeCategoria.setFont(Font.font("Berlin Sans FB", 30));
                nomeCategoria.setTextFill(Color.WHITE);
                nomeCategoria.setPrefSize(250, 35);
                raiz.getChildren().add(nomeCategoria);
                
                if(pontoUmDisplay == false){
                    int potUm = dadosDoJogo.getPlayerClientScore();
                    pontuUm = new Label(dadosDoJogo.getPlayerClientName()+": " + potUm);
                    pontuUm.setLayoutX(775);
                    pontuUm.setLayoutY(30);
                    pontuUm.setAlignment(Pos.CENTER);
                    pontuUm.setFont(Font.font("Berlin Sans FB", 30));
                    pontuUm.setTextFill(Color.WHITE);
                    pontuUm.setPrefSize(250, 35);
                    raiz.getChildren().add(pontuUm);
                    pontoUmDisplay = true;
                }else{
                    pontuUm.setText(dadosDoJogo.getPlayerClientName()+": "+dadosDoJogo.getPlayerClientScore());
                }
                
                if(pontoDoisDisplay == false){
                    int potDois = dadosDoJogo.getPlayerServerScore();
                    pontuDois = new Label(dadosDoJogo.getPlayerServerName()+": " + potDois);
                    pontuDois.setLayoutX(775);
                    pontuDois.setLayoutY(50);
                    pontuDois.setAlignment(Pos.CENTER);
                    pontuDois.setFont(Font.font("Berlin Sans FB", 30));
                    pontuDois.setTextFill(Color.WHITE);
                    pontuDois.setPrefSize(250, 35);
                    raiz.getChildren().add(pontuDois);
                    pontoDoisDisplay = true;
                }else{
                    pontuDois.setText(dadosDoJogo.getPlayerServerName()+": "+dadosDoJogo.getPlayerServerScore());
                }
                
                String dica = "";
                if(dadosDoJogo.getCategoria().equals("comida")){
                    dica = "Está presente na maioria dos pratos\ndos brasileiros durante o almoço.";
                }
                else if(dadosDoJogo.getCategoria().equals("objeto")){
                    dica = "Está presente na maioria dos quartos.";
                }
                else if(dadosDoJogo.getCategoria().equals("esporte")){
                    dica = "O Brasil possui vários títulos.";
                }
                
                nomeDica = new Label("Dica: " + dica);
                nomeDica.setWrapText(true);
                nomeDica.setLayoutX(280);
                nomeDica.setLayoutY(30);
                nomeDica.setAlignment(Pos.CENTER);
                nomeDica.setFont(Font.font("Berlin Sans FB", 28));
                nomeDica.setTextFill(Color.YELLOW);
                nomeDica.setPrefSize(550, 80);
                raiz.getChildren().add(nomeDica);
                
                forca(gc);
            }
        }.start();
        primaryStage.show();
    } //fim do start
    
      
    public void forca(GraphicsContext gc){
        gc.drawImage(new Image("img/forca.png"), 50, 250);
        if(erros > 0){ //cabeca
            gc.drawImage(new Image("img/cabeca.png"), 258, 300);
        }
        if(erros > 1 ){ //corpo
            gc.drawImage(new Image("img/corpo.png"), 255, 385);
        }
        if(erros > 2){ //braco 1
            gc.drawImage(new Image("img/braco-esquerdo.png"), 200, 395);
        }
        if(erros > 3){ // braco 2
            gc.drawImage(new Image("img/braco-direito.png"), 330, 398);
        }
        if(erros > 4){ // perna 1
            gc.drawImage(new Image("img/perna-esquerda.png"), 220, 500);
        }
        if(erros > 5){ //perna 2
            gc.drawImage(new Image("img/perna-direita.png"), 318, 500);
        }
        if(erros > 6){ //corpo completo
            gc.drawImage(new Image("img/cabeca-morta.png"), 258, 300);
        }
    }

    public ArrayList<Potion> criarPocoes() {
        ArrayList<Potion> pocoes = new ArrayList();
        
        Potion p1 = new Potion("img/a.jpeg", "img/a-usado.jpeg", 717, 93, "a");//string da imagem, posicao x e y, string do acento gráfico
        Potion p2 = new Potion("img/b.jpeg", "img/b-usado.jpeg", 797, 93, "b");
        Potion p3 = new Potion("img/c.jpeg", "img/c-usado.jpeg", 877, 93, "c");
        
        Potion p4 = new Potion("img/d.jpeg", "img/d-usado.jpeg", 717, 143, "d");
        Potion p5 = new Potion("img/e.jpeg", "img/e-usado.jpeg", 797, 143, "e");
        Potion p6 = new Potion("img/f.jpeg", "img/f-usado.jpeg", 877, 143, "f");
        
        Potion p7 = new Potion("img/g.jpeg", "img/g-usado.jpeg", 717, 193, "g");
        Potion p8 = new Potion("img/h.jpeg", "img/h-usado.jpeg", 797, 193, "h");
        Potion p9 = new Potion("img/i.jpeg", "img/i-usado.jpeg", 877, 193, "i");
        
        Potion p10 = new Potion("img/j.jpeg", "img/j-usado.jpeg", 717, 243, "j");
        Potion p11 = new Potion("img/k.jpeg", "img/k-usado.jpeg", 797, 243, "k");
        Potion p12 = new Potion("img/l.jpeg", "img/l-usado.jpeg", 877, 243, "l");
        
        Potion p13 = new Potion("img/m.jpeg", "img/m-usado.jpeg", 717, 293, "m");
        Potion p14 = new Potion("img/n.jpeg", "img/n-usado.jpeg", 797, 293, "n");
        Potion p15 = new Potion("img/o.jpeg", "img/o-usado.jpeg", 877, 293, "o");
        
        Potion p16 = new Potion("img/p.jpeg", "img/p-usado.jpeg", 717, 343, "p");
        Potion p17 = new Potion("img/q.jpeg", "img/q-usado.jpeg", 797, 343, "q");
        Potion p18 = new Potion("img/r.jpeg", "img/r-usado.jpeg", 877, 343, "r");
        
        Potion p19 = new Potion("img/s.jpeg", "img/s-usado.jpeg", 717, 393, "s");
        Potion p20 = new Potion("img/t.jpeg", "img/t-usado.jpeg", 797, 393, "t");
        Potion p21 = new Potion("img/u.jpeg", "img/u-usado.jpeg", 877, 393, "u");
        
        Potion p22 = new Potion("img/v.jpeg", "img/v-usado.jpeg", 717, 443, "v");
        Potion p23 = new Potion("img/w.jpeg", "img/w-usado.jpeg", 797, 443, "w");
        Potion p24 = new Potion("img/x.jpeg", "img/x-usado.jpeg", 877, 443, "x");
        
        Potion p25 = new Potion("img/y.jpeg", "img/y-usado.jpeg", 717, 493, "y");
        Potion p26 = new Potion("img/z.jpeg", "img/z-usado.jpeg", 797, 493, "z");
        
        pocoes.add(p1);
        pocoes.add(p2);
        pocoes.add(p3);
        pocoes.add(p4);
        pocoes.add(p5);
        pocoes.add(p6);
        pocoes.add(p7);
        pocoes.add(p8);
        pocoes.add(p9);
        pocoes.add(p10);
        pocoes.add(p11);
        pocoes.add(p12);
        pocoes.add(p13);
        pocoes.add(p14);
        pocoes.add(p15);
        pocoes.add(p16);
        pocoes.add(p17);
        pocoes.add(p18);
        pocoes.add(p19);
        pocoes.add(p20);
        pocoes.add(p21);
        pocoes.add(p22);
        pocoes.add(p23);
        pocoes.add(p24);
        pocoes.add(p25);
        pocoes.add(p26);
        return pocoes;
    }
    
    private Jogador criarJogador() {
        String nome;
        Jogador novo;
        nome = JOptionPane.showInputDialog(null, "Digite seu nome: ", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        novo = new Jogador(nome);
    
        for(Jogador jogo : Inicio.lista){
            if(novo.getNome().equalsIgnoreCase(jogo.getNome())){
                jogador = Inicio.lista.remove();
                return jogador;
            }
        }
        
        jogador = novo;
        return jogador;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
