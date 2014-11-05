/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Umair
 */
public class Staff {
    private String navn;
    private int telefon;
    private String stilling;
    private HashMap<Date, Ordre> arbejdeskema;

    public Staff(String navn, int telefon, String stilling) {
        this.navn = navn;
        this.telefon = telefon;
        this.stilling = stilling;
        this.arbejdeskema = new HashMap<>();
    }

    //Tilføj opgave i arbejdeskema
    public void addOpgave(Date d, Ordre o){
        arbejdeskema.put(d, o);
    }
    
    public String getNavn() {
        return navn;
    }

    public int getTelefon() {
        return telefon;
    }

    public String getStilling() {
        return stilling;
    }

    public HashMap<Date, Ordre> getArbejdeskema() {
        return arbejdeskema;
    }
    
    
}
