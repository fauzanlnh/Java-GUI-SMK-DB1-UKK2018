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
public class SuratMasukData extends javax.swing.JFrame {

    /**
     * Creates new form SuratMasukData
     */
    Connection koneksi;

    public SuratMasukData() {
        initComponents();
        setLocationRelativeTo(null);
        koneksi = DatabaseConnection.bukaKoneksi("localhost", "3306", "root", "", "db_arsip_surat");
        showData();
    }

    public void Simpan() {

        String TanggalLahir = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(TanggalLahir);
        String tanggal = String.valueOf(fm.format(txtTanggalDis.getDate()));

        String no = txtNoDis.getText();
        String perihal = txtPerihalDis.getText();
        String tujuan = txtTujuanDis.getText();
        String deskripsi = txtDeskripsiDis.getText();

        try {
            Statement stmt = koneksi.createStatement();

            String query = "insert into t_disposisi values ('" + no + "', '" + tanggal + "','" + perihal + "'"
                    + ",'" + tujuan + "','" + deskripsi + "')";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dimasukan");
                jDialogDisposisi.dispose();
                showDataDisposisi();
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Dimasukan");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database");
        }

    }

    public void Edit() {

        String TanggalLahir = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(TanggalLahir);
        String tanggal = String.valueOf(fm.format(txtTanggal1.getDate()));
        String tanggal2 = String.valueOf(fm.format(txtTanggal2.getDate()));

        String no = txtNo.getText();
        String perihal = txtPerihal.getText();
        String jenis = cmbJenis.getSelectedItem().toString();
        String pengirim = txtPengirim.getText();
        String tujuan = txtTujuan.getText();
        String deskripsi = txtDeskripsi.getText();

        try {
            Statement stmt = koneksi.createStatement();

            String query = "update t_suratmasuk set perihal ='" + perihal + "', " + "tanggal_surat = '" + tanggal + "', " + "tanggal_diterima = '" + tanggal2 + "'"
                    + ", " + "id_jenis_surat = '" + jenis + "', " + "pengirim = '" + pengirim + "'"
                    + ", " + "tujuan = '" + tujuan + "', " + "deskripsi = '" + deskripsi + "' "
                    + " where no_surat = '" + no + "' ";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                jDialogEdit.dispose();
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

        String kolom[] = {"NO", "No Surat", "Tanggal Surat", "Tanggal Diterima", "Perihal", "Jenis Surat", "Pengirim", "Tujuan", "Deskripsi"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT t_suratmasuk.no_surat, t_suratmasuk.tanggal_surat, t_suratmasuk.tanggal_diterima, t_suratmasuk.perihal,"
                    + "t_suratmasuk.id_jenis_surat,t_suratmasuk.pengirim,t_suratmasuk.tujuan,t_suratmasuk.deskripsi from t_suratmasuk";
            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String a = rs.getString("no_surat");
                String b = rs.getString("tanggal_surat");
                String c = rs.getString("tanggal_diterima");
                String d = rs.getString("perihal");
                String e = rs.getString("id_jenis_surat");
                String f = rs.getString("pengirim");
                String g = rs.getString("tujuan");
                String h = rs.getString("deskripsi");

                dtm.addRow(new String[]{no + "", a, b, c, d, e, f, g, h});
                no++;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database");
        }

        tblSurat.setModel(dtm);

    }

    public void cari() {

        String kolom[] = {"NO", "No Surat", "Tanggal Surat", "Tanggal Diterima", "Perihal", "Jenis Surat", "Pengirim", "Tujuan", "Deskripsi"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT * FROM t_suratmasuk where no_surat like '%" + txtCari.getText() + "%' OR perihal like '%" + txtCari.getText() + "%'"
                    + "OR pengirim like '%" + txtCari.getText() + "%'OR tujuan like '%" + txtCari.getText() + "%' ";
            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String a = rs.getString("no_surat");
                String b = rs.getString("tanggal_surat");
                String c = rs.getString("tanggal_diterima");
                String d = rs.getString("perihal");
                String e = rs.getString("id_jenis_surat");
                String f = rs.getString("pengirim");
                String g = rs.getString("tujuan");
                String h = rs.getString("deskripsi");

                dtm.addRow(new String[]{no + "", a, b, c, d, e, f, g, h});
                no++;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database");
        }
        tblSurat.setModel(dtm);
    }

    public void showDataDisposisi() {

        String kolom[] = {"NO", "No Surat", "Tanggal Surat", "Perihal", "Tujuan", "Deskripsi"};
        DefaultTableModel dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT * FROM t_suratmasuk";
            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String a = rs.getString("no_surat");
                String b = rs.getString("tanggal_surat");
                String c = rs.getString("perihal");
                String g = rs.getString("tujuan");
                String h = rs.getString("deskripsi");

                dtm.addRow(new String[]{no + "", a, b, c, g, h});
                no++;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kesalahan Pada Database");
        }
        tblSuratDis.setModel(dtm);
    }

    public void klik() {
        String getNo = tblSurat.getValueAt(tblSurat.getSelectedRow(), 1).toString();
        String getTanggalS = tblSurat.getValueAt(tblSurat.getSelectedRow(), 2).toString();
        String getTanggalD = tblSurat.getValueAt(tblSurat.getSelectedRow(), 3).toString();
        String getPerihal = tblSurat.getValueAt(tblSurat.getSelectedRow(), 4).toString();
        String getIdJenis = tblSurat.getValueAt(tblSurat.getSelectedRow(), 5).toString();
        String getPengirim = tblSurat.getValueAt(tblSurat.getSelectedRow(), 6).toString();
        String getTujuan = tblSurat.getValueAt(tblSurat.getSelectedRow(), 7).toString();
        String getDeskripsi = tblSurat.getValueAt(tblSurat.getSelectedRow(), 8).toString();

        txtNo.setText(getNo);
        txtPerihal.setText(getPerihal);
        cmbJenis.setSelectedItem(getIdJenis);
        txtPengirim.setText(getPengirim);
        txtTujuan.setText(getTujuan);
        txtDeskripsi.setText(getDeskripsi);
    }

    public void klik2() {
        String getNo = tblSurat.getValueAt(tblSurat.getSelectedRow(), 1).toString();
        String getTanggalS = tblSurat.getValueAt(tblSurat.getSelectedRow(), 2).toString();
        String getTanggalD = tblSurat.getValueAt(tblSurat.getSelectedRow(), 3).toString();
        String getPerihal = tblSurat.getValueAt(tblSurat.getSelectedRow(), 4).toString();
        String getIdJenis = tblSurat.getValueAt(tblSurat.getSelectedRow(), 5).toString();
        String getPengirim = tblSurat.getValueAt(tblSurat.getSelectedRow(), 6).toString();
        String getTujuan = tblSurat.getValueAt(tblSurat.getSelectedRow(), 7).toString();
        String getDeskripsi = tblSurat.getValueAt(tblSurat.getSelectedRow(), 8).toString();

        txtNoDis.setText(getNo);
        txtPerihalDis.setText(getPerihal);
        txtDeskripsiDis.setText(getDeskripsi);
    }

    public void Delete() {

        String no = txtNoDis.getText();
        String perihal = txtPerihalDis.getText();
        String tujuan = txtTujuanDis.getText();
        String deskripsi = txtDeskripsiDis.getText();

        try {
            Statement stmt = koneksi.createStatement();

            String query = "Delete  from t_suratmasuk where no_surat = '" + no + "'";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                jDialogEdit.dispose();
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

        jDialogEdit = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        txtTanggal1 = new com.toedter.calendar.JDateChooser();
        txtPerihal = new javax.swing.JTextField();
        txtPengirim = new javax.swing.JTextField();
        txtTujuan = new javax.swing.JTextField();
        txtDeskripsi = new javax.swing.JTextField();
        cmbJenis = new javax.swing.JComboBox<String>();
        btnSubmit1 = new javax.swing.JButton();
        btnCancelEdit = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        txtTanggal2 = new com.toedter.calendar.JDateChooser();
        btnDelete = new javax.swing.JButton();
        jDialogDisposisi = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        txtNoDis = new javax.swing.JTextField();
        txtTanggalDis = new com.toedter.calendar.JDateChooser();
        txtPerihalDis = new javax.swing.JTextField();
        txtTujuanDis = new javax.swing.JTextField();
        txtDeskripsiDis = new javax.swing.JTextField();
        btnSubmit2 = new javax.swing.JButton();
        btnCancelEdit1 = new javax.swing.JButton();
        jDialogData = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSuratDis = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnClose = new javax.swing.JPanel();
        Tittle2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSurat = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDisposisi = new javax.swing.JButton();
        btnDisposisi1 = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
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
        jLabel74 = new javax.swing.JLabel();
        btnTambahPengguna = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        btnHelp = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();

        jPanel9.setBackground(new java.awt.Color(153, 153, 153));

        jLabel57.setFont(new java.awt.Font("Poor Richard", 0, 24)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(66, 66, 66));
        jLabel57.setText("Edit Incoming Mail");

        jLabel58.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(66, 66, 66));
        jLabel58.setText("No. Surat");

        jLabel59.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(66, 66, 66));
        jLabel59.setText("Tanggal Surat");

        jLabel60.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(66, 66, 66));
        jLabel60.setText("Jenis Surat");

        jLabel61.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(66, 66, 66));
        jLabel61.setText("Pengirim");

        jLabel62.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(66, 66, 66));
        jLabel62.setText("Tujuan");

        jLabel63.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(66, 66, 66));
        jLabel63.setText("Perihal");

        jLabel64.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(66, 66, 66));
        jLabel64.setText("Deskripsi");

        cmbJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-Pilih Salah Satu-", "RSM", "PRI", "NRS" }));

        btnSubmit1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnSubmit1.setText("Submit");
        btnSubmit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmit1ActionPerformed(evt);
            }
        });

        btnCancelEdit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCancelEdit.setText("Cancel");
        btnCancelEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCancelEditMousePressed(evt);
            }
        });
        btnCancelEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelEditActionPerformed(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("Poor Richard", 0, 18)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(66, 66, 66));
        jLabel65.setText("Tanggal Diterima");

        btnDelete.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel63)
                    .addComponent(jLabel60)
                    .addComponent(jLabel61)
                    .addComponent(jLabel62)
                    .addComponent(jLabel64)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnSubmit1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel57)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel65)
                                .addComponent(jLabel58)
                                .addComponent(jLabel59))
                            .addGap(30, 30, 30)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTanggal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTanggal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPerihal, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 150, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel57)
                .addGap(50, 50, 50)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59)
                    .addComponent(txtTanggal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel65)
                    .addComponent(txtTanggal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(txtPerihal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(cmbJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(txtPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(txtTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit1)
                    .addComponent(btnCancelEdit)
                    .addComponent(btnDelete))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogEditLayout = new javax.swing.GroupLayout(jDialogEdit.getContentPane());
        jDialogEdit.getContentPane().setLayout(jDialogEditLayout);
        jDialogEditLayout.setHorizontalGroup(
            jDialogEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogEditLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDialogEditLayout.setVerticalGroup(
            jDialogEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogEditLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(153, 153, 153));

        jLabel66.setFont(new java.awt.Font("Poor Richard", 0, 24)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(66, 66, 66));
        jLabel66.setText("Disposisi Surat");

        jLabel67.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(66, 66, 66));
        jLabel67.setText("No. Surat");

        jLabel68.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(66, 66, 66));
        jLabel68.setText("Tanggal Surat");

        jLabel71.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(66, 66, 66));
        jLabel71.setText("Tujuan");

        jLabel72.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(66, 66, 66));
        jLabel72.setText("Perihal");

        jLabel73.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(66, 66, 66));
        jLabel73.setText("Deskripsi");

        btnSubmit2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnSubmit2.setText("Submit");
        btnSubmit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmit2ActionPerformed(evt);
            }
        });

        btnCancelEdit1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCancelEdit1.setText("Cancel");
        btnCancelEdit1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCancelEdit1MousePressed(evt);
            }
        });
        btnCancelEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelEdit1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(btnSubmit2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancelEdit1))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel67)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNoDis, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel68)
                                    .addComponent(jLabel72))
                                .addGap(43, 43, 43)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPerihalDis, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTanggalDis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jLabel66))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(127, 127, 127)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTujuanDis, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDeskripsiDis, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel71)
                            .addComponent(jLabel73))))
                .addGap(0, 130, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel66)
                .addGap(50, 50, 50)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(txtNoDis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel68)
                    .addComponent(txtTanggalDis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(txtPerihalDis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(txtTujuanDis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(txtDeskripsiDis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(138, 138, 138)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit2)
                    .addComponent(btnCancelEdit1))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogDisposisiLayout = new javax.swing.GroupLayout(jDialogDisposisi.getContentPane());
        jDialogDisposisi.getContentPane().setLayout(jDialogDisposisiLayout);
        jDialogDisposisiLayout.setHorizontalGroup(
            jDialogDisposisiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogDisposisiLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jDialogDisposisiLayout.setVerticalGroup(
            jDialogDisposisiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        jLabel3.setFont(new java.awt.Font("Poor Richard", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(66, 66, 66));
        jLabel3.setText("Incoming Mail Data");

        tblSuratDis.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblSuratDis);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(223, 223, 223))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout jDialogDataLayout = new javax.swing.GroupLayout(jDialogData.getContentPane());
        jDialogData.getContentPane().setLayout(jDialogDataLayout);
        jDialogDataLayout.setHorizontalGroup(
            jDialogDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogDataLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDialogDataLayout.setVerticalGroup(
            jDialogDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

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
        jLabel1.setForeground(new java.awt.Color(66, 66, 66));
        jLabel1.setText("Incoming Mail Data");

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
        tblSurat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSuratMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSurat);

        btnEdit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEditMousePressed(evt);
            }
        });

        btnDisposisi.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnDisposisi.setText("Disposisi");
        btnDisposisi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDisposisiMousePressed(evt);
            }
        });

        btnDisposisi1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnDisposisi1.setText("Data Surat Disposisi");
        btnDisposisi1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDisposisi1MousePressed(evt);
            }
        });

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        jLabel2.setText("Cari BerdasarkanNo Surat / Perihal / Pengirim /Tujuan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(175, 175, 175)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnDisposisi1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDisposisi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEdit))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(32, 32, 32)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 36, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnDisposisi)
                    .addComponent(btnDisposisi1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(21, 21, 21));

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

        jLabel74.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Profile.png"))); // NOI18N
        jLabel74.setText("Profile");

        javax.swing.GroupLayout btnGantiPasswordLayout = new javax.swing.GroupLayout(btnGantiPassword);
        btnGantiPassword.setLayout(btnGantiPasswordLayout);
        btnGantiPasswordLayout.setHorizontalGroup(
            btnGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGantiPasswordLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel74)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnGantiPasswordLayout.setVerticalGroup(
            btnGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGantiPasswordLayout.createSequentialGroup()
                .addComponent(jLabel74)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTambahPengguna.setBackground(new java.awt.Color(21, 21, 21));
        btnTambahPengguna.setPreferredSize(new java.awt.Dimension(75, 24));
        btnTambahPengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnTambahPenggunaMousePressed(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tambah pengguna.png"))); // NOI18N
        jLabel75.setText("New User");

        javax.swing.GroupLayout btnTambahPenggunaLayout = new javax.swing.GroupLayout(btnTambahPengguna);
        btnTambahPengguna.setLayout(btnTambahPenggunaLayout);
        btnTambahPenggunaLayout.setHorizontalGroup(
            btnTambahPenggunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnTambahPenggunaLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel75)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnTambahPenggunaLayout.setVerticalGroup(
            btnTambahPenggunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel75, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnHelp.setBackground(new java.awt.Color(21, 21, 21));
        btnHelp.setPreferredSize(new java.awt.Dimension(75, 24));
        btnHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnHelpMousePressed(evt);
            }
        });

        jLabel76.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Help.png"))); // NOI18N
        jLabel76.setText("Help");

        javax.swing.GroupLayout btnHelpLayout = new javax.swing.GroupLayout(btnHelp);
        btnHelp.setLayout(btnHelpLayout);
        btnHelpLayout.setHorizontalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHelpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel76)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnHelpLayout.setVerticalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel76, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel12.setBackground(new java.awt.Color(21, 21, 21));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel12MousePressed(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("Poor Richard", 0, 16)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Logout.png"))); // NOI18N
        jLabel77.setText("Logout");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel77)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel77)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnHome4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSuratMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addComponent(btnSuratKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addComponent(btnLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addComponent(btnSettings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addComponent(btnHelp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGantiPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSKnew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnSMdata, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnLapSM, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnSKdata, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnSMnew, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnLapSk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnLapDisposisi, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(btnTambahPengguna, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(Tittle5)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
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
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tittle2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tittle2MousePressed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_Tittle2MousePressed

    private void btnEditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMousePressed
        // TODO add your handling code here:

        jDialogEdit.setVisible(true);
        jDialogEdit.setSize(423, 500);
        klik();
    }//GEN-LAST:event_btnEditMousePressed

    private void btnDisposisiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisposisiMousePressed
        // TODO add your handling code here:

        jDialogDisposisi.setVisible(true);
        jDialogDisposisi.setSize(423, 500);
        klik2();
    }//GEN-LAST:event_btnDisposisiMousePressed

    private void btnSubmit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmit1ActionPerformed
        // TODO add your handling code here:
        Edit();
    }//GEN-LAST:event_btnSubmit1ActionPerformed

    private void btnCancelEditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelEditMousePressed
        // TODO add your handling code here:
        jDialogEdit.dispose();
    }//GEN-LAST:event_btnCancelEditMousePressed

    private void btnCancelEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelEditActionPerformed

    private void btnSubmit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmit2ActionPerformed
        // TODO add your handling code here:
        Simpan();
    }//GEN-LAST:event_btnSubmit2ActionPerformed

    private void btnCancelEdit1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelEdit1MousePressed
        // TODO add your handling code here:
        jDialogDisposisi.dispose();
    }//GEN-LAST:event_btnCancelEdit1MousePressed

    private void btnCancelEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelEdit1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelEdit1ActionPerformed

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

    private void jPanel12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MousePressed
        // TODO add your handling code here:
        Login l = new Login();
        l.show();
        dispose();
    }//GEN-LAST:event_jPanel12MousePressed

    private void btnDisposisi1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisposisi1MousePressed
        // TODO add your handling code here:
        jDialogData.setVisible(true);
        jDialogData.setSize(605, 548);
        showDataDisposisi();
    }//GEN-LAST:event_btnDisposisi1MousePressed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_txtCariKeyReleased

    private void tblSuratMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSuratMouseClicked
        // TODO add your handling code here:
        klik();
        klik2();
    }//GEN-LAST:event_tblSuratMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        Delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariKeyPressed

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
            java.util.logging.Logger.getLogger(SuratMasukData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SuratMasukData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SuratMasukData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SuratMasukData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SuratMasukData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Tittle2;
    private javax.swing.JLabel Tittle5;
    private javax.swing.JButton btnCancelEdit;
    private javax.swing.JButton btnCancelEdit1;
    private javax.swing.JPanel btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDisposisi;
    private javax.swing.JButton btnDisposisi1;
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
    private javax.swing.JButton btnSubmit1;
    private javax.swing.JButton btnSubmit2;
    private javax.swing.JPanel btnSuratKeluar;
    private javax.swing.JPanel btnSuratMasuk;
    private javax.swing.JPanel btnTambahPengguna;
    private javax.swing.JComboBox<String> cmbJenis;
    private javax.swing.JDialog jDialogData;
    private javax.swing.JDialog jDialogDisposisi;
    private javax.swing.JDialog jDialogEdit;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblSurat;
    private javax.swing.JTable tblSuratDis;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtDeskripsi;
    private javax.swing.JTextField txtDeskripsiDis;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtNoDis;
    private javax.swing.JTextField txtPengirim;
    private javax.swing.JTextField txtPerihal;
    private javax.swing.JTextField txtPerihalDis;
    private com.toedter.calendar.JDateChooser txtTanggal1;
    private com.toedter.calendar.JDateChooser txtTanggal2;
    private com.toedter.calendar.JDateChooser txtTanggalDis;
    private javax.swing.JTextField txtTujuan;
    private javax.swing.JTextField txtTujuanDis;
    // End of variables declaration//GEN-END:variables
}
