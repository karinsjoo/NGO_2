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
public class HanteraPartnerAdmin extends javax.swing.JFrame {
    private final InfDB idb;
    private final String aid;
    
    /**
     * Creates new form HanteraPartnerAdmin
     * @param idb
     * @param aid
     */
    public HanteraPartnerAdmin(InfDB idb, String aid) {
        this.idb = idb;
        this.aid = aid;
        initComponents();// Skrivs här för att använda sig av NetBeans GUI komponenter
        // setSize(1750, 500); // Storlek på fönstret, tog bort denna och lade till storleksanpassning nedan istället
        setLocationRelativeTo(null); // Fönstret hamnar i mitten av dataskärmen
        laddaHanteraPartnerData(); // Öppnar tabellen direkt när konstruktorn körs och objektet skapas
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Stänger MinaUppgifterPopup när man trycker på krysset i rutan utan att stänga ner hela programmet
        
        
        // Tabell - höjden anpassas efter antal rader som laddas för användaren
        int antalRader = tblHanteraPartnerAdmin.getRowCount();
        int radHojden =tblHanteraPartnerAdmin.getRowHeight();
        int totalHojden = antalRader * radHojden;
        
        // Tabell - bredden anpassas efter en satt bredd och antal kolumner
        int antalKolumner = tblHanteraPartnerAdmin.getColumnCount();
        int kolumnBredden = 150; // Den satta bredden varje kolumn får, kommer klippa värdet
        int totalaBredden = antalKolumner * kolumnBredden;
        
        // Ställer in tabellen efter storleken på anpassningarna ovanför
        tblHanteraPartnerAdmin.setPreferredSize(new Dimension(totalaBredden, totalHojden));
        tblHanteraPartnerAdmin.revalidate();
        
        // Här kan man sortera på värdena i kolumnerna
        DefaultTableModel projektModell = (DefaultTableModel) tblHanteraPartnerAdmin.getModel();
        TableRowSorter<DefaultTableModel> tabellSortering = new TableRowSorter<>(projektModell);
        tblHanteraPartnerAdmin.setRowSorter(tabellSortering);
    }
    
    public void laddaHanteraPartnerData(){
            // Fungerar annorlunda än JButton - här var vi tvungna att skriva in kod för kolumner osv och koppla metoden laddaProjektData till konstruktorn.
    // Detta för att öppna den direkt när MinaProjektPopup öppnas
        try{
            // SQL fråga som hämtar ALLA projekt
            String query = "SELECT pid, namn, kontaktperson, kontaktepost, telefon, adress, branch, stad "
                    + "FROM partner ";
            
            // Här skapas tabellen med tabellnamnen
            DefaultTableModel projektModellAdmin = new DefaultTableModel();
            String [] kolumnNamn = {"Partner-ID", "Namn", "Kontaktperson", "E-post", "Telefon", "Adress", "Branch","Stad"};
            projektModellAdmin.setColumnIdentifiers(kolumnNamn);
            
            // Hämtar ifrån databasen i form av en ArrayList eftersom vi hämtar flera rader på en gång. Lagrat i en HashMap
            ArrayList<HashMap<String, String>> partnerAdmin = idb.fetchRows(query);
            
            if(partnerAdmin != null){ // Använder oss inte av Valideringsklassen här. Skulle behövt iterera igenom varje rad
                // Nu kollar vi om HELA projektlistan är tom vilket är mer logiskt i detta fall då vi vill ha fram flera kolumner data
                for(HashMap<String, String> hanteraPartnerAdmin : partnerAdmin){
                    projektModellAdmin.addRow(new Object []{
                        hanteraPartnerAdmin.get("pid"),
                        hanteraPartnerAdmin.get("namn"), // Projektnycklarna (varje kolumn i tabellen i detta fall)
                        hanteraPartnerAdmin.get("kontaktperson"),
                        hanteraPartnerAdmin.get("kontaktepost"),
                        hanteraPartnerAdmin.get("telefon"),
                        hanteraPartnerAdmin.get("adress"),
                        hanteraPartnerAdmin.get("branch"),
                        hanteraPartnerAdmin.get("stad"),

                    });
                }
            } else { // Om projektData är null eller tom
                JOptionPane.showMessageDialog(this, "Inga partners hittades", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            // Laddar upp anvandarProjekt till tabellen tblMinaProjekt
            tblHanteraPartnerAdmin.setModel(projektModellAdmin);
            
    }catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid hämtning av partners" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
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

        scrHanteraPartnerAdmin = new javax.swing.JScrollPane();
        tblHanteraPartnerAdmin = new javax.swing.JTable();
        lblRubrikHanteraPartnerAdmin = new javax.swing.JLabel();
        btnHanteraPartnerAdmin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblHanteraPartnerAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Namn", "Kontaktperson", "E-post", "Telefon", "Adress", "Branch", "Stad"
            }
        ));
        scrHanteraPartnerAdmin.setViewportView(tblHanteraPartnerAdmin);

        lblRubrikHanteraPartnerAdmin.setText("Här kan du ändra uppgifter om Partners som Admin");

        btnHanteraPartnerAdmin.setText("Spara Ändringar");
        btnHanteraPartnerAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHanteraPartnerAdminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrHanteraPartnerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHanteraPartnerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRubrikHanteraPartnerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(lblRubrikHanteraPartnerAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrHanteraPartnerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHanteraPartnerAdmin)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHanteraPartnerAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHanteraPartnerAdminActionPerformed
        // TODO add your handling code here:
                            
        try{
            DefaultTableModel partnerModellAdmin = (DefaultTableModel) tblHanteraPartnerAdmin.getModel();
            for(int index = 0; index < partnerModellAdmin.getRowCount(); index++){
                int pid = Integer.parseInt(partnerModellAdmin.getValueAt(index, 0).toString());
                String namn = partnerModellAdmin.getValueAt(index, 1).toString();
                String kontaktperson = partnerModellAdmin.getValueAt(index, 2).toString();        
                String kontaktepost = partnerModellAdmin.getValueAt(index, 3).toString();        
                String telefon = partnerModellAdmin.getValueAt(index, 4).toString();                               
                String adress = partnerModellAdmin.getValueAt(index, 5).toString();
                String branch = partnerModellAdmin.getValueAt(index, 6).toString();      
                String stad = partnerModellAdmin.getValueAt(index, 7).toString(); 
            
            String updateQuery = "UPDATE partner SET namn = '" + namn
                    + "', kontaktperson = '" + kontaktperson
                    + "', kontaktepost = '" + kontaktepost
                    + "', telefon = '" + telefon
                    + "', adress = '" + adress
                    + "', branch = '" + branch
                    + "', stad = '" + stad
                    + "' WHERE pid = " + pid;
            
                    System.out.println("SQL QUERY: " + updateQuery);        
                    idb.update(updateQuery);
            } 
            JOptionPane.showMessageDialog(this, "Partner har uppdaterats!", "Info", JOptionPane.INFORMATION_MESSAGE);

        }catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid uppdatering av partner" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_btnHanteraPartnerAdminActionPerformed

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
            java.util.logging.Logger.getLogger(HanteraPartnerAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HanteraPartnerAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HanteraPartnerAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HanteraPartnerAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            //    new HanteraPartnerAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHanteraPartnerAdmin;
    private javax.swing.JLabel lblRubrikHanteraPartnerAdmin;
    private javax.swing.JScrollPane scrHanteraPartnerAdmin;
    private javax.swing.JTable tblHanteraPartnerAdmin;
    // End of variables declaration//GEN-END:variables
}
