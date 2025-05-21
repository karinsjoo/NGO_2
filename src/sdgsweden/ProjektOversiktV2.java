/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdgsweden;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import oru.inf.InfDB;
import oru.inf.InfException;

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
            String sqlFraga = "SELECT projektchef FROM projekt WHERE aid = '" + aId + "'";

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
        
    private void visaAvdelningensProjekt() //Visar alla projekt där någon i användarens avdelning deltar
    {
        try {
            String sqlFraga = "SELECT DISTINCT projektnamn FROM projekt "
                    + "JOIN ans_proj ON projekt.pid = ans_proj.projekt "
                    + "JOIN anstalld ON ans-Proj.anstalid = anstalld.aid"
                    + "WHERE anstalld.avdelning = '" + anvandareBehorighet.avdelningId + "'";

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
        try {
            String sql = "SELECT DISTINCT projektnamn FROM projekt "
                    + "JOIN ans_proj ON projekt.pid = ans_proj.projekt"
                    + "JOIN anstalld ON ans_proj.anstalid = anstalld.aid "
                    + "WHERE anstalld.avdelning = '" +  anvandareBehorighet.avdelningId + "'"
                    + "AND projekt.startdatum >= '" + from + "' "
                    + "AND projekt.slutdatum<= '" + to + "'";
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(lblFrom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lblTo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSok)
                        .addGap(18, 18, 18)
                        .addComponent(lblFelDatumFormat, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(293, Short.MAX_VALUE))
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
                    .addComponent(lblFelDatumFormat))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(235, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromActionPerformed

    private void txtToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtToActionPerformed

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
    private javax.swing.JButton btnSok;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFelDatumFormat;
    private javax.swing.JLabel lblFrom;
    private javax.swing.JLabel lblTo;
    private javax.swing.JList<String> lstProjekt;
    private javax.swing.JTextField txtFrom;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}
