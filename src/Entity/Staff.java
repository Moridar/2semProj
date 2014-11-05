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
    //arbejdsSkema<Date, OrderID>
    private HashMap<Date, Integer> arbejdeskema;

    public Staff(String navn, int telefon, String stilling, HashMap<Date, Integer> arbejdeskema) {
        this.navn = navn;
        this.telefon = telefon;
        this.stilling = stilling;
        this.arbejdeskema = arbejdeskema;
    }

    //Tilføj opgave i arbejdeskema
    public void addOpgave(Date d, Integer oID){
        arbejdeskema.put(d, oID);
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

    public HashMap<Date, Integer> getArbejdeskema() {
        return arbejdeskema;
    }
    
    
}
