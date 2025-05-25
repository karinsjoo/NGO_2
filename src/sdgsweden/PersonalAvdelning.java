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
public class PersonalAvdelning extends javax.swing.JFrame {
    private final InfDB idb;
    private final String aid;
    
    /**
     * Creates new form PersonalAvdelning
     * @param idb
     * @param aid
     */
    public PersonalAvdelning(InfDB idb, String aid) {
        this.idb = idb;
        this.aid = aid;
        initComponents(); // Skrivs här för att använda sig av NetBeans GUI komponenter
        setLocationRelativeTo(null); // Fönstret hamnar i mitten av dataskärmen
        laddaPersonalData(); // Öppnar tabellen med personal på avdelningen direkt när konstruktorn körs och objektet skapas
        laddaAvdelningPersonalNamn(); // Visar vilken avdelning användaren är inloggad på
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Stänger MinaUppgifterPopup när man trycker på krysset i rutan utan att stänga ner hela programmet
        
        // Tabell - höjden anpassas efter antal rader som laddas för användaren
        int antalRader = tblPersonalPaAvdelning.getRowCount();
        int radHojden =tblPersonalPaAvdelning.getRowHeight();
        int totalHojden = antalRader * radHojden;
        
        // Tabell - bredden anpassas efter en satt bredd och antal kolumner
        int antalKolumner = tblPersonalPaAvdelning.getColumnCount();
        int kolumnBredden = 150; // Den satta bredden varje kolumn får, kommer klippa värdet
        int totalaBredden = antalKolumner * kolumnBredden;
        
        // Ställer in tabellen efter storleken på anpassningarna ovanför
        tblPersonalPaAvdelning.setPreferredSize(new Dimension(totalaBredden, totalHojden));
        tblPersonalPaAvdelning.revalidate(); // Uppdaaterar layout på tabell utifrån storleken på mängden data
        
        // Här kan man sortera på värdena i kolumnerna
        DefaultTableModel anstalldModell = (DefaultTableModel) tblPersonalPaAvdelning.getModel();
        TableRowSorter<DefaultTableModel> tabellSortera = new TableRowSorter<>(anstalldModell);
        tblPersonalPaAvdelning.setRowSorter(tabellSortera);
    }
    
