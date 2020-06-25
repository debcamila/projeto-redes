
package game;

import java.io.File;
import java.io.IOException;

public class ExecutaArquivo {
    private ManipulacaoArquivo m;
    private String arqJogador;
    
    public ExecutaArquivo(ManipulacaoArquivo mani, String arqJogador) {
        this.m = mani;
        this.arqJogador = arqJogador;
    }
    public void LerArqJogador(ListaDeJogador lista) {
        Jogador jogador;
        try {
            File file = new File(arqJogador); // arq para jogador
            if (!file.exists()) { // arq de jogadores não existe
                file.createNewFile(); //crio um novo arq
            } else { // Arquivo existe e eu posso ler
                m.openToRead(arqJogador);
                jogador = (Jogador) m.lerObjeto();
                while (jogador != null) {
                    System.out.println(jogador);
                    lista.adicionar(jogador);
                    jogador = (Jogador) m.lerObjeto();
                }
            }
        } catch (IOException err) {
            System.err.println("Falha ao pegar os arquivos");
            System.exit(1);
        }
        m.closeAfterRead();
    }
        
    public void GravarJogadores(ListaDeJogador lista) {
        m.openToWrite(arqJogador);
        NodeJogador aux = lista.getInicio();
        while(aux != null){
            m.gravarObjeto(aux.getData());
            aux = aux.getNext();
        }
        m.closeAfterWrite();
    }
}
