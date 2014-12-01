/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Control.Control;
import Entity.Lager;
import Entity.Ordre;
import com.sun.glass.events.KeyEvent;
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
                        i += lager.getKompList().get(id);
                    }
                    return i;
                case 4:
                    i = 0;
                    for (Ordre order : con.getOrdreList().values()) {
                        if (new Date().after(order.getDatoStart())
                                && new Date().before(order.getDatoSlut())) {
                            i += order.getKompList().get(id);
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
                    return con.getStaffList().get(con.getOrdreList().get(id).getSalgsmedarbsID());
                case 1:
                    return con.getKundeList().get(con.getOrdreList().get(id).getKundeID());
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
        jPanelResource = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableStaffStatus.setModel(new StaffStatusModel());
        jTableStaffStatus.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTableStaffStatus);
        jTableStaffStatus.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanelStaffStatusLayout = new javax.swing.GroupLayout(jPanelStaffStatus);
        jPanelStaffStatus.setLayout(jPanelStaffStatusLayout);
        jPanelStaffStatusLayout.setHorizontalGroup(
            jPanelStaffStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
        );
        jPanelStaffStatusLayout.setVerticalGroup(
            jPanelStaffStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Medarbejdere", jPanelStaffStatus);

        jTable3.setModel(new LastbilStatusModel());
        jScrollPane4.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Lastbiler", jPanel6);

        jTable4.setModel(new KomponentStatusModel());
        jScrollPane5.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
        jPanelNuOrdreLayout.setVerticalGroup(
            jPanelNuOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
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
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
        jPanelTilOrdreLayout.setVerticalGroup(
            jPanelTilOrdreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
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
                .addGap(18, 18, 18)
                .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLPanelOpretOPart2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLPanelOpretOPart2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButtonLastUde)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonLastHjem)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
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
                .addComponent(jLPanelOpretOPart2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jPanelKundeLayout = new javax.swing.GroupLayout(jPanelKunde);
        jPanelKunde.setLayout(jPanelKundeLayout);
        jPanelKundeLayout.setHorizontalGroup(
            jPanelKundeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 895, Short.MAX_VALUE)
        );
        jPanelKundeLayout.setVerticalGroup(
            jPanelKundeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 672, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("Kunde", jPanelKunde);

        javax.swing.GroupLayout jPanelResourceLayout = new javax.swing.GroupLayout(jPanelResource);
        jPanelResource.setLayout(jPanelResourceLayout);
        jPanelResourceLayout.setHorizontalGroup(
            jPanelResourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 895, Short.MAX_VALUE)
        );
        jPanelResourceLayout.setVerticalGroup(
            jPanelResourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 672, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("Resource", jPanelResource);

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
    private javax.swing.JButton jButtonOpretOrdre;
    private javax.swing.JComboBox jCBoxKunde;
    private com.toedter.calendar.JDateChooser jDcDatoSlut;
    private com.toedter.calendar.JDateChooser jDcDatoStart;
    private javax.swing.JLayeredPane jLPanelOpretOPart2;
    private javax.swing.JLabel jLabelDatoSlut;
    private javax.swing.JLabel jLabelDatoStart;
    private javax.swing.JLabel jLabelKomp;
    private javax.swing.JLabel jLabelKunde;
    private javax.swing.JLabel jLabelLast;
    private javax.swing.JLabel jLabelMon;
    private javax.swing.JLabel jLabelOSide1;
    private javax.swing.JLabel jLabelOSide2;
    private javax.swing.JLabel jLabelOverskrift;
    private javax.swing.JLabel jLabelPostNR;
    private javax.swing.JLabel jLabelVej;
    private javax.swing.JLayeredPane jLayeredPaneOpretOrdre;
    private javax.swing.JList jListKompHjem;
    private javax.swing.JList jListKompUde;
    private javax.swing.JList jListLastHjem;
    private javax.swing.JList jListLastUde;
    private javax.swing.JList jListMonHjem;
    private javax.swing.JList jListMonUde;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelKunde;
    private javax.swing.JPanel jPanelNuOrdre;
    private javax.swing.JPanel jPanelOpretOPart1;
    private javax.swing.JPanel jPanelOrdre;
    private javax.swing.JPanel jPanelOverblik;
    private javax.swing.JPanel jPanelResource;
    private javax.swing.JPanel jPanelStaffStatus;
    private javax.swing.JPanel jPanelStatus;
    private javax.swing.JPanel jPanelTilOrdre;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTFPostNR;
    private javax.swing.JTextField jTFVej;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTableStaffStatus;
    // End of variables declaration//GEN-END:variables
}
