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
public class Lager {
    private String navn;
    private HashMap<Komponent, Integer> KompList;

    public Lager(String navn, HashMap<Komponent, Integer> KompList) {
        this.navn = navn;
        this.KompList = KompList;
    }
    
}
