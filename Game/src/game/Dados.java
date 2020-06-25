
package game;

import java.io.Serializable;

public class Dados implements Serializable{
    private String playerServerName;
    private String playerClientName;

    private boolean playing;
    private int playerServerScore;
    private int playerClientScore;
    private String categoria;
    private boolean isServerTurn;
    
    public Dados(boolean playing, int playerServerScore, int playerClientScore, String categoria, boolean isServerTurn) {
        this.playing = playing;
        this.playerServerScore = playerServerScore;
        this.playerClientScore = playerClientScore;
        this.categoria = categoria;
        this.isServerTurn = isServerTurn;
    }

    public String getPlayerServerName() {
        return playerServerName;
    }

    public void setPlayerServerName(String playerServerName) {
        this.playerServerName = playerServerName;
    }

    public String getPlayerClientName() {
        return playerClientName;
    }

    public void setPlayerClientName(String playerClientName) {
        this.playerClientName = playerClientName;
    }
    
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setPlayerServerScore(int playerServerScore) {
        this.playerServerScore = playerServerScore;
    }

    public void setPlayerClientScore(int playerClientScore) {
        this.playerClientScore = playerClientScore;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setIsServerTurn(boolean isServerTurn) {
        this.isServerTurn = isServerTurn;
    }

    public boolean getisPlaying() {
        return playing;
    }

    public int getPlayerServerScore() {
        return playerServerScore;
    }

    public int getPlayerClientScore() {
        return playerClientScore;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean getisIsServerTurn() {
        return isServerTurn;
    }
   
    
    
    
}
