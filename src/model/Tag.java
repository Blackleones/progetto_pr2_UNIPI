package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/*
    OVERVIEW: lista di tag associati a un messaggio.
*/
public class Tag {
    private List<String> tags;

    private Tag(){
        /*blocco il costruttore di default*/
    }

    /*
        prendo in ingresso un array di stringhe a dimensione dinamica
        successivamente creo un arraylist con le stringhe ricevute
    */
    public Tag(String ... s){

        tags = new ArrayList<String>(Arrays.asList(s));
    }

    public List<String> getTags(){
        return tags;
    }

    /*
        creo una lista di comparazione con gli stessi argomenti di t (cosi da non eliminare direttamente t)
        dalla lista di comparazione tolgo tutti gli elementi che sono presenti in tags e se ottengo
        una lista vuota => t è incluso(uguale) a tags, quindi ritorno True, false altrimenti
    */
    public boolean containTag(List<String> t){
        List<String> compare = new ArrayList<>(t);
        compare.removeAll(tags);

        if(compare.isEmpty())
            return true;

        return false;
    }

    @Override
    public String toString() {
        String s = ""+tags.get(0);

        for(int i = 1; i < tags.size(); i++)
            s = s + " " + tags.get(i);

        return s;
    }
}
