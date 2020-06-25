
package palavras;

import game.Palavra;
import java.util.ArrayList;
import java.util.Random;

public class PalavraRandom {
    ArrayList<Palavra> comida = new ArrayList();
    ArrayList<Palavra> objeto = new ArrayList();
    ArrayList<Palavra> esporte = new ArrayList();
    
    public PalavraRandom(){
        comida.add(new Farofa());
        comida.add(new Feijao());
        comida.add(new Arroz());
        
        objeto.add(new Armario());
        objeto.add(new Cama());
        objeto.add(new Ventilador());
        
        esporte.add(new Futebol());
        esporte.add(new Volei());
        esporte.add(new Basquete());
    }
    
    public Palavra gerarPalavra(int categoria) {
        if(categoria == 0){
            Random rand = new Random();
            int pal = rand.nextInt(3);
            return comida.get(pal);
        }else if (categoria == 1){
            Random rand = new Random();
            int pal = rand.nextInt(3);
            return objeto.get(pal);
        }else if(categoria == 2){
            Random rand = new Random();
            int pal = rand.nextInt(3);
            return esporte.get(pal);
        }
        return null;
    }
    public int proxPalavra(int atual){
        return (atual +1)%10;
    }
}
