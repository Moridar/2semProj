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
        try {
            KundeList = DB.getAllKunde();            
            OrdreList = DB.getAllOrder();
            KompList = DB.getAllKomponenter();
            StaffList = DB.getAllStaff();
            LastbilList = DB.getAllLastbil();
            LagerList = DB.getAllLager();
        } catch (Exception e) {
            System.out.println("bubugbug");
            System.out.println(e);
        }
    }

    public HashMap<Integer, Kunde> getKundeList() {
        return KundeList;
    }

    public HashMap<Integer, Ordre> getOrdreList() {
        return OrdreList;
    }

    public HashMap<Integer, Komponent> getKompList() {
        return KompList;
    }

    public HashMap<Integer, Staff> getStaffList() {
        return StaffList;
    }

    public HashMap<Integer, Lastbil> getLastbilList() {
        return LastbilList;
    }

    public HashMap<Integer, Lager> getLagerList() {
        return LagerList;
    }

}
