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
public class MinaProjektPopup extends javax.swing.JFrame {
    private final InfDB idb;
    private final String aid;
    
    /**
     * Creates new form MinaProjektPopup
     * @param idb
     * @param aid
     */
    public MinaProjektPopup(InfDB idb, String aid) {
        this.idb = idb;
        this.aid = aid;
        initComponents(); // Skrivs här för att använda sig av NetBeans GUI komponenter
        // setSize(1750, 500); // Storlek på fönstret, tog bort denna och lade till storleksanpassning nedan istället
        setLocationRelativeTo(null); // Fönstret hamnar i mitten av dataskärmen
        laddaProjektData(); // Öppnar tabellen direkt när konstruktorn körs och objektet skapas
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Stänger MinaUppgifterPopup när man trycker på krysset i rutan utan att stänga ner hela programmet
        
        
        // Tabell - höjden anpassas efter antal rader som laddas för användaren
        int antalRader = tblMinaProjekt.getRowCount();
        int radHojden =tblMinaProjekt.getRowHeight();
        int totalHojden = antalRader * radHojden;
        
        // Tabell - bredden anpassas efter en satt bredd och antal kolumner
        int antalKolumner = tblMinaProjekt.getColumnCount();
        int kolumnBredden = 150; // Den satta bredden varje kolumn får, kommer klippa värdet
        int totalaBredden = antalKolumner * kolumnBredden;
        
        // Ställer in tabellen efter storleken på anpassningarna ovanför
        tblMinaProjekt.setPreferredSize(new Dimension(totalaBredden, totalHojden));
        tblMinaProjekt.revalidate();
        
        // Här kan man sortera på värdena i kolumnerna
        DefaultTableModel projektModell = (DefaultTableModel) tblMinaProjekt.getModel();
        TableRowSorter<DefaultTableModel> tabellSortering = new TableRowSorter<>(projektModell);
        tblMinaProjekt.setRowSorter(tabellSortering);
    }
    
    private void laddaProjektData(){ 
    // Fungerar annorlunda än JButton - här var vi tvungna att skriva in kod för kolumner osv och koppla metoden laddaProjektData till konstruktorn.
    // Detta för att öppna den direkt när MinaProjektPopup öppnas
        try{
            // SQL fråga som hämtar projekt kopplade till inloggade användaren. Konstruktorn bär med sig info kring aid
            // aid skickas med vid skapandet av MinaProjektPopup och sedan använder vi aid i SQl frågan nedan som filter för resultatet
            // DISTINCT kopplar bara samman samma projekt med partner en gång, fick dubbla rader först på en användare
            String query = "SELECT DISTINCT projekt.projektnamn, projekt.beskrivning, projekt.startdatum, projekt.slutdatum, projekt.kostnad, projekt.status, "
                    + "projekt.prioritet, projekt.projektchef, projekt.land, partner.namn, partner.branch "
                    + "FROM projekt "
                    + "JOIN ans_proj ON projekt.pid = ans_proj.pid "
                    // Left JOIN för att även se projekt utan partner
                    + "LEFT JOIN projekt_partner ON projekt.pid = projekt_partner.pid "
                    + "LEFT JOIN partner ON projekt_partner.pid = partner.pid "
                    + "WHERE ans_proj.aid = '" + aid + "'";
            
            // Här skapas tabellen med tabellnamnen
            DefaultTableModel projektModell = new DefaultTableModel();
            String [] kolumnNamn = {"Projektnamn", "Beskrivning", "Startdatum", "Slutdatum", "Kostnad", "Status","Prioritet", "Projektchef", "Land", "Partner namn", "Partner branch"};
            projektModell.setColumnIdentifiers(kolumnNamn);
            
            // Hämtar ifrån databasen i form av en ArrayList eftersom vi hämtar flera rader på en gång. Lagrat i en HashMap
            ArrayList<HashMap<String, String>> projektData = idb.fetchRows(query);
            
            if(projektData != null){ // Använder oss inte av Valideringsklassen här. Skulle behövt iterera igenom varje rad
                // Nu kollar vi om HELA projektlistan är tom vilket är mer logiskt i detta fall då vi vill ha fram flera kolumner data
                for(HashMap<String, String> anvandarProjekt : projektData){
                    projektModell.addRow(new Object []{
                        anvandarProjekt.get("projektnamn"), // Projektnycklarna (varje kolumn i tabellen i detta fall)
                        anvandarProjekt.get("beskrivning"),
                        anvandarProjekt.get("startdatum"),
                        anvandarProjekt.get("slutdatum"),
                        anvandarProjekt.get("kostnad"),
                        anvandarProjekt.get("status"),
                        anvandarProjekt.get("prioritet"),
                        anvandarProjekt.get("projektchef"),
                        anvandarProjekt.get("land"),
                        anvandarProjekt.get("namn"),
                        anvandarProjekt.get("branch")
                    });
                }
            } else { // Om projektData är null eller tom
                JOptionPane.showMessageDialog(this, "Inga projekt hittades för användaren", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            // Laddar upp anvandarProjekt till tabellen tblMinaProjekt
            tblMinaProjekt.setModel(projektModell);
            
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
        jTable1 = new javax.swing.JTable();
        lblMinaProjektPopup = new javax.swing.JLabel();
        scrMinaProjekt = new javax.swing.JScrollPane();
        tblMinaProjekt = new javax.swing.JTable();
        btnSokaProjekt = new javax.swing.JButton();
        txtSokfaltProjekt = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblMinaProjektPopup.setText("Mina projekt");

        tblMinaProjekt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Projektnamn", "Beskrivning", "Startdatum", "Slutdatum", "Kostnad", "Status", "Prioritet", "Projektchef", "Land", "Partner namn", "Partner bransch"
            }
        ));
        scrMinaProjekt.setViewportView(tblMinaProjekt);

        btnSokaProjekt.setText("Sök");
        btnSokaProjekt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSokaProjektActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrMinaProjekt)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMinaProjektPopup, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSokfaltProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnSokaProjekt)))
                .addContainerGap(668, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblMinaProjektPopup)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSokaProjekt)
                    .addComponent(txtSokfaltProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(scrMinaProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSokaProjektActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSokaProjektActionPerformed
        // TODO add your handling code here:
        // Sökknapp för att leta efter en anställd på avdelningen
        String sokaTextRuta = txtSokfaltProjekt.getText();
        DefaultTableModel projektModell = (DefaultTableModel) tblMinaProjekt.getModel();
        TableRowSorter<DefaultTableModel> tabellSortera = (TableRowSorter<DefaultTableModel>) tblMinaProjekt.getRowSorter();
        
        if(sokaTextRuta.trim().isEmpty()){ // Validering fungerade inte här
            tabellSortera.setRowFilter(null); // Visar ALLA rader om vi inte söker på något
        } else {
            // RowFilter med (?i) gör sökningen case-insensitive
            tabellSortera.setRowFilter(RowFilter.regexFilter("(?i)" + sokaTextRuta)); // Fritextsökning som inte beror på stora/små bokstäver
        }
         
    }//GEN-LAST:event_btnSokaProjektActionPerformed

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
            java.util.logging.Logger.getLogger(MinaProjektPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MinaProjektPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MinaProjektPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MinaProjektPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new MinaProjektPopup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSokaProjekt;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblMinaProjektPopup;
    private javax.swing.JScrollPane scrMinaProjekt;
    private javax.swing.JTable tblMinaProjekt;
    private javax.swing.JTextField txtSokfaltProjekt;
    // End of variables declaration//GEN-END:variables

}
