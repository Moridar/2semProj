/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.HashMap;
import java.util.Date;

/**
 *
 * @author Computer
 */
public class Ordre {

    private int SalgsmedarbsID;
    private int KundeID;
    private String vej;
    private int PostNR;
    private boolean confirmation;
    private double pris;
    private Date datoStart;
    private Date datoSlut;
    
    //KompList<KompID, Antal>
    private HashMap<Integer, Integer> KompList;
    private HashMap<Integer, Date> StaffList;
    private HashMap<Integer, Date> LastbilList;

    public Ordre(int SalgsmedarbsID, int KundeID, String vej, int PostNR, boolean confirmation, double pris, Date datoStart, Date datoSlut, HashMap<Integer, Integer> KompList, HashMap<Integer, Date> StaffList, HashMap<Integer, Date> LastbilList) {
        this.SalgsmedarbsID = SalgsmedarbsID;
        this.KundeID = KundeID;
        this.vej = vej;
        this.PostNR = PostNR;
        this.confirmation = confirmation;
        this.pris = pris;
        this.datoStart = datoStart;
        this.datoSlut = datoSlut;
        this.KompList = KompList;
        this.StaffList = StaffList;
        this.LastbilList = LastbilList;
    }
    
    public Ordre(int SalgsmedarbsID, int KundeID, String vej, int PostNR, double pris, Date datoStart, Date datoSlut) {
        this.SalgsmedarbsID = SalgsmedarbsID;
        this.KundeID = KundeID;
        this.vej = vej;
        this.PostNR = PostNR;
        this.pris = pris;
        this.datoStart = datoStart;
        this.datoSlut = datoSlut;
        confirmation = false;
    }

    public void confirmThis() {
        confirmation = true;
    }

    public void extendOrdre(Date d) {
        datoSlut = d;
    }

    public void setPrice(int p) {
        pris = p;
    }

    public int getSalgsmedarbsID() {
        return SalgsmedarbsID;
    }

    public int getKundeID() {
        return KundeID;
    }

    public String getVej() {
        return vej;
    }

    public int getPostNR() {
        return PostNR;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public double getPris() {
        return pris;
    }

    public Date getDatoStart() {
        return datoStart;
    }

    public Date getDatoSlut() {
        return datoSlut;
    }

//    public HashMap<Integer, Komponent> getKompList() {
//        return KompList;
//    }
//
//    public HashMap<Integer, Staff> getStaffList() {
//        return StaffList;
//    }
//
//    public HashMap<Integer, Lastbil> getLastbilList() {
//        return LastbilList;
//    }
}
