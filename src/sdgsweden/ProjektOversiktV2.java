/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdgsweden;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import oru.inf.InfDB;
import oru.inf.InfException;
import javax.swing.JOptionPane;

/**
 *
 * @author karin
 */
public class ProjektOversiktV2 extends javax.swing.JFrame {

    private final InfDB idb; //Databsuppkoppling
    private AnvandareBehorighet anvandareBehorighet; //Håller info om den inloggade personen

    /**
     * Creates new form ProjektOversiktV2
     */
    public ProjektOversiktV2(InfDB idb, String epost) {
        this.idb = idb;

        var aId = getId(epost);
        this.anvandareBehorighet = getAnvandareBehorighet(aId);

        initComponents();
        var modell = new DefaultListModel<String>();
        lstProjekt.setModel(modell);
        // Hämta och visa projekt direkt baserat på roll
        visaProjekt();
        laggTillProjektLyssnare();
//  Visa projekt efter filtrering status
        cbStatusFilter.addActionListener(evt -> filtreraProjektPaStatus());
// Dölj datumfilter-komponenter från start
        lblFrom.setVisible(false);
        txtFrom.setVisible(false);
        lblTo.setVisible(false);
        txtTo.setVisible(false);
        btnSok.setVisible(false);

// Dölj felmeddelande
        lblFelDatumFormat.setVisible(false);

        lblFelDatumFormat.setText(anvandareBehorighet.aId + anvandareBehorighet.avdelningId + anvandareBehorighet.isHandlaggare + anvandareBehorighet.isAdmin + anvandareBehorighet.isProjektChef);
        lblFelDatumFormat.setVisible(true);
        lblFrom.setVisible(false);//Sätter dessa datumfält till false så att de inte syns från start
        txtFrom.setVisible(false);
        lblTo.setVisible(false);
        txtTo.setVisible(false);
        btnSok.setVisible(false);
    }

    private AnvandareBehorighet getAnvandareBehorighet(String aId) //
    {
        boolean isAdmin = false;
        boolean isHandlaggare = false;
        boolean isProjektChef = false;
        String avdelningId = "";

        //hämta admin behörighet
        try {
            String sqlFraga = "SELECT aid FROM admin WHERE aid = '" + aId + "'";

            //Hämta resultat från DB: 
            String resultat = idb.fetchSingle(sqlFraga);

            if (resultat != null && aId.equals(resultat)) {
                isAdmin = true;
            }
        } catch (InfException e) {
            //lblFelDatumFormat.setText("Fel uppstod vid hämtning av projekt.");
            System.out.println("Databasfel: " + e.getMessage());
        }

        //hämta handläggare behörighet
        try {
            String sqlFraga = "SELECT aid FROM handlaggare WHERE aid = '" + aId + "'";

            //Hämta resultat från DB: 
            String resultat = idb.fetchSingle(sqlFraga);

            if (resultat != null && aId.equals(resultat)) {
                isHandlaggare = true;
            }
        } catch (InfException e) {
            //lblFelDatumFormat.setText("Fel uppstod vid hämtning av projekt.");
            System.out.println("Databasfel: " + e.getMessage());
        }

        //hämta projektchef behörighet
        try {
            String sqlFraga = "SELECT projektchef FROM projekt WHERE projektchef = '" + aId + "'";

            //Hämta resultat från DB: 
            String resultat = idb.fetchSingle(sqlFraga);

            if (resultat != null && aId.equals(resultat)) {
                isProjektChef = true;
            }
        } catch (InfException e) {
            //lblFelDatumFormat.setText("Fel uppstod vid hämtning av projekt.");
            System.out.println("Databasfel: " + e.getMessage());
        }

        //hämta avdelningId
        try {
            String sqlFraga = "SELECT avdelning FROM anstalld WHERE aid = '" + aId + "'";

            //Hämta resultat från DB: 
            String resultat = idb.fetchSingle(sqlFraga);
            avdelningId = resultat;

        } catch (InfException e) {
            //lblFelDatumFormat.setText("Fel uppstod vid hämtning av projekt.");
            System.out.println("Databasfel: " + e.getMessage());
        }

        var anvandareBehorighet = new AnvandareBehorighet();
        anvandareBehorighet.isAdmin = isAdmin;
        anvandareBehorighet.isHandlaggare = isHandlaggare;
        anvandareBehorighet.isProjektChef = isProjektChef;
        anvandareBehorighet.aId = aId;
        anvandareBehorighet.avdelningId = avdelningId;

        return anvandareBehorighet;

    }

