import MyTwPackage.MyTw;
import MyTwPackage.SimpleTw;
import model.Tag;
import model.User;
import twException.*;

import java.util.List;
import java.util.Random;

/**
 * Created by blackleones on 27/05/15.
 */
public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    //modificare NUM_MEX con i seguenti valori: 0 | 5 | >= 20 per verificare situazioni diverse (nessun messaggio, etc)
    private static int NUM_MEX = 15;
    private static String password = "password";

    public static void stampaTag(List<String> l, Tag t){

        for(int i = 0; i < l.size(); i++)
            System.out.println(ANSI_YELLOW+t+ANSI_RESET+": "+l.get(i));
    }

    public static void stampa(List<String> l){
        System.out.println("TUTTI I MESSAGGI INSERITI:");

        for(int i = 0; i < l.size(); i++)
            System.out.println(l.get(i));
    }

    public static void main(String args[]){
        SimpleTw tw = new MyTw(password);

        User[] u = {new User("utente 1"), new User("utente 2"), new User("utente 3"), new User("Utente 4")};

        //utente1 sbaglia la password

        try{
            tw.addUser(u[0], "PassWord");
        }catch(UnauthorizedAccessException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: UnauthorizedAccessException"+ANSI_RESET);
            System.out.println("\tmotivo: "+u[0]+" ha sbagliato password");
        }

        try{
            tw.addUser(u[0], null);
        }catch(UnauthorizedAccessException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: UnauthorizedAccessException"+ANSI_RESET);
            System.out.println("\tmotivo: "+u[0]+" ha sbagliato password");
        }

        //utente null tenta l'accesso

        try{
            tw.addUser(null, "password");
        }
        catch(UnauthorizedAccessException e){
            System.out.println(ANSI_RED+"eccezione catturata: UnauthorizedAccessException"+ANSI_RESET);
            System.out.println("\tmotivo: tentato accesso da utente NULL");
        }

        tw.addUser(u[0], password);
        tw.addUser(u[1], password);
        tw.addUser(u[2], password);
        tw.addUser(u[3], password);

        //tento di leggere un messaggio di u[2] ma la lista tweets è vuota

        try{
            tw.readLast(u[2]);
        }catch(EmptyMsgException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: EmptyMsgException"+ANSI_RESET);
            System.out.println("\tmotivo: "+u[2]+" non ha scritto ancora nulla");
        }

        Tag t[] = {
                new Tag("#UNIPI"),
                new Tag("#UNIPI", "#FIBONACCI"),
                new Tag("#PRL2")
        };

        Random r = new Random();

        for(int i = 0; i < NUM_MEX; i++){
            String message = "messaggio numero "+i;

            tw.tweet(message, t[r.nextInt(t.length)], u[r.nextInt(u.length)]);
        }

        //tento di inserire un tweet non corretto

        try{
            tw.tweet(null, t[1], u[1]);
        }
        catch(MsgException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: MsgException"+ANSI_RESET);
            System.out.println("\tmotivo: messaggio == null");
        }

        try{
            tw.tweet("ciao", null, u[1]);
        }
        catch(MsgException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: MsgException"+ANSI_RESET);
            System.out.println("\tmotivo: tag == null");
        }

        try{
            tw.tweet("ciao", t[1], new User("nessuno"));
        }
        catch(UnauthorizedUserException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: UnauthorizedUserException"+ANSI_RESET);
            System.out.println("\tmotivo: utente non registrato");
        }

        try{
            String s = "asdasdasdasdasdasdasdasdasdsadasdasdasdasdsadasdasdasdasdasdsadsada"
                        +"asdasdasdsadsadsadasdsadasdasdasdsadasdasdasdsadsadsadsadsadsadsad"
                        +"saasdasdasdasdasdsadsadsadsa";

            tw.tweet(s, t[1], u[1]); 
        }catch(MsgException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: MsgException"+ANSI_RESET);
            System.out.println("\tmotivo: messaggio oltre i 140 caratteri");            
        }

        //leggo l'ultimo messaggio di tutti gli utenti, qualcuno potrebbe non avere mai scritto

        try{
            String m1 = tw.readLast(u[0]);
            System.out.println("ultimo messaggio di "+ u[0] +" "+ m1);
        }catch(EmptyMsgException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: EmptyMsgException"+ANSI_RESET);
            System.out.println("utente "+u[0]+" non ha scritto niente");
        }

        try{
            String m1 = tw.readLast(u[1]);
            System.out.println("ultimo messaggio di "+ u[1] + " " + m1);
        }catch(EmptyMsgException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: EmptyMsgException"+ANSI_RESET);
            System.out.println("utente "+u[1]+" non ha scritto niente");
        }

        try{
            String m1 = tw.readLast(u[2]);
            System.out.println("ultimo messaggio di "+ u[2] +" "+ m1);
        }catch(EmptyMsgException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: EmptyMsgException"+ANSI_RESET);
            System.out.println("utente "+u[2]+" non ha scritto niente");
        }

        try{
            String m1 = tw.readLast(u[3]);
            System.out.println("ultimo messaggio di "+ u[3] + " " +m1);
        }catch(EmptyMsgException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: EmptyMsgException"+ANSI_RESET);
            System.out.println("utente "+u[3]+" non ha scritto niente");
        }

        //leggo tutti i messaggi  di ogni tag, qualche tag potrebbe non essere mai stato usato

        List<String> messaggi = null;

        messaggi = tw.readAll(t[0]);
        if(messaggi.size() != 0)
            stampaTag(messaggi, t[0]);
        else
            System.out.println(ANSI_RED+"IL TAG "+t[0]+" NON HA MESSAGGI ASSOCIATI"+ANSI_RESET);

        messaggi = tw.readAll(t[1]);
        if(messaggi.size() != 0)
            stampaTag(messaggi, t[1]);
        else
            System.out.println(ANSI_RED+"IL TAG "+t[1]+" NON HA MESSAGGI ASSOCIATI"+ANSI_RESET);

        messaggi = tw.readAll(t[2]);
        if(messaggi.size() != 0)
            stampaTag(messaggi, t[2]);
        else
            System.out.println(ANSI_RED+"IL TAG "+t[2]+" NON HA MESSAGGI ASSOCIATI"+ANSI_RESET);

        //leggo tutti i messaggi scritti
        messaggi = tw.readAll();
        stampa(messaggi);

        //elimino un messaggio che non esiste
        try{
            tw.delete(123456);
        }catch(WrongCodeException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: WrongCodeException"+ANSI_RESET);
            System.out.println("\tmotivo: il codice 123546 non è presente");
        }

        //controllo empty, ora deve valere false
        System.out.println("il valore di isEmpty è "+tw.empty());

        //elimino tutti i messaggi
        for(int i = 0; i < NUM_MEX; i++)
            tw.delete(i);

        //controllo empty, ora deve valere true
        System.out.println("il valore di isEmpty è "+tw.empty());

        //elimino un utente errato

        try{
            tw.deleteUser(null, password);
        }
        catch(UnauthorizedAccessException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: UnauthorizedAccessException"+ANSI_RESET);
           System.out.println("\tmotivo: non esiste utente NULL");
        }

        try{
            tw.deleteUser(u[1], "psw");
        }
        catch(UnauthorizedAccessException e)
        {
            System.out.println(ANSI_RED+"eccezione catturata: UnauthorizedAccessException"+ANSI_RESET);
            System.out.println("\tmotivo: password errata");
        }

        //elimino tutti gli utenti
        tw.deleteUser(u[0], password);
        tw.deleteUser(u[1], password);
        tw.deleteUser(u[2], password);

        System.out.println("");
        System.out.println(ANSI_GREEN+"**************************************************");
        System.out.println("!!!!!!!!!        TEST SUPERATO           !!!!!!!!!");
        System.out.println("**************************************************"+ANSI_RESET);
    }
}
