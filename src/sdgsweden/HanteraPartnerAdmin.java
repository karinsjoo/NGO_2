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
        jPanelLaggTillPartner = new javax.swing.JPanel();
        lblRubrikLaggTillPartner = new javax.swing.JLabel();
        lblNamn = new javax.swing.JLabel();
        lblKontaktperson = new javax.swing.JLabel();
        lblEpost = new javax.swing.JLabel();
        lblStad = new javax.swing.JLabel();
        lblTelefon = new javax.swing.JLabel();
        lblAdress = new javax.swing.JLabel();
        lblBranch = new javax.swing.JLabel();
        txtNamn = new javax.swing.JTextField();
        txtKontaktperson = new javax.swing.JTextField();
        txtEpost = new javax.swing.JTextField();
        txtStad = new javax.swing.JTextField();
        txtTelefon = new javax.swing.JTextField();
        txtAdress = new javax.swing.JTextField();
        txtBranch = new javax.swing.JTextField();
        btnSparapartner = new javax.swing.JButton();
        lblFormatTel = new javax.swing.JLabel();
        btnTaBortPartner = new javax.swing.JButton();

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

        lblRubrikLaggTillPartner.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRubrikLaggTillPartner.setText("Lägg till ny partner");

        lblNamn.setText("Namn");

        lblKontaktperson.setText("Kontaktperson");

        lblEpost.setText("E-post");

        lblStad.setText("Stad");

        lblTelefon.setText("Telefon");

        lblAdress.setText("Adress");

        lblBranch.setText("Branch");

        btnSparapartner.setText("Spara tillagd partner");
        btnSparapartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparapartnerActionPerformed(evt);
            }
        });

        lblFormatTel.setText("000-000-0000");

        javax.swing.GroupLayout jPanelLaggTillPartnerLayout = new javax.swing.GroupLayout(jPanelLaggTillPartner);
        jPanelLaggTillPartner.setLayout(jPanelLaggTillPartnerLayout);
        jPanelLaggTillPartnerLayout.setHorizontalGroup(
            jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLaggTillPartnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLaggTillPartnerLayout.createSequentialGroup()
                        .addComponent(lblRubrikLaggTillPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelLaggTillPartnerLayout.createSequentialGroup()
                        .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblKontaktperson, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(lblEpost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblStad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNamn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtEpost)
                            .addComponent(txtKontaktperson)
                            .addComponent(txtNamn)
                            .addComponent(txtStad, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                        .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLaggTillPartnerLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBranch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTelefon, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                    .addComponent(txtAdress)
                                    .addComponent(txtBranch))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFormatTel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLaggTillPartnerLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSparapartner)
                                .addGap(172, 172, 172))))))
        );
        jPanelLaggTillPartnerLayout.setVerticalGroup(
            jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLaggTillPartnerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRubrikLaggTillPartner)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNamn)
                    .addComponent(lblTelefon)
                    .addComponent(txtNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFormatTel))
                .addGap(18, 18, 18)
                .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKontaktperson)
                    .addComponent(lblAdress)
                    .addComponent(txtKontaktperson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEpost)
                    .addComponent(lblBranch)
                    .addComponent(txtEpost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLaggTillPartnerLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelLaggTillPartnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStad)
                            .addComponent(txtStad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLaggTillPartnerLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSparapartner)
                        .addGap(17, 17, 17))))
        );

        btnTaBortPartner.setText("Ta bort vald partner");
        btnTaBortPartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortPartnerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrHanteraPartnerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnHanteraPartnerAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(btnTaBortPartner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40)
                        .addComponent(jPanelLaggTillPartner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblRubrikHanteraPartnerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRubrikHanteraPartnerAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrHanteraPartnerAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelLaggTillPartner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHanteraPartnerAdmin)
                        .addGap(18, 18, 18)
                        .addComponent(btnTaBortPartner))))
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

    private void btnSparapartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparapartnerActionPerformed
        //För att lägga till ny partner
                try{
                String namn = txtNamn.getText().trim();
                String kontaktperson = txtKontaktperson.getText().trim();
                String epost = txtEpost.getText().trim();
                String telefon = txtTelefon.getText().trim();
                String adress = txtAdress.getText().trim();
                String branch = txtBranch.getText().trim();
                String stad = txtStad.getText().trim();
               
               
                if (!Validation.okNullEllerTom(namn)) {
                        JOptionPane.showMessageDialog(this, "Fältet 'namn' saknas eller är ogiltigt.");
                        return;
                    }
                    if (!Validation.okNullEllerTom(kontaktperson)) {
                        JOptionPane.showMessageDialog(this, "Fältet 'kontaktperson' saknas eller är ogiltigt.");
                        return;
                    }
                    if (!Validation.okEpost(epost)) {
                        JOptionPane.showMessageDialog(this, "Ogiltig e-postadress.");
                        return;
                    }
                    if (!Validation.okTelefon(telefon)) {
                        JOptionPane.showMessageDialog(this, "Ogiltigt telefonnummer. Exempel: 070-123-4567");
                        return;
                    }
                    if (!Validation.okNullEllerTom(adress)) {
                        JOptionPane.showMessageDialog(this, "Fältet 'adress' saknas eller är ogiltigt.");
                        return;
                    }
                    if (!Validation.okNullEllerTom(branch)) {
                        JOptionPane.showMessageDialog(this, "Fältet 'branch' saknas eller är ogiltigt.");
                        return;
                    }
                    if (!Validation.okNullEllerTom(stad)) {
                        JOptionPane.showMessageDialog(this, "Fältet 'stad' saknas eller är ogiltigt.");
                        return;
                    }
                   
                    // Kontrollera att stad är ett tal
                    try {
                        Integer.parseInt(stad);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Stad måste vara ett heltal (t.ex. 1 för Stockholm).");
                        return;
                    }
                    //Skapar nytt pid utan att det behöver fyllas i, tar maxvärdet på befintliga och ökar värdet med 1. Garderar för null
                    String nyttPid = idb.fetchSingle("SELECT MAX(pid) FROM partner");
                    if (nyttPid == null) {
                        nyttPid = "1";
                    } else {
                        nyttPid = String.valueOf(Integer.parseInt(nyttPid) + 1);
                    }

                String sql = "INSERT INTO PARTNER (pid, namn, kontaktperson, kontaktepost, telefon, adress, branch, stad)" +  
                        "VALUES (" + nyttPid + ", '" + namn + "', '" + kontaktperson + "', '" + epost + "', '" + telefon + "', '" + adress + "', '" + branch + "', " + stad+")";
                
                idb.insert(sql);
                JOptionPane.showMessageDialog(this, "Partner tillagd");
  
                //Använder metoden laddaHanteraProjektData för att uppdatera tabell och gör därefter textfälten tomma 
                laddaHanteraPartnerData();
                txtNamn.setText("");
                txtKontaktperson.setText("");
                txtEpost.setText("");
                txtTelefon.setText("");
                txtAdress.setText("");
                txtBranch.setText("");
                txtStad.setText("");
                
            } catch (InfException ex){
                JOptionPane.showMessageDialog(this, "Fel vid tillägg: " + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
            }
                                      
    }//GEN-LAST:event_btnSparapartnerActionPerformed

    private void btnTaBortPartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortPartnerActionPerformed
        // Metod för att ta bort partner
        
        int rad = tblHanteraPartnerAdmin.getSelectedRow();
        
        if (rad == -1){
            JOptionPane.showMessageDialog(this, "Markera en partner att ta bort.");
            return;
        }
        //Kontroll, rätt partner att ta bort?
        int bekrafta = JOptionPane.showConfirmDialog(this, "Är du säker på att du vill ta bort denna partner?", "Bekräfta borttagning", JOptionPane.YES_NO_OPTION);
        if (bekrafta != JOptionPane.YES_OPTION)
            return;

        try {
            String pid = tblHanteraPartnerAdmin.getValueAt(rad, 0).toString();
            String sql = "DELETE FROM partner WHERE pid = " + pid;
            idb.delete(sql);

            JOptionPane.showMessageDialog(this, "Partnern har tagits bort.");
            laddaHanteraPartnerData();
        } catch (InfException ex){
            JOptionPane.showMessageDialog(this, "Fel vid borttagning: " + ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
        }
                                                    
    }//GEN-LAST:event_btnTaBortPartnerActionPerformed

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
    private javax.swing.JButton btnSparapartner;
    private javax.swing.JButton btnTaBortPartner;
    private javax.swing.JPanel jPanelLaggTillPartner;
    private javax.swing.JLabel lblAdress;
    private javax.swing.JLabel lblBranch;
    private javax.swing.JLabel lblEpost;
    private javax.swing.JLabel lblFormatTel;
    private javax.swing.JLabel lblKontaktperson;
    private javax.swing.JLabel lblNamn;
    private javax.swing.JLabel lblRubrikHanteraPartnerAdmin;
    private javax.swing.JLabel lblRubrikLaggTillPartner;
    private javax.swing.JLabel lblStad;
    private javax.swing.JLabel lblTelefon;
    private javax.swing.JScrollPane scrHanteraPartnerAdmin;
    private javax.swing.JTable tblHanteraPartnerAdmin;
    private javax.swing.JTextField txtAdress;
    private javax.swing.JTextField txtBranch;
    private javax.swing.JTextField txtEpost;
    private javax.swing.JTextField txtKontaktperson;
    private javax.swing.JTextField txtNamn;
    private javax.swing.JTextField txtStad;
    private javax.swing.JTextField txtTelefon;
    // End of variables declaration//GEN-END:variables
}
