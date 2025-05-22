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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author erikaekholm
 */
public class HanteraProjektProjektledare extends javax.swing.JFrame {
    private final InfDB idb;
    private final String aid;
    private DefaultTableModel hanteraProjektModell;
    
    /**
     * Creates new form HanteraProjektProjektledare
     * @param idb
     * @param aid
     */
    public HanteraProjektProjektledare(InfDB idb, String aid) {
        this.idb = idb;
        this.aid = aid;
        initComponents(); // Skrivs här för att använda sig av NetBeans GUI komponenter
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
        try{
        // SQL fråga som hämtar projekt kopplade till inloggade användaren. Konstruktorn bär med sig info kring aid
        // aid skickas med vid skapandet av MinaProjektPopup och sedan använder vi aid i SQl frågan nedan som filter för resultatet
        System.out.println("aid: " + aid);
        String query = "SELECT projekt.pid, projekt.projektnamn, projekt.startdatum, projekt.slutdatum, projekt.kostnad, projekt.status, "
                + "projekt.prioritet, projekt.projektchef, projekt.land, partner.namn "
                + "FROM projekt "
                + "LEFT JOIN ans_proj ON projekt.pid = ans_proj.pid " // Kopplar projektchefens aid till projektet
                + "LEFT JOIN projekt_partner ON projekt.pid = projekt_partner.pid " // Kopplar projekt till partner
                + "LEFT JOIN partner ON projekt_partner.partner_pid = partner.pid " // Hämtar namnet på partner till projekt
                + "WHERE projekt.projektchef = '" + aid + "' "
                + "ORDER BY projekt.pid";
        
        
        // Här skapas tabellen med tabellnamnen
            hanteraProjektModell = new DefaultTableModel();
            tblHanteraProjekt.setModel(hanteraProjektModell);
            String [] kolumnNamn = {"Projektnamn", "Startdatum", "Slutdatum", "Kostnad", "Status","Prioritet", "Projektchef", "Land", "Partner namn"};
            hanteraProjektModell.setColumnIdentifiers(kolumnNamn);
            
            // Hämtar ifrån databasen i form av en ArrayList eftersom vi hämtar flera rader på en gång. Lagrat i en HashMap
            ArrayList<HashMap<String, String>> hanteraProjektData = idb.fetchRows(query);
            
            if(hanteraProjektData != null){ // Använder oss inte av Valideringsklassen här. Skulle behövt iterera igenom varje rad
                // Nu kollar vi om HELA projektlistan är tom vilket är mer logiskt i detta fall då vi vill ha fram flera kolumner data
                for(HashMap<String, String> hanteraProjekt : hanteraProjektData){
                    hanteraProjektModell.addRow(new Object []{
                        hanteraProjekt.get("projektnamn"), // Projektnycklarna (varje kolumn i tabellen i detta fall)
                        hanteraProjekt.get("startdatum"),
                        hanteraProjekt.get("slutdatum"),
                        hanteraProjekt.get("kostnad"),
                        hanteraProjekt.get("status"),
                        hanteraProjekt.get("prioritet"),
                        hanteraProjekt.get("projektchef"),
                        hanteraProjekt.get("land"),
                        hanteraProjekt.get("namn"),
                    });
                    
                }
            } else { // Om projektData är null eller tom
                JOptionPane.showMessageDialog(this, "Inga projekt hittades för användaren", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            // Laddar upp anvandarProjekt till tabellen tblMinaProjekt
            tblHanteraProjekt.setModel(hanteraProjektModell);
            
            // Skrollen
            if(scrHanteraProjekt.getParent() == null){
                getContentPane().add(scrHanteraProjekt);
            }
            
            //Koppla tabellen till skrollen
            scrHanteraProjekt.setViewportView(tblHanteraProjekt);
            
            // Uppdatera GUI och visa ändringar
            revalidate();
            repaint();
            
        } catch(InfException ex){
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

        lblRubrikHanteraProjekt = new javax.swing.JLabel();
        btnSparaAndringarHanteraProjekt = new javax.swing.JButton();
        scrHanteraProjekt = new javax.swing.JScrollPane();
        tblHanteraProjekt = new javax.swing.JTable();
        btnSeProjektStatistikKostnad = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblRubrikHanteraProjekt.setText("Mina projekt och projektpartners");

        btnSparaAndringarHanteraProjekt.setText("Spara Ändringar");
        btnSparaAndringarHanteraProjekt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaAndringarHanteraProjektActionPerformed(evt);
            }
        });

        tblHanteraProjekt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Namn", "Startdatum", "Slutdatum", "Kostnad", "Status", "Prio", "Projektchef", "Land", "Partner"
            }
        ));
        scrHanteraProjekt.setViewportView(tblHanteraProjekt);

        btnSeProjektStatistikKostnad.setText("Se Projektstatistik");
        btnSeProjektStatistikKostnad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeProjektStatistikKostnadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(lblRubrikHanteraProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSparaAndringarHanteraProjekt)
                                .addGap(18, 18, 18)
                                .addComponent(btnSeProjektStatistikKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scrHanteraProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblRubrikHanteraProjekt)
                .addGap(24, 24, 24)
                .addComponent(scrHanteraProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSparaAndringarHanteraProjekt)
                    .addComponent(btnSeProjektStatistikKostnad))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSparaAndringarHanteraProjektActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaAndringarHanteraProjektActionPerformed
        // TODO add your handling code here:
        
        try{
            DefaultTableModel hanteraProjektModell = (DefaultTableModel) tblHanteraProjekt.getModel();
            for(int index = 0; index < hanteraProjektModell.getRowCount(); index++){
                String pid = hanteraProjektModell.getValueAt(index, 0).toString();
                String projektnamn = hanteraProjektModell.getValueAt(index, 1).toString();
                String startdatum = hanteraProjektModell.getValueAt(index, 2).toString();        
                String kostnad = hanteraProjektModell.getValueAt(index, 3).toString();                               
                String status = hanteraProjektModell.getValueAt(index, 4).toString();
                String prioritet = hanteraProjektModell.getValueAt(index, 5).toString();
                String projektchef = hanteraProjektModell.getValueAt(index, 6).toString();
                String land = hanteraProjektModell.getValueAt(index, 7).toString();
                String partner = hanteraProjektModell.getValueAt(index, 8).toString();
            
            
            String updateQuery = "UPDATE projekt SET projektnamn = '" + projektnamn
                    + "', startdatum = '" + startdatum
      //              + "', slutdatum = '" + slutdatum
                    + "', kostnad = '" + kostnad
                    + "', status = '" + status
                    + "', prioritet = '" + prioritet
                    + "', projektchef = '" + projektchef
                    + "', land = '" + land
                    + "', partner = '" + partner + "' "
                    + " WHERE pid = '" + pid + "'";
                    
                    idb.update(updateQuery);
            } 
            JOptionPane.showMessageDialog(this, "Projekt har uppdaterasts!", "Info", JOptionPane.INFORMATION_MESSAGE);

        }catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid uppdatering av projektdata" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
            }

    }//GEN-LAST:event_btnSparaAndringarHanteraProjektActionPerformed

    private void btnSeProjektStatistikKostnadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeProjektStatistikKostnadActionPerformed
 //       // TODO add your handling code here:
 //       // SQL fråga som hämtar sumerad kostnad baserad på om mitt projekt är avslutat eller pågående
 //       String query = "SELECT status, SUM(kostnad) AS total_kostnad FROM projekt WHERE projektchef = '" + aid + "' GROUP BY status";
 //       
 //       ArrayList<HashMap<String, String>> statistikOverProjekten;
 //       try {
 //           statistikOverProjekten = idb.fetchRows(query);
 //       } catch (InfException ex) {
 //           Logger.getLogger(HanteraProjektProjektledare.class.getName()).log(Level.SEVERE, null, ex);
 //       }
 //       
 //       // Lokala variabler för att lagra pågående och avslutade projekt
 //       int projektAvsSumKost = 0;
 //       int projektPagSumKost= 0;
 //       
 //       if(statistikOverProjekten != null) {
 //           for(HashMap<String, String> radenIListan : statistikOverProjekten){
 //               String status = radenIListan.get("status");
 //               // Använder Integrer.parseInt eftersom 
 //               int kostnad = Integer.parseInt(radenIListan.get("total_kostnad")); 
 //               
 //               if("Avslutat".equalsIgnoreCase(status)){
//                    projektAvsSumKost = projektAvsSumKost + kostnad;
 //               } else if ("Pågående".equalsIgnoreCase(status)){
 //                   projektPagSumKost = projektPagSumKost + kostnad;
 //               }
 //           }
//        }
        // Visa statistiken i en ruta som poppar upp
  //      JOptionPane.showMessageDialog(this, "Total kostnad avslutade projekt: " + projektAvsSumKost + " kr" +
  //              "Total kostnad pågående projekt: " + projektPagSumKost + " kr" , JOptionPane.INFORMATION_MESSAGE);
        
    }//GEN-LAST:event_btnSeProjektStatistikKostnadActionPerformed

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
            java.util.logging.Logger.getLogger(HanteraProjektProjektledare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HanteraProjektProjektledare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HanteraProjektProjektledare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HanteraProjektProjektledare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new HanteraProjektProjektledare().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSeProjektStatistikKostnad;
    private javax.swing.JButton btnSparaAndringarHanteraProjekt;
    private javax.swing.JLabel lblRubrikHanteraProjekt;
    private javax.swing.JScrollPane scrHanteraProjekt;
    private javax.swing.JTable tblHanteraProjekt;
    // End of variables declaration//GEN-END:variables

}