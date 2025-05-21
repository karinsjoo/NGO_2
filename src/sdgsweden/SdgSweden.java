/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sdgsweden;

import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author karin
 */
public class SdgSweden {
    
    private static InfDB idb;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {
            idb = new InfDB("sdgsweden", "3306", "dbAdmin2024", "dbAdmin2024PW");
            new Inloggning(idb).setVisible(true);
            System.out.println("funkar");
        }catch (InfException ex)
        {
            System.out.println(ex.getMessage());
        }

    }
    
}
