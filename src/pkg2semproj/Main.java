/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg2semproj;
/**
 *
 * @author Computer
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("lols");
        
        Runtime rt=Runtime.getRuntime();

        String file="ToBePrint.txt";
        try {
            Process p= rt.exec("notepad "+file); // a space is required after notepad
        } catch (Exception e) {
        }
        
    }
    
}
