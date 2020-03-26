/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Class.DatabaseConnection;
import Class.Login;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SMKN4
 */
public class SuratKeluarData extends javax.swing.JFrame {

    /**
     * Creates new form SuratKeluarData
     */
    Connection koneksi;

    public SuratKeluarData() {
        initComponents();
        setLocationRelativeTo(null);
        koneksi = DatabaseConnection.bukaKoneksi("localhost", "3306", "root", "", "db_arsip_surat");
        showData();
    }

    public void Edit() {

        String TanggalLahir = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(TanggalLahir);
        String tanggal = String.valueOf(fm.format(txtTanggal.getDate()));

        String no = txtNo.getText();
        String perihal = txtPerihal.getText();
        String jenis = cmbJenis.getSelectedItem().toString();
        String pengirim = txtPengirim.getText();
        String tujuan = txtTujuan.getText();
        String deskripsi = txtDeskripsi.getText();

        try {
            Statement stmt = koneksi.createStatement();

            String query = "update t_suratkeluar set perihal ='" + perihal + "', " + "tanggal = '" + tanggal + "'"
                    + ", " + "id_jenis_surat = '" + jenis + "', " + "pengirim = '" + pengirim + "'"
                    + ", " + "tujuan = '" + tujuan + "', " + "deskripsi = '" + deskripsi + "' "
                    + " where no_surat = '" + no + "' ";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                dispose();
                showData();
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Diubah");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database");
        }
    }

    public void showData() {

        String kolom[] = {"NO", "No Surat", "Tanggal Surat", "Perihal", "Jenis Surat", "Pengirim", "Tujuan", "Deskripsi"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT * FROM t_suratkeluar";
            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String a = rs.getString("no_surat");
                String b = rs.getString("tanggal");
                String d = rs.getString("perihal");
                String e = rs.getString("id_jenis_surat");
                String f = rs.getString("pengirim");
                String g = rs.getString("tujuan");
                String h = rs.getString("deskripsi");

                dtm.addRow(new String[]{no + "", a, b, d, e, f, g, h});
                no++;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database");
        }
        tblSurat.setModel(dtm);
    }

    public void klik() {
        String getNo = tblSurat.getValueAt(tblSurat.getSelectedRow(), 1).toString();
        String getTanggalS = tblSurat.getValueAt(tblSurat.getSelectedRow(), 2).toString();
        String getPerihal = tblSurat.getValueAt(tblSurat.getSelectedRow(), 3).toString();
        String getIdJenis = tblSurat.getValueAt(tblSurat.getSelectedRow(), 4).toString();
        String getPengirim = tblSurat.getValueAt(tblSurat.getSelectedRow(), 5).toString();
        String getTujuan = tblSurat.getValueAt(tblSurat.getSelectedRow(), 6).toString();
        String getDeskripsi = tblSurat.getValueAt(tblSurat.getSelectedRow(), 7).toString();

        txtNo.setText(getNo);
        txtPerihal.setText(getPerihal);
        cmbJenis.setSelectedItem(getIdJenis);
        txtPengirim.setText(getPengirim);
        txtTujuan.setText(getTujuan);
        txtDeskripsi.setText(getDeskripsi);
    }

    public void cari() {

        String kolom[] = {"NO", "No Surat", "Tanggal Surat", "Perihal", "Jenis Surat", "Pengirim", "Tujuan", "Deskripsi"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT * FROM t_suratkeluar where no_surat like '%" + txtCari1.getText() + "%' OR perihal like '%" + txtCari1.getText() + "%'"
                    + "OR pengirim like '%" + txtCari1.getText() + "%'OR tujuan like '%" + txtCari1.getText() + "%' ";
            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String a = rs.getString("no_surat");
                String b = rs.getString("tanggal");
                String d = rs.getString("perihal");
                String e = rs.getString("id_jenis_surat");
                String f = rs.getString("pengirim");
                String g = rs.getString("tujuan");
                String h = rs.getString("deskripsi");

                dtm.addRow(new String[]{no + "", a, b, d, e, f, g, h});
                no++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database");
        }
        tblSurat.setModel(dtm);

    }

    public void delete() {

        String no = txtNo.getText();

        try {
            Statement stmt = koneksi.createStatement();

            String query = "Delete  from t_suratkeluar where no_surat = '" + no + "'";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                jDialog1.dispose();
                showData();
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database");
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

        jDialog1 = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        txtPerihal = new javax.swing.JTextField();
        txtPengirim = new javax.swing.JTextField();
        txtTujuan = new javax.swing.JTextField();
        txtDeskripsi = new javax.swing.JTextField();
        cmbJenis = new javax.swing.JComboBox<String>();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnCancel1 = new javax.swing.JButton();
        txtCari = new javax.swing.JPanel();
        btnClose = new javax.swing.JPanel();
        Tittle2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSurat = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        txtCari1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        Tittle5 = new javax.swing.JLabel();
        btnHome4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnSuratMasuk = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btnSMnew = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnSMdata = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnSuratKeluar = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnSKnew = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnSKdata = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnLaporan = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnLapSM = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnLapSk = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        btnLapDisposisi = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btnSettings = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        btnGantiPassword = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        btnTambahPengguna = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        btnHelp = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        jLabel34.setFont(new java.awt.Font("Poor Richard", 0, 24)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(66, 66, 66));
        jLabel34.setText("Edit Outgoing Mail");

        jLabel35.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(66, 66, 66));
        jLabel35.setText("No. Surat");

        jLabel36.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(66, 66, 66));
        jLabel36.setText("Tanggal");

        jLabel37.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(66, 66, 66));
        jLabel37.setText("Jenis Surat");

        jLabel38.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(66, 66, 66));
        jLabel38.setText("Pengirim");

        jLabel39.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(66, 66, 66));
        jLabel39.setText("Tujuan");

        jLabel40.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(66, 66, 66));
        jLabel40.setText("Perihal");

        jLabel41.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(66, 66, 66));
        jLabel41.setText("Deskripsi");

        cmbJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-Pilih Salah Satu-", "RSM", "PRI", "NRS" }));

        btnSubmit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnCancel1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCancel1.setText("Delete");
        btnCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancel1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel41))
                                .addGap(57, 57, 57)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPerihal, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnSubmit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel1))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(jLabel34)))
                .addGap(0, 179, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addGap(67, 67, 67)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtPerihal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(cmbJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit)
                    .addComponent(btnCancel)
                    .addComponent(btnCancel1))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        txtCari.setBackground(new java.awt.Color(153, 153, 153));

        btnClose.setBackground(new java.awt.Color(153, 153, 153));

        Tittle2.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        Tittle2.setText("X");
        Tittle2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Tittle2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout btnCloseLayout = new javax.swing.GroupLayout(btnClose);
        btnClose.setLayout(btnCloseLayout);
        btnCloseLayout.setHorizontalGroup(
            btnCloseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCloseLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(Tittle2)
                .addContainerGap())
        );
        btnCloseLayout.setVerticalGroup(
            btnCloseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Tittle2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jLabel1.setFont(new java.awt.Font("Poor Richard", 0, 24)); // NOI18N
        jLabel1.setText("Outgoing Mail Data");

        tblSurat.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblSurat);

        btnEdit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEditMousePressed(evt);
            }
        });

        txtCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCari1KeyReleased(evt);
            }
        });

        jLabel2.setText("Cari BerdasarkanNo Surat / Perihal / Pengirim /Tujuan");

        javax.swing.GroupLayout txtCariLayout = new javax.swing.GroupLayout(txtCari);
        txtCari.setLayout(txtCariLayout);
        txtCariLayout.setHorizontalGroup(
            txtCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtCariLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txtCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtCariLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtCariLayout.createSequentialGroup()
                        .addGap(0, 190, Short.MAX_VALUE)
                        .addGroup(txtCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtCariLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(169, 169, 169)
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtCariLayout.createSequentialGroup()
                                .addGroup(txtCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnEdit)
                                    .addGroup(txtCariLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCari1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())))))
        );
        txtCariLayout.setVerticalGroup(
            txtCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtCariLayout.createSequentialGroup()
                .addGroup(txtCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(txtCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCari1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(21, 21, 21));

        Tittle5.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        Tittle5.setForeground(new java.awt.Color(255, 255, 255));
        Tittle5.setText("Aplikasi Informasi Surat");

        btnHome4.setBackground(new java.awt.Color(21, 21, 21));
        btnHome4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnHome4MousePressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/home.png"))); // NOI18N
        jLabel6.setText("Home");

        javax.swing.GroupLayout btnHome4Layout = new javax.swing.GroupLayout(btnHome4);
        btnHome4.setLayout(btnHome4Layout);
        btnHome4Layout.setHorizontalGroup(
            btnHome4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHome4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnHome4Layout.setVerticalGroup(
            btnHome4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnSuratMasuk.setBackground(new java.awt.Color(21, 21, 21));
        btnSuratMasuk.setPreferredSize(new java.awt.Dimension(75, 24));
        btnSuratMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSuratMasukMousePressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Surat Masuk.png"))); // NOI18N
        jLabel7.setText("Incoming Mail");

        javax.swing.GroupLayout btnSuratMasukLayout = new javax.swing.GroupLayout(btnSuratMasuk);
        btnSuratMasuk.setLayout(btnSuratMasukLayout);
        btnSuratMasukLayout.setHorizontalGroup(
            btnSuratMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSuratMasukLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSuratMasukLayout.setVerticalGroup(
            btnSuratMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnSMnew.setBackground(new java.awt.Color(21, 21, 21));
        btnSMnew.setPreferredSize(new java.awt.Dimension(75, 24));
        btnSMnew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSMnewMousePressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/New Entry.png"))); // NOI18N
        jLabel8.setText("New Entry");

        javax.swing.GroupLayout btnSMnewLayout = new javax.swing.GroupLayout(btnSMnew);
        btnSMnew.setLayout(btnSMnewLayout);
        btnSMnewLayout.setHorizontalGroup(
            btnSMnewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSMnewLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSMnewLayout.setVerticalGroup(
            btnSMnewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnSMdata.setBackground(new java.awt.Color(21, 21, 21));
        btnSMdata.setPreferredSize(new java.awt.Dimension(75, 24));
        btnSMdata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSMdataMousePressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Data Surat.png"))); // NOI18N
        jLabel9.setText("Incoming Mail Data");

        javax.swing.GroupLayout btnSMdataLayout = new javax.swing.GroupLayout(btnSMdata);
        btnSMdata.setLayout(btnSMdataLayout);
        btnSMdataLayout.setHorizontalGroup(
            btnSMdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSMdataLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSMdataLayout.setVerticalGroup(
            btnSMdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnSuratKeluar.setBackground(new java.awt.Color(21, 21, 21));
        btnSuratKeluar.setPreferredSize(new java.awt.Dimension(75, 24));
        btnSuratKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSuratKeluarMousePressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Surat Keluar.png"))); // NOI18N
        jLabel10.setText("Outgoing Mail");

        javax.swing.GroupLayout btnSuratKeluarLayout = new javax.swing.GroupLayout(btnSuratKeluar);
        btnSuratKeluar.setLayout(btnSuratKeluarLayout);
        btnSuratKeluarLayout.setHorizontalGroup(
            btnSuratKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSuratKeluarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSuratKeluarLayout.setVerticalGroup(
            btnSuratKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnSKnew.setBackground(new java.awt.Color(21, 21, 21));
        btnSKnew.setPreferredSize(new java.awt.Dimension(75, 24));
        btnSKnew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSKnewMousePressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Data Surat.png"))); // NOI18N
        jLabel11.setText("Outgoiong Mail");

        javax.swing.GroupLayout btnSKnewLayout = new javax.swing.GroupLayout(btnSKnew);
        btnSKnew.setLayout(btnSKnewLayout);
        btnSKnewLayout.setHorizontalGroup(
            btnSKnewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSKnewLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSKnewLayout.setVerticalGroup(
            btnSKnewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnSKdata.setBackground(new java.awt.Color(21, 21, 21));
        btnSKdata.setPreferredSize(new java.awt.Dimension(75, 24));
        btnSKdata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSKdataMousePressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/New Entry.png"))); // NOI18N
        jLabel12.setText("New Entry");

        javax.swing.GroupLayout btnSKdataLayout = new javax.swing.GroupLayout(btnSKdata);
        btnSKdata.setLayout(btnSKdataLayout);
        btnSKdataLayout.setHorizontalGroup(
            btnSKdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSKdataLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSKdataLayout.setVerticalGroup(
            btnSKdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnLaporan.setBackground(new java.awt.Color(21, 21, 21));
        btnLaporan.setPreferredSize(new java.awt.Dimension(75, 24));
        btnLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLaporanMousePressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Laporan.png"))); // NOI18N
        jLabel13.setText("Report");

        javax.swing.GroupLayout btnLaporanLayout = new javax.swing.GroupLayout(btnLaporan);
        btnLaporan.setLayout(btnLaporanLayout);
        btnLaporanLayout.setHorizontalGroup(
            btnLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLaporanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnLaporanLayout.setVerticalGroup(
            btnLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnLapSM.setBackground(new java.awt.Color(21, 21, 21));
        btnLapSM.setPreferredSize(new java.awt.Dimension(75, 24));
        btnLapSM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLapSMMousePressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Surat Masuk.png"))); // NOI18N
        jLabel14.setText("Incoming Mail");

        javax.swing.GroupLayout btnLapSMLayout = new javax.swing.GroupLayout(btnLapSM);
        btnLapSM.setLayout(btnLapSMLayout);
        btnLapSMLayout.setHorizontalGroup(
            btnLapSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLapSMLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnLapSMLayout.setVerticalGroup(
            btnLapSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnLapSk.setBackground(new java.awt.Color(21, 21, 21));
        btnLapSk.setPreferredSize(new java.awt.Dimension(75, 24));
        btnLapSk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLapSkMousePressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Surat Keluar.png"))); // NOI18N
        jLabel15.setText("Outgoing Mail");

        javax.swing.GroupLayout btnLapSkLayout = new javax.swing.GroupLayout(btnLapSk);
        btnLapSk.setLayout(btnLapSkLayout);
        btnLapSkLayout.setHorizontalGroup(
            btnLapSkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLapSkLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnLapSkLayout.setVerticalGroup(
            btnLapSkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnLapDisposisi.setBackground(new java.awt.Color(21, 21, 21));
        btnLapDisposisi.setPreferredSize(new java.awt.Dimension(75, 24));
        btnLapDisposisi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLapDisposisiMousePressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Disposisi.png"))); // NOI18N
        jLabel16.setText("Disposition");

        javax.swing.GroupLayout btnLapDisposisiLayout = new javax.swing.GroupLayout(btnLapDisposisi);
        btnLapDisposisi.setLayout(btnLapDisposisiLayout);
        btnLapDisposisiLayout.setHorizontalGroup(
            btnLapDisposisiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLapDisposisiLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnLapDisposisiLayout.setVerticalGroup(
            btnLapDisposisiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnSettings.setBackground(new java.awt.Color(21, 21, 21));
        btnSettings.setPreferredSize(new java.awt.Dimension(75, 24));
        btnSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSettingsMousePressed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Setting.png"))); // NOI18N
        jLabel28.setText("Settings");

        javax.swing.GroupLayout btnSettingsLayout = new javax.swing.GroupLayout(btnSettings);
        btnSettings.setLayout(btnSettingsLayout);
        btnSettingsLayout.setHorizontalGroup(
            btnSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSettingsLayout.setVerticalGroup(
            btnSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnGantiPassword.setBackground(new java.awt.Color(21, 21, 21));
        btnGantiPassword.setPreferredSize(new java.awt.Dimension(75, 24));
        btnGantiPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnGantiPasswordMousePressed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Profile.png"))); // NOI18N
        jLabel42.setText("Profile");

        javax.swing.GroupLayout btnGantiPasswordLayout = new javax.swing.GroupLayout(btnGantiPassword);
        btnGantiPassword.setLayout(btnGantiPasswordLayout);
        btnGantiPasswordLayout.setHorizontalGroup(
            btnGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGantiPasswordLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnGantiPasswordLayout.setVerticalGroup(
            btnGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGantiPasswordLayout.createSequentialGroup()
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTambahPengguna.setBackground(new java.awt.Color(21, 21, 21));
        btnTambahPengguna.setPreferredSize(new java.awt.Dimension(75, 24));
        btnTambahPengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnTambahPenggunaMousePressed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tambah pengguna.png"))); // NOI18N
        jLabel43.setText("New User");

        javax.swing.GroupLayout btnTambahPenggunaLayout = new javax.swing.GroupLayout(btnTambahPengguna);
        btnTambahPengguna.setLayout(btnTambahPenggunaLayout);
        btnTambahPenggunaLayout.setHorizontalGroup(
            btnTambahPenggunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnTambahPenggunaLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel43)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnTambahPenggunaLayout.setVerticalGroup(
            btnTambahPenggunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnHelp.setBackground(new java.awt.Color(21, 21, 21));
        btnHelp.setPreferredSize(new java.awt.Dimension(75, 24));
        btnHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnHelpMousePressed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Help.png"))); // NOI18N
        jLabel44.setText("Help");

        javax.swing.GroupLayout btnHelpLayout = new javax.swing.GroupLayout(btnHelp);
        btnHelp.setLayout(btnHelpLayout);
        btnHelpLayout.setHorizontalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHelpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnHelpLayout.setVerticalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel9.setBackground(new java.awt.Color(21, 21, 21));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel9MousePressed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Logout.png"))); // NOI18N
        jLabel45.setText("Logout");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel45)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel45)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnHome4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSuratMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addComponent(btnSuratKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addComponent(btnLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addComponent(btnSettings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addComponent(btnHelp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGantiPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSKnew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnSMdata, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnLapSM, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnSKdata, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnSMnew, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnLapSk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnLapDisposisi, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnTambahPengguna, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(Tittle5)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Tittle5)
                .addGap(18, 18, 18)
                .addComponent(btnHome4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSuratMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSMnew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSMdata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSuratKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSKdata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSKnew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLapSM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLapSk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLapDisposisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGantiPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTambahPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tittle2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tittle2MousePressed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_Tittle2MousePressed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        Edit();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        jDialog1.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnEditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMousePressed
        // TODO add your handling code here:
        jDialog1.setVisible(true);
        jDialog1.setSize(423, 500);
        klik();
    }//GEN-LAST:event_btnEditMousePressed

    int baris;
    private void btnHome4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHome4MousePressed
        // TODO add your handling code here:
        HomeAdmin l = new HomeAdmin();
        l.show();
        dispose();
    }//GEN-LAST:event_btnHome4MousePressed

    private void btnSuratMasukMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuratMasukMousePressed
        // TODO add your handling code here:
        SuratMasuk l = new SuratMasuk();
        l.show();
        dispose();
    }//GEN-LAST:event_btnSuratMasukMousePressed

    private void btnSMnewMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSMnewMousePressed
        // TODO add your handling code here:
        SuratMasukEntry l = new SuratMasukEntry();
        l.show();
        dispose();
    }//GEN-LAST:event_btnSMnewMousePressed

    private void btnSMdataMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSMdataMousePressed
        // TODO add your handling code here:
        SuratMasukData l = new SuratMasukData();
        l.show();
        dispose();
    }//GEN-LAST:event_btnSMdataMousePressed

    private void btnSuratKeluarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuratKeluarMousePressed
        // TODO add your handling code here:
        SuratKeluar l = new SuratKeluar();
        l.show();
        dispose();
    }//GEN-LAST:event_btnSuratKeluarMousePressed

    private void btnSKnewMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSKnewMousePressed
        // TODO add your handling code here:
        SuratKeluarData l = new SuratKeluarData();
        l.show();
        dispose();
    }//GEN-LAST:event_btnSKnewMousePressed

    private void btnSKdataMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSKdataMousePressed
        // TODO add your handling code here:
        SuratKeluarEntry l = new SuratKeluarEntry();
        l.show();
        dispose();
    }//GEN-LAST:event_btnSKdataMousePressed

    private void btnLaporanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMousePressed
        // TODO add your handling code here:
        Laporan l = new Laporan();
        l.show();
        dispose();
    }//GEN-LAST:event_btnLaporanMousePressed

    private void btnLapSMMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLapSMMousePressed
        // TODO add your handling code here:
        ReportSuratMasuk l = new ReportSuratMasuk();
        l.show();
        dispose();
    }//GEN-LAST:event_btnLapSMMousePressed

    private void btnLapSkMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLapSkMousePressed
        // TODO add your handling code here:
        ReportSuratKeluar l = new ReportSuratKeluar();
        l.show();
        dispose();
    }//GEN-LAST:event_btnLapSkMousePressed

    private void btnLapDisposisiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLapDisposisiMousePressed
        // TODO add your handling code here:
        ReportDisposisi l = new ReportDisposisi();
        l.show();
        dispose();
    }//GEN-LAST:event_btnLapDisposisiMousePressed

    private void btnSettingsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSettingsMousePressed
        // TODO add your handling code here:
        Settings l = new Settings();
        l.show();
        dispose();
    }//GEN-LAST:event_btnSettingsMousePressed

    private void btnGantiPasswordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGantiPasswordMousePressed
        // TODO add your handling code here:
        Profile l = new Profile();
        l.show();
        dispose();
    }//GEN-LAST:event_btnGantiPasswordMousePressed

    private void btnTambahPenggunaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahPenggunaMousePressed
        // TODO add your handling code here:
        TambahPengguna l = new TambahPengguna();
        l.show();
        dispose();
    }//GEN-LAST:event_btnTambahPenggunaMousePressed

    private void btnHelpMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMousePressed
        // TODO add your handling code here:
        Help l = new Help();
        l.show();
        dispose();
    }//GEN-LAST:event_btnHelpMousePressed

    private void jPanel9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MousePressed
        // TODO add your handling code here:
        Login l = new Login();
        l.show();
        dispose();
    }//GEN-LAST:event_jPanel9MousePressed

    private void txtCari1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCari1KeyReleased
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_txtCari1KeyReleased

    private void btnCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel1ActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnCancel1ActionPerformed

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
            java.util.logging.Logger.getLogger(SuratKeluarData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SuratKeluarData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SuratKeluarData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SuratKeluarData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SuratKeluarData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Tittle2;
    private javax.swing.JLabel Tittle5;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCancel1;
    private javax.swing.JPanel btnClose;
    private javax.swing.JButton btnEdit;
    private javax.swing.JPanel btnGantiPassword;
    private javax.swing.JPanel btnHelp;
    private javax.swing.JPanel btnHome4;
    private javax.swing.JPanel btnLapDisposisi;
    private javax.swing.JPanel btnLapSM;
    private javax.swing.JPanel btnLapSk;
    private javax.swing.JPanel btnLaporan;
    private javax.swing.JPanel btnSKdata;
    private javax.swing.JPanel btnSKnew;
    private javax.swing.JPanel btnSMdata;
    private javax.swing.JPanel btnSMnew;
    private javax.swing.JPanel btnSettings;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel btnSuratKeluar;
    private javax.swing.JPanel btnSuratMasuk;
    private javax.swing.JPanel btnTambahPengguna;
    private javax.swing.JComboBox<String> cmbJenis;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSurat;
    private javax.swing.JPanel txtCari;
    private javax.swing.JTextField txtCari1;
    private javax.swing.JTextField txtDeskripsi;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtPengirim;
    private javax.swing.JTextField txtPerihal;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private javax.swing.JTextField txtTujuan;
    // End of variables declaration//GEN-END:variables
}
