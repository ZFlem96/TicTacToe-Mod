package tictactoemod.game.tictactoemod;

/**
 * Created by ZFleezy on 7/13/2017.
 */

class Player {
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    String playerName;

    public String getPlayerSymbol() {
        return playerSymbol;
    }

    public void setPlayerSymbol(String playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    String playerSymbol;

    public Player(){}
}
