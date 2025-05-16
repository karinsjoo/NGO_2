/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdgsweden;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

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
 * @param value som ska kollas
 * @return "true" om den är tom eller null, annars "false"
 */
public static boolean nullEllerTom(String value){
    return value == null || value.trim().isEmpty();
}

}
