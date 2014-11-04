/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.util.ArrayList;

/**
 *
 * @author Umair
 */
public class Kunde {
    private String name;
    private int telefon;
    private String email;
    private int rabat;
    private ArrayList<Ordre> ordreList;

    public Kunde(String name, int telefon, String email, int rabat, ArrayList<Ordre> ordreList) {
        this.name = name;
        this.telefon = telefon;
        this.email = email;
        this.rabat = rabat;
        this.ordreList = ordreList;
    }

    public String getName() {
        return name;
    }

    public int getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }

    public int getRabat() {
        return rabat;
    }

    public ArrayList<Ordre> getOrdreList() {
        return ordreList;
    }
    
    
    
    
}
