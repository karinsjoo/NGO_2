/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdgsweden;

import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author karin
 */
public class ProjektDetaljer extends javax.swing.JFrame {

    private String projektID;
    private InfDB idb;
    private AnvandareBehorighet anvandareBehorighet;
    //private franAnvandarKlass anvandare;

    /**
     * Creates new form ProjektDetaljer
     */
    public ProjektDetaljer(InfDB idb, String projektID, AnvandareBehorighet anvandareBehorighet) {
        initComponents();
        this.idb = idb;
        this.projektID = projektID;
        this.anvandareBehorighet = anvandareBehorighet;
        //this.anvandare = anvandare;
        
//        kontrolleraBehorighet();
       fyllProjektinfo();
        fyllHandlaggare();
        fyllPartner();
        kontrolleraBehorighet();
//        setFaltRedigerbara(false);
    }

    private ProjektDetaljer() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //Metod för att hämta info om ett projekt från DB
    private void fyllProjektinfo() 
    {
        System.out.println("Fyller info för PID: " + projektID);
        try {
            String sql = "SELECT * FROM projekt WHERE pid = '" + projektID + "';";
            HashMap<String, String> projektData = idb.fetchRow(sql);
            System.out.println("Projektdata: " + projektData);
            
            if (projektData != null) {
                var projektnamn = projektData.get("projektnamn");
                txtProjektnamn.setText(projektData.get("projektnamn"));
                txtProjektID.setText(projektData.get("pid"));
                txtLand.setText(projektData.get("land"));
                txtBeskrivning.setText(projektData.get("beskrivning"));
                txtStartdatum.setText(projektData.get("startdatum"));
                txtSlutdatum.setText(projektData.get("slutdatum"));
                cbStatus.setSelectedItem(projektData.get("status"));
                txtKostnad.setText(projektData.get("kostnad"));
                cbPrio.setSelectedItem(projektData.get("prioritet"));
                
                //  Hämta ID på projektchefen från projekt-tabellen
                String projektchefId = projektData.get("projektchef");
                fyllProjektchefCombo(projektchefId);

            }

        } catch (InfException e) {
            System.out.println("Fel vid hämtning av projektinfo: " + e.getMessage());
        }
    }
//Metod för att läsa in Handläggare till listor

    private void fyllHandlaggare() {
        try {
            String sql = "SELECT fornamn, efternamn FROM anstalld "
                    + "JOIN ans_proj ON anstalld.aid = ans_proj.aid "
                    + "WHERE ans_proj.pid = '" + projektID + "'";
            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sql);

            DefaultListModel<String> modell = new DefaultListModel<>();

            for (HashMap<String, String> rad : resultat) {
                String namn = rad.get("fornamn") + " " + rad.get("efternamn");
                modell.addElement(namn);
            }

            listHandlaggare.setModel(modell);
        } catch (InfException e) {
            System.out.println("Fel vid hämtning av handläggare: " + e.getMessage());
        }
    }

