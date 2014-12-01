/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Control.Control;
import Entity.Komponent;
import Entity.Kunde;
import Entity.Lager;
import Entity.Lastbil;
import Entity.Ordre;
import Entity.Staff;
import com.sun.glass.events.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Umair
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    private final Control con;
    //Temperoily list for jCBoxKunde
    private final HashMap<String, Integer> tempKundeList = new HashMap<>();

    //Temperoily list for JList i opret ordre
    private final HashMap<String, Integer> tempMonHjemList = new HashMap<>();

    public GUI() {
        con = new Control();
        initComponents();
        jLayeredPaneOpretOrdre.setVisible(false);
        jLPanelOpretOPart2.setVisible(false);
        jLayeredPaneKundeOprettelse.setVisible(false);
        jLayeredPaneKomponentOprettelse.setVisible(false);
        jLayeredPaneStaffOprettelse.setVisible(false);
        jLayeredPaneLastbilOprettelse.setVisible(false);

        //JComboBox Kunde 
        jCBoxKunde.setModel(new DefaultComboBoxModel());//.removeAllItems();
        for (int kundeid : con.getKundeList().keySet()) {
            tempKundeList.put(con.getKundeList().get(kundeid).getName(), kundeid);
        }
        for (String kundeNavn : tempKundeList.keySet()) {
            this.jCBoxKunde.addItem(kundeNavn);
        }

        //JList i opret ordre
        DefaultListModel ListModelMonHjem = new DefaultListModel();
        for (int staffid : con.getStaffList().keySet()) {
            tempMonHjemList.put(con.getStaffList().get(staffid).getNavn(), staffid);
        }
        for (String staffNavn : tempMonHjemList.keySet()) {
            ListModelMonHjem.addElement(staffNavn);
        }

        jListMonHjem.setModel(ListModelMonHjem);
    }

    class StaffStatusModel extends AbstractTableModel {

        String[] columnNames = {"Navn", "Telefon", "Stilling"};

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return con.getStaffList().keySet().size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            int id = (int) con.getStaffList().keySet().toArray()[row];
            switch (col) {
                case 0:
                    return con.getStaffList().get(id).getNavn();
                case 1:
                    return con.getStaffList().get(id).getTelefon();
                case 2:
                    return con.getStaffList().get(id).getStilling();
            }
            return null;
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

    }

    class LastbilStatusModel extends AbstractTableModel {

        String[] columnNames = {"Navn", "Telefon"};

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return con.getLastbilList().keySet().size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            int id = (int) con.getLastbilList().keySet().toArray()[row];
            switch (col) {
                case 0:
                    return con.getLastbilList().get(id).getNavn();
                case 1:
                    return con.getLastbilList().get(id).getTelefon();
            }
            return null;
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

    }

    class KomponentStatusModel extends AbstractTableModel {

        String[] columnNames = {"Navn", "Pris/Dag", "Antal Hjemme", "Antal Ude"};

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return con.getKompList().keySet().size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            int id = (int) con.getKompList().keySet().toArray()[row];
            int i;
            switch (col) {
                case 0:
                    return con.getKompList().get(id).getNavn();
                case 1:
                    return con.getKompList().get(id).getPrisPerDag();
                case 2:
                    i = 0;
                    for (Lager lager : con.getLagerList().values()) {
                        if (lager.getKompList().containsKey(id)) {
                            i += lager.getKompList().get(id);
                        }
                    }
                    return i;
                case 3:
                    i = 0;
                    for (Ordre order : con.getOrdreList().values()) {
                        if (new Date().after(order.getDatoStart())
                                && new Date().before(order.getDatoSlut())) {
                            if (order.getKompList().containsKey(id)) {
                                i += order.getKompList().get(id);
                            }
                        }
                    }
                    return i;
            }
            return null;
        }

    }

    class OrdreTableModel extends AbstractTableModel {

        String[] columnNames = {"Salgsmedarbjer", "Kunde", "Vej", "Post", "Pris", "Start", "Slut", "Bekræftet"};

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return con.getOrdreList().keySet().size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            int id = (int) con.getOrdreList().keySet().toArray()[row];
            switch (col) {
                case 0:
                    return con.getStaffList().get(con.getOrdreList().get(id).getSalgsmedarbsID()).getNavn();
                case 1:
                    return con.getKundeList().get(con.getOrdreList().get(id).getKundeID()).getName();
                case 2:
                    return con.getOrdreList().get(id).getVej();
                case 3:
                    return con.getOrdreList().get(id).getPostNR();
                case 4:
                    return con.getOrdreList().get(id).getPris();
                case 5:
                    return con.getOrdreList().get(id).getDatoStart();
                case 6:
                    return con.getOrdreList().get(id).getDatoSlut();
                case 7:
                    return con.getOrdreList().get(id).getConfirmation() == 1;
            }
            return null;
        }

    }

    class KundeTableModel extends AbstractTableModel {

        String[] columnNames = {"Navn", "Telefon", "E-mail", "Rabat"};

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return con.getKundeList().keySet().size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            int id = (int) con.getKundeList().keySet().toArray()[row];
            switch (col) {
                case 0:
                    return con.getKundeList().get(id).getName();
                case 1:
                    return con.getKundeList().get(id).getTelefon();
                case 2:
                    return con.getKundeList().get(id).getEmail();
                case 3:
                    return con.getKundeList().get(id).getRabat();
            }
            return null;
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

        jTabbedPaneMain = new javax.swing.JTabbedPane();
        jPanelOrdre = new javax.swing.JPanel();
        jButtonOpretOrdre = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanelOverblik = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanelNuOrdre = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanelTilOrdre = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLayeredPaneOpretOrdre = new javax.swing.JLayeredPane();
        jLabelOverskrift = new javax.swing.JLabel();
        jPanelOpretOPart1 = new javax.swing.JPanel();
        jLabelKunde = new javax.swing.JLabel();
        jCBoxKunde = new javax.swing.JComboBox();
        jLabelPostNR = new javax.swing.JLabel();
        jTFPostNR = new javax.swing.JTextField();
        jLabelVej = new javax.swing.JLabel();
        jTFVej = new javax.swing.JTextField();
        jLabelDatoStart = new javax.swing.JLabel();
        jDcDatoStart = new com.toedter.calendar.JDateChooser();
        jLabelDatoSlut = new javax.swing.JLabel();
        jDcDatoSlut = new com.toedter.calendar.JDateChooser();
        jButtonONext = new javax.swing.JButton();
        jButtonOCancel = new javax.swing.JButton();
        jLabelOSide1 = new javax.swing.JLabel();
        jLPanelOpretOPart2 = new javax.swing.JLayeredPane();
        jButtonOCancel2 = new javax.swing.JButton();
        jButtonOInsert = new javax.swing.JButton();
        jLabelOSide2 = new javax.swing.JLabel();
        jButtonOBack = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jListMonHjem = new javax.swing.JList();
        jScrollPane7 = new javax.swing.JScrollPane();
        jListMonUde = new javax.swing.JList();
        jButtonMonUde = new javax.swing.JButton();
        jButtonMonHjem = new javax.swing.JButton();
        jLabelMon = new javax.swing.JLabel();
        jLabelLast = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jListLastHjem = new javax.swing.JList();
        jButtonLastHjem = new javax.swing.JButton();
        jButtonLastUde = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jListLastUde = new javax.swing.JList();
        jLabelKomp = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jListKompHjem = new javax.swing.JList();
        jButtonKompHjem = new javax.swing.JButton();
        jButtonKompUde = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jListKompUde = new javax.swing.JList();
        jPanelKunde = new javax.swing.JPanel();
        jLayeredPaneKundeOverview = new javax.swing.JLayeredPane();
        jButtonOpretNyKunde = new javax.swing.JButton();
        jScrollPaneKunde = new javax.swing.JScrollPane();
        jTableKunde = new javax.swing.JTable();
        jLayeredPaneKundeOprettelse = new javax.swing.JLayeredPane();
        jLabelOverskriftKunde = new javax.swing.JLabel();
        jLabelKundeNavn = new javax.swing.JLabel();
        jLabelKundeTelefon = new javax.swing.JLabel();
        jLabelKundeEmail = new javax.swing.JLabel();
        jLabelKundeRabat = new javax.swing.JLabel();
        jTextFieldKundeNavn = new javax.swing.JTextField();
        jTextFieldKundeTelefon = new javax.swing.JTextField();
        jTextFieldKundeEmail = new javax.swing.JTextField();
        jTextFieldRabat = new javax.swing.JTextField();
        jButtonOpretKundeAnnuller = new javax.swing.JButton();
        jButtonOpretKundeOpret = new javax.swing.JButton();
        jPanelKomponent = new javax.swing.JPanel();
        jLayeredPaneKomponentOverview = new javax.swing.JLayeredPane();
        jButtonOpretNyKomponent = new javax.swing.JButton();
        jScrollPaneKomponentTable = new javax.swing.JScrollPane();
        jTableKomponent = new javax.swing.JTable();
        jLayeredPaneKomponentOprettelse = new javax.swing.JLayeredPane();
        jLabelOverskriftKomponent = new javax.swing.JLabel();
        jLabelKomponentNavn = new javax.swing.JLabel();
        jLabelKomponentPPD = new javax.swing.JLabel();
        jTextFieldKomponentNavn = new javax.swing.JTextField();
        jTextFieldKomponentPPD = new javax.swing.JTextField();
        jButtonOpretKomponentAnnuller = new javax.swing.JButton();
        jButtonOpretKomponentOpret = new javax.swing.JButton();
        jPanelStaff = new javax.swing.JPanel();
        jLayeredPaneStaffOverview = new javax.swing.JLayeredPane();
        jButtonOpretNyStaff = new javax.swing.JButton();
        jScrollPaneStaff = new javax.swing.JScrollPane();
        jTableStaff = new javax.swing.JTable();
        jLayeredPaneStaffOprettelse = new javax.swing.JLayeredPane();
        jLabelOverskriftStaff = new javax.swing.JLabel();
        jLabelStaffNavn = new javax.swing.JLabel();
        jLabelStaffTelefon = new javax.swing.JLabel();
        jLabelStaffStilling = new javax.swing.JLabel();
        jTextFieldStaffNavn = new javax.swing.JTextField();
        jTextFieldStaffTelefon = new javax.swing.JTextField();
        jTextFieldStaffStilling = new javax.swing.JTextField();
        jButtonOpretStaffAnnuller = new javax.swing.JButton();
        jButtonOpretStaffOpret = new javax.swing.JButton();
        jPanelLastbil = new javax.swing.JPanel();
        jLayeredPaneLastbilOverview = new javax.swing.JLayeredPane();
        jButtonOpretNyLastbil = new javax.swing.JButton();
        jScrollPaneLastbil = new javax.swing.JScrollPane();
        jTableLastbil = new javax.swing.JTable();
        jLayeredPaneLastbilOprettelse = new javax.swing.JLayeredPane();
        jLabelOverskriftLastbil = new javax.swing.JLabel();
        jLabelLastbilNavn = new javax.swing.JLabel();
        jLabelLastbilTelefon = new javax.swing.JLabel();
        jTextFieldLastbilNavn = new javax.swing.JTextField();
        jTextFieldLastbilTelefon = new javax.swing.JTextField();
        jButtonOpretLastbilAnnuller = new javax.swing.JButton();
        jButtonOpretLastbilOpret = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonOpretOrdre.setText("Opret en ordre");
        jButtonOpretOrdre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretOrdreActionPerformed(evt);
            }
        });

        jTable1.setModel(new OrdreTableModel());
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanelNuOrdreLayout = new javax.swing.GroupLayout(jPanelNuOrdre);
        jPanelNuOrdre.setLayout(jPanelNuOrdreLayout);
        jPanelNuOrdreLayout.setHorizontalGroup(
            jPanelNuOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
        );
        jPanelNuOrdreLayout.setVerticalGroup(
            jPanelNuOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Nuværende ordrer", jPanelNuOrdre);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable2);

        javax.swing.GroupLayout jPanelTilOrdreLayout = new javax.swing.GroupLayout(jPanelTilOrdre);
        jPanelTilOrdre.setLayout(jPanelTilOrdreLayout);
        jPanelTilOrdreLayout.setHorizontalGroup(
            jPanelTilOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
        );
        jPanelTilOrdreLayout.setVerticalGroup(
            jPanelTilOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Tidligere ordrer", jPanelTilOrdre);

        javax.swing.GroupLayout jPanelOverblikLayout = new javax.swing.GroupLayout(jPanelOverblik);
        jPanelOverblik.setLayout(jPanelOverblikLayout);
        jPanelOverblikLayout.setHorizontalGroup(
            jPanelOverblikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOverblikLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3))
        );
        jPanelOverblikLayout.setVerticalGroup(
            jPanelOverblikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOverblikLayout.createSequentialGroup()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        jLabelOverskrift.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelOverskrift.setText("Opret en ordre");

        jLabelKunde.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelKunde.setText("Kunde: ");

        jCBoxKunde.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelPostNR.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelPostNR.setText("Post nummer:");

        jTFPostNR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTFPostNRKeyTyped(evt);
            }
        });

        jLabelVej.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelVej.setText("Vej:");

        jLabelDatoStart.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDatoStart.setText("Dato opbygning:");

        jLabelDatoSlut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDatoSlut.setText("Dato nedrivning:");

        jButtonONext.setText("Næste");
        jButtonONext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonONextActionPerformed(evt);
            }
        });

        jButtonOCancel.setText("Annuller");
        jButtonOCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOCancelActionPerformed(evt);
            }
        });

        jLabelOSide1.setText("1 af 2");

        javax.swing.GroupLayout jPanelOpretOPart1Layout = new javax.swing.GroupLayout(jPanelOpretOPart1);
        jPanelOpretOPart1.setLayout(jPanelOpretOPart1Layout);
        jPanelOpretOPart1Layout.setHorizontalGroup(
            jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOpretOPart1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonOCancel)
                .addGap(18, 18, 18)
                .addComponent(jButtonONext)
                .addContainerGap(691, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOpretOPart1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelOSide1)
                .addContainerGap())
            .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelOpretOPart1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanelOpretOPart1Layout.createSequentialGroup()
                            .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelPostNR)
                                .addComponent(jLabelKunde)
                                .addComponent(jLabelVej))
                            .addGap(36, 36, 36)
                            .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTFPostNR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTFVej)
                                .addComponent(jCBoxKunde, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelOpretOPart1Layout.createSequentialGroup()
                            .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelDatoStart)
                                .addComponent(jLabelDatoSlut))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jDcDatoStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDcDatoSlut, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(561, Short.MAX_VALUE)))
        );
        jPanelOpretOPart1Layout.setVerticalGroup(
            jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOpretOPart1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelOSide1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
                .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonONext)
                    .addComponent(jButtonOCancel))
                .addGap(160, 160, 160))
            .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelOpretOPart1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelKunde)
                        .addComponent(jCBoxKunde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(30, 30, 30)
                    .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelPostNR)
                        .addComponent(jTFPostNR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelVej)
                        .addComponent(jTFVej, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelDatoStart)
                        .addComponent(jDcDatoStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanelOpretOPart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelDatoSlut)
                        .addComponent(jDcDatoSlut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(305, Short.MAX_VALUE)))
        );

        jLPanelOpretOPart2.setPreferredSize(new java.awt.Dimension(855, 485));

        jButtonOCancel2.setText("Annuller");
        jButtonOCancel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOCancel2ActionPerformed(evt);
            }
        });

        jButtonOInsert.setText("Indsæt ordren");
        jButtonOInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOInsertActionPerformed(evt);
            }
        });

        jLabelOSide2.setText("2 af 2");

        jButtonOBack.setText("Tilbage");
        jButtonOBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOBackActionPerformed(evt);
            }
        });

        jListMonHjem.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(jListMonHjem);

        jListMonUde.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(jListMonUde);

        jButtonMonUde.setText(">");

        jButtonMonHjem.setText("<");

        jLabelMon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMon.setText("Montører");

        jLabelLast.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelLast.setText("Lastbiler");

        jListLastHjem.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane8.setViewportView(jListLastHjem);

        jButtonLastHjem.setText("<");

        jButtonLastUde.setText(">");

        jListLastUde.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane9.setViewportView(jListLastUde);

        jLabelKomp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelKomp.setText("Komponenter");

        jListKompHjem.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane10.setViewportView(jListKompHjem);

        jButtonKompHjem.setText("<");

        jButtonKompUde.setText(">");

        jListKompUde.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane11.setViewportView(jListKompUde);

        javax.swing.GroupLayout jLPanelOpretOPart2Layout = new javax.swing.GroupLayout(jLPanelOpretOPart2);
        jLPanelOpretOPart2.setLayout(jLPanelOpretOPart2Layout);
        jLPanelOpretOPart2Layout.setHorizontalGroup(
            jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                        .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLPanelOpretOPart2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabelOSide2))
                            .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                                .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                                        .addComponent(jButtonOCancel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonOBack)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonOInsert))
                                    .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButtonMonUde)
                                            .addComponent(jButtonMonHjem))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabelLast)
                                    .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButtonLastUde)
                                            .addComponent(jButtonLastHjem))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(71, 71, 71)
                                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonKompUde)
                                    .addComponent(jButtonKompHjem))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 98, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                        .addComponent(jLabelMon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelKomp)
                        .addGap(335, 335, 335))))
        );
        jLPanelOpretOPart2Layout.setVerticalGroup(
            jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLPanelOpretOPart2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelOSide2)
                .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelMon)
                            .addComponent(jLabelKomp))
                        .addGap(18, 18, 18)
                        .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(jButtonKompUde)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButtonKompHjem)))))
                    .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jButtonMonUde)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonMonHjem)))
                .addGap(18, 18, 18)
                .addComponent(jLabelLast)
                .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButtonLastUde)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonLastHjem)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOCancel2)
                    .addComponent(jButtonOInsert)
                    .addComponent(jButtonOBack))
                .addGap(52, 52, 52))
        );
        jLPanelOpretOPart2.setLayer(jButtonOCancel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jButtonOInsert, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jLabelOSide2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jButtonOBack, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jScrollPane6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jScrollPane7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jButtonMonUde, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jButtonMonHjem, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jLabelMon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jLabelLast, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jScrollPane8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jButtonLastHjem, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jButtonLastUde, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jScrollPane9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jLabelKomp, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jScrollPane10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jButtonKompHjem, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jButtonKompUde, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLPanelOpretOPart2.setLayer(jScrollPane11, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPaneOpretOrdreLayout = new javax.swing.GroupLayout(jLayeredPaneOpretOrdre);
        jLayeredPaneOpretOrdre.setLayout(jLayeredPaneOpretOrdreLayout);
        jLayeredPaneOpretOrdreLayout.setHorizontalGroup(
            jLayeredPaneOpretOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneOpretOrdreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLPanelOpretOPart2, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jLayeredPaneOpretOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPaneOpretOrdreLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelOverskrift)
                    .addContainerGap(706, Short.MAX_VALUE)))
            .addGroup(jLayeredPaneOpretOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPaneOpretOrdreLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanelOpretOPart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jLayeredPaneOpretOrdreLayout.setVerticalGroup(
            jLayeredPaneOpretOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneOpretOrdreLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(jLPanelOpretOPart2, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jLayeredPaneOpretOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPaneOpretOrdreLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelOverskrift)
                    .addContainerGap(558, Short.MAX_VALUE)))
            .addGroup(jLayeredPaneOpretOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneOpretOrdreLayout.createSequentialGroup()
                    .addContainerGap(87, Short.MAX_VALUE)
                    .addComponent(jPanelOpretOPart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jLayeredPaneOpretOrdre.setLayer(jLabelOverskrift, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneOpretOrdre.setLayer(jPanelOpretOPart1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneOpretOrdre.setLayer(jLPanelOpretOPart2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelOverblik, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLayeredPaneOpretOrdre))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelOverblik, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLayeredPaneOpretOrdre))
        );

        javax.swing.GroupLayout jPanelOrdreLayout = new javax.swing.GroupLayout(jPanelOrdre);
        jPanelOrdre.setLayout(jPanelOrdreLayout);
        jPanelOrdreLayout.setHorizontalGroup(
            jPanelOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrdreLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOrdreLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonOpretOrdre))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelOrdreLayout.setVerticalGroup(
            jPanelOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrdreLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButtonOpretOrdre)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneMain.addTab("Ordre", jPanelOrdre);

        jButtonOpretNyKunde.setText("Opret Ny Kunde");
        jButtonOpretNyKunde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretNyKundeActionPerformed(evt);
            }
        });

        jTableKunde.setModel(new KundeTableModel());
        jScrollPaneKunde.setViewportView(jTableKunde);

        javax.swing.GroupLayout jLayeredPaneKundeOverviewLayout = new javax.swing.GroupLayout(jLayeredPaneKundeOverview);
        jLayeredPaneKundeOverview.setLayout(jLayeredPaneKundeOverviewLayout);
        jLayeredPaneKundeOverviewLayout.setHorizontalGroup(
            jLayeredPaneKundeOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKundeOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneKunde, javax.swing.GroupLayout.DEFAULT_SIZE, 905, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneKundeOverviewLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOpretNyKunde)
                .addGap(62, 62, 62))
        );
        jLayeredPaneKundeOverviewLayout.setVerticalGroup(
            jLayeredPaneKundeOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKundeOverviewLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButtonOpretNyKunde)
                .addGap(29, 29, 29)
                .addComponent(jScrollPaneKunde, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE))
        );
        jLayeredPaneKundeOverview.setLayer(jButtonOpretNyKunde, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOverview.setLayer(jScrollPaneKunde, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabelOverskriftKunde.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelOverskriftKunde.setText("Opret en kunde");

        jLabelKundeNavn.setText("Navn:");

        jLabelKundeTelefon.setText("Telefon:");

        jLabelKundeEmail.setText("E-mail:");

        jLabelKundeRabat.setText("Rabat");

        jTextFieldKundeNavn.setText("jTextField1");

        jTextFieldKundeTelefon.setText("jTextField2");

        jTextFieldKundeEmail.setText("jTextField3");

        jTextFieldRabat.setText("jTextField4");

        jButtonOpretKundeAnnuller.setText("Annuller");
        jButtonOpretKundeAnnuller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretKundeAnnullerActionPerformed(evt);
            }
        });

        jButtonOpretKundeOpret.setText("Opret!");
        jButtonOpretKundeOpret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretKundeOpretActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPaneKundeOprettelseLayout = new javax.swing.GroupLayout(jLayeredPaneKundeOprettelse);
        jLayeredPaneKundeOprettelse.setLayout(jLayeredPaneKundeOprettelseLayout);
        jLayeredPaneKundeOprettelseLayout.setHorizontalGroup(
            jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKundeOprettelseLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jLayeredPaneKundeOprettelseLayout.createSequentialGroup()
                            .addComponent(jLabelKundeNavn)
                            .addGap(18, 18, 18)
                            .addComponent(jTextFieldKundeNavn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabelOverskriftKunde))
                    .addGroup(jLayeredPaneKundeOprettelseLayout.createSequentialGroup()
                        .addGroup(jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelKundeEmail)
                            .addComponent(jLabelKundeTelefon)
                            .addComponent(jLabelKundeRabat))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldKundeTelefon, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(jTextFieldKundeEmail)
                            .addComponent(jTextFieldRabat))
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPaneKundeOprettelseLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jButtonOpretKundeAnnuller)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonOpretKundeOpret)))
                .addContainerGap(678, Short.MAX_VALUE))
        );
        jLayeredPaneKundeOprettelseLayout.setVerticalGroup(
            jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKundeOprettelseLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabelOverskriftKunde)
                .addGap(27, 27, 27)
                .addGroup(jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKundeNavn)
                    .addComponent(jTextFieldKundeNavn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKundeTelefon)
                    .addComponent(jTextFieldKundeTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKundeEmail)
                    .addComponent(jTextFieldKundeEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKundeRabat)
                    .addComponent(jTextFieldRabat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(jLayeredPaneKundeOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOpretKundeAnnuller)
                    .addComponent(jButtonOpretKundeOpret))
                .addContainerGap(375, Short.MAX_VALUE))
        );
        jLayeredPaneKundeOprettelse.setLayer(jLabelOverskriftKunde, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOprettelse.setLayer(jLabelKundeNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOprettelse.setLayer(jLabelKundeTelefon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOprettelse.setLayer(jLabelKundeEmail, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOprettelse.setLayer(jLabelKundeRabat, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOprettelse.setLayer(jTextFieldKundeNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOprettelse.setLayer(jTextFieldKundeTelefon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOprettelse.setLayer(jTextFieldKundeEmail, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOprettelse.setLayer(jTextFieldRabat, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOprettelse.setLayer(jButtonOpretKundeAnnuller, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOprettelse.setLayer(jButtonOpretKundeOpret, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanelKundeLayout = new javax.swing.GroupLayout(jPanelKunde);
        jPanelKunde.setLayout(jPanelKundeLayout);
        jPanelKundeLayout.setHorizontalGroup(
            jPanelKundeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneKundeOverview)
            .addGroup(jPanelKundeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelKundeLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneKundeOprettelse)
                    .addContainerGap()))
        );
        jPanelKundeLayout.setVerticalGroup(
            jPanelKundeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneKundeOverview)
            .addGroup(jPanelKundeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelKundeLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneKundeOprettelse)
                    .addContainerGap()))
        );

        jTabbedPaneMain.addTab("Kunde", jPanelKunde);

        jButtonOpretNyKomponent.setText("Opret Ny Komponent");
        jButtonOpretNyKomponent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretNyKomponentActionPerformed(evt);
            }
        });

        jTableKomponent.setModel(new KomponentStatusModel());
        jScrollPaneKomponentTable.setViewportView(jTableKomponent);

        javax.swing.GroupLayout jLayeredPaneKomponentOverviewLayout = new javax.swing.GroupLayout(jLayeredPaneKomponentOverview);
        jLayeredPaneKomponentOverview.setLayout(jLayeredPaneKomponentOverviewLayout);
        jLayeredPaneKomponentOverviewLayout.setHorizontalGroup(
            jLayeredPaneKomponentOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKomponentOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneKomponentTable, javax.swing.GroupLayout.DEFAULT_SIZE, 885, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneKomponentOverviewLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOpretNyKomponent)
                .addGap(62, 62, 62))
        );
        jLayeredPaneKomponentOverviewLayout.setVerticalGroup(
            jLayeredPaneKomponentOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKomponentOverviewLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButtonOpretNyKomponent)
                .addGap(29, 29, 29)
                .addComponent(jScrollPaneKomponentTable, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE))
        );
        jLayeredPaneKomponentOverview.setLayer(jButtonOpretNyKomponent, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKomponentOverview.setLayer(jScrollPaneKomponentTable, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabelOverskriftKomponent.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelOverskriftKomponent.setText("Opret en komponent");

        jLabelKomponentNavn.setText("Navn:");

        jLabelKomponentPPD.setText("Pris per dag:");

        jTextFieldKomponentNavn.setText("jTextField1");

        jTextFieldKomponentPPD.setText("jTextField2");

        jButtonOpretKomponentAnnuller.setText("Annuller");
        jButtonOpretKomponentAnnuller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretKomponentAnnullerActionPerformed(evt);
            }
        });

        jButtonOpretKomponentOpret.setText("Opret!");
        jButtonOpretKomponentOpret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretKomponentOpretActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPaneKomponentOprettelseLayout = new javax.swing.GroupLayout(jLayeredPaneKomponentOprettelse);
        jLayeredPaneKomponentOprettelse.setLayout(jLayeredPaneKomponentOprettelseLayout);
        jLayeredPaneKomponentOprettelseLayout.setHorizontalGroup(
            jLayeredPaneKomponentOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKomponentOprettelseLayout.createSequentialGroup()
                .addGroup(jLayeredPaneKomponentOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPaneKomponentOprettelseLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabelOverskriftKomponent))
                    .addGroup(jLayeredPaneKomponentOprettelseLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jLayeredPaneKomponentOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPaneKomponentOprettelseLayout.createSequentialGroup()
                                .addComponent(jButtonOpretKomponentAnnuller)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonOpretKomponentOpret))
                            .addGroup(jLayeredPaneKomponentOprettelseLayout.createSequentialGroup()
                                .addGroup(jLayeredPaneKomponentOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelKomponentPPD)
                                    .addComponent(jLabelKomponentNavn))
                                .addGap(18, 18, 18)
                                .addGroup(jLayeredPaneKomponentOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldKomponentNavn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldKomponentPPD, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(635, Short.MAX_VALUE))
        );
        jLayeredPaneKomponentOprettelseLayout.setVerticalGroup(
            jLayeredPaneKomponentOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKomponentOprettelseLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabelOverskriftKomponent)
                .addGap(32, 32, 32)
                .addGroup(jLayeredPaneKomponentOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKomponentNavn)
                    .addComponent(jTextFieldKomponentNavn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPaneKomponentOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKomponentPPD)
                    .addComponent(jTextFieldKomponentPPD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jLayeredPaneKomponentOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOpretKomponentAnnuller)
                    .addComponent(jButtonOpretKomponentOpret))
                .addContainerGap(429, Short.MAX_VALUE))
        );
        jLayeredPaneKomponentOprettelse.setLayer(jLabelOverskriftKomponent, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKomponentOprettelse.setLayer(jLabelKomponentNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKomponentOprettelse.setLayer(jLabelKomponentPPD, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKomponentOprettelse.setLayer(jTextFieldKomponentNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKomponentOprettelse.setLayer(jTextFieldKomponentPPD, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKomponentOprettelse.setLayer(jButtonOpretKomponentAnnuller, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKomponentOprettelse.setLayer(jButtonOpretKomponentOpret, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanelKomponentLayout = new javax.swing.GroupLayout(jPanelKomponent);
        jPanelKomponent.setLayout(jPanelKomponentLayout);
        jPanelKomponentLayout.setHorizontalGroup(
            jPanelKomponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 915, Short.MAX_VALUE)
            .addGroup(jPanelKomponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelKomponentLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneKomponentOverview)
                    .addContainerGap()))
            .addGroup(jPanelKomponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelKomponentLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneKomponentOprettelse)
                    .addContainerGap()))
        );
        jPanelKomponentLayout.setVerticalGroup(
            jPanelKomponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 694, Short.MAX_VALUE)
            .addGroup(jPanelKomponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelKomponentLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneKomponentOverview)
                    .addContainerGap()))
            .addGroup(jPanelKomponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelKomponentLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneKomponentOprettelse)
                    .addContainerGap()))
        );

        jTabbedPaneMain.addTab("Komponenter", jPanelKomponent);

        jButtonOpretNyStaff.setText("Opret Ny Staff");
        jButtonOpretNyStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretNyStaffActionPerformed(evt);
            }
        });

        jTableStaff.setModel(new StaffStatusModel()
        );
        jScrollPaneStaff.setViewportView(jTableStaff);

        javax.swing.GroupLayout jLayeredPaneStaffOverviewLayout = new javax.swing.GroupLayout(jLayeredPaneStaffOverview);
        jLayeredPaneStaffOverview.setLayout(jLayeredPaneStaffOverviewLayout);
        jLayeredPaneStaffOverviewLayout.setHorizontalGroup(
            jLayeredPaneStaffOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneStaffOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneStaff, javax.swing.GroupLayout.DEFAULT_SIZE, 885, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneStaffOverviewLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOpretNyStaff)
                .addGap(62, 62, 62))
        );
        jLayeredPaneStaffOverviewLayout.setVerticalGroup(
            jLayeredPaneStaffOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneStaffOverviewLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButtonOpretNyStaff)
                .addGap(29, 29, 29)
                .addComponent(jScrollPaneStaff, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE))
        );
        jLayeredPaneStaffOverview.setLayer(jButtonOpretNyStaff, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneStaffOverview.setLayer(jScrollPaneStaff, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabelOverskriftStaff.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelOverskriftStaff.setText("Opret en staff");

        jLabelStaffNavn.setText("Navn:");

        jLabelStaffTelefon.setText("Telefon:");

        jLabelStaffStilling.setText("Stilling:");

        jTextFieldStaffNavn.setText("jTextField1");

        jTextFieldStaffTelefon.setText("jTextField2");

        jTextFieldStaffStilling.setText("jTextField4");

        jButtonOpretStaffAnnuller.setText("Annuller");
        jButtonOpretStaffAnnuller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretStaffAnnullerActionPerformed(evt);
            }
        });

        jButtonOpretStaffOpret.setText("Opret!");
        jButtonOpretStaffOpret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretStaffOpretActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPaneStaffOprettelseLayout = new javax.swing.GroupLayout(jLayeredPaneStaffOprettelse);
        jLayeredPaneStaffOprettelse.setLayout(jLayeredPaneStaffOprettelseLayout);
        jLayeredPaneStaffOprettelseLayout.setHorizontalGroup(
            jLayeredPaneStaffOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneStaffOprettelseLayout.createSequentialGroup()
                .addGroup(jLayeredPaneStaffOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPaneStaffOprettelseLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jButtonOpretStaffAnnuller)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonOpretStaffOpret))
                    .addGroup(jLayeredPaneStaffOprettelseLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jLayeredPaneStaffOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelOverskriftStaff, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPaneStaffOprettelseLayout.createSequentialGroup()
                                .addGroup(jLayeredPaneStaffOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelStaffTelefon)
                                    .addComponent(jLabelStaffStilling))
                                .addGap(18, 18, 18)
                                .addGroup(jLayeredPaneStaffOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldStaffTelefon, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                    .addComponent(jTextFieldStaffStilling))
                                .addGap(29, 29, 29))))
                    .addGroup(jLayeredPaneStaffOprettelseLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabelStaffNavn)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldStaffNavn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(678, Short.MAX_VALUE))
        );
        jLayeredPaneStaffOprettelseLayout.setVerticalGroup(
            jLayeredPaneStaffOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneStaffOprettelseLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabelOverskriftStaff)
                .addGap(27, 27, 27)
                .addGroup(jLayeredPaneStaffOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStaffNavn)
                    .addComponent(jTextFieldStaffNavn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPaneStaffOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStaffTelefon)
                    .addComponent(jTextFieldStaffTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPaneStaffOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStaffStilling)
                    .addComponent(jTextFieldStaffStilling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98)
                .addGroup(jLayeredPaneStaffOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOpretStaffAnnuller)
                    .addComponent(jButtonOpretStaffOpret))
                .addContainerGap(372, Short.MAX_VALUE))
        );
        jLayeredPaneStaffOprettelse.setLayer(jLabelOverskriftStaff, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneStaffOprettelse.setLayer(jLabelStaffNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneStaffOprettelse.setLayer(jLabelStaffTelefon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneStaffOprettelse.setLayer(jLabelStaffStilling, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneStaffOprettelse.setLayer(jTextFieldStaffNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneStaffOprettelse.setLayer(jTextFieldStaffTelefon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneStaffOprettelse.setLayer(jTextFieldStaffStilling, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneStaffOprettelse.setLayer(jButtonOpretStaffAnnuller, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneStaffOprettelse.setLayer(jButtonOpretStaffOpret, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanelStaffLayout = new javax.swing.GroupLayout(jPanelStaff);
        jPanelStaff.setLayout(jPanelStaffLayout);
        jPanelStaffLayout.setHorizontalGroup(
            jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 915, Short.MAX_VALUE)
            .addGroup(jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelStaffLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneStaffOverview)
                    .addContainerGap()))
            .addGroup(jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelStaffLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneStaffOprettelse)
                    .addContainerGap()))
        );
        jPanelStaffLayout.setVerticalGroup(
            jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 694, Short.MAX_VALUE)
            .addGroup(jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelStaffLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneStaffOverview)
                    .addContainerGap()))
            .addGroup(jPanelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelStaffLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneStaffOprettelse)
                    .addContainerGap()))
        );

        jTabbedPaneMain.addTab("Staff", jPanelStaff);

        jButtonOpretNyLastbil.setText("Opret Ny Lastbil");
        jButtonOpretNyLastbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretNyLastbilActionPerformed(evt);
            }
        });

        jTableLastbil.setModel(new LastbilStatusModel());
        jScrollPaneLastbil.setViewportView(jTableLastbil);

        javax.swing.GroupLayout jLayeredPaneLastbilOverviewLayout = new javax.swing.GroupLayout(jLayeredPaneLastbilOverview);
        jLayeredPaneLastbilOverview.setLayout(jLayeredPaneLastbilOverviewLayout);
        jLayeredPaneLastbilOverviewLayout.setHorizontalGroup(
            jLayeredPaneLastbilOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneLastbilOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneLastbil, javax.swing.GroupLayout.DEFAULT_SIZE, 885, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneLastbilOverviewLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOpretNyLastbil)
                .addGap(62, 62, 62))
        );
        jLayeredPaneLastbilOverviewLayout.setVerticalGroup(
            jLayeredPaneLastbilOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneLastbilOverviewLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButtonOpretNyLastbil)
                .addGap(29, 29, 29)
                .addComponent(jScrollPaneLastbil, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE))
        );
        jLayeredPaneLastbilOverview.setLayer(jButtonOpretNyLastbil, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneLastbilOverview.setLayer(jScrollPaneLastbil, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabelOverskriftLastbil.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelOverskriftLastbil.setText("Opret en lastbil");

        jLabelLastbilNavn.setText("Navn:");

        jLabelLastbilTelefon.setText("Telefon:");

        jTextFieldLastbilNavn.setText("jTextField1");

        jTextFieldLastbilTelefon.setText("jTextField2");

        jButtonOpretLastbilAnnuller.setText("Annuller");
        jButtonOpretLastbilAnnuller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretLastbilAnnullerActionPerformed(evt);
            }
        });

        jButtonOpretLastbilOpret.setText("Opret!");
        jButtonOpretLastbilOpret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretLastbilOpretActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPaneLastbilOprettelseLayout = new javax.swing.GroupLayout(jLayeredPaneLastbilOprettelse);
        jLayeredPaneLastbilOprettelse.setLayout(jLayeredPaneLastbilOprettelseLayout);
        jLayeredPaneLastbilOprettelseLayout.setHorizontalGroup(
            jLayeredPaneLastbilOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneLastbilOprettelseLayout.createSequentialGroup()
                .addGroup(jLayeredPaneLastbilOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPaneLastbilOprettelseLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jButtonOpretLastbilAnnuller)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonOpretLastbilOpret))
                    .addGroup(jLayeredPaneLastbilOprettelseLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jLayeredPaneLastbilOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelOverskriftLastbil, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPaneLastbilOprettelseLayout.createSequentialGroup()
                                .addComponent(jLabelLastbilTelefon)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldLastbilTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))))
                    .addGroup(jLayeredPaneLastbilOprettelseLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabelLastbilNavn)
                        .addGap(22, 22, 22)
                        .addComponent(jTextFieldLastbilNavn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(678, Short.MAX_VALUE))
        );
        jLayeredPaneLastbilOprettelseLayout.setVerticalGroup(
            jLayeredPaneLastbilOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneLastbilOprettelseLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabelOverskriftLastbil)
                .addGap(27, 27, 27)
                .addGroup(jLayeredPaneLastbilOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLastbilNavn)
                    .addComponent(jTextFieldLastbilNavn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPaneLastbilOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLastbilTelefon)
                    .addComponent(jTextFieldLastbilTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(121, 121, 121)
                .addGroup(jLayeredPaneLastbilOprettelseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOpretLastbilAnnuller)
                    .addComponent(jButtonOpretLastbilOpret))
                .addContainerGap(375, Short.MAX_VALUE))
        );
        jLayeredPaneLastbilOprettelse.setLayer(jLabelOverskriftLastbil, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneLastbilOprettelse.setLayer(jLabelLastbilNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneLastbilOprettelse.setLayer(jLabelLastbilTelefon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneLastbilOprettelse.setLayer(jTextFieldLastbilNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneLastbilOprettelse.setLayer(jTextFieldLastbilTelefon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneLastbilOprettelse.setLayer(jButtonOpretLastbilAnnuller, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneLastbilOprettelse.setLayer(jButtonOpretLastbilOpret, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanelLastbilLayout = new javax.swing.GroupLayout(jPanelLastbil);
        jPanelLastbil.setLayout(jPanelLastbilLayout);
        jPanelLastbilLayout.setHorizontalGroup(
            jPanelLastbilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 915, Short.MAX_VALUE)
            .addGroup(jPanelLastbilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelLastbilLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneLastbilOverview)
                    .addContainerGap()))
            .addGroup(jPanelLastbilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelLastbilLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneLastbilOprettelse)
                    .addContainerGap()))
        );
        jPanelLastbilLayout.setVerticalGroup(
            jPanelLastbilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 694, Short.MAX_VALUE)
            .addGroup(jPanelLastbilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelLastbilLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneLastbilOverview)
                    .addContainerGap()))
            .addGroup(jPanelLastbilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelLastbilLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPaneLastbilOprettelse)
                    .addContainerGap()))
        );

        jTabbedPaneMain.addTab("Lastbil", jPanelLastbil);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneMain)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneMain)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOBackActionPerformed
        jPanelOpretOPart1.setVisible(true);
        jLPanelOpretOPart2.setVisible(false);
    }//GEN-LAST:event_jButtonOBackActionPerformed

    private void jButtonOInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOInsertActionPerformed
        int salgsmedarbejderID = 0000;
        int kundeID = tempKundeList.get(jCBoxKunde.getSelectedItem());
        String vej = jTFVej.getText();
        int Postnr = Integer.parseInt(jTFPostNR.getText());
        boolean confirm = false;
        int pris = 0;
        Date datoStart = jDcDatoStart.getDate();
        Date datoSlut = jDcDatoSlut.getDate();

        Ordre o = new Ordre(salgsmedarbejderID, kundeID, vej, Postnr, confirm, pris, datoStart, datoSlut);

        con.createNewOrdre(o);
    }//GEN-LAST:event_jButtonOInsertActionPerformed

    private void jButtonOCancel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOCancel2ActionPerformed
        jLPanelOpretOPart2.setVisible(false);
        jPanelOverblik.setVisible(true);
        jButtonOpretOrdre.setVisible(true);
    }//GEN-LAST:event_jButtonOCancel2ActionPerformed

    private void jButtonOCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOCancelActionPerformed
        jPanelOpretOPart1.setVisible(false);
        jPanelOverblik.setVisible(true);
        jButtonOpretOrdre.setVisible(true);
    }//GEN-LAST:event_jButtonOCancelActionPerformed

    private void jButtonONextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonONextActionPerformed
        jPanelOpretOPart1.setVisible(false);
        jLPanelOpretOPart2.setVisible(true);
    }//GEN-LAST:event_jButtonONextActionPerformed

    private void jTFPostNRKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFPostNRKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACKSPACE) || c == KeyEvent.VK_DELETE) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTFPostNRKeyTyped

    private void jButtonOpretOrdreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretOrdreActionPerformed
        // TODO add your handling code here:
        jPanelOverblik.setVisible(false);
        jLayeredPaneOpretOrdre.setVisible(true);
        jButtonOpretOrdre.setVisible(false);
        jPanelOpretOPart1.setVisible(true);
        jTFPostNR.setText("");
        jTFVej.setText("");
        jDcDatoStart.setDate(null);
        jDcDatoSlut.setDate(null);

    }//GEN-LAST:event_jButtonOpretOrdreActionPerformed

    private void jButtonOpretNyKundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretNyKundeActionPerformed
        jLayeredPaneKundeOverview.setVisible(false);
        jLayeredPaneKundeOprettelse.setVisible(true);
        jTextFieldKundeNavn.setText("");
                jTextFieldKundeEmail.setText("");
                jTextFieldKundeTelefon.setText("");
                jTextFieldRabat.setText("");
    }//GEN-LAST:event_jButtonOpretNyKundeActionPerformed

    private void jButtonOpretKundeOpretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretKundeOpretActionPerformed
        String navn = jTextFieldKundeNavn.getText();
        String email = jTextFieldKundeEmail.getText();
        try {
            int telefon = Integer.parseInt(jTextFieldKundeTelefon.getText());
            int rabat = Integer.parseInt(jTextFieldRabat.getText());

            Kunde k = new Kunde(navn, telefon, email, rabat, new ArrayList<Integer>());
            con.createNewKunde(k);

            jLayeredPaneKundeOverview.setVisible(true);
            jLayeredPaneKundeOprettelse.setVisible(false);
        } catch (Exception e) {
            System.out.println("GUI: Opret Kunde fail");
        }
    }//GEN-LAST:event_jButtonOpretKundeOpretActionPerformed

    private void jButtonOpretKundeAnnullerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretKundeAnnullerActionPerformed
        jLayeredPaneKundeOverview.setVisible(true);
        jLayeredPaneKundeOprettelse.setVisible(false);
    }//GEN-LAST:event_jButtonOpretKundeAnnullerActionPerformed

    private void jButtonOpretNyKomponentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretNyKomponentActionPerformed
        jLayeredPaneKomponentOverview.setVisible(false);
        jLayeredPaneKomponentOprettelse.setVisible(true);
        jTextFieldKomponentNavn.setText("");
        jTextFieldKomponentPPD.setText("");
    }//GEN-LAST:event_jButtonOpretNyKomponentActionPerformed

    private void jButtonOpretKomponentAnnullerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretKomponentAnnullerActionPerformed
        jLayeredPaneKomponentOverview.setVisible(true);
        jLayeredPaneKomponentOprettelse.setVisible(false);
    }//GEN-LAST:event_jButtonOpretKomponentAnnullerActionPerformed

    private void jButtonOpretKomponentOpretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretKomponentOpretActionPerformed
        String navn = jTextFieldKomponentNavn.getText();
        try {
            int PPD = Integer.parseInt(jTextFieldKomponentPPD.getText());

            Komponent k = new Komponent(navn, PPD);
            con.createNewKomponent(k);

            jLayeredPaneKomponentOverview.setVisible(true);
            jLayeredPaneKomponentOprettelse.setVisible(false);
        } catch (Exception e) {
            System.out.println("GUI: Opret Komponent fail");
        }
    }//GEN-LAST:event_jButtonOpretKomponentOpretActionPerformed

    private void jButtonOpretNyStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretNyStaffActionPerformed
        jLayeredPaneStaffOverview.setVisible(false);
        jLayeredPaneStaffOprettelse.setVisible(true);
        jTextFieldStaffNavn.setText("");
        jTextFieldStaffStilling.setText("");
        jTextFieldStaffTelefon.setText("");
    }//GEN-LAST:event_jButtonOpretNyStaffActionPerformed

    private void jButtonOpretStaffAnnullerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretStaffAnnullerActionPerformed
        jLayeredPaneStaffOverview.setVisible(true);
        jLayeredPaneStaffOprettelse.setVisible(false);
    }//GEN-LAST:event_jButtonOpretStaffAnnullerActionPerformed

    private void jButtonOpretStaffOpretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretStaffOpretActionPerformed
        String navn = jTextFieldStaffNavn.getText();
        String stilling = jTextFieldStaffStilling.getText();
        try {
            int telefon = Integer.parseInt(jTextFieldStaffTelefon.getText());

            Staff s = new Staff(navn, telefon, stilling);
            con.createNewStaff(s);

            jLayeredPaneStaffOverview.setVisible(true);
            jLayeredPaneStaffOprettelse.setVisible(false);
        } catch (Exception e) {
            System.out.println("GUI: Opret Staff fail");
        }
    }//GEN-LAST:event_jButtonOpretStaffOpretActionPerformed

    private void jButtonOpretNyLastbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretNyLastbilActionPerformed
        jLayeredPaneLastbilOverview.setVisible(false);
        jLayeredPaneLastbilOprettelse.setVisible(true);
        jTextFieldLastbilNavn.setText("");
        jTextFieldLastbilTelefon.setText("");
    }//GEN-LAST:event_jButtonOpretNyLastbilActionPerformed

    private void jButtonOpretLastbilAnnullerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretLastbilAnnullerActionPerformed
        jLayeredPaneLastbilOverview.setVisible(true);
        jLayeredPaneLastbilOprettelse.setVisible(false);
    }//GEN-LAST:event_jButtonOpretLastbilAnnullerActionPerformed

    private void jButtonOpretLastbilOpretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretLastbilOpretActionPerformed
        String navn = jTextFieldLastbilNavn.getText();
        try {
            int telefon = Integer.parseInt(jTextFieldLastbilTelefon.getText());

            Lastbil L = new Lastbil(navn, telefon);
            con.createNewLastbil(L);

            jLayeredPaneLastbilOverview.setVisible(true);
            jLayeredPaneLastbilOprettelse.setVisible(false);
        } catch (Exception e) {
            System.out.println("GUI: Opret Lastbil fail");
        }
    }//GEN-LAST:event_jButtonOpretLastbilOpretActionPerformed

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
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonKompHjem;
    private javax.swing.JButton jButtonKompUde;
    private javax.swing.JButton jButtonLastHjem;
    private javax.swing.JButton jButtonLastUde;
    private javax.swing.JButton jButtonMonHjem;
    private javax.swing.JButton jButtonMonUde;
    private javax.swing.JButton jButtonOBack;
    private javax.swing.JButton jButtonOCancel;
    private javax.swing.JButton jButtonOCancel2;
    private javax.swing.JButton jButtonOInsert;
    private javax.swing.JButton jButtonONext;
    private javax.swing.JButton jButtonOpretKomponentAnnuller;
    private javax.swing.JButton jButtonOpretKomponentOpret;
    private javax.swing.JButton jButtonOpretKundeAnnuller;
    private javax.swing.JButton jButtonOpretKundeOpret;
    private javax.swing.JButton jButtonOpretLastbilAnnuller;
    private javax.swing.JButton jButtonOpretLastbilOpret;
    private javax.swing.JButton jButtonOpretNyKomponent;
    private javax.swing.JButton jButtonOpretNyKunde;
    private javax.swing.JButton jButtonOpretNyLastbil;
    private javax.swing.JButton jButtonOpretNyStaff;
    private javax.swing.JButton jButtonOpretOrdre;
    private javax.swing.JButton jButtonOpretStaffAnnuller;
    private javax.swing.JButton jButtonOpretStaffOpret;
    private javax.swing.JComboBox jCBoxKunde;
    private com.toedter.calendar.JDateChooser jDcDatoSlut;
    private com.toedter.calendar.JDateChooser jDcDatoStart;
    private javax.swing.JLayeredPane jLPanelOpretOPart2;
    private javax.swing.JLabel jLabelDatoSlut;
    private javax.swing.JLabel jLabelDatoStart;
    private javax.swing.JLabel jLabelKomp;
    private javax.swing.JLabel jLabelKomponentNavn;
    private javax.swing.JLabel jLabelKomponentPPD;
    private javax.swing.JLabel jLabelKunde;
    private javax.swing.JLabel jLabelKundeEmail;
    private javax.swing.JLabel jLabelKundeNavn;
    private javax.swing.JLabel jLabelKundeRabat;
    private javax.swing.JLabel jLabelKundeTelefon;
    private javax.swing.JLabel jLabelLast;
    private javax.swing.JLabel jLabelLastbilNavn;
    private javax.swing.JLabel jLabelLastbilTelefon;
    private javax.swing.JLabel jLabelMon;
    private javax.swing.JLabel jLabelOSide1;
    private javax.swing.JLabel jLabelOSide2;
    private javax.swing.JLabel jLabelOverskrift;
    private javax.swing.JLabel jLabelOverskriftKomponent;
    private javax.swing.JLabel jLabelOverskriftKunde;
    private javax.swing.JLabel jLabelOverskriftLastbil;
    private javax.swing.JLabel jLabelOverskriftStaff;
    private javax.swing.JLabel jLabelPostNR;
    private javax.swing.JLabel jLabelStaffNavn;
    private javax.swing.JLabel jLabelStaffStilling;
    private javax.swing.JLabel jLabelStaffTelefon;
    private javax.swing.JLabel jLabelVej;
    private javax.swing.JLayeredPane jLayeredPaneKomponentOprettelse;
    private javax.swing.JLayeredPane jLayeredPaneKomponentOverview;
    private javax.swing.JLayeredPane jLayeredPaneKundeOprettelse;
    private javax.swing.JLayeredPane jLayeredPaneKundeOverview;
    private javax.swing.JLayeredPane jLayeredPaneLastbilOprettelse;
    private javax.swing.JLayeredPane jLayeredPaneLastbilOverview;
    private javax.swing.JLayeredPane jLayeredPaneOpretOrdre;
    private javax.swing.JLayeredPane jLayeredPaneStaffOprettelse;
    private javax.swing.JLayeredPane jLayeredPaneStaffOverview;
    private javax.swing.JList jListKompHjem;
    private javax.swing.JList jListKompUde;
    private javax.swing.JList jListLastHjem;
    private javax.swing.JList jListLastUde;
    private javax.swing.JList jListMonHjem;
    private javax.swing.JList jListMonUde;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanelKomponent;
    private javax.swing.JPanel jPanelKunde;
    private javax.swing.JPanel jPanelLastbil;
    private javax.swing.JPanel jPanelNuOrdre;
    private javax.swing.JPanel jPanelOpretOPart1;
    private javax.swing.JPanel jPanelOrdre;
    private javax.swing.JPanel jPanelOverblik;
    private javax.swing.JPanel jPanelStaff;
    private javax.swing.JPanel jPanelTilOrdre;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollPaneKomponentTable;
    private javax.swing.JScrollPane jScrollPaneKunde;
    private javax.swing.JScrollPane jScrollPaneLastbil;
    private javax.swing.JScrollPane jScrollPaneStaff;
    private javax.swing.JTextField jTFPostNR;
    private javax.swing.JTextField jTFVej;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableKomponent;
    private javax.swing.JTable jTableKunde;
    private javax.swing.JTable jTableLastbil;
    private javax.swing.JTable jTableStaff;
    private javax.swing.JTextField jTextFieldKomponentNavn;
    private javax.swing.JTextField jTextFieldKomponentPPD;
    private javax.swing.JTextField jTextFieldKundeEmail;
    private javax.swing.JTextField jTextFieldKundeNavn;
    private javax.swing.JTextField jTextFieldKundeTelefon;
    private javax.swing.JTextField jTextFieldLastbilNavn;
    private javax.swing.JTextField jTextFieldLastbilTelefon;
    private javax.swing.JTextField jTextFieldRabat;
    private javax.swing.JTextField jTextFieldStaffNavn;
    private javax.swing.JTextField jTextFieldStaffStilling;
    private javax.swing.JTextField jTextFieldStaffTelefon;
    // End of variables declaration//GEN-END:variables
}