    private String getId(String epost) {
        //hämta aId

        String aId = "";

        try {
            String sqlFraga = "SELECT aid FROM anstalld WHERE epost = '" + epost + "'";

            //Hämta resultat från DB: 
            String resultat = idb.fetchSingle(sqlFraga);

            aId = resultat;

        } catch (InfException e) {
            // lblFelDatumFormat.setText("Fel uppstod vid hämtning av projekt.");
            System.out.println("Databasfel: " + e.getMessage());
        }

        return aId;
    }

    private void visaProjekt() {
        try {

            ArrayList<HashMap<String, String>> resultat = new ArrayList<>();

            if (anvandareBehorighet.isAdmin) {
                // Admin: visa alla projekt
                String sql = "SELECT projektnamn FROM projekt";
                resultat = idb.fetchRows(sql);
            } else if (anvandareBehorighet.isProjektChef) {
                // Projektchef: visa projekt där användaren är projektchef
                String sql = "SELECT projektnamn FROM projekt WHERE projektchef = " + anvandareBehorighet.aId;
                resultat = idb.fetchRows(sql);
            } else if (anvandareBehorighet.isHandlaggare) {
                // Handläggare: visa projekt där användaren deltar
                String sql = "SELECT DISTINCT projektnamn FROM projekt "
                        + "JOIN ans_proj ON projekt.pid = ans_proj.pid "
                        + "WHERE ans_proj.aid = " + anvandareBehorighet.aId;
                resultat = idb.fetchRows(sql);
            }

            var modell = new DefaultListModel<String>();

            for (HashMap<String, String> rad : resultat) {
                modell.addElement(rad.get("projektnamn"));
            }

            lstProjekt.setModel(modell);

        } catch (InfException e) {
            lblFelDatumFormat.setText("Fel uppstod vid hämtning av projekt.");
            System.out.println("Databasfel: " + e.getMessage());
        }
    }

