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
 * @author Computer
 */
public class Lastbil {

    private String navn;
    private int telefon;
    private HashMap<Date, Ordre> transportskema;

    public Lastbil(String navn, int telefon) {
        this.navn = navn;
        this.telefon = telefon;
        transportskema = new HashMap<>();
    }

    //Tilføjer en opgave til transportskema
    public void addOpgave(Date d, Ordre o) {
        transportskema.put(d, o);
    }

    public void setTelefon(int t) {
        telefon = t;
    }

    public String getNavn() {
        return navn;
    }

    public int getTelefon() {
        return telefon;
    }

    public HashMap<Date, Ordre> getTransportskema() {
        return transportskema;
    }

}