////Metod för att läsa in partner till listor
private void fyllPartner() {
        try {
            String sql = "SELECT namn FROM partner "
                    + "JOIN projekt_partner ON partner.pid = projekt_partner.partner_pid "
                    + "WHERE projekt_partner.pid = '" + projektID + "'";
            ArrayList<String> resultat = idb.fetchColumn(sql);

            DefaultListModel<String> modell = new DefaultListModel<>();

            for (String namn : resultat) {
                modell.addElement(namn);
            }

            listPartner.setModel(modell);
        } catch (InfException e) {
            System.out.println("Fel vid hämtning av partner: " + e.getMessage());
        }
    }

    private void laggTillHandlaggare() {
        try {
            // Hämta alla handläggare som inte redan är kopplade till projektet
            String sql = "SELECT anstalld.aid, fornamn, efternamn FROM anstalld "
                    + "JOIN handlaggare ON anstalld.aid = handlaggare.aid "
                    + "WHERE anstalld.aid NOT IN ("
                    + "SELECT aid FROM ans_proj WHERE pid = '" + projektID + "')";

            ArrayList<HashMap<String, String>> handlaggare = idb.fetchRows(sql);

            if (handlaggare.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Alla handläggare är redan kopplade.");
                return;
            }

            // Skapa en lista att visa i dialogrutan
            String[] val = new String[handlaggare.size()];
            for (int i = 0; i < handlaggare.size(); i++) {
                HashMap<String, String> rad = handlaggare.get(i);
                val[i] = rad.get("aid") + " - " + rad.get("fornamn") + " " + rad.get("efternamn");
            }

            // Visa valruta
            String valt = (String) JOptionPane.showInputDialog(this, "Välj handläggare att lägga till:",
                    "Lägg till handläggare", JOptionPane.PLAIN_MESSAGE, null, val, val[0]);

            if (valt != null) {
                String valtAid = valt.split(" - ")[0];
                String insertSql = "INSERT INTO ans_proj (aid, pid) VALUES ('" + valtAid + "', '" + projektID + "')";
                idb.insert(insertSql);

                JOptionPane.showMessageDialog(this, "Handläggare lades till.");
                fyllHandlaggare(); // uppdatera listan
            }

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Fel vid tillägg: " + e.getMessage());
        }
    }

    private void tabortHandlaggare() {
        try {
            // Kolla om något är markerat
            String vald = listHandlaggare.getSelectedValue();

            if (vald == null) {
                JOptionPane.showMessageDialog(this, "Välj en handläggare att ta bort.");
                return;
            }

            // Fråga användaren för säkerhets skull
            int svar = JOptionPane.showConfirmDialog(this,
                    "Vill du ta bort handläggaren från projektet?",
                    "Bekräfta", JOptionPane.YES_NO_OPTION);

            if (svar != JOptionPane.YES_OPTION) {
                return;
            }

            // Plocka ut för- och efternamn
            String[] namn = vald.split(" ");
            String fornamn = namn[0];
            String efternamn = namn[1];

            // Hämta aid för vald person
            String aidSql = "SELECT aid FROM anstalld WHERE fornamn = '" + fornamn + "' AND efternamn = '" + efternamn + "'";
            String aid = idb.fetchSingle(aidSql);

            if (aid != null) {
                // Ta bort kopplingen från ans_proj
                String deleteSql = "DELETE FROM ans_proj WHERE pid = '" + projektID + "' AND aid = '" + aid + "'";
                idb.delete(deleteSql);

                JOptionPane.showMessageDialog(this, "Handläggare borttagen.");
                fyllHandlaggare(); // uppdatera listan
            }

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Fel vid borttagning: " + e.getMessage());
        }
    }
    
    private void laggTillPartner() {
        try {
            // Hämta alla partners som inte redan är kopplade till projektet
            String sql = "SELECT pid, namn FROM partner "
                    + "WHERE pid NOT IN (SELECT partner_pid FROM projekt_partner WHERE pid = '" + projektID + "')";
            ArrayList<HashMap<String, String>> partnerLista = idb.fetchRows(sql);

            if (partnerLista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Alla partners är redan kopplade till projektet.");
                return;
            }

            String[] val = new String[partnerLista.size()];
            for (int i = 0; i < partnerLista.size(); i++) {
                HashMap<String, String> rad = partnerLista.get(i);
                val[i] = rad.get("pid") + " - " + rad.get("namn");
            }

            String valt = (String) JOptionPane.showInputDialog(this, "Välj partner att lägga till:",
                    "Lägg till partner", JOptionPane.PLAIN_MESSAGE, null, val, val[0]);

            if (valt != null) {
                String partnerPid = valt.split(" - ")[0];
                String insertSql = "INSERT INTO projekt_partner (pid, partner_pid) VALUES ('" + projektID + "', '" + partnerPid + "')";
                idb.insert(insertSql);

                JOptionPane.showMessageDialog(this, "Partner lades till.");
                fyllPartner(); // Uppdatera listan
            }

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Fel vid tillägg av partner: " + e.getMessage());
        }
    }

    private void tabortPartner() {
        try {
            String vald = listPartner.getSelectedValue();

            if (vald == null) {
                JOptionPane.showMessageDialog(this, "Välj en partner att ta bort.");
                return;
            }

            int svar = JOptionPane.showConfirmDialog(this,
                    "Vill du ta bort partnern från projektet?",
                    "Bekräfta borttagning", JOptionPane.YES_NO_OPTION);

            if (svar != JOptionPane.YES_OPTION) {
                return;
            }

            // Hämta partner_pid baserat på namn
            String partnerSql = "SELECT pid FROM partner WHERE namn = '" + vald + "'";
            String partnerPid = idb.fetchSingle(partnerSql);

            if (partnerPid != null) {
                String deleteSql = "DELETE FROM projekt_partner WHERE pid = '" + projektID + "' AND partner_pid = '" + partnerPid + "'";
                idb.delete(deleteSql);

                JOptionPane.showMessageDialog(this, "Partner borttagen.");
                fyllPartner(); // Uppdatera listan
            }

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Fel vid borttagning av partner: " + e.getMessage());
        }
    }

    private void kontrolleraBehorighet() {
        boolean tillatet = false;

        if (anvandareBehorighet.isAdmin) {
            tillatet = true;
        } else if (anvandareBehorighet.isProjektChef) {
            try {
                String sql = "SELECT projektchef FROM projekt WHERE pid = '" + projektID + "'";
                String ansvarigAid = idb.fetchSingle(sql);

                if (anvandareBehorighet.aId.equals(ansvarigAid)) {
                    tillatet = true;
                }
            } catch (InfException e) {
                System.out.println("Fel vid behörighetskontroll: " + e.getMessage());
            }
        }

        if (!tillatet) {
            btnLaggtillHl.setEnabled(false);
            btnTabortHl.setEnabled(false);
            btnLaggtillP.setEnabled(false);
            btnTabortP.setEnabled(false);
        }
        cbProjektchef.setEnabled(anvandareBehorighet.isAdmin);
    }

    private void fyllProjektchefCombo(String aktuellChefId) {
        try {
            String sql = """
            SELECT anstalld.aid, fornamn, efternamn
            FROM anstalld
            JOIN handlaggare ON anstalld.aid = handlaggare.aid
        """;
            ArrayList<HashMap<String, String>> lista = idb.fetchRows(sql);

            cbProjektchef.removeAllItems();

            for (HashMap<String, String> rad : lista) {
                String aid = rad.get("aid");
                String namn = rad.get("fornamn") + " " + rad.get("efternamn");
                String item = aid + " - " + namn;
                cbProjektchef.addItem(item);

                if (aid.equals(aktuellChefId)) {
                    cbProjektchef.setSelectedItem(item);
                }
            }

        } catch (InfException e) {
            System.out.println("Fel vid laddning av projektchefer: " + e.getMessage());
        }

    }