    private void visaAvdelningensProjekt() //Visar alla projekt där någon i användarens avdelning deltar
    {
        try {
            String sqlFraga = "SELECT DISTINCT projektnamn FROM projekt "
                    + "JOIN ans_proj ON projekt.pid = ans_proj.pid "
                    + "JOIN anstalld ON ans_proj.aid = anstalld.aid "
                    + "WHERE anstalld.avdelning = " + anvandareBehorighet.avdelningId;

            //Hämta resultat från DB: 
            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sqlFraga);

            //Skapa modell till listan
            DefaultListModel<String> modell = new DefaultListModel<>();

            for (HashMap<String, String> rad : resultat) {
                modell.addElement(rad.get("projektnamn")); //varje projekt
            }

            lstProjekt.setModel(modell);
        } catch (InfException e) {
            lblFelDatumFormat.setText("Fel uppstod vid hämtning av projekt.");
            System.out.println("Databasfel: " + e.getMessage());
        }
    }

    private void visaProjektInomDatum(String from, String to) //Visar projekt från användarens avdelning inom ett valt datumintervall
    {
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        try {
            String sql = "SELECT DISTINCT projektnamn FROM projekt "
                    + "JOIN ans_proj ON projekt.pid = ans_proj.pid "
                    + "JOIN anstalld ON ans_proj.aid = anstalld.aid "
                    + "WHERE anstalld.avdelning = " + anvandareBehorighet.avdelningId + " "
                    + "AND projekt.startdatum >= '" + from + "' "
                    + "AND projekt.slutdatum <= '" + to + "' "
                    + "AND projekt.status = 'Pågående'";

//Hämta resultat från DB: 
            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sql);
            //Skapa modell till listan
            DefaultListModel<String> modell = new DefaultListModel<>();

            for (HashMap<String, String> rad : resultat) {
                modell.addElement(rad.get("projektnamn")); //varje projekt
            }

            lstProjekt.setModel(modell);
        } catch (InfException e) {
            lblFelDatumFormat.setText("Fel uppstod vid hämtning av projekt.");
            System.out.println("Databasfel: " + e.getMessage());
        }
    }
    private void filtreraProjektPaStatus() {
        if (!anvandareBehorighet.isHandlaggare && !anvandareBehorighet.isProjektChef) {
            return;
        }

        String valdStatus = (String) cbStatusFilter.getSelectedItem();
        String statusVillkor = "";

        if (!"Alla".equals(valdStatus)) {
            statusVillkor = "AND projekt.status = '" + valdStatus + "' ";
        }

        try {
            String sql = "SELECT DISTINCT projektnamn FROM projekt "
                    + "JOIN ans_proj ON projekt.pid = ans_proj.pid "
                    + "JOIN anstalld ON ans_proj.aid = anstalld.aid "
                    + "WHERE anstalld.avdelning = '" + anvandareBehorighet.avdelningId + "' "
                    + statusVillkor;

            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sql);
            DefaultListModel<String> modell = new DefaultListModel<>();

            for (HashMap<String, String> rad : resultat) {
                modell.addElement(rad.get("projektnamn"));
            }

            lstProjekt.setModel(modell);
        } catch (InfException e) {
            System.out.println("Fel vid filtrering: " + e.getMessage());
        }
    }

    private void laggTillProjektLyssnare() {
        lstProjekt.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String valtProjektnamn = lstProjekt.getSelectedValue();
                if (valtProjektnamn != null) {
                    oppnaProjektDetaljer(valtProjektnamn);
                }
            }
        });
    }

    private void oppnaProjektDetaljer(String projektnamn) {
        try {
            String sql = "SELECT pid FROM projekt WHERE projektnamn = '" + projektnamn + "'";
            String projektID = idb.fetchSingle(sql);

            if (projektID != null) {
                ProjektDetaljer detaljVy = new ProjektDetaljer(idb, projektID, anvandareBehorighet);
                detaljVy.setVisible(true);
            } else {
                System.out.println("Projekt-ID kunde inte hittas för: " + projektnamn);
            }

        } catch (InfException ex) {
            System.out.println("Fel vid öppning av projekt: " + ex.getMessage());
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

        lblFrom = new javax.swing.JLabel();
        txtFrom = new javax.swing.JTextField();
        lblTo = new javax.swing.JLabel();
        txtTo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstProjekt = new javax.swing.JList<>();
        btnSok = new javax.swing.JButton();
        lblFelDatumFormat = new javax.swing.JLabel();
        btnAvdProjekt = new javax.swing.JButton();
        btnDatumProjekt = new javax.swing.JButton();
        cbStatusFilter = new javax.swing.JComboBox<>();
        lblFiltrerastatus = new javax.swing.JLabel();
        btnVisaStatistik = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblFrom.setText("Från datum");

        txtFrom.setText("YYYY-MM-DD");
        txtFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFromActionPerformed(evt);
            }
        });

        lblTo.setText("Till datum");

        txtTo.setText("YYYY-MM-DD");
        txtTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtToActionPerformed(evt);
            }
        });

        lstProjekt.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstProjekt);

        btnSok.setText("Sök");
        btnSok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSokActionPerformed(evt);
            }
        });

        btnAvdProjekt.setText("Avdelningens projekt");
        btnAvdProjekt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvdProjektActionPerformed(evt);
            }
        });

        btnDatumProjekt.setText("Projekt inom datum");
        btnDatumProjekt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatumProjektActionPerformed(evt);
            }
        });

        cbStatusFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alla", "Planerat", "Pågående", "Avslutat" }));

        lblFiltrerastatus.setText("Filtrera på status");

        btnVisaStatistik.setText("Visa kostnadsstatistik");
        btnVisaStatistik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisaStatistikActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAvdProjekt)
                            .addComponent(lblFiltrerastatus, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDatumProjekt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFrom)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSok)
                                .addGap(82, 82, 82)
                                .addComponent(lblFelDatumFormat, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnVisaStatistik, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(150, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFrom)
                    .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTo)
                    .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSok)
                    .addComponent(lblFelDatumFormat)
                    .addComponent(btnDatumProjekt)
                    .addComponent(btnAvdProjekt))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFiltrerastatus))
                .addGap(18, 18, 18)
                .addComponent(btnVisaStatistik)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromActionPerformed

    private void txtToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtToActionPerformed

    private void btnAvdProjektActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvdProjektActionPerformed
        visaAvdelningensProjekt();
    }//GEN-LAST:event_btnAvdProjektActionPerformed

    private void btnDatumProjektActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatumProjektActionPerformed
        lblFrom.setVisible(true);
        txtFrom.setVisible(true);
        lblTo.setVisible(true);
        txtTo.setVisible(true);
        btnSok.setVisible(true);
    }//GEN-LAST:event_btnDatumProjektActionPerformed

    private void btnSokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSokActionPerformed
        String from = txtFrom.getText();
        String to = txtTo.getText();

        if (Validering.arDatum(from) && Validering.arDatum(to)) {
            visaProjektInomDatum(from, to);
            lblFelDatumFormat.setVisible(false);
        } else {
            lblFelDatumFormat.setText("Felaktigt datumformat (YYYY-MM-DD)");
            lblFelDatumFormat.setVisible(true);
        }
    }//GEN-LAST:event_btnSokActionPerformed

    private void btnVisaStatistikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisaStatistikActionPerformed
    
    if (!anvandareBehorighet.isProjektChef) {
        JOptionPane.showMessageDialog(this, "Endast projektchefer kan se denna statistik.");
        return;
    }

    try {
        String sql = "SELECT kostnad FROM projekt WHERE projektchef = '" + anvandareBehorighet.aId + "'";
        ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sql);

        double totalKostnad = 0;
        int antalProjekt = 0;

        for (HashMap<String, String> rad : resultat) {
            String kostnadStr = rad.get("kostnad");
            if (kostnadStr != null && kostnadStr.trim().matches("\\d+(\\.\\d+)?")) {
                totalKostnad += Double.parseDouble(kostnadStr.trim());
                antalProjekt++;
            }
        }

        double genomsnitt = (antalProjekt > 0) ? (double) totalKostnad / antalProjekt : 0;

        String meddelande = "Du är/har varit ansvarig för " + antalProjekt + " projekt.\n"
                + "Total kostnad: " + totalKostnad + " kr\n"
                + "Genomsnittlig kostnad: " + String.format("%.2f", genomsnitt) + " kr";

        JOptionPane.showMessageDialog(this, meddelande, "Statistik", JOptionPane.INFORMATION_MESSAGE);

    } catch (InfException e) {
        JOptionPane.showMessageDialog(this, "Fel vid hämtning av statistik: " + e.getMessage());
    }


    }//GEN-LAST:event_btnVisaStatistikActionPerformed

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
            java.util.logging.Logger.getLogger(ProjektOversiktV2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjektOversiktV2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjektOversiktV2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjektOversiktV2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ProjektOversiktV2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvdProjekt;
    private javax.swing.JButton btnDatumProjekt;
    private javax.swing.JButton btnSok;
    private javax.swing.JButton btnVisaStatistik;
    private javax.swing.JComboBox<String> cbStatusFilter;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFelDatumFormat;
    private javax.swing.JLabel lblFiltrerastatus;
    private javax.swing.JLabel lblFrom;
    private javax.swing.JLabel lblTo;
    private javax.swing.JList<String> lstProjekt;
    private javax.swing.JTextField txtFrom;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}
