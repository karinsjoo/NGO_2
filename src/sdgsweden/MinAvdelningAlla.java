/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdgsweden;

import java.awt.Dimension;
import java.util.ArrayList;
import oru.inf.InfDB;
import oru.inf.InfException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author erikaekholm
 */
public class MinAvdelningAlla extends javax.swing.JFrame {
    private final InfDB idb;
    private final String aid;
    
    /**
     * Creates new form MinAvdelning
     * @param idb
     * @param aid
     */
    public MinAvdelningAlla(InfDB idb, String aid) {
        this.idb = idb;
        this.aid = aid;

        initComponents(); // Skrivs här för att använda sig av NetBeans GUI komponenter
        setLocationRelativeTo(null); // Fönstret hamnar i mitten av dataskärmen
        laddaAvdelningData(); // Öppnar tabellen direkt när konstruktorn körs och objektet skapas
        laddaAvdelningNamn();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Stänger MinaUppgifterPopup när man trycker på krysset i rutan utan att stänga ner hela programmet
             
        // Tabell - höjden anpassas efter antal rader som laddas för användaren
        int antalRader = tblAvdelningTabell.getRowCount();
        int radHojden =tblAvdelningTabell.getRowHeight();
        int totalHojden = antalRader * radHojden;
        
        // Tabell - bredden anpassas efter en satt bredd och antal kolumner
        int antalKolumner = tblAvdelningTabell.getColumnCount();
        int kolumnBredden = 150; // Den satta bredden varje kolumn får, kommer klippa värdet
        int totalaBredden = antalKolumner * kolumnBredden;
        
        // Ställer in tabellen efter storleken på anpassningarna ovanför
        tblAvdelningTabell.setPreferredSize(new Dimension(totalaBredden, totalHojden));
        tblAvdelningTabell.revalidate();
        
        // Här kan man sortera på värdena i kolumnerna
        DefaultTableModel avdelningModell = (DefaultTableModel) tblAvdelningTabell.getModel();
        TableRowSorter<DefaultTableModel> tabellSortering = new TableRowSorter<>(avdelningModell);
        tblAvdelningTabell.setRowSorter(tabellSortering);
    }   
        
    
    public void laddaAvdelningData(){
    // Fungerar annorlunda än JButton - här var vi tvungna att skriva in kod för kolumner osv och koppla metoden laddaProjektData till konstruktorn.
    // Detta för att öppna den direkt när MinaProjektPopup öppnas
        try{
            // SQL fråga som hämtar projekt kopplade till inloggade användaren. Konstruktorn bär med sig info kring aid
            // aid skickas med vid skapandet av MinaProjektPopup och sedan använder vi aid i SQl frågan nedan som filter för resultatet
            String query = "SELECT avdelning.namn, projekt.projektnamn, projekt.beskrivning, projekt.startdatum, projekt.slutdatum, projekt.status "
                    + "FROM ans_proj "
                    + "JOIN projekt ON ans_proj.pid = projekt.pid "
                    + "JOIN anstalld ON ans_proj.aid = anstalld.aid "
                    + "JOIN avdelning ON anstalld.avdelning = avdelning.avdid "
                    + "WHERE ans_proj.aid = '" + aid + "'";
            
            // Här skapas tabellen med tabellnamnen
            DefaultTableModel avdelningModell = new DefaultTableModel();
            String [] kolumnNamn = {"Avdelningsnamn", "Projektnamn", "Beskrivning", "Startdatum", "Slutdatum", "Status","Prioritet"};
            avdelningModell.setColumnIdentifiers(kolumnNamn);
            
            // Hämtar ifrån databasen i form av en ArrayList eftersom vi hämtar flera rader på en gång. Lagrat i en HashMap
            ArrayList<HashMap<String, String>> avdelningData = idb.fetchRows(query);
            
            if(avdelningData != null){ // Använder oss inte av Valideringsklassen här. Skulle behövt iterera igenom varje rad
                // Nu kollar vi om HELA projektlistan är tom vilket är mer logiskt i detta fall då vi vill ha fram flera kolumner data
                for(HashMap<String, String> anvandarAvdelning : avdelningData){
                    avdelningModell.addRow(new Object []{
                        anvandarAvdelning.get("namn"), // Projektnycklarna (varje kolumn i tabellen i detta fall)
                        anvandarAvdelning.get("projektnamn"),
                        anvandarAvdelning.get("beskrivning"),
                        anvandarAvdelning.get("startdatum"),
                        anvandarAvdelning.get("slutdatum"),
                        anvandarAvdelning.get("status"),
                        anvandarAvdelning.get("prioritet"),
                    });
                }
            } else { // Om projektData är null eller tom
                JOptionPane.showMessageDialog(this, "Inga avdelningsdata hittades för användaren", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            // Laddar upp avdelningModell till tabellen tblMinaProjekt
            tblAvdelningTabell.setModel(avdelningModell);
            
    }catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid hämtning av avdelningsdata" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
            }
}
    
    public void laddaAvdelningNamn(){
    try{
        String query = "SELECT avdelning.namn FROM anstalld "
                + "JOIN avdelning ON anstalld.avdelning = avdelning.avdid "
                + "WHERE anstalld.aid = '" + aid + "'";
        
        String avdelningsnamnet = idb.fetchSingle(query);
        lblAvdelningsNamn.setText(avdelningsnamnet);
    } catch(InfException ex){
        JOptionPane.showMessageDialog(this, "Fel vid hämtning av avdelningsnamnet: " + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAvdelningsNamn = new javax.swing.JLabel();
        jskrAvdelningTabell = new javax.swing.JScrollPane();
        tblAvdelningTabell = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblAvdelningsNamn.setText("Avdelningsnamn");

        tblAvdelningTabell.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Avdelningsnamn", "Projektnamn", "Beskrivning", "Startdatum", "Slutdatum", "Status", "Prioritet"
            }
        ));
        jskrAvdelningTabell.setViewportView(tblAvdelningTabell);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jskrAvdelningTabell, javax.swing.GroupLayout.PREFERRED_SIZE, 947, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAvdelningsNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAvdelningsNamn)
                .addGap(18, 18, 18)
                .addComponent(jskrAvdelningTabell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MinAvdelningAlla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MinAvdelningAlla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MinAvdelningAlla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MinAvdelningAlla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             //   new MinAvdelningAlla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jskrAvdelningTabell;
    private javax.swing.JLabel lblAvdelningsNamn;
    private javax.swing.JTable tblAvdelningTabell;
    // End of variables declaration//GEN-END:variables
}