//    private void setFaltRedigerbara(boolean redigerbar) 
//    {
//        txtProjektnamn.setEditable(redigerbar);
//        txtStartdatum.setEditable(redigerbar);
//        txtSlutdatum.setEditable(redigerbar);
//        txtKostnad.setEditable(redigerbar);
//        txtProjektchef.setEditable(redigerbar);
//        txtLand.setEditable(redigerbar);
//        txtBeskrivning.setEditable(redigerbar);
//        cbStatus.setEnabled(redigerbar);
//        cbPrio.setEnabled(redigerbar);
//        btnSpara.setEnabled(redigerbar);
//    }
//
//    private void kontrolleraBehorighet() {
//        String roll = anvandare.getRoll();
//        boolean arAdmin = roll.equals("admin");
//        // Låser redigering från början
//        btnRedigera.setEnabled(false);
//        btnLaggtillHl.setEnabled(false);
//        btnTabortHl.setEnabled(false);
//        btnLaggtillP.setEnabled(false);
//        btnTabortP.setEnabled(false);
//
//        if (arAdmin) {
//            //Allt får ändras
//            btnRedigera.setEnabled(true);
//            btnLaggtillHl.setEnabled(true);
//            btnTabortHl.setEnabled(true);
//            btnLaggtillP.setEnabled(true);
//            btnTabortP.setEnabled(true);
//        } else if (roll.equals("projektchef")) {
//            try {
//                String sql = "SELECT projektchef FROM projekt WHERE pid = '" + projektID + "'";
//                String ansvarig = idb.fetchSingle(sql);
//                if (ansvarig.equals(anvandare.getAid())) {
//                    btnRedigera.setEnabled(true);
//                    btnLaggtillHl.setEnabled(true);
//                    btnTabortHl.setEnabled(true);
//                    btnLaggtillP.setEnabled(true);
//                    btnTabortP.setEnabled(true);
//                }
//            } catch (InfException e) 
//            {
//                System.out.println("Fel vid behörighetskontroll: " + e.getMessage());
//            }
//        }
//
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        txtProjektnamn = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtBeskrivning = new javax.swing.JTextArea();
        txtProjektID = new javax.swing.JTextField();
        cbStatus = new javax.swing.JComboBox<>();
        txtKostnad = new javax.swing.JTextField();
        cbPrio = new javax.swing.JComboBox<>();
        txtStartdatum = new javax.swing.JTextField();
        txtSlutdatum = new javax.swing.JTextField();
        txtLand = new javax.swing.JTextField();
        btnRedigera = new javax.swing.JButton();
        btnSpara = new javax.swing.JButton();
        btnStang = new javax.swing.JButton();
        lblProjektnamn = new javax.swing.JLabel();
        lblProjektID = new javax.swing.JLabel();
        lblLand = new javax.swing.JLabel();
        lblStartdatum = new javax.swing.JLabel();
        lblSlutdatum = new javax.swing.JLabel();
        lblProjektbeskrivning = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblKostnad = new javax.swing.JLabel();
        lblPrio = new javax.swing.JLabel();
        lblProjektchef = new javax.swing.JLabel();
        lblHandlaggare = new javax.swing.JLabel();
        scrollHL = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        listHandlaggare = new javax.swing.JList<>();
        btnLaggtillHl = new javax.swing.JButton();
        btnTabortHl = new javax.swing.JButton();
        lblPartner = new javax.swing.JLabel();
        scrollPartner = new javax.swing.JScrollPane();
        listPartner = new javax.swing.JList<>();
        btnLaggtillP = new javax.swing.JButton();
        btnTabortP = new javax.swing.JButton();
        cbProjektchef = new javax.swing.JComboBox<>();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtProjektnamn.setText("Projektnamn");

        txtBeskrivning.setColumns(20);
        txtBeskrivning.setRows(5);
        txtBeskrivning.setText("Beskrivning");
        jScrollPane1.setViewportView(txtBeskrivning);

        txtProjektID.setText("ProjektID");

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pågående", "Avslutat", "Planerat", " " }));

        txtKostnad.setText("Kostnad");

        cbPrio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Låg", "Medel", "Hög", " " }));

        txtStartdatum.setText("Startdatum");

        txtSlutdatum.setText("Slutdatum");

        txtLand.setText("Land");

        btnRedigera.setText("Redigera");
        btnRedigera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedigeraActionPerformed(evt);
            }
        });

        btnSpara.setText("Spara");
        btnSpara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaActionPerformed(evt);
            }
        });

        btnStang.setText("Stäng");

        lblProjektnamn.setText("Projektnamn");

        lblProjektID.setText("ProjektID");

        lblLand.setText("Land");

        lblStartdatum.setText("Startdatum YYYY-MM-DD");

        lblSlutdatum.setText("Avslutat YYYY-MM-DD");

        lblProjektbeskrivning.setText("Projektbeskrivning");

        lblStatus.setText("Status");

        lblKostnad.setText("Kostnad");

        lblPrio.setText("Prioritet");

        lblProjektchef.setText("Projektchef");

        lblHandlaggare.setText("Handläggare");

        listHandlaggare.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listHandlaggare);

        scrollHL.setViewportView(jScrollPane3);

        btnLaggtillHl.setText("Lägg till");
        btnLaggtillHl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggtillHlActionPerformed(evt);
            }
        });

        btnTabortHl.setText("Ta Bort");
        btnTabortHl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabortHlActionPerformed(evt);
            }
        });

        lblPartner.setText("Partner");

        listPartner.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        scrollPartner.setViewportView(listPartner);

        btnLaggtillP.setText("Lägg till ");
        btnLaggtillP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggtillPActionPerformed(evt);
            }
        });

        btnTabortP.setText("Ta bort");
        btnTabortP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabortPActionPerformed(evt);
            }
        });

        cbProjektchef.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollHL, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTabortHl)
                            .addComponent(btnLaggtillHl)))
                    .addComponent(lblHandlaggare))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLaggtillP)
                            .addComponent(btnTabortP, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(282, 282, 282))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProjektnamn, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProjektnamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblProjektID, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtProjektID, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblLand, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLand, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(157, 157, 157))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProjektbeskrivning)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(lblKostnad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblProjektchef)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbProjektchef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(229, 229, 229))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblPrio, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbPrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblStartdatum)
                                .addGap(18, 18, 18)
                                .addComponent(txtStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(lblSlutdatum)
                                .addGap(18, 18, 18)
                                .addComponent(txtSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnRedigera)
                            .addComponent(btnSpara, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnStang, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProjektnamn, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProjektID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProjektnamn)
                    .addComponent(lblProjektID)
                    .addComponent(lblLand))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProjektbeskrivning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStatus)
                            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKostnad)
                            .addComponent(txtKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrio)
                            .addComponent(cbPrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStartdatum)
                            .addComponent(txtStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSlutdatum)
                            .addComponent(txtSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblProjektchef)
                        .addComponent(cbProjektchef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHandlaggare)
                    .addComponent(lblPartner))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollHL, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnLaggtillP, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnTabortP))
                                    .addComponent(scrollPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(64, 64, 64))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLaggtillHl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTabortHl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(btnRedigera)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSpara)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStang)
                .addGap(68, 68, 68))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRedigeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedigeraActionPerformed
         
    //setFaltRedigerbara(true);//När användaren klickar på knappen
