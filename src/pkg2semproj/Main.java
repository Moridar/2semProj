/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg2semproj;

import Control.Control;

/**
 *
 * @author Computer
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Umair!");
        for (String string : args) {
            System.out.println(string);
        }
        Control con = new Control();
        System.out.println(con.getKundeList().get(0).getName());
        System.out.println("lel");
    }
    
}