    private void laddaPersonalData(){
    // Fungerar annorlunda än JButton - här var vi tvungna att skriva in kod för kolumner osv och koppla metoden laddaPersonalData till konstruktorn.
    // Detta för att öppna den direkt när PersonalAvdelning öppnas 
    
        try{ // SQL-fråga för att hämta alla anställda ifrån listan som jobbar på samma avdelning som aid
            String query = "SELECT anstalld.aid, anstalld.fornamn, anstalld.efternamn, anstalld.epost, anstalld.telefon FROM anstalld "
                    + "WHERE anstalld.avdelning = (SELECT avdelning FROM anstalld WHERE aid = '" + aid + "') "
                    + "ORDER BY anstalld.aid ASC";
            
            
          // Här skapas tabellen med tabellnamnen
            DefaultTableModel anstalldModell = new DefaultTableModel();
            String [] kolumnNamn = {"Anställd ID", "Förnamn", "Efternamn", "E-post", "Telefon"};
            anstalldModell.setColumnIdentifiers(kolumnNamn);
            
            // Hämtar ifrån databasen i form av en ArrayList eftersom vi hämtar flera rader på en gång. Lagrat i en HashMap
            ArrayList<HashMap<String, String>> anstalldData = idb.fetchRows(query);
            
            if(anstalldData != null){ // Använder oss inte av Valideringsklassen här. Skulle behövt iterera igenom varje rad
                // Nu kollar vi om HELA anstalld tabellen är tom vilket är mer logiskt i detta fall då vi vill ha fram flera kolumner data
                for(HashMap<String, String> anstalldLista : anstalldData){
                    anstalldModell.addRow(new Object []{
                        anstalldLista.get("aid"), // Anstalld-nycklarna (varje kolumn i tabellen i detta fall)
                        anstalldLista.get("fornamn"),
                        anstalldLista.get("efternamn"),
                        anstalldLista.get("epost"),
                        anstalldLista.get("telefon")
                    });
                }
            }
            
            // Laddar upp anstalldLista till tabellen tblPersonalPaAvdelning
            tblPersonalPaAvdelning.setModel(anstalldModell);
            
            // Använder befintlig scroll
            tblPersonalPaAvdelning.setFillsViewportHeight(true); // Gör så tabellen fyller skrollområdet
            if(scrPersonalPaAvdelning.getParent() == null) { // Kolla om skrollpanelen redan finns i GUI
                getContentPane().add(scrPersonalPaAvdelning);
            }
            revalidate();
            repaint(); // Skrollning behöver läggas in EFTER att tabellen fyllts med data så den fungerar som den ska och har värden att sortera
        
        } catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid hämtning av anställda på avdelningen" + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void laddaAvdelningPersonalNamn(){
    try{
        String query = "SELECT avdelning.namn FROM anstalld "
                + "JOIN avdelning ON anstalld.avdelning = avdelning.avdid "
                + "WHERE anstalld.aid = '" + aid + "'";
        
        String avdelningsnamnet = idb.fetchSingle(query);
        lblAvdelningsnamn.setText(avdelningsnamnet);
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

        lblAvdelningsnamn = new javax.swing.JLabel();
        scrPersonalPaAvdelning = new javax.swing.JScrollPane();
        tblPersonalPaAvdelning = new javax.swing.JTable();
        txtSokfalt = new javax.swing.JTextField();
        btnSokKnapp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblAvdelningsnamn.setText("Personal på avdelning");

        tblPersonalPaAvdelning.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Anställd ID", "Förnamn", "Efternamn", "E-post", "Telefon"
            }
        ));
        scrPersonalPaAvdelning.setViewportView(tblPersonalPaAvdelning);

        btnSokKnapp.setText("Sök efter Handläggare, ange E-post");
        btnSokKnapp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSokKnappActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrPersonalPaAvdelning, javax.swing.GroupLayout.PREFERRED_SIZE, 812, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtSokfalt, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSokKnapp))
                        .addComponent(lblAvdelningsnamn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(lblAvdelningsnamn)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSokfalt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSokKnapp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrPersonalPaAvdelning, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSokKnappActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSokKnappActionPerformed
        // TODO add your handling code here:
        // Sökknapp för att leta efter en handläggare via epost
        String sokaTextRuta = txtSokfalt.getText();
        String query;
        
        if(!Validation.okNullEllerTom(sokaTextRuta)){
            // Om rutan är tom visas alla anställda utan filter
            laddaPersonalData();
            return;
            } else {
            // Om man anger e-post i textfältet txtSokfalt - filtrera på handläggare inom avdelningen
            query = "SELECT anstalld.aid, anstalld.fornamn, anstalld.efternamn, anstalld.epost, anstalld.telefon FROM anstalld "
                    + "JOIN handlaggare ON anstalld.aid = handlaggare.aid " // Sök bara på handläggare
                    + "WHERE anstalld.avdelning = (SELECT avdelning FROM anstalld WHERE aid = '" + aid + "') "
                    + "AND anstalld.epost LIKE '%" + sokaTextRuta + "%' "
                    + "ORDER BY anstalld.aid ASC";
                    }
        
        DefaultTableModel anstalldModell = (DefaultTableModel) tblPersonalPaAvdelning.getModel();
        anstalldModell.setRowCount(0); // Rensar befintliga rader
        
        try {
        
        ArrayList<HashMap<String, String>> anstalldData = idb.fetchRows(query);
        
        if(anstalldData != null){
            for(HashMap<String, String> anstalldLista : anstalldData) {
            anstalldModell.addRow(new Object[]{
                anstalldLista.get("aid"),
                anstalldLista.get("fornamn"),
                anstalldLista.get("efternamn"),
                anstalldLista.get("epost"),
                anstalldLista.get("telefon")
            
            });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingen Handläggare med den e-postadressen hittades.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
       // Sortering och filtrering av data
       TableRowSorter<DefaultTableModel> tabellSortering = (TableRowSorter<DefaultTableModel>) tblPersonalPaAvdelning.getRowSorter();
       if(sokaTextRuta.isEmpty()){
           tabellSortering.setRowFilter(null); // Visar alla rader om textrutan är tom!
       } else {
           tabellSortering.setRowFilter(RowFilter.regexFilter("(?i)" + sokaTextRuta)); // Case insensitive sätt att söka i rutan.
       }

    }//GEN-LAST:event_btnSokKnappActionPerformed
        catch(InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid sökning av handläggare: ", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
    
        }
    }
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
            java.util.logging.Logger.getLogger(PersonalAvdelning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PersonalAvdelning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PersonalAvdelning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PersonalAvdelning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new PersonalAvdelning().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSokKnapp;
    private javax.swing.JLabel lblAvdelningsnamn;
    private javax.swing.JScrollPane scrPersonalPaAvdelning;
    private javax.swing.JTable tblPersonalPaAvdelning;
    private javax.swing.JTextField txtSokfalt;
    // End of variables declaration//GEN-END:variables
}
