
package game;

import java.io.File;
import java.io.IOException;

public class OperacaoArquivo {
    private ManipulacaoArquivo mani;
    private String arqJogador;

    public OperacaoArquivo(ManipulacaoArquivo mani, String arqJogador) {
        this.mani = mani;
        this.arqJogador = arqJogador;
    }

    public void LerArqJogador() {
        Jogador jogador;
        try {
            File file = new File(arqJogador); // ----Arquivo para clientes
            if (!file.exists()) { // Arquivo de clientes não existe
                file.createNewFile();
            } else { // Arquivo existe e eu posso ler
                mani.openToRead(arqJogador);
                jogador = (Jogador) mani.lerObjeto();
                while (jogador != null) {
                    System.out.println(jogador);
                    Inicio.lista.add(jogador);
                    jogador = (Jogador) mani.lerObjeto();
                }
            }
        } catch (IOException err) {
            System.err.println("Falha ao tentar pegar os arquvios");
            System.exit(1);
        }
        mani.closeAfterRead();
    }

    public void GravarJogadores() {
        mani.openToWrite(arqJogador);        
        for (Jogador jogo : Inicio.lista) {
            mani.gravarObjeto(jogo);
        }
        mani.closeAfterWrite();
    }
}
