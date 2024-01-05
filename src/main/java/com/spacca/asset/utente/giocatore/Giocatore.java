package com.spacca.asset.utente.giocatore;

public class Giocatore implements GiocatoreInterface {

    String nickname;
    String password;

    public Giocatore(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    @Override
    public void gioca() {
        // TODO Auto-generated method stub

    }

    @Override
    public void mostraLeaderboard() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pesca() {
        // TODO Auto-generated method stub

    }

    @Override
    public void prendi() {
        // TODO Auto-generated method stub

    }

    @Override
    public void prendiTutto() {
        // TODO Auto-generated method stub

    }

    @Override
    public void scarta() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
