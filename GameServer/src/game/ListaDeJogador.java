
package game;

public class ListaDeJogador {
    private NodeJogador inicio;
    private NodeJogador fim;
    private int qtd;    
    
    public void ListaJogador(){
        this.qtd = 0;
    }
    
    private boolean isEmpty(){
        if(inicio == null){
            return true;
        }
        return false;
    }        
    
    private NodeJogador buscar(String nome){//busca pelo nome
        
        NodeJogador aux;
        aux = inicio;
        while(aux != null){
            
            if(aux.getData().getNome().equals(nome)){
                return aux; 
            }
            aux = aux.getNext();
        }
        return null;
    }
    
    public void adicionar(Jogador novo){
        
        NodeJogador aux,aux2;
        NodeJogador jogador = new NodeJogador(novo);
        
        if(isEmpty() == true){
            inicio = jogador;
            inicio.setPrev(null);
            fim = inicio;
            fim.setPrev(inicio);
            qtd = 1;
        }else if(novo.getTotalPontos() >= inicio.getData().getTotalPontos()){ // insercao no inicio
            jogador.setNext(inicio);
            jogador.setPrev(null);
            inicio.setPrev(jogador);
            inicio = jogador;
            qtd ++;
            
        }else if(novo.getTotalPontos() <= fim.getData().getTotalPontos()){ // insercao no fim
            jogador.setPrev(fim);
            fim.setNext(jogador);
            fim = jogador;
            qtd++;
            
        }else{ // insercao no meio de aux e aux2
            aux = inicio;
            aux2 = inicio.getNext();
            
            while(aux2 != fim){
                if(novo.getTotalPontos() <= aux.getData().getTotalPontos()  &&  novo.getTotalPontos() > aux2.getData().getTotalPontos()){ // aux < novo < aux2
                    aux.setNext(jogador);
                    jogador.setPrev(aux);
                    jogador.setNext(aux2);
                    aux2.setPrev(jogador);
                    qtd++;
                }
                aux = aux2;
                aux2 = aux2.getNext();
            }
        }
    }
    
    public Jogador remover(String existe){
        
        NodeJogador aux,aux2,aux3;
        
        if(isEmpty() == true){
            return null;
        }else{
            aux = buscar(existe);
            if(qtd == 1){
                inicio = null;
                fim = null;
                qtd =0;
                return aux.getData();
            }else if(aux.getData().getTotalPontos() > inicio.getData().getTotalPontos()){ // é o 1 elemento
                inicio = inicio.getNext();
                inicio.setPrev(null);
                qtd --;
                return aux.getData();
            }else if(aux.getData().getTotalPontos() < fim.getData().getTotalPontos()){ // é o ultimo elemento
                aux2 = fim.getPrev();
                aux2.setNext(null);
                fim = aux2;
                qtd --;
                return aux.getData();
            }else{ // esta no meio
                aux2 = aux.getPrev();
                aux3 = aux.getNext();
                aux2.setNext(aux3);
                aux3.setPrev(aux2);
                qtd --;
                return aux.getData();
            }
        }
    }

    public int getQtd() {
        return qtd;
    }

    public NodeJogador getInicio() {
        return inicio;
    }
}
