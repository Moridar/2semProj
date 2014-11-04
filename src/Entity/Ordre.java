/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.util.ArrayList;
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
//    private ArrayList<Komponent> KompList;
//    private ArrayList<Staff> StaffList;
//    private ArrayList<Lastbil> LastbilList;
    
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

    public void confirmThis(){
        confirmation = true;
    }
    public void extendOrdre(Date d){
        datoSlut = d;
    }
    public void setPrice(int p){
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

//    public ArrayList<Komponent> getKompList() {
//        return KompList;
//    }
//
//    public ArrayList<Staff> getStaffList() {
//        return StaffList;
//    }
//
//    public ArrayList<Lastbil> getLastbilList() {
//        return LastbilList;
//    }

    
    
    
    
    
}
