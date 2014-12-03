/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 *
 * @author Bobbie Apitzsch
 */
public class Printer {

    public static boolean printOrdreList(HashMap<Integer, Integer> OrdreKompList, HashMap<Integer, Komponent> KompList) throws IOException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter("ToBePrint.txt");
        } catch (Exception e) {
            return false;
        }
        for (Integer KompID : OrdreKompList.keySet()) {
            pw.println(OrdreKompList.get(KompID) + ": " + KompList.get(KompID).getNavn());
        }
        Runtime rt=Runtime.getRuntime();

        String file="ToBePrint.txt";

        Process p= rt.exec("notepad "+file); // a space is required after notepad
        pw.close();
        return true;

    }
}
