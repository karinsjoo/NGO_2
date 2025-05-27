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
public class HanteraLandAdmin extends javax.swing.JFrame {
    private final InfDB idb;
    private final String aid;
    
    /**
     * Creates new form HanteraLandAdmin
     * @param idb
     * @param aid
     */
    public HanteraLandAdmin(InfDB idb, String aid) {
        this.idb = idb;
        this.aid = aid;
        initComponents();// Skrivs här för att använda sig av NetBeans GUI komponenter
        // setSize(1750, 500); // Storlek på fönstret, tog bort denna och lade till storleksanpassning nedan istället
        setLocationRelativeTo(null); // Fönstret hamnar i mitten av dataskärmen
        laddaHanteraLandData(); // Öppnar tabellen direkt när konstruktorn körs och objektet skapas
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Stänger MinaUppgifterPopup när man trycker på krysset i rutan utan att stänga ner hela programmet
        
        
        // Tabell - höjden anpassas efter antal rader som laddas för användaren
        int antalRader = tblHanteraLandAdmin.getRowCount();
        int radHojden =tblHanteraLandAdmin.getRowHeight();
        int totalHojden = antalRader * radHojden;
        
        // Tabell - bredden anpassas efter en satt bredd och antal kolumner
        int antalKolumner = tblHanteraLandAdmin.getColumnCount();
        int kolumnBredden = 150; // Den satta bredden varje kolumn får, kommer klippa värdet
        int totalaBredden = antalKolumner * kolumnBredden;
        
        // Ställer in tabellen efter storleken på anpassningarna ovanför
        tblHanteraLandAdmin.setPreferredSize(new Dimension(totalaBredden, totalHojden));
        tblHanteraLandAdmin.revalidate();
        
        // Här kan man sortera på värdena i kolumnerna
        DefaultTableModel projektModell = (DefaultTableModel) tblHanteraLandAdmin.getModel();
        TableRowSorter<DefaultTableModel> tabellSortering = new TableRowSorter<>(projektModell);
        tblHanteraLandAdmin.setRowSorter(tabellSortering);
    }
    
    public void laddaHanteraLandData(){
                // Fungerar annorlunda än JButton - här var vi tvungna att skriva in kod för kolumner osv och koppla metoden laddaProjektData till konstruktorn.
    // Detta för att öppna den direkt när MinaProjektPopup öppnas
        try{
            // SQL fråga som hämtar ALLA projekt
            String query = "SELECT lid, namn, sprak, valuta, tidszon, politisk_struktur, ekonomi "
                    + "FROM land ";
            
            // Här skapas tabellen med tabellnamnen
            DefaultTableModel projektModellAdmin = new DefaultTableModel();
            String [] kolumnNamn = {"Land-ID", "Namn", "Språk", "Valuta", "Tidszon", "Politisk struktur", "Ekonomi"};
            projektModellAdmin.setColumnIdentifiers(kolumnNamn);
            
            // Hämtar ifrån databasen i form av en ArrayList eftersom vi hämtar flera rader på en gång. Lagrat i en HashMap
            ArrayList<HashMap<String, String>> landAdmin = idb.fetchRows(query);
            
            if(landAdmin != null && !landAdmin.isEmpty()){ // Använder oss inte av Valideringsklassen här. Skulle behövt iterera igenom varje rad
                // Nu kollar vi om HELA projektlistan är tom vilket är mer logiskt i detta fall då vi vill ha fram flera kolumner data
                for(HashMap<String, String> hanteraPartnerAdmin : landAdmin){
                    projektModellAdmin.addRow(new Object []{
                        hanteraPartnerAdmin.get("lid"),
                        hanteraPartnerAdmin.get("namn"), // Projektnycklarna (varje kolumn i tabellen i detta fall)
                        hanteraPartnerAdmin.get("sprak"),
                        hanteraPartnerAdmin.get("valuta"),
                        hanteraPartnerAdmin.get("tidszon"),
                        hanteraPartnerAdmin.get("politisk_struktur"),
                        hanteraPartnerAdmin.get("ekonomi"),

                    });
                }
            } else { // Om projektData är null eller tom
                JOptionPane.showMessageDialog(this, "Inga länder hittades", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            // Laddar upp anvandarProjekt till tabellen tblMinaProjekt
            tblHanteraLandAdmin.setModel(projektModellAdmin);
            
    }catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid hämtning av länder" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
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

        scrHanteraLandAdmin = new javax.swing.JScrollPane();
        tblHanteraLandAdmin = new javax.swing.JTable();
        lblRubrikHanderaLandAdmin = new javax.swing.JLabel();
        btnSparaAndringarHanteraLandAdmin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblHanteraLandAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrHanteraLandAdmin.setViewportView(tblHanteraLandAdmin);

        lblRubrikHanderaLandAdmin.setText("Här kan du ändra uppgifter om olika länder som Admin");

        btnSparaAndringarHanteraLandAdmin.setText("Spara Ändringar");
        btnSparaAndringarHanteraLandAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaAndringarHanteraLandAdminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrHanteraLandAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRubrikHanderaLandAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSparaAndringarHanteraLandAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(lblRubrikHanderaLandAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrHanteraLandAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSparaAndringarHanteraLandAdmin)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSparaAndringarHanteraLandAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaAndringarHanteraLandAdminActionPerformed
        // TODO add your handling code here:
                            
        try{
            DefaultTableModel landModellAdmin = (DefaultTableModel) tblHanteraLandAdmin.getModel();
            for(int index = 0; index < landModellAdmin.getRowCount(); index++){
                int lid = Integer.parseInt(landModellAdmin.getValueAt(index, 0).toString());
                String namn = landModellAdmin.getValueAt(index, 1).toString();
                String sprak = landModellAdmin.getValueAt(index, 2).toString();        
                String valuta = landModellAdmin.getValueAt(index, 3).toString();        
                String tidszon = landModellAdmin.getValueAt(index, 4).toString();                               
                String politisk_struktur = landModellAdmin.getValueAt(index, 5).toString();
                String ekonomi = landModellAdmin.getValueAt(index, 6).toString();      
            
            String updateQuery = "UPDATE land SET namn = '" + namn
                    + "', sprak = '" + sprak
                    + "', valuta = '" + valuta
                    + "', tidszon = '" + tidszon
                    + "', politisk_struktur = '" + politisk_struktur
                    + "', ekonomi = '" + ekonomi
                    + "' WHERE lid = " + lid;
                    
                    System.out.println("SQL QUERY: " + updateQuery);
                    idb.update(updateQuery);
            } 
            JOptionPane.showMessageDialog(this, "Land har uppdaterats!", "Info", JOptionPane.INFORMATION_MESSAGE);

        }catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid uppdatering av länder" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_btnSparaAndringarHanteraLandAdminActionPerformed

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
            java.util.logging.Logger.getLogger(HanteraLandAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HanteraLandAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HanteraLandAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HanteraLandAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            //    new HanteraLandAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSparaAndringarHanteraLandAdmin;
    private javax.swing.JLabel lblRubrikHanderaLandAdmin;
    private javax.swing.JScrollPane scrHanteraLandAdmin;
    private javax.swing.JTable tblHanteraLandAdmin;
    // End of variables declaration//GEN-END:variables
}
