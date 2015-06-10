package model;

/*
    OVERVIEW: tweet rappresenta un messaggio scritto da un utente
        un messaggio è identificato da:
            un codice
            un messaggio
            una lista di tag
            l'utente che ha scritto il messaggio
*/
public class Tweet {
    private int id;
    private String message;
    private Tag tag;
    private User user;

    private Tweet()
    {
        /*blocco il costruttore di default*/
    }

    public Tweet(int id, Tag tag, String message, User user)
    {
        this.id = id;
        this.tag = tag;
        this.message = message;
        this.user = user;
    }

    public int getId(){
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Tag getTag() {
        return tag;
    }

    public User getUser(){
        return user;
    }
}
