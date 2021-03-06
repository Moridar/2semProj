/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entity.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Computer
 */
public class Control {

    private HashMap<Integer, Kunde> KundeList;
    private Date KundeListLastRefresh;
    private HashMap<Integer, Ordre> OrdreList;
    private Date OrdreListLastRefresh;
    private HashMap<Integer, Ordre> OldOrdreList;
    private Date OldOrdreListLastRefresh;
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
        OldOrdreListLastRefresh = new Date(0, 0, 1);
        StaffListLastRefresh = new Date(0, 0, 1);
        LastbilListLastRefresh = new Date(0, 0, 1);
        LagerListLastRefresh = new Date(0, 0, 1);
        KompListLastRefresh = new Date(0, 0, 1);
    }

    public HashMap<Integer, Kunde> getKundeList() {
        if (new Date().getTime() - KundeListLastRefresh.getTime() > 300000) {
            try {
                KundeList = DB.getAllKunde();
                KundeListLastRefresh = new Date();
            } catch (Exception e) {
                KundeList = new HashMap<>();
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
                OrdreList = new HashMap<>();
            }
        }
        return OrdreList;
    }
    
    public HashMap<Integer, Ordre> getOldOrdreList() {
        if (new Date().getTime() - OldOrdreListLastRefresh.getTime() > 300000) {
            try {
                OldOrdreList = DB.getAllOldOrder();
                OldOrdreListLastRefresh = new Date();
            } catch (Exception e) {
                OldOrdreList = new HashMap<>();
            }
        }
        return OldOrdreList;
    }

    public HashMap<Integer, Staff> getStaffList() {
        if (new Date().getTime() - StaffListLastRefresh.getTime() > 300000) {
            try {
                StaffList = DB.getAllStaff();
                StaffListLastRefresh = new Date();
            } catch (Exception e) {
                StaffList = new HashMap<>();
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
                LastbilList = new HashMap<>();
            }
        }
        return LastbilList;
    }

    public HashMap<Integer, Lager> getLagerList() {
        if (new Date().getTime() - LagerListLastRefresh.getTime() > 300000) {
            try {
                LagerList = DB.getAllLager();
                LagerListLastRefresh = new Date();
            } catch (Exception e) {
                LagerList = new HashMap<>();
            }
        }
        return LagerList;
    }

    public HashMap<Integer, Komponent> getKompList() {
        if (new Date().getTime() - KompListLastRefresh.getTime() > 300000) {
            try {
                KompList = DB.getAllKomponenter();
                KompListLastRefresh = new Date();
            } catch (Exception e) {
                KompList = new HashMap<>();
            }
        }
        return KompList;
    }

    public void createNewOrdre(Ordre o) {
        try {
            DB.createNewOrdre(DB.getHighestOrderID()+1, o);
            OrdreList = DB.getAllOrder();
        } catch (Exception e) {
            System.out.println("Control:createNewOrdre failed");
        }

    }

    public void createNewKunde(Kunde k) {
        int id = 0;
        for (Integer Kundeid : KundeList.keySet()) {
            if (Kundeid >= id) {
                id = Kundeid + 1;
            }
        }
        try {
            DB.createNewKunde(id, k);
            KundeList = DB.getAllKunde();
        } catch (Exception e) {
            System.out.println("Control:createNewKunde failed");
        }
    }

    public void createNewKomponent(Komponent k) {
        int id = 0;
        for (Integer Kompid : KompList.keySet()) {
            if (Kompid >= id) {
                id = Kompid + 1;
            }
        }
        try {
            DB.createNewKomponent(id, k);
            KompList = DB.getAllKomponenter();
        } catch (Exception e) {
            System.out.println("Control:createNewKomponent failed");
        }
    }
    
    public void createNewLastbil(Lastbil k) {
        int id = 0;
        for (Integer Lastbilid : LastbilList.keySet()) {
            if (Lastbilid >= id) {
                id = Lastbilid + 1;
            }
        }
        try {
            DB.createNewLastbil(id, k);
            LastbilList = DB.getAllLastbil();
        } catch (Exception e) {
            System.out.println("Control:createNewLastbil failed");
        }
    }
     
    public void createNewStaff(Staff s) {
        int id = 0;
        for (Integer Staffid : StaffList.keySet()) {
            if (Staffid >= id) {
                id = Staffid + 1;
            }
        }
        try {
            DB.createNewStaff(id, s);
            StaffList = DB.getAllStaff();
        } catch (Exception e) {
            System.out.println("Control:createNewStaff failed");
        }
    }
      
    public void updateOrdre(int id, Ordre o) {
        try {
            DB.updateOrdre(id, o);
            OrdreList = DB.getAllOrder();
        } catch (Exception e) {
            System.out.println("Control:updateOrdre failed");
        }
    }
    
    public void updateKomp(int id, Komponent k) {
        try {
            DB.updateKomponent(id, k);
            KompList = DB.getAllKomponenter();
        } catch (Exception e) {
            System.out.println("Control:updateKomp failed");
        }
    }
     public void updateLagerKomp(int Lagerid, int kompID, int antal) {
        try {
            DB.updateLagerKompList(Lagerid, kompID, antal);
            LagerList = DB.getAllLager();
        } catch (Exception e) {
            System.out.println("Control:updateKomp failed");
        }
    }
    
   public void printOrdreList(HashMap<Integer, Integer> OrdreKompList){
        try {
            Printer.printOrdreList(OrdreKompList, KompList);
        } catch (IOException ex) {
            System.out.println("Printer failed");;
        }
   }
    
    

}
