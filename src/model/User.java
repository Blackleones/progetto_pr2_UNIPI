package model;

/*
    OVERVIEW: la classe user rappresenta un singolo utente identificato da un nickname
*/
public class User {
    private String nickname;

    private User()
    {
        /*blocco la possibilà di usare il costruttore di default*/
    }

    public User(String nickname){
        this.nickname = nickname;

    }

    public String getNickname(){
        return nickname;
    }

    @Override
    public String toString() {
        return nickname;
    }
}
