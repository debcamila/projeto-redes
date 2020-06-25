
package game;

import javafx.scene.image.Image;

public class Potion {
    private int x;
    private int y;
    private int largura = 40;
    private int altura = 40;
    private Image pot;
    private Image cinza;
    private int posiInicialX;
    private int posiInicialY;
    
    public boolean isSelecionado = false;
    public String acento;
    public boolean usado = false;

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public String getAcento() {
        return acento;
    }

    public void setAcento(String acento) {
        this.acento = acento;
    }
    
   public Potion(String img, String imgCinza, int x, int y, String acento) {
        this.pot = new Image(img);
        this.cinza = new Image(imgCinza);
        this.x = x;
        this.y = y;
        this.posiInicialX = x;
        this.posiInicialY = y;
        this.acento = acento;
    }
        
    public void setX(int value){
        x = value;
    }
    
    public int getX(){
        return x;
    }
    
    public void setY(int value){
        y = value;
    }
    
    public int getY(){
        return y;
    }
    
    public void setLargura(int l){
        largura = l;
    }
    
    public int getLargura(){
        return largura;
    }
    
    public void setAltura(int a){
        altura = a;
    }
    
    public int getAltura(){
        return altura;
    }
    
    public void setPosX(int posiX){
        posiInicialX = posiX;
    }
    
    public int getPosX(){
        return posiInicialX;
    }
    
    public void setPosY(int posiY){
        posiInicialY = posiY;
    }
    
    public int getPosY(){
        return posiInicialY;
    }
    
    public void setImage(String img){
        pot = new Image(img);
    }
    
    public Image getImg(){
        if(!usado){
            return pot;
        }else{
            return cinza;
        }
    }
    
    public void resetar() {
        x = posiInicialX;
        y = posiInicialY;
    }
    
    public boolean checarMouse(int mx, int my) {
        return mx > x &&
               mx <= x + largura &&
               my > y &&
               my <= y + altura; 
    }
}
