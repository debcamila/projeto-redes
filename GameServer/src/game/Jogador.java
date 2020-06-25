
package game;

import java.io.Serializable;

public class Jogador implements Serializable{
    private String nome;
    private int totalPontos;
    
    public Jogador(String n){
        this.nome = n;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getTotalPontos(){
        return totalPontos;
    }
    
    public void setTotalPontos(int p){
        this.totalPontos = totalPontos + p;
    }
}
