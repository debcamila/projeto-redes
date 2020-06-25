
package game;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Letra {
    private int x;
    private int y;
    private int altura = 32;
    private int largura = 32;
    private String letra;
    public Color cor = Color.WHITE;
    private boolean isAtivo = true;
    
    public Letra(int x, int y, String l){
        this.x = x;
        this.y = y;
        this.letra = l;
    }
    
    public Letra(String l){
        this.letra = l;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }

    public String getLetra() {
        return letra;
    }
    
    // checar se o mouse X e Y está dentro desta letra
    public boolean checarMouse(int mx, int my) {
        return mx > x &&
               mx <= x + largura &&
               my > y &&
               my <= y + altura; 
    }
    
    public void render(GraphicsContext gc) {//desenhar a letra
        gc.setFill(cor);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(letra, x - 5, y + 10);
        
        // debug
        //gc.setFill(Color.WHITE);
        //gc.strokeRect(x, y, largura, altura);
    }

    
    public boolean isIsAtivo() {
        return isAtivo;
    }

    
    public void setIsAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }
    
}

