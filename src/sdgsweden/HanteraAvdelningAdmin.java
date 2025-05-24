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
public class HanteraAvdelningAdmin extends javax.swing.JFrame {
    private final InfDB idb;
    private final String aid;
    /**
     * Creates new form HanteraAvdelningAdmin
     * @param idb
     * @param aid
     */
    public HanteraAvdelningAdmin(InfDB idb, String aid) {
        this.idb = idb;
        this.aid = aid;
        initComponents();// Skrivs här för att använda sig av NetBeans GUI komponenter
        // setSize(1750, 500); // Storlek på fönstret, tog bort denna och lade till storleksanpassning nedan istället
        setLocationRelativeTo(null); // Fönstret hamnar i mitten av dataskärmen
        laddaHanteraAvdelningData(); // Öppnar tabellen direkt när konstruktorn körs och objektet skapas
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Stänger MinaUppgifterPopup när man trycker på krysset i rutan utan att stänga ner hela programmet
        
        
        // Tabell - höjden anpassas efter antal rader som laddas för användaren
        int antalRader = tblHanteraAvdelningAdmin.getRowCount();
        int radHojden =tblHanteraAvdelningAdmin.getRowHeight();
        int totalHojden = antalRader * radHojden;
        
        // Tabell - bredden anpassas efter en satt bredd och antal kolumner
        int antalKolumner = tblHanteraAvdelningAdmin.getColumnCount();
        int kolumnBredden = 150; // Den satta bredden varje kolumn får, kommer klippa värdet
        int totalaBredden = antalKolumner * kolumnBredden;
        
        // Ställer in tabellen efter storleken på anpassningarna ovanför
        tblHanteraAvdelningAdmin.setPreferredSize(new Dimension(totalaBredden, totalHojden));
        tblHanteraAvdelningAdmin.revalidate();
        
        // Här kan man sortera på värdena i kolumnerna
        DefaultTableModel projektModell = (DefaultTableModel) tblHanteraAvdelningAdmin.getModel();
        TableRowSorter<DefaultTableModel> tabellSortering = new TableRowSorter<>(projektModell);
        tblHanteraAvdelningAdmin.setRowSorter(tabellSortering);
    }
    
    public void laddaHanteraAvdelningData(){
        // Fungerar annorlunda än JButton - här var vi tvungna att skriva in kod för kolumner osv och koppla metoden laddaProjektData till konstruktorn.
    // Detta för att öppna den direkt när MinaProjektPopup öppnas
        try{
            // SQL fråga som hämtar ALLA projekt
            String query = "SELECT namn, beskrivning, adress, epost, telefon, stad, chef "
                    + "FROM avdelning ";
            
            // Här skapas tabellen med tabellnamnen
            DefaultTableModel projektModellAdmin = new DefaultTableModel();
            String [] kolumnNamn = {"Namn", "Beskrivning", "Adress", "E-post", "Telefon", "Stad","Chef"};
            projektModellAdmin.setColumnIdentifiers(kolumnNamn);
            
            // Hämtar ifrån databasen i form av en ArrayList eftersom vi hämtar flera rader på en gång. Lagrat i en HashMap
            ArrayList<HashMap<String, String>> avdelningAdmin = idb.fetchRows(query);
            
            if(avdelningAdmin != null){ // Använder oss inte av Valideringsklassen här. Skulle behövt iterera igenom varje rad
                // Nu kollar vi om HELA projektlistan är tom vilket är mer logiskt i detta fall då vi vill ha fram flera kolumner data
                for(HashMap<String, String> hanteraAvdelningAdmin : avdelningAdmin){
                    projektModellAdmin.addRow(new Object []{
                        hanteraAvdelningAdmin.get("namn"), // Projektnycklarna (varje kolumn i tabellen i detta fall)
                        hanteraAvdelningAdmin.get("beskrivning"),
                        hanteraAvdelningAdmin.get("adress"),
                        hanteraAvdelningAdmin.get("epost"),
                        hanteraAvdelningAdmin.get("telefon"),
                        hanteraAvdelningAdmin.get("status"),
                        hanteraAvdelningAdmin.get("stad"),
                        hanteraAvdelningAdmin.get("chef"),

                    });
                }
            } else { // Om projektData är null eller tom
                JOptionPane.showMessageDialog(this, "Inga avdelningar hittades", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            // Laddar upp anvandarProjekt till tabellen tblMinaProjekt
            tblHanteraAvdelningAdmin.setModel(projektModellAdmin);
            
    }catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid hämtning av avdelningsdata" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
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

        srcHanteraAvdelningAdmin = new javax.swing.JScrollPane();
        tblHanteraAvdelningAdmin = new javax.swing.JTable();
        btnSparaAndringarHanteraAvdelningAdmin = new javax.swing.JButton();
        lblRubrikHanteraAvdelningAdmin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblHanteraAvdelningAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Namn", "Beskrivning", "Adress", "Epost", "Telefon", "Stad", "Chef"
            }
        ));
        srcHanteraAvdelningAdmin.setViewportView(tblHanteraAvdelningAdmin);

