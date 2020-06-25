
package game;

public class NodeJogador {
    private Jogador data;
    private NodeJogador next;
    private NodeJogador prev;
    
    public NodeJogador (Jogador num){
        data = num;
    }  
    
    public Jogador getData() {
        return data;
    }
    
    public void setData(Jogador d) {
        this.data = d;
    }
    
    public NodeJogador getNext() {
        return next;
    }
    
    public void setNext(NodeJogador n) {
        this.next = n;
    }
    
    public NodeJogador getPrev() {
        return prev;
    }
    
    public void setPrev(NodeJogador p) {
        this.prev = p;
    }
}
