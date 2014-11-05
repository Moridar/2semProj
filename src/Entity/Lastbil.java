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
    //TransportSkema<Date, OrderID>
    private HashMap<Date, Integer> transportskema;

    public Lastbil(String navn, int telefon, HashMap<Date, Integer> transportskema) {
        this.navn = navn;
        this.telefon = telefon;
        this.transportskema = transportskema;
    }
 
    //Tilføjer en opgave til transportskema
    public void addOpgave(Date d, int oID) {
        transportskema.put(d, oID);
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

    public HashMap<Date, Integer> getTransportskema() {
        return transportskema;
    }

}
