/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdgsweden;

/**
 *
 * @author karin
 */
public class Validering 
{
  
    // Kontrollerar att text inte är tom och innehåller bokstäver (valfritt bindestreck/blanksteg)
    public static boolean arText(String text) {
        return text != null && text.matches("^[A-Za-zÅÄÖåäö0-9\\s\\-]+$");
    }

    // Kontrollerar att ett datum följer formatet YYYY-MM-DD
    public static boolean arDatum(String datum) {
        return datum != null && datum.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    // Kontrollerar att kostnad endast innehåller siffror (heltal)
    public static boolean arKostnad(String kostnad) {
        return kostnad != null && kostnad.matches("^\\d+$");
    }

    // Kontrollerar om fältet är tomt
    public static boolean arTomt(String text) {
        return text == null || text.trim().isEmpty();
    }
}


