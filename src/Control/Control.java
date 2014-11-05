/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Entity.*;
import java.util.HashMap;

/**
 *
 * @author Computer
 */
public class Control {
    private HashMap<Integer, Kunde> KundeList;
    private HashMap<Integer, Ordre> OrdreList;
    private HashMap<Integer, Komponent> KompList;
    private HashMap<Integer, Staff> StaffList;
    private HashMap<Integer, Lastbil> LastbilList;
    private HashMap<Integer, Lager> LagerList;

    public Control() {
        KundeList = new HashMap<>();
        OrdreList = new HashMap<>();
        KompList = new HashMap<>();
        StaffList = new HashMap<>();
        LastbilList = new HashMap<>();
        LagerList = new HashMap<>();
           
    }
    
    public Kunde getKunde(int kundeID){
        return KundeList.get(kundeID);
    }
}