//    try {
//        String sql = "SELECT projektchef FROM projekt WHERE pid = '" + projektID + "'";
//        String projektchefID = idb.fetchSingle(sql);
//
//        boolean arAdmin = anvandare.getRoll().equals("admin");
//        boolean arProjektchef = anvandare.getRoll().equals("projektchef") && anvandare.getAid().equals(projektchefID);
//
//        if (arAdmin || arProjektchef) {
//            txtProjektnamn.setEditable(true);
//            txtStartdatum.setEditable(true);
//            txtSlutdatum.setEditable(true);
//            txtKostnad.setEditable(true);
//            txtProjektchef.setEditable(true); // ev. slå av om bara admin ska få ändra detta
//            txtLand.setEditable(true);
//            txtBeskrivning.setEditable(true);
//            cbStatus.setEnabled(true);
//            cbPrio.setEnabled(true);
//
//            btnSpara.setEnabled(true); // aktivera spara-knappen
//        } else {
//            javax.swing.JOptionPane.showMessageDialog(this, 
//                "Du har inte behörighet att redigera detta projekt.",
//                "Behörighet saknas", javax.swing.JOptionPane.WARNING_MESSAGE);
//        }
//
//    } catch (InfException e) {
//        System.out.println("Fel vid kontroll av projektchef: " + e.getMessage());
//    }


    }//GEN-LAST:event_btnRedigeraActionPerformed

