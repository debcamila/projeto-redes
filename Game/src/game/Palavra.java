
package game;

import java.util.ArrayList;

public class Palavra {
    private ArrayList <Letra> letras = new ArrayList(); // cria objeto letras do tipo Letra
    private ArrayList <String> acentos = new ArrayList(); // cria objeto acentos do tipo String
    private ArrayList <String> letraCorrigida = new ArrayList(); // cria objeto letraCorrigida do tipo String
    private ArrayList <Boolean> acertos = new ArrayList();
    private Letra blankLetra = new Letra(0, 0, "_");
    
    public ArrayList<Letra> getLetras(){
        return letras;
    }
    
    public ArrayList<Boolean> getAcertos(){
        return acertos;
    }
    
    public Letra getBlank(){
        return blankLetra;
    }
    
    // Corrige a letra baseado no indice certo
    public void corrigir(int indice){
        Letra l = this.letras.get(indice);
        
        if(l != null) {
            String letra = this.letraCorrigida.get(indice); // O índice da letraCorrigida é o mesmo da Letra na palavra
            if(letra != null) // Pra não ocorrer erro
                l.setLetra(letra); // Se existir uma letra corrigida para essa letra então faz a substituição
        }
    }
    
    public Boolean tentaLetra(String letra){
        Boolean achou = false;
        for(int i=0; i < letras.size(); i++){
            System.out.println(letras.get(i).getLetra());
            System.out.println(letra);
            if(letras.get(i).getLetra().toLowerCase().equals(letra)){
                achou = true;
                acertos.set(i, true);
            }
        }
        return achou;
    }
    
    //PALAVRA SEM ACENTO
    public Palavra(String palavra){//construtor
        for(int i = 0; i < palavra.length(); i++){
            letras.add(new Letra(String.valueOf(palavra.charAt(i))));
            acentos.add(""); // ArrayList tem tamanho 0, então sempre que entrar uma letra nova eu aumento 
            letraCorrigida.add(""); //o tamanho do acento para ficar igual ao tamanho da palavra
            acertos.add(false);
        }
    }
    
    public void addAcento(int indice, String acento, String letraCorrigida){
        acentos.set(indice, acento);
        this.letraCorrigida.set(indice, letraCorrigida);
    }
    
    public boolean verificarAcento(int indice, String acento) {
        return acento.equals(acentos.get(indice));
    }
    
    public int checarColisao(int mx, int my) {//cada letra tem sua propria colisao 
        for (int i = 0; i < letras.size(); i++) { //se pra cada letra do vetor de letras
            if(letras.get(i).checarMouse(mx, my) == true)//se eu to tocando nessa letra, que eu nao sei se eh correta ou talvez
                return i;
        }
        return -1;
    }
    
    public boolean checarAcerto(){
        boolean resultado = true;
        for(int a = 0; a < this.acertos.size(); a++){
            if(!this.acertos.get(a)){
                return false;
            }
        }
        return true;
    }
   
}
