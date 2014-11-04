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
    HashMap<Integer, Kunde> KundeList;
    HashMap<Integer, Ordre> OrdreList;
    HashMap<Integer, Komponent> KompList;
    HashMap<Integer, Staff> StaffList;
    HashMap<Integer, Lastbil> LastbilList;
    HashMap<Integer, Lager> LagerList;

    public Control() {
        KundeList = new HashMap<>();
        OrdreList = new HashMap<>();
        KompList = new HashMap<>();
        StaffList = new HashMap<>();
        LastbilList = new HashMap<>();
        LagerList = new HashMap<>();
               
    }
    
    
}
