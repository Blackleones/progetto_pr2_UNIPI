package MyTwPackage;

import model.Tag;
import model.Tweet;
import model.User;
import twException.*;

import java.util.ArrayList;
import java.util.List;

public class MyTw implements SimpleTw {

    /*
    * OVERVIEW: creazione e gestione di un sistema di microblogging formato da: utenti e tweet
    *---------------------------------------------------------------------------------------------------
    * FUNZIONE ASTRAZIONE:
    *   {
    *       password: chiave segreta per accedere al sistema
    *       messageSize: lunghezza massima del messaggio
    *       code: codice identificativo del singolo messaggio
    *
    *       <users[0], users[1], .... , users[n]>
    *           per ogni i appartenente [0, users.size()]
    *               user[i] = è una stringa che identifica l'utente
    *
    *       <tweets[0], tweets[1], .... , tweets[n]>
    *           per ogni i appartenente [0, tweets.size()]
    *
    *           tweets[i] = {
    *               code
    *               message
    *               tag
    *               user
    *           }
    *
    *       tweets[i] mantiene le informazioni che legano un messaggio a un utente e ai tag
    *---------------------------------------------------------------------------------------------------
    * INVARIANTE DI RAPPRESENTAZIONE:
    *   password != NULL
    *   code != NULL
    *   messageSize != NULL
    *
    *   users != NULL
    *
    *   per ogni i appartenente [0, users.size()]
    *       users[i] != null && user[i].nick != null
    *       per ogni k (con k != i) tale che users[i].nickname != users[k].nickname
    *
    *   tweets != NULL
    *
    *   per ogni i appartenente [0, tweets.size()]
    *       tweets[i] != null &&
    *                          tweets[i].code != null
    *                          tweets[i].t != null
    *                          tweets[i].user != null
    *                          tweets[i].message != null
    *       esiste k tale che tweet[i].user = users[k]  <- collegamento tra un messaggio e un utente
    *       per ogni k (con k != i) tale che tweets[i].code != tweets[k].code
    * */

    private final String password;
    private List<User> users;
    private List<Tweet> tweets;
    private static int code = 0;
    private static int messageSize = 140;

    public MyTw(String password){
        this.password = password;
        users = new ArrayList<User>();
        tweets = new ArrayList<Tweet>();
    }

    /*
    * R: -
    * E: ritorna i se esiste un utente collegato con nickname = bob.nickname, -1 altrimenti
    * M: -
    * */
    private int checkUser(User bob){
        for(int i = 0; i < users.size(); i++){
            User u = users.get(i);

            //verifico che non esista gia un utente con nickname = bob.nickname
            if(u.getNickname() == bob.getNickname())
                return i;
        }

        return -1;
    }

    /*
    * R: bob != null && pass != null
    * E: lancia UnauthorizedAccessException se l'utente è uguale a null e se la password non è corretta,
    *      altrimenti aggiunge bob alla lista utenti
    * M: aggiunge bob alla lista utenti
    * */
    @Override
    public void addUser(User bob, String pass) throws UnauthorizedAccessException {
        if(bob == null || pass == null || !pass.equals(password))
            throw new UnauthorizedAccessException();

        if(checkUser(bob) == -1)
            users.add(bob);
    }

    /*
    * R: bob != null && pass != null
    * E: lancia UnauthorizedAccessException se l'utente è uguale a null e se la password non è corretta,
    *       altrimenti rimuove bob dalla lista utenti
    * M: rimuove bob dalla lista utenti
    * */
    @Override
    public void deleteUser(User bob, String pass) throws UnauthorizedAccessException {
        if(bob == null || pass == null || !pass.equals(password))
            throw new UnauthorizedAccessException();

        int index = checkUser(bob);

        if(index != -1)
            users.remove(index);
    }

    /*
    * R: message != null && t != null && bob != null
    * E: lancia UnauthorizedUserException se bob è uguale a null oppure se non appartiene alla lista degli utenti
    *    lancia un Msg Exception se il messaggio è uguale a null o se supera la lunghezza prestabilita,
    *    altrimenti aggiunge un nuovo tweet di bob con intestazione t alla lista dei tweets
    * M: aggiunge un nuovo tweet alla lista dei tweets
    * */
    @Override
    public int tweet(String message, Tag t, User bob) throws UnauthorizedUserException, MsgException {
        if(bob == null || checkUser(bob) == -1)
            throw new UnauthorizedUserException();

        if(message == null || message.length() > messageSize || t == null)
            throw new MsgException();

        Tweet tweet = new Tweet(code, t, message, bob);
        tweets.add(tweet);

        return code++;
    }

    /*
    * R: -
    * E: ritorna l'ultimo tweet di bob. lancia un eccezione se non trova nessun messaggio di bob (questo implica anche
    *       il caso bob == null)
    * M: -
    * */
    @Override
    public String readLast(User bob) throws EmptyMsgException {

        //se tweets è empty => i = -1 => no ciclo
        for(int i = tweets.size()-1; i >= 0; i--)
            if(tweets.get(i).getUser() == bob)
                return tweets.get(i).getMessage();

        throw new EmptyMsgException();
    }

    /*
    * R: -
    * E: ritorna l'ultimo tweet scritto. lancia un EmptyMsgException se la lista tweets è vuota
    * M: -
    * */
    @Override
    public String readLast() throws EmptyMsgException {
        if(empty())
            throw new EmptyMsgException();

        return tweets.get(tweets.size()-1).getMessage();
    }

    /*
    * R: -
    * E: ritorna una lista contenente tutti i messaggi relativi al tag t. se t è == null viene ritornata una lista vuota
    *       a causa dell'invariante di rappresentazione (tweets[i].t != null)
    * M: -
    * */
    @Override
    public List<String> readAll(Tag t) {
        List<String> messages = new ArrayList<String>();

        if(empty() || t == null)
            return messages;

        for(int i = 0; i < tweets.size(); i++)
            if(tweets.get(i).getTag().containTag(t.getTags()))
                messages.add(tweets.get(i).getMessage());

        return messages;
    }

    /*
    * R: -
    * E: ritorna una lista contenente tutti i messaggi scritti
    * M: -
    * */
    @Override
    public List<String> readAll() {
        List<String> messages = new ArrayList<String>();

        if(empty())
            return messages;

        for(int i = 0; i < tweets.size(); i++)
            messages.add(tweets.get(i).getMessage());

        return messages;
    }

    /*
    * R: -
    * E: rimuove il tweets[i] tale che tweets[i].code = code. lancia WrongCodeException se non esiste alcun
    *       tweets tale che tweets[i].code == code (questo implica anche i casi: code == null e code errato)
    * M: modifica la lista tweets
    * */
    @Override
    public void delete(int code) throws WrongCodeException {
        if(empty())
            return;

        for(int i = 0; i < tweets.size(); i++)
            if(tweets.get(i).getId() == code){
                tweets.remove(i);
                return;
            }

        throw new WrongCodeException();
    }
    /*
    * R: -
    * E: ritorna true se tweets è vuota, false altrimenti
    * M: -
    * */
    @Override
    public boolean empty() {
        return tweets.isEmpty();
    }
}
