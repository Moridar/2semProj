/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Entity.*;
import java.util.ArrayList;

/**
 *
 * @author Computer
 */
public class Control {
    ArrayList<Kunde> KundeList;
    ArrayList<Ordre> OrdreList;
    ArrayList<Komponent> KompList;
    ArrayList<Staff> StaffList;
    ArrayList<Lastbil> LastbilList;
    ArrayList<Lager> LagerList;

    public Control() {
        KundeList = new ArrayList<>();
        OrdreList = new ArrayList<>();
        KompList = new ArrayList<>();
        StaffList = new ArrayList<>();
        LastbilList = new ArrayList<>();
        LagerList = new ArrayList<>();
               
    }
    
    
}
