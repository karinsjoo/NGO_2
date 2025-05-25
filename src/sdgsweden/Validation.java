/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdgsweden;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Eftersom alla metoder nedan är satta som static behöver vi ingen konstruktor
 * Alla metoder kan därmed kallas på direkt utan att skapa en instans av klassen
 * @author erikaekholm
 */
public class Validation{

/**
* Kollar om e-postadress har rätt format
* @param epost som ska kollas
* @return "true" om epost har rätt format annars "false
*/
public static boolean okEpost(String epost){
    if(isNullOrEmpty(epost)){
    return false;
    }
    return epost.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

/**
 * Kollar om String är tom eller null
 * @param telefon som ska kollas
 * @return "true" om den är tom eller null, annars "false"
 */
public static boolean okTelefon(String telefon){
    return telefon.matches("\\d{7,15}"); // Tillåter 7 till 15 siffror
}

/**
 * Kollar om String är tom eller null
 * @param text som ska kollas
 * @return "true" om den är tom eller null, annars "false"
 */
public static boolean okNullEllerTom(String text){
    return text != null && !text.trim().isEmpty();
}

/**
 * Kollar att lösenord innehåller bokstäver, siffror och är emellan 7-15 tecken långt
 * @param losenord
 */
public static boolean okLosenord(String losenord){
    return losenord.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,15}$");
}

/**
 * Kollar om datum är av formatet YYYY-MM-DD
 */
public static boolean okDatumFormat(String datum){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false); // Förhindrar datum som inte finns ex skottdag etc
    try {
        Date parseDate = sdf.parse(datum);
        return true;
    } catch (ParseException ex){
        return false;
    }
}

}