        btnSparaAndringarHanteraAvdelningAdmin.setText("Spara Ändringar");
        btnSparaAndringarHanteraAvdelningAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaAndringarHanteraAvdelningAdminActionPerformed(evt);
            }
        });

        lblRubrikHanteraAvdelningAdmin.setText("Här kan du ändra Avdelningsuppgifter som Admin");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSparaAndringarHanteraAvdelningAdmin)
                    .addComponent(srcHanteraAvdelningAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 889, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRubrikHanteraAvdelningAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblRubrikHanteraAvdelningAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(srcHanteraAvdelningAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSparaAndringarHanteraAvdelningAdmin)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSparaAndringarHanteraAvdelningAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaAndringarHanteraAvdelningAdminActionPerformed
        // TODO add your handling code here:
                
        try{
            DefaultTableModel avdelningModellAdmin = (DefaultTableModel) tblHanteraAvdelningAdmin.getModel();
            for(int index = 0; index < avdelningModellAdmin.getRowCount(); index++){
                String namn = avdelningModellAdmin.getValueAt(index, 0).toString();
                String beskrivning = avdelningModellAdmin.getValueAt(index, 1).toString();        
                String adress = avdelningModellAdmin.getValueAt(index, 2).toString();        
                String epost = avdelningModellAdmin.getValueAt(index, 3).toString();                               
                String telefon = avdelningModellAdmin.getValueAt(index, 4).toString();
                String stad = avdelningModellAdmin.getValueAt(index, 5).toString();      
                String chef = avdelningModellAdmin.getValueAt(index, 6).toString(); 
            
            String updateQuery = "UPDATE projekt SET namn = '" + namn
                    + "', beskrivning = '" + beskrivning
                    + "', adress = '" + adress
                    + "', epost = '" + epost
                    + "', telefon = '" + telefon
                    + "', stad = '" + stad
                    + "', chef = '" + chef;
                    
                    idb.update(updateQuery);
            } 
            JOptionPane.showMessageDialog(this, "Avdelning har uppdaterasts!", "Info", JOptionPane.INFORMATION_MESSAGE);

        }catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid uppdatering av avdelning" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_btnSparaAndringarHanteraAvdelningAdminActionPerformed

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
            java.util.logging.Logger.getLogger(HanteraAvdelningAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HanteraAvdelningAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HanteraAvdelningAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HanteraAvdelningAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new HanteraAvdelningAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSparaAndringarHanteraAvdelningAdmin;
    private javax.swing.JLabel lblRubrikHanteraAvdelningAdmin;
    private javax.swing.JScrollPane srcHanteraAvdelningAdmin;
    private javax.swing.JTable tblHanteraAvdelningAdmin;
    // End of variables declaration//GEN-END:variables
}
