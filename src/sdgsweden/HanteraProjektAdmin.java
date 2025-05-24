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
public class HanteraProjektAdmin extends javax.swing.JFrame {
    private final InfDB idb;
    private final String aid;

    /**
     * Creates new form HanteraProjektAdmin
     * @param idb
     * @param aid
     */
    public HanteraProjektAdmin(InfDB idb, String aid) {
        this.idb = idb;
        this.aid = aid;
        initComponents();// Skrivs här för att använda sig av NetBeans GUI komponenter
        // setSize(1750, 500); // Storlek på fönstret, tog bort denna och lade till storleksanpassning nedan istället
        setLocationRelativeTo(null); // Fönstret hamnar i mitten av dataskärmen
        laddaHanteraProjektData(); // Öppnar tabellen direkt när konstruktorn körs och objektet skapas
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Stänger MinaUppgifterPopup när man trycker på krysset i rutan utan att stänga ner hela programmet
        
        
        // Tabell - höjden anpassas efter antal rader som laddas för användaren
        int antalRader = tblHanteraProjekt.getRowCount();
        int radHojden =tblHanteraProjekt.getRowHeight();
        int totalHojden = antalRader * radHojden;
        
        // Tabell - bredden anpassas efter en satt bredd och antal kolumner
        int antalKolumner = tblHanteraProjekt.getColumnCount();
        int kolumnBredden = 150; // Den satta bredden varje kolumn får, kommer klippa värdet
        int totalaBredden = antalKolumner * kolumnBredden;
        
        // Ställer in tabellen efter storleken på anpassningarna ovanför
        tblHanteraProjekt.setPreferredSize(new Dimension(totalaBredden, totalHojden));
        tblHanteraProjekt.revalidate();
        
        // Här kan man sortera på värdena i kolumnerna
        DefaultTableModel projektModell = (DefaultTableModel) tblHanteraProjekt.getModel();
        TableRowSorter<DefaultTableModel> tabellSortering = new TableRowSorter<>(projektModell);
        tblHanteraProjekt.setRowSorter(tabellSortering);
    }
    
    private void laddaHanteraProjektData(){
    // Fungerar annorlunda än JButton - här var vi tvungna att skriva in kod för kolumner osv och koppla metoden laddaProjektData till konstruktorn.
    // Detta för att öppna den direkt när MinaProjektPopup öppnas
        try{
            // SQL fråga som hämtar ALLA projekt
            String query = "SELECT projektnamn, beskrivning, startdatum, slutdatum, kostnad, status, "
                    + "prioritet, projektchef, land "
                    + "FROM projekt ";
            
            // Här skapas tabellen med tabellnamnen
            DefaultTableModel projektModell = new DefaultTableModel();
            String [] kolumnNamn = {"Projektnamn", "Beskrivning", "Startdatum", "Slutdatum", "Kostnad", "Status","Prioritet", "Projektchef", "Land"};
            projektModell.setColumnIdentifiers(kolumnNamn);
            
            // Hämtar ifrån databasen i form av en ArrayList eftersom vi hämtar flera rader på en gång. Lagrat i en HashMap
            ArrayList<HashMap<String, String>> projektAdmin = idb.fetchRows(query);
            
            if(projektAdmin != null){ // Använder oss inte av Valideringsklassen här. Skulle behövt iterera igenom varje rad
                // Nu kollar vi om HELA projektlistan är tom vilket är mer logiskt i detta fall då vi vill ha fram flera kolumner data
                for(HashMap<String, String> hanteraProjektAdmin : projektAdmin){
                    projektModell.addRow(new Object []{
                        hanteraProjektAdmin.get("projektnamn"), // Projektnycklarna (varje kolumn i tabellen i detta fall)
                        hanteraProjektAdmin.get("beskrivning"),
                        hanteraProjektAdmin.get("startdatum"),
                        hanteraProjektAdmin.get("slutdatum"),
                        hanteraProjektAdmin.get("kostnad"),
                        hanteraProjektAdmin.get("status"),
                        hanteraProjektAdmin.get("prioritet"),
                        hanteraProjektAdmin.get("projektchef"),
                        hanteraProjektAdmin.get("land"),
                    });
                }
            } else { // Om projektData är null eller tom
                JOptionPane.showMessageDialog(this, "Inga projekt hittades", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            // Laddar upp anvandarProjekt till tabellen tblMinaProjekt
            tblHanteraProjekt.setModel(projektModell);
            
    }catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid hämtning av projektdata" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblHanteraProjekt = new javax.swing.JTable();
        btnSparaAndringarHanteraProjektAdmin = new javax.swing.JButton();
        lblAndraProjektdataAdmin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblHanteraProjekt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Projektnamn", "Beskrivning", "Startdatum", "Slutdatum", "Kostnad", "Status", "Prio", "Projektchef", "Land"
            }
        ));
        jScrollPane1.setViewportView(tblHanteraProjekt);

        btnSparaAndringarHanteraProjektAdmin.setText("Spara Ändringar");
        btnSparaAndringarHanteraProjektAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaAndringarHanteraProjektAdminActionPerformed(evt);
            }
        });

        lblAndraProjektdataAdmin.setText("Här kan du ändra projektdata som Admin");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSparaAndringarHanteraProjektAdmin)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 873, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAndraProjektdataAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblAndraProjektdataAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSparaAndringarHanteraProjektAdmin)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSparaAndringarHanteraProjektAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaAndringarHanteraProjektAdminActionPerformed
        // TODO add your handling code here:
        
        try{
            DefaultTableModel hanteraProjektModell = (DefaultTableModel) tblHanteraProjekt.getModel();
            for(int index = 0; index < hanteraProjektModell.getRowCount(); index++){
                String projektnamn = hanteraProjektModell.getValueAt(index, 0).toString();
                String beskrivning = hanteraProjektModell.getValueAt(index, 1).toString();        
                String startdatum = hanteraProjektModell.getValueAt(index, 2).toString();        
                String slutdatum = hanteraProjektModell.getValueAt(index, 3).toString();        
                String kostnad = hanteraProjektModell.getValueAt(index, 4).toString();                               
                String status = hanteraProjektModell.getValueAt(index, 5).toString();
                String prioritet = hanteraProjektModell.getValueAt(index, 6).toString();
                String projektchef = hanteraProjektModell.getValueAt(index, 7).toString();      
                String land = hanteraProjektModell.getValueAt(index, 8).toString(); 
            
            String updateQuery = "UPDATE projekt SET projektnamn = '" + projektnamn
                    + "', beskrivning = '" + beskrivning
                    + "', startdatum = '" + startdatum
                    + "', slutdatum = '" + slutdatum
                    + "', kostnad = '" + kostnad
                    + "', status = '" + status
                    + "', prioritet = '" + prioritet
                    + "', projektchef = '" + projektchef
                    + "', land = '" + land;
                    
                    idb.update(updateQuery);
            } 
            JOptionPane.showMessageDialog(this, "Projekt har uppdaterasts!", "Info", JOptionPane.INFORMATION_MESSAGE);

        }catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid uppdatering av projektdata" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_btnSparaAndringarHanteraProjektAdminActionPerformed

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
            java.util.logging.Logger.getLogger(HanteraProjektAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HanteraProjektAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HanteraProjektAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HanteraProjektAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new HanteraProjektAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSparaAndringarHanteraProjektAdmin;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAndraProjektdataAdmin;
    private javax.swing.JTable tblHanteraProjekt;
    // End of variables declaration//GEN-END:variables
}