//Metod för att spara ändringar gjorda till databasen
    private void btnSparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaActionPerformed
        
        String projektchefId = null;
        String projektnamn = txtProjektnamn.getText().trim();

        if (anvandareBehorighet.isAdmin) {
            String valtChef = (String) cbProjektchef.getSelectedItem();
            projektchefId = valtChef.split(" - ")[0];

            String sql = "UPDATE projekt SET projektnamn = '" + projektnamn + "', "
                    + "projektchef = '" + projektchefId + "' "
                    + "WHERE pid = '" + projektID + "'";

        }

//    String projektnamn = txtProjektnamn.getText().trim();
//    String startdatum = txtStartdatum.getText().trim();
//    String slutdatum = txtSlutdatum.getText().trim();
//    String kostnad = txtKostnad.getText().trim();
//    String status = (String) cbStatus.getSelectedItem();
//    String prio = (String) cbPrio.getSelectedItem();
//    String beskrivning = txtBeskrivning.getText().trim();
//
//    // Samla alla fel i en sträng
//    StringBuilder felmeddelanden = new StringBuilder();
//
//    if (!Validering.arText(projektnamn)) 
//    {
//        felmeddelanden.append("Projektnamn får bara innehålla bokstäver.\n");
//    }
//
//    if (!Validering.arDatum(startdatum)) 
//    {
//        felmeddelanden.append("Startdatum måste ha formatet YYYY-MM-DD.\n");
//    }
//
//    if (!Validering.arDatum(slutdatum)) 
//    {
//        felmeddelanden.append("Slutdatum måste ha formatet YYYY-MM-DD.\n");
//    }
//
//    if (!Validering.arHeltal(kostnad)) 
//    {
//        felmeddelanden.append("Kostnad måste vara ett heltal.\n");
//    }
//    //Kontroll att slutdatum inte är före startdatum.
//    if (Validering.arDatum(startdatum) && Validering.arDatum(slutdatum)) 
//    {
//    if (startdatum.compareTo(slutdatum) > 0) 
//    {
//        felmeddelanden.append("Slutdatum kan inte vara före startdatum.\n");
//    }
//    // Visa alla fel på en gång om det finns några
//    if (felmeddelanden.length() > 0) 
//    {
//        JOptionPane.showMessageDialog(this, felmeddelanden.toString(), "Fel i inmatning", JOptionPane.WARNING_MESSAGE);
//    } 
//    else 
//    {
//        // Uppdatera databasen om alla fält är godkända
//        try {
//            String sql =
//                    "UPDATE projekt SET " +
//                    "projektnamn = '" + projektnamn + "', " +
//                    "startdatum = '" + startdatum + "', " +
//                    "slutdatum = '" + slutdatum + "', " +
//                    "kostnad = '" + kostnad + "', " +
//                    "status = '" + status + "', " +
//                    "prioritet = '" + prio + "', " +
//                    "beskrivning = '" + beskrivning + "' " +
//                    "WHERE pid = '" + projektID + "'";
//
//            idb.update(sql);
//
//            JOptionPane.showMessageDialog(this, "Projektet har uppdaterats.", "Sparat", JOptionPane.INFORMATION_MESSAGE);
//
//            // Lås fälten igen (valfritt)
//            txtProjektnamn.setEditable(false);
//            txtStartdatum.setEditable(false);
//            txtSlutdatum.setEditable(false);
//            txtKostnad.setEditable(false);
//            txtProjektchef.setEditable(false);
//            txtLand.setEditable(false);
//            txtBeskrivning.setEditable(false);
//            cbStatus.setEnabled(false);
//            cbPrio.setEnabled(false);
//            btnSpara.setEnabled(false);
//
//        } 
//        catch (InfException e) 
//        {
//            JOptionPane.showMessageDialog(this, "Fel vid uppdatering: " + e.getMessage(), "Databasfel", JOptionPane.ERROR_MESSAGE);
//        }
    

    }//GEN-LAST:event_btnSparaActionPerformed

    private void btnLaggtillHlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggtillHlActionPerformed
        laggTillHandlaggare();
    }//GEN-LAST:event_btnLaggtillHlActionPerformed

    private void btnTabortHlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabortHlActionPerformed
         tabortHandlaggare();
    }//GEN-LAST:event_btnTabortHlActionPerformed

    private void btnLaggtillPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggtillPActionPerformed
        laggTillPartner();
    }//GEN-LAST:event_btnLaggtillPActionPerformed

    private void btnTabortPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabortPActionPerformed
        tabortPartner();
    }//GEN-LAST:event_btnTabortPActionPerformed

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
            java.util.logging.Logger.getLogger(ProjektDetaljer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjektDetaljer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjektDetaljer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjektDetaljer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProjektDetaljer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLaggtillHl;
    private javax.swing.JButton btnLaggtillP;
    private javax.swing.JButton btnRedigera;
    private javax.swing.JButton btnSpara;
    private javax.swing.JButton btnStang;
    private javax.swing.JButton btnTabortHl;
    private javax.swing.JButton btnTabortP;
    private javax.swing.JComboBox<String> cbPrio;
    private javax.swing.JComboBox<String> cbProjektchef;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblHandlaggare;
    private javax.swing.JLabel lblKostnad;
    private javax.swing.JLabel lblLand;
    private javax.swing.JLabel lblPartner;
    private javax.swing.JLabel lblPrio;
    private javax.swing.JLabel lblProjektID;
    private javax.swing.JLabel lblProjektbeskrivning;
    private javax.swing.JLabel lblProjektchef;
    private javax.swing.JLabel lblProjektnamn;
    private javax.swing.JLabel lblSlutdatum;
    private javax.swing.JLabel lblStartdatum;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList<String> listHandlaggare;
    private javax.swing.JList<String> listPartner;
    private javax.swing.JScrollPane scrollHL;
    private javax.swing.JScrollPane scrollPartner;
    private javax.swing.JTextArea txtBeskrivning;
    private javax.swing.JTextField txtKostnad;
    private javax.swing.JTextField txtLand;
    private javax.swing.JTextField txtProjektID;
    private javax.swing.JTextField txtProjektnamn;
    private javax.swing.JTextField txtSlutdatum;
    private javax.swing.JTextField txtStartdatum;
    // End of variables declaration//GEN-END:variables
}
