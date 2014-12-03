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
    //KompList<KompID, Antal>
    private HashMap<Integer, Integer> KompList;

    public Lager(String navn) {
        this.navn = navn;
        this.KompList = new HashMap<>();
    }

    public String getNavn() {
        return navn;
    }

    public HashMap<Integer, Integer> getKompList() {
        return KompList;
    }
}
