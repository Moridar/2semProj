/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Control.Control;
import Entity.DB;
import Entity.Komponent;
import Entity.Kunde;
import Entity.Lager;
import Entity.Ordre;
import com.sun.glass.events.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
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
    private HashMap<String, Integer> tempList = new HashMap<>();

    public GUI() {
        con = new Control();
        initComponents();
        jLayeredPaneOpretOrdre.setVisible(false);
        jLPanelOpretOPart2.setVisible(false);
        jLayeredPaneKundeOpret.setVisible(false);
        jLayeredPaneKompOpret.setVisible(false);

        //JComboBox Kunde - !!!Stadig fejl men kører fint!!!
        jCBoxKunde.setModel(new DefaultComboBoxModel());//.removeAllItems();
        for (int kundeid : con.getKundeList().keySet()) {
            tempList.put(con.getKundeList().get(kundeid).getName(), kundeid);
        }
        for (String kundeNavn : tempList.keySet()) {
            this.jCBoxKunde.addItem(kundeNavn);
        }
    }

    class StaffStatusModel extends AbstractTableModel {

        String[] columnNames = {"ID", "Navn", "Telefon", "Stilling"};

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
                    return id;
                case 1:
                    return con.getStaffList().get(id).getNavn();
                case 2:
                    return con.getStaffList().get(id).getTelefon();
                case 3:
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

        String[] columnNames = {"ID", "Navn", "Telefon"};

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
                    return id;
                case 1:
                    return con.getLastbilList().get(id).getNavn();
                case 2:
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

        String[] columnNames = {"ID", "Navn", "Pris/Dag", "Antal Hjemme", "Antal Ude"};

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
                    return id;
                case 1:
                    return con.getKompList().get(id).getNavn();
                case 2:
                    return con.getKompList().get(id).getPrisPerDag();
                case 3:
                    i = 0;
                    for (Lager lager : con.getLagerList().values()) {
                        if(lager.getKompList().containsKey(id))i += lager.getKompList().get(id);
                    }
                    return i;
                case 4:
                    i = 0;
                    for (Ordre order : con.getOrdreList().values()) {
                        if (new Date().after(order.getDatoStart())
                                && new Date().before(order.getDatoSlut())) {
                           if(order.getKompList().containsKey(id)) i += order.getKompList().get(id);
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

        String[] columnNames = {"Navn", "Telefon", "E-mail", "Rabatordning"};

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
        jPanelStatus = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanelStaffStatus = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableStaffStatus = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
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
        jPanelKunde = new javax.swing.JPanel();
        jPanelKundeOverview = new javax.swing.JPanel();
        jLayeredPaneKundeOverview = new javax.swing.JLayeredPane();
        jButtonOpretNyKunde = new javax.swing.JButton();
        jScrollPaneKundeOverview = new javax.swing.JScrollPane();
        jTableKundeOverview = new javax.swing.JTable();
        jLayeredPaneKundeOpret = new javax.swing.JLayeredPane();
        jLabelNavn = new javax.swing.JLabel();
        jTextFieldKundeNavn = new javax.swing.JTextField();
        jLabelKundeTelefon = new javax.swing.JLabel();
        jLabelKundeEmail = new javax.swing.JLabel();
        jLabelKundeRabat = new javax.swing.JLabel();
        jTextFieldKundeTelefon = new javax.swing.JTextField();
        jTextFieldKundeEmail = new javax.swing.JTextField();
        jTextFieldKundeRabat = new javax.swing.JTextField();
        jLabelOverskriftKundeOpret = new javax.swing.JLabel();
        jButtonKundeAnnuller = new javax.swing.JButton();
        jButtonKundeOpret = new javax.swing.JButton();
        jPanelResource = new javax.swing.JPanel();
        jPanelKompOverview = new javax.swing.JPanel();
        jLayeredPaneKompOverview = new javax.swing.JLayeredPane();
        jButtonOpretNyKomp = new javax.swing.JButton();
        jScrollPaneKompOverview = new javax.swing.JScrollPane();
        jTableKompOverview = new javax.swing.JTable();
        jLayeredPaneKompOpret = new javax.swing.JLayeredPane();
        jLabelKompNavn = new javax.swing.JLabel();
        jTextFieldKompNavn = new javax.swing.JTextField();
        jLabelKompPPD = new javax.swing.JLabel();
        jTextFieldKompPPD = new javax.swing.JTextField();
        jLabelOverskriftKompOpret = new javax.swing.JLabel();
        jButtonKompAnnuller = new javax.swing.JButton();
        jButtonKompOpret = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableStaffStatus.setModel(new StaffStatusModel());
        jTableStaffStatus.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTableStaffStatus);
        jTableStaffStatus.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanelStaffStatusLayout = new javax.swing.GroupLayout(jPanelStaffStatus);
        jPanelStaffStatus.setLayout(jPanelStaffStatusLayout);
        jPanelStaffStatusLayout.setHorizontalGroup(
            jPanelStaffStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
        );
        jPanelStaffStatusLayout.setVerticalGroup(
            jPanelStaffStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Medarbejdere", jPanelStaffStatus);

        jTable3.setModel(new LastbilStatusModel());
        jScrollPane4.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Lastbiler", jPanel6);

        jTable4.setModel(new KomponentStatusModel());
        jScrollPane5.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Komponenter", jPanel7);

        javax.swing.GroupLayout jPanelStatusLayout = new javax.swing.GroupLayout(jPanelStatus);
        jPanelStatus.setLayout(jPanelStatusLayout);
        jPanelStatusLayout.setHorizontalGroup(
            jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanelStatusLayout.setVerticalGroup(
            jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        jTabbedPaneMain.addTab("Status", jPanelStatus);

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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
        );
        jPanelNuOrdreLayout.setVerticalGroup(
            jPanelNuOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
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
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
        );
        jPanelTilOrdreLayout.setVerticalGroup(
            jPanelTilOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jLPanelOpretOPart2Layout = new javax.swing.GroupLayout(jLPanelOpretOPart2);
        jLPanelOpretOPart2.setLayout(jLPanelOpretOPart2Layout);
        jLPanelOpretOPart2Layout.setHorizontalGroup(
            jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLPanelOpretOPart2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelOSide2))
                    .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                        .addComponent(jButtonOCancel2)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonOBack)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonOInsert)
                        .addGap(0, 558, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jLPanelOpretOPart2Layout.setVerticalGroup(
            jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLPanelOpretOPart2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelOSide2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 433, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jLayeredPaneOpretOrdreLayout = new javax.swing.GroupLayout(jLayeredPaneOpretOrdre);
        jLayeredPaneOpretOrdre.setLayout(jLayeredPaneOpretOrdreLayout);
        jLayeredPaneOpretOrdreLayout.setHorizontalGroup(
            jLayeredPaneOpretOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneOpretOrdreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLPanelOpretOPart2, javax.swing.GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
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
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(jLPanelOpretOPart2, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jLayeredPaneOpretOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPaneOpretOrdreLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelOverskrift)
                    .addContainerGap(559, Short.MAX_VALUE)))
            .addGroup(jLayeredPaneOpretOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneOpretOrdreLayout.createSequentialGroup()
                    .addContainerGap(88, Short.MAX_VALUE)
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

        jButtonOpretNyKunde.setText("Opert en kunde");
        jButtonOpretNyKunde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretNyKundeActionPerformed(evt);
            }
        });

        jTableKundeOverview.setModel(new KundeTableModel());
        jScrollPaneKundeOverview.setViewportView(jTableKundeOverview);

        javax.swing.GroupLayout jLayeredPaneKundeOverviewLayout = new javax.swing.GroupLayout(jLayeredPaneKundeOverview);
        jLayeredPaneKundeOverview.setLayout(jLayeredPaneKundeOverviewLayout);
        jLayeredPaneKundeOverviewLayout.setHorizontalGroup(
            jLayeredPaneKundeOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneKundeOverview, javax.swing.GroupLayout.DEFAULT_SIZE, 941, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneKundeOverviewLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOpretNyKunde)
                .addGap(70, 70, 70))
        );
        jLayeredPaneKundeOverviewLayout.setVerticalGroup(
            jLayeredPaneKundeOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKundeOverviewLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jButtonOpretNyKunde)
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneKundeOverview, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
        );
        jLayeredPaneKundeOverview.setLayer(jButtonOpretNyKunde, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOverview.setLayer(jScrollPaneKundeOverview, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabelNavn.setText("Navn:");

        jTextFieldKundeNavn.setText("jTextField1");

        jLabelKundeTelefon.setText("Telefon:");

        jLabelKundeEmail.setText("E-mail:");

        jLabelKundeRabat.setText("Rabat:");

        jTextFieldKundeTelefon.setText("jTextField2");

        jTextFieldKundeEmail.setText("jTextField3");

        jTextFieldKundeRabat.setText("jTextField4");

        jLabelOverskriftKundeOpret.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelOverskriftKundeOpret.setText("Opret en kunde");

        jButtonKundeAnnuller.setText("Annuller");
        jButtonKundeAnnuller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKundeAnnullerActionPerformed(evt);
            }
        });

        jButtonKundeOpret.setText("Opret!");
        jButtonKundeOpret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKundeOpretActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPaneKundeOpretLayout = new javax.swing.GroupLayout(jLayeredPaneKundeOpret);
        jLayeredPaneKundeOpret.setLayout(jLayeredPaneKundeOpretLayout);
        jLayeredPaneKundeOpretLayout.setHorizontalGroup(
            jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKundeOpretLayout.createSequentialGroup()
                .addGroup(jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPaneKundeOpretLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPaneKundeOpretLayout.createSequentialGroup()
                                .addComponent(jButtonKundeAnnuller)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonKundeOpret))
                            .addGroup(jLayeredPaneKundeOpretLayout.createSequentialGroup()
                                .addGroup(jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelKundeRabat)
                                    .addComponent(jLabelKundeEmail)
                                    .addComponent(jLabelKundeTelefon)
                                    .addComponent(jLabelNavn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldKundeTelefon)
                                    .addComponent(jTextFieldKundeEmail)
                                    .addComponent(jTextFieldKundeRabat)
                                    .addComponent(jTextFieldKundeNavn, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jLayeredPaneKundeOpretLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabelOverskriftKundeOpret)))
                .addContainerGap(695, Short.MAX_VALUE))
        );
        jLayeredPaneKundeOpretLayout.setVerticalGroup(
            jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKundeOpretLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabelOverskriftKundeOpret)
                .addGap(49, 49, 49)
                .addGroup(jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNavn)
                    .addComponent(jTextFieldKundeNavn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKundeTelefon)
                    .addComponent(jTextFieldKundeTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKundeEmail)
                    .addComponent(jTextFieldKundeEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKundeRabat)
                    .addComponent(jTextFieldKundeRabat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addGroup(jLayeredPaneKundeOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonKundeAnnuller)
                    .addComponent(jButtonKundeOpret))
                .addContainerGap(347, Short.MAX_VALUE))
        );
        jLayeredPaneKundeOpret.setLayer(jLabelNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOpret.setLayer(jTextFieldKundeNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOpret.setLayer(jLabelKundeTelefon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOpret.setLayer(jLabelKundeEmail, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOpret.setLayer(jLabelKundeRabat, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOpret.setLayer(jTextFieldKundeTelefon, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOpret.setLayer(jTextFieldKundeEmail, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOpret.setLayer(jTextFieldKundeRabat, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOpret.setLayer(jLabelOverskriftKundeOpret, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOpret.setLayer(jButtonKundeAnnuller, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKundeOpret.setLayer(jButtonKundeOpret, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanelKundeOverviewLayout = new javax.swing.GroupLayout(jPanelKundeOverview);
        jPanelKundeOverview.setLayout(jPanelKundeOverviewLayout);
        jPanelKundeOverviewLayout.setHorizontalGroup(
            jPanelKundeOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneKundeOverview)
            .addGroup(jPanelKundeOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelKundeOverviewLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLayeredPaneKundeOpret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanelKundeOverviewLayout.setVerticalGroup(
            jPanelKundeOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneKundeOverview)
            .addGroup(jPanelKundeOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelKundeOverviewLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLayeredPaneKundeOpret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanelKundeLayout = new javax.swing.GroupLayout(jPanelKunde);
        jPanelKunde.setLayout(jPanelKundeLayout);
        jPanelKundeLayout.setHorizontalGroup(
            jPanelKundeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelKundeOverview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelKundeLayout.setVerticalGroup(
            jPanelKundeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelKundeOverview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("Kunde", jPanelKunde);

        jButtonOpretNyKomp.setText("Opert en komponent");
        jButtonOpretNyKomp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpretNyKompActionPerformed(evt);
            }
        });

        jTableKompOverview.setModel(new KomponentStatusModel());
        jScrollPaneKompOverview.setViewportView(jTableKompOverview);

        javax.swing.GroupLayout jLayeredPaneKompOverviewLayout = new javax.swing.GroupLayout(jLayeredPaneKompOverview);
        jLayeredPaneKompOverview.setLayout(jLayeredPaneKompOverviewLayout);
        jLayeredPaneKompOverviewLayout.setHorizontalGroup(
            jLayeredPaneKompOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneKompOverview, javax.swing.GroupLayout.DEFAULT_SIZE, 941, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneKompOverviewLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOpretNyKomp)
                .addGap(70, 70, 70))
        );
        jLayeredPaneKompOverviewLayout.setVerticalGroup(
            jLayeredPaneKompOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKompOverviewLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jButtonOpretNyKomp)
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneKompOverview, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
        );
        jLayeredPaneKompOverview.setLayer(jButtonOpretNyKomp, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKompOverview.setLayer(jScrollPaneKompOverview, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabelKompNavn.setText("Navn:");

        jTextFieldKompNavn.setText("jTextField1");

        jLabelKompPPD.setText("Pris per dag");

        jTextFieldKompPPD.setText("jTextField2");

        jLabelOverskriftKompOpret.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelOverskriftKompOpret.setText("Opret en komponent");

        jButtonKompAnnuller.setText("Annuller");
        jButtonKompAnnuller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKompAnnullerActionPerformed(evt);
            }
        });

        jButtonKompOpret.setText("Opret!");
        jButtonKompOpret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKompOpretActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPaneKompOpretLayout = new javax.swing.GroupLayout(jLayeredPaneKompOpret);
        jLayeredPaneKompOpret.setLayout(jLayeredPaneKompOpretLayout);
        jLayeredPaneKompOpretLayout.setHorizontalGroup(
            jLayeredPaneKompOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKompOpretLayout.createSequentialGroup()
                .addGroup(jLayeredPaneKompOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPaneKompOpretLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jLayeredPaneKompOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPaneKompOpretLayout.createSequentialGroup()
                                .addComponent(jButtonKompAnnuller)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonKompOpret))
                            .addGroup(jLayeredPaneKompOpretLayout.createSequentialGroup()
                                .addGroup(jLayeredPaneKompOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelKompPPD)
                                    .addComponent(jLabelKompNavn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jLayeredPaneKompOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldKompPPD)
                                    .addComponent(jTextFieldKompNavn, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jLayeredPaneKompOpretLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabelOverskriftKompOpret)))
                .addContainerGap(695, Short.MAX_VALUE))
        );
        jLayeredPaneKompOpretLayout.setVerticalGroup(
            jLayeredPaneKompOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneKompOpretLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabelOverskriftKompOpret)
                .addGap(49, 49, 49)
                .addGroup(jLayeredPaneKompOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKompNavn)
                    .addComponent(jTextFieldKompNavn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPaneKompOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKompPPD)
                    .addComponent(jTextFieldKompPPD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(133, 133, 133)
                .addGroup(jLayeredPaneKompOpretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonKompAnnuller)
                    .addComponent(jButtonKompOpret))
                .addContainerGap(347, Short.MAX_VALUE))
        );
        jLayeredPaneKompOpret.setLayer(jLabelKompNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKompOpret.setLayer(jTextFieldKompNavn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKompOpret.setLayer(jLabelKompPPD, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKompOpret.setLayer(jTextFieldKompPPD, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKompOpret.setLayer(jLabelOverskriftKompOpret, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKompOpret.setLayer(jButtonKompAnnuller, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneKompOpret.setLayer(jButtonKompOpret, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanelKompOverviewLayout = new javax.swing.GroupLayout(jPanelKompOverview);
        jPanelKompOverview.setLayout(jPanelKompOverviewLayout);
        jPanelKompOverviewLayout.setHorizontalGroup(
            jPanelKompOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneKompOverview)
            .addGroup(jPanelKompOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelKompOverviewLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLayeredPaneKompOpret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanelKompOverviewLayout.setVerticalGroup(
            jPanelKompOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneKompOverview)
            .addGroup(jPanelKompOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelKompOverviewLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLayeredPaneKompOpret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanelResourceLayout = new javax.swing.GroupLayout(jPanelResource);
        jPanelResource.setLayout(jPanelResourceLayout);
        jPanelResourceLayout.setHorizontalGroup(
            jPanelResourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelKompOverview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelResourceLayout.setVerticalGroup(
            jPanelResourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelKompOverview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("Komponenter", jPanelResource);

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

    private void jTFPostNRKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFPostNRKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACKSPACE) || c == KeyEvent.VK_DELETE) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTFPostNRKeyTyped

    private void jButtonONextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonONextActionPerformed
        jPanelOpretOPart1.setVisible(false);
        jLPanelOpretOPart2.setVisible(true);
    }//GEN-LAST:event_jButtonONextActionPerformed

    private void jButtonOBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOBackActionPerformed
        jPanelOpretOPart1.setVisible(true);
        jLPanelOpretOPart2.setVisible(false);
    }//GEN-LAST:event_jButtonOBackActionPerformed

    private void jButtonOCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOCancelActionPerformed
        jPanelOpretOPart1.setVisible(false);
        jPanelOverblik.setVisible(true);
        jButtonOpretOrdre.setVisible(true);
    }//GEN-LAST:event_jButtonOCancelActionPerformed

    private void jButtonOCancel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOCancel2ActionPerformed
        jLPanelOpretOPart2.setVisible(false);
        jPanelOverblik.setVisible(true);
        jButtonOpretOrdre.setVisible(true);
    }//GEN-LAST:event_jButtonOCancel2ActionPerformed

    private void jButtonOInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOInsertActionPerformed
        int salgsmedarbejderID = 0000;
        int kundeID = tempList.get(jCBoxKunde.getSelectedItem());
        String vej = jTFVej.getText();
        int Postnr = Integer.parseInt(jTFPostNR.getText());
        boolean confirm = false;
        int pris = 0;
        Date datoStart = jDcDatoStart.getDate();
        Date datoSlut = jDcDatoSlut.getDate();

        Ordre o = new Ordre(salgsmedarbejderID, kundeID, vej, Postnr, confirm, pris, datoStart, datoSlut);

        con.createNewOrdre(o);
    }//GEN-LAST:event_jButtonOInsertActionPerformed

    private void jButtonOpretNyKundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretNyKundeActionPerformed
        jTextFieldKundeNavn.setText("");
        jTextFieldKundeTelefon.setText("");
        jTextFieldKundeEmail.setText("");
        jTextFieldKundeRabat.setText("");

        jLayeredPaneKundeOverview.setVisible(false);
        jLayeredPaneKundeOpret.setVisible(true);
    }//GEN-LAST:event_jButtonOpretNyKundeActionPerformed

    private void jButtonKundeAnnullerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKundeAnnullerActionPerformed
        jLayeredPaneKundeOpret.setVisible(false);
        jLayeredPaneKundeOverview.setVisible(true);

    }//GEN-LAST:event_jButtonKundeAnnullerActionPerformed

    private void jButtonKundeOpretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKundeOpretActionPerformed
        String navn = jTextFieldKundeNavn.getText();
        String email = jTextFieldKundeEmail.getText();

        try {
            int telefon = Integer.parseInt(jTextFieldKundeTelefon.getText());
            int rabat = Integer.parseInt(jTextFieldKundeRabat.getText());
            Kunde k = new Kunde(navn, telefon, email, rabat, new ArrayList<>());
            con.createNewKunde(k);

            jLayeredPaneKundeOpret.setVisible(false);
            jLayeredPaneKundeOverview.setVisible(true);
        } catch (Exception e) {
            System.out.println("JButton opret kunde fail");
        }

    }//GEN-LAST:event_jButtonKundeOpretActionPerformed

    private void jButtonOpretNyKompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpretNyKompActionPerformed
        jTextFieldKompNavn.setText("");
        jTextFieldKompPPD.setText("");

        jLayeredPaneKompOverview.setVisible(false);
        jLayeredPaneKompOpret.setVisible(true);
    }//GEN-LAST:event_jButtonOpretNyKompActionPerformed

    private void jButtonKompAnnullerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKompAnnullerActionPerformed
        jLayeredPaneKompOverview.setVisible(true);
        jLayeredPaneKompOpret.setVisible(false);
    }//GEN-LAST:event_jButtonKompAnnullerActionPerformed

    private void jButtonKompOpretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKompOpretActionPerformed
        String navn = jTextFieldKompNavn.getText();
        try {
            int PPD = Integer.parseInt(jTextFieldKompPPD.getText());

            Komponent k = new Komponent(navn, PPD);
            con.createNewKomponent(k);
            
            jLayeredPaneKompOverview.setVisible(true);
            jLayeredPaneKompOpret.setVisible(false);
        } catch (Exception e) {
            System.out.println("JButton opret komponent fail");
        }
    }//GEN-LAST:event_jButtonKompOpretActionPerformed

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
    private javax.swing.JButton jButtonKompAnnuller;
    private javax.swing.JButton jButtonKompOpret;
    private javax.swing.JButton jButtonKundeAnnuller;
    private javax.swing.JButton jButtonKundeOpret;
    private javax.swing.JButton jButtonOBack;
    private javax.swing.JButton jButtonOCancel;
    private javax.swing.JButton jButtonOCancel2;
    private javax.swing.JButton jButtonOInsert;
    private javax.swing.JButton jButtonONext;
    private javax.swing.JButton jButtonOpretNyKomp;
    private javax.swing.JButton jButtonOpretNyKunde;
    private javax.swing.JButton jButtonOpretOrdre;
    private javax.swing.JComboBox jCBoxKunde;
    private com.toedter.calendar.JDateChooser jDcDatoSlut;
    private com.toedter.calendar.JDateChooser jDcDatoStart;
    private javax.swing.JLayeredPane jLPanelOpretOPart2;
    private javax.swing.JLabel jLabelDatoSlut;
    private javax.swing.JLabel jLabelDatoStart;
    private javax.swing.JLabel jLabelKompNavn;
    private javax.swing.JLabel jLabelKompPPD;
    private javax.swing.JLabel jLabelKunde;
    private javax.swing.JLabel jLabelKundeEmail;
    private javax.swing.JLabel jLabelKundeRabat;
    private javax.swing.JLabel jLabelKundeTelefon;
    private javax.swing.JLabel jLabelNavn;
    private javax.swing.JLabel jLabelOSide1;
    private javax.swing.JLabel jLabelOSide2;
    private javax.swing.JLabel jLabelOverskrift;
    private javax.swing.JLabel jLabelOverskriftKompOpret;
    private javax.swing.JLabel jLabelOverskriftKundeOpret;
    private javax.swing.JLabel jLabelPostNR;
    private javax.swing.JLabel jLabelVej;
    private javax.swing.JLayeredPane jLayeredPaneKompOpret;
    private javax.swing.JLayeredPane jLayeredPaneKompOverview;
    private javax.swing.JLayeredPane jLayeredPaneKundeOpret;
    private javax.swing.JLayeredPane jLayeredPaneKundeOverview;
    private javax.swing.JLayeredPane jLayeredPaneOpretOrdre;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelKompOverview;
    private javax.swing.JPanel jPanelKunde;
    private javax.swing.JPanel jPanelKundeOverview;
    private javax.swing.JPanel jPanelNuOrdre;
    private javax.swing.JPanel jPanelOpretOPart1;
    private javax.swing.JPanel jPanelOrdre;
    private javax.swing.JPanel jPanelOverblik;
    private javax.swing.JPanel jPanelResource;
    private javax.swing.JPanel jPanelStaffStatus;
    private javax.swing.JPanel jPanelStatus;
    private javax.swing.JPanel jPanelTilOrdre;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPaneKompOverview;
    private javax.swing.JScrollPane jScrollPaneKundeOverview;
    private javax.swing.JTextField jTFPostNR;
    private javax.swing.JTextField jTFVej;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTableKompOverview;
    private javax.swing.JTable jTableKundeOverview;
    private javax.swing.JTable jTableStaffStatus;
    private javax.swing.JTextField jTextFieldKompNavn;
    private javax.swing.JTextField jTextFieldKompPPD;
    private javax.swing.JTextField jTextFieldKundeEmail;
    private javax.swing.JTextField jTextFieldKundeNavn;
    private javax.swing.JTextField jTextFieldKundeRabat;
    private javax.swing.JTextField jTextFieldKundeTelefon;
    // End of variables declaration//GEN-END:variables
}
