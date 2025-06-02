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
        jPanelLaggTillLand = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNamn = new javax.swing.JLabel();
        lblSprak = new javax.swing.JLabel();
        lblValuta = new javax.swing.JLabel();
        txtNamn = new javax.swing.JTextField();
        txtSprak = new javax.swing.JTextField();
        txtValuta = new javax.swing.JTextField();
        lblTidszon = new javax.swing.JLabel();
        lblPolitsikStruktur = new javax.swing.JLabel();
        lblEkonomi = new javax.swing.JLabel();
        txtTidszon = new javax.swing.JTextField();
        txtPolitiskStruktur = new javax.swing.JTextField();
        txtEkonomi = new javax.swing.JTextField();
        btnLaggTillLand = new javax.swing.JButton();

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Lägg till nytt land");

        lblNamn.setText("Namn");

        lblSprak.setText("Språk");

        lblValuta.setText("Valuta");

        lblTidszon.setText("Tidszon");

        lblPolitsikStruktur.setText("Politisk Struktur");

        lblEkonomi.setText("Ekonomi");

        btnLaggTillLand.setText("Spara nytt land");
        btnLaggTillLand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggTillLandActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLaggTillLandLayout = new javax.swing.GroupLayout(jPanelLaggTillLand);
        jPanelLaggTillLand.setLayout(jPanelLaggTillLandLayout);
        jPanelLaggTillLandLayout.setHorizontalGroup(
            jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLaggTillLandLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelLaggTillLandLayout.createSequentialGroup()
                        .addGroup(jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblValuta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                            .addComponent(lblSprak, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNamn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSprak, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValuta, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTidszon, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblEkonomi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPolitsikStruktur, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)))
                        .addGap(36, 36, 36)
                        .addGroup(jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPolitiskStruktur, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTidszon, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEkonomi, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(195, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLaggTillLandLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLaggTillLand, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanelLaggTillLandLayout.setVerticalGroup(
            jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLaggTillLandLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNamn)
                    .addComponent(txtNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTidszon)
                    .addComponent(txtTidszon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSprak)
                    .addComponent(txtSprak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPolitsikStruktur)
                    .addComponent(txtPolitiskStruktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelLaggTillLandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValuta)
                    .addComponent(txtValuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEkonomi)
                    .addComponent(txtEkonomi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(btnLaggTillLand))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrHanteraLandAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblRubrikHanderaLandAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSparaAndringarHanteraLandAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jPanelLaggTillLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRubrikHanderaLandAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrHanteraLandAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSparaAndringarHanteraLandAdmin)
                    .addComponent(jPanelLaggTillLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
            
                // Validering
                
                
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

    private void btnLaggTillLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillLandActionPerformed
        //Metod för att lägga till land
        
    try {
        String namn = txtNamn.getText().trim();
        String sprak = txtSprak.getText().trim();
        String valuta = txtValuta.getText().trim();
        String tidszon = txtTidszon.getText().trim();
        String politiskStruktur = txtPolitiskStruktur.getText().trim();
        String ekonomi = txtEkonomi.getText().trim();

        // Validering med felmeddelanden.
        if (!Validation.okNullEllerTom(namn)) {
            JOptionPane.showMessageDialog(this, "Fältet 'Namn' får inte vara tomt.");
            return;
        }

        if (!Validation.okNullEllerTom(sprak)) {
            JOptionPane.showMessageDialog(this, "Fältet 'Språk' får inte vara tomt.");
            return;
        }

        if (!Validation.okNullEllerTom(valuta)) {
            JOptionPane.showMessageDialog(this, "Fältet 'Valuta' får inte vara tomt.");
            return;
        }
        //Denna visar felmeddelandet om tex en sträng skrivs in.
        try {
            Double.parseDouble(valuta);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Fältet 'Valuta' måste vara ett decimaltal, t.ex. 10.00.");
            return;
        }

        if (!Validation.okNullEllerTom(tidszon)) {
            JOptionPane.showMessageDialog(this, "Fältet 'Tidszon' får inte vara tomt.");
            return;
        }

        if (!Validation.okNullEllerTom(politiskStruktur)) {
            JOptionPane.showMessageDialog(this, "Fältet 'Politisk struktur' får inte vara tomt.");
            return;
        }

        if (!Validation.okNullEllerTom(ekonomi)) {
            JOptionPane.showMessageDialog(this, "Fältet 'Ekonomi' får inte vara tomt.");
            return;
        }

        // Skapa nytt land-ID 
        String nyttLid = idb.fetchSingle("SELECT MAX(lid) FROM land");
        if (nyttLid == null) {
            nyttLid = "1";
        } else {
            nyttLid = String.valueOf(Integer.parseInt(nyttLid) + 1);
        }

        // Valuta utan ''
        String sql = "INSERT INTO land (lid, namn, sprak, valuta, tidszon, politisk_struktur, ekonomi) VALUES (" +
                nyttLid + ", '" + namn + "', '" + sprak + "', " + valuta + ", '" + tidszon + "', '" + politiskStruktur + "', '" + ekonomi + "')";

        idb.insert(sql);
        JOptionPane.showMessageDialog(this, "Land tillagt.");
        //Använder befintlig metod
        laddaHanteraLandData();

        // Töm inmatningsfält
        txtNamn.setText("");
        txtSprak.setText("");
        txtValuta.setText("");
        txtTidszon.setText("");
        txtPolitiskStruktur.setText("");
        txtEkonomi.setText("");

    } catch (InfException ex) {
        JOptionPane.showMessageDialog(this, "Fel vid tillägg: " + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_btnLaggTillLandActionPerformed

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
    private javax.swing.JButton btnLaggTillLand;
    private javax.swing.JButton btnSparaAndringarHanteraLandAdmin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelLaggTillLand;
    private javax.swing.JLabel lblEkonomi;
    private javax.swing.JLabel lblNamn;
    private javax.swing.JLabel lblPolitsikStruktur;
    private javax.swing.JLabel lblRubrikHanderaLandAdmin;
    private javax.swing.JLabel lblSprak;
    private javax.swing.JLabel lblTidszon;
    private javax.swing.JLabel lblValuta;
    private javax.swing.JScrollPane scrHanteraLandAdmin;
    private javax.swing.JTable tblHanteraLandAdmin;
    private javax.swing.JTextField txtEkonomi;
    private javax.swing.JTextField txtNamn;
    private javax.swing.JTextField txtPolitiskStruktur;
    private javax.swing.JTextField txtSprak;
    private javax.swing.JTextField txtTidszon;
    private javax.swing.JTextField txtValuta;
    // End of variables declaration//GEN-END:variables
}
