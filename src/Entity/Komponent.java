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
    private int PrisPerDag;
    private int Opbygningtid;
    
    public Komponent(String navn, int PrisPerDag) {
        this.navn = navn;
        this.PrisPerDag = PrisPerDag;
     }

    public Komponent(String navn, int PrisPerDag, int Opbygningtid) {
        this.navn = navn;
        this.PrisPerDag = PrisPerDag;
        this.Opbygningtid = Opbygningtid;
    }
    
    
    public void updatePris(int p){
        PrisPerDag = p;
    }

    public String getNavn() {
        return navn;
    }

    public int getPrisPerDag() {
        return PrisPerDag;
    }

    public int getOpbygningtid() {
        return Opbygningtid;
    }
    
}


