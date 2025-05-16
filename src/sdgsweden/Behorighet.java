/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdgsweden;

import oru.inf.InfDB;
import oru.inf.InfException;

/**
 * Kallas på för att hantera användare i systemet
 * @author erikaekholm
 */
public class Behorighet {
    private final InfDB idb;
    
    /**
     * Konstruktor för Databasåtkomst
     * @param idb
     */
    public Behorighet(InfDB idb){ // Åtkomst till databasen
        this.idb = idb;
    
    }
    /**
     * Hämtar användarens aid ifrån den e-post man skriver in
     * @param epost
     * @return aid om användaren finns
     */
    public String getAID(String epost){
        try{
            return idb.fetchSingle("SELECT AID FROM anstalld WHERE epost = '" + epost + "'");
        }catch(InfException ex){
            System.out.println("Fel vid hämtning av anställd: " + ex.getMessage());
            return null;
        }
    }
    
    /**
     * Kollar om anställd är projektledare
     * @param aid
     * @return "true" om aid finns i tabell projekt, annars "false"
     */
        public boolean isProjektledare(String aid){
        try{
            String isProjektledare = idb.fetchSingle("SELECT COUNT(*) FROM projekt WHERE projektchef = '" + aid + "'");
            return isProjektledare != null && ! "0".equals(isProjektledare); // Om det finns en aid som ingår i tabell handläggare
        }catch(InfException ex){
            System.out.println("Anställd är inte Projektledare: " + ex.getMessage());
            return false;
        }
    }
    
    /**
     * Kolla om anställd är Handläggare
     * @param aid
     * return "true" om aid finns i tabell handlaggare, annars retrnera "false"
     * @return "true on aid finns i tabell handläggare, annars returnera "false"
     */
    public boolean isHandlaggare(String aid){
        try{
        String isHandlaggare = idb.fetchSingle("SELECT COUNT(*) FROM handlaggare WHERE AID = '" + aid + "'");
        return isHandlaggare != null && ! "0".equals(isHandlaggare);
        }catch(InfException ex){
            System.out.println("Anställd är inte Handläggare: " + ex.getMessage());
            return false;
        }
    }}


