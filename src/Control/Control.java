/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entity.*;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Computer
 */
public class Control {

    private HashMap<Integer, Kunde> KundeList;
    private Date KundeListLastRefresh;
    private HashMap<Integer, Ordre> OrdreList;
    private Date OrdreListLastRefresh;
    private HashMap<Integer, Staff> StaffList;
    private Date StaffListLastRefresh;
    private HashMap<Integer, Lastbil> LastbilList;
    private Date LastbilListLastRefresh;
    private HashMap<Integer, Lager> LagerList;
    private Date LagerListLastRefresh;
    private HashMap<Integer, Komponent> KompList;
    private Date KompListLastRefresh;

    public Control() {
        KundeListLastRefresh = new Date(0, 0, 1);
        OrdreListLastRefresh = new Date(0, 0, 1);
        StaffListLastRefresh = new Date(0, 0, 1);
        LastbilListLastRefresh = new Date(0, 0, 1);
        LagerListLastRefresh = new Date(0, 0, 1);
        KompListLastRefresh = new Date(0,0,1);
    }

    public HashMap<Integer, Kunde> getKundeList() {
        if (new Date().getTime() - KundeListLastRefresh.getTime() > 300000) {
            try {
                KundeList = DB.getAllKunde();
                KundeListLastRefresh = new Date();
            } catch (Exception e) {
            }
        }
        return KundeList;
    }

    public HashMap<Integer, Ordre> getOrdreList() {
        if (new Date().getTime() - OrdreListLastRefresh.getTime() > 300000) {
            try {
                OrdreList = DB.getAllOrder();
                OrdreListLastRefresh = new Date();
            } catch (Exception e) {
            }
        }
        return OrdreList;
    }

    public HashMap<Integer, Staff> getStaffList() {
        if (new Date().getTime() - StaffListLastRefresh.getTime() > 300000) {
            try {
                StaffList = DB.getAllStaff();
                StaffListLastRefresh = new Date();
            } catch (Exception e) {
            }
        }
        return StaffList;
    }

    public HashMap<Integer, Lastbil> getLastbilList() {
        if (new Date().getTime() - LastbilListLastRefresh.getTime() > 300000) {
            try {
                LastbilList = DB.getAllLastbil();
                LastbilListLastRefresh = new Date();
            } catch (Exception e) {
            }
        }
        return LastbilList;
    }

    public HashMap<Integer, Lager> getLagerList() {
        if (new Date().getTime() - LagerListLastRefresh.getTime() > 300000) {
            try {
                LagerList = DB.getAllLager();
                for (int id : LagerList.keySet()) {
                    LagerList.get(id).setKompList(DB.getKompToLager(id));
                }
                LagerListLastRefresh = new Date();
            } catch (Exception e) {
            }
        }
        return LagerList;
    }
    
    public HashMap<Integer, Komponent> getKompList(){
        if (new Date().getTime() - KompListLastRefresh.getTime() > 300000) {
            try {
                KompList = DB.getAllKomponenter();
                KompListLastRefresh = new Date();
            } catch (Exception e) {
            }
        }
        return KompList;
    }
    
    public void createNewOrdre(Ordre o){
        int id = 0;
        for (Integer Ordreid : OrdreList.keySet()) {
            if(Ordreid >= id) id = Ordreid +1;
        }        
        try {
            DB.createNewOrdre(id, o);
            OrdreList = DB.getAllOrder();
        } catch (Exception e) {
            System.out.println("Control:createNewOrdre failed");
        }
        
    }
}
