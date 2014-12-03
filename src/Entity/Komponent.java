/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.util.HashMap;

/**
 *
 * @author Computer
 */
public class Komponent {
    private String navn;
    private int Opbygningtid;
    
    public Komponent(String navn) {
        this.navn = navn;
        Opbygningtid = 0;
     }

    public Komponent(String navn, int Opbygningtid) {
        this.navn = navn;
        this.Opbygningtid = Opbygningtid;
    }
    
    public String getNavn() {
        return navn;
    }

    public int getOpbygningtid() {
        return Opbygningtid;
    }
    
}


