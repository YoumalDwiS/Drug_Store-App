package Master;

import Config.DBConnect;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Obat1 extends JFrame {
    private JPanel mObat1;
    private JTextField txtID;
    private JTextField txtNama;
    private JTextArea txtKandungan;
    private JComboBox cmbKategori;
    private JComboBox cmbJenis;
    private JComboBox cmbDosis;
    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBatal;
    private JPanel jpCald;
    private JTextField txtStok;
    private JTextField txtHarga;
    private JTable tbObat;
    private JRadioButton rbID;
    private JRadioButton rbNama;
    private JTextField txtID1;
    private JTextField txtNama1;
    private JButton btnRefresh;
    private JButton btnKembali;
    private DefaultTableModel model;

    DBConnect connection = new DBConnect();
    JDateChooser datechoos = new JDateChooser();
    String id, nama, kategori, kandungan, jenis, dosis, tgl, stok, harga;

    public void autokode() {

        try {
            String sql = "SELECT * FROM mObat ORDER BY id_obat desc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            if (connection.result.next()) {
                id = connection.result.getString("id_obat").substring(4);
                String AN = "" + (Integer.parseInt(id) + 1);
                String nol = "";

                if (AN.length() == 1) {
                    nol = "000";
                }
                else if (AN.length() == 2) {
                    nol = "00";
                } else if (AN.length() == 3) {
                    nol = "0";
                } else if (AN.length() == 4) {
                    nol = "";
                }
                txtID.setText("OBT" + nol + AN);
                txtID.requestFocus();

            } else {
                txtID.setText("OBT0001");
                txtID.requestFocus();
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Terjadi error pada kode data Obat : " + e1);
        }
    }

    public boolean validasi(String action){
        Boolean[] check = {false,false,false,false,false,false,false,false};
        if(!txtID.getText().isEmpty()){
            check[0]=true;
        }
        if(!txtNama.getText().isEmpty()){
            check[1]=true;
        }
        if(!cmbKategori.getSelectedItem().equals("")){
            check[2]=true;
        }
        if(!txtKandungan.getText().isEmpty()){
            check[3]=true;
        }
        if(!cmbJenis.getSelectedItem().equals("")){
            check[4]=true;
        }
        if(!cmbDosis.getSelectedItem().equals("")){
            check[5]=true;
        }
        if(!txtStok.getText().isEmpty()){
            check[6]=true;
        }
        if(!txtHarga.getText().isEmpty()){
            check[7]=true;
        }

        if(action=="tambah"&&check[1]&&check[2]&&check[3]&&check[4]&&check[5]&&check[6]&&check[7]){
            return true;
        }
        if(action=="update"&&check[0]&&check[1]&&check[2]&&check[3]&&check[4]&&check[5]&&check[6]&&check[7]){
            return true;
        }
        if(action=="hapus"&&check[0]){
            return true;
        }
        return false;
    }

    public void FrameConfig() {
        add(mObat1);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        jpCald.add(datechoos);
    }

    public void FrameRadioButton(){
        ButtonGroup grup = new ButtonGroup();
        grup.add(rbID);
        grup.add(rbNama);
    }

    public void tampilKategori(){
        try{
            connection.stat = connection.conn.createStatement();
            String sql = "SELECT id_kategori, nama_kategori FROM mKategori";
            connection.result = connection.stat.executeQuery(sql);

            while (connection.result.next()){
                cmbKategori.addItem(connection.result.getString("nama_kategori"));
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (SQLException ex) {
            System.out.println("Terjadi error saat load data Kategori : " + ex);
        }
    }

    public void addColomn(){
        model.addColumn("ID OBAT");
        model.addColumn("NAMA OBAT");
        model.addColumn("ID KATEGORI");
        model.addColumn("KANDUNGAN");
        model.addColumn("JENIS");
        model.addColumn("DOSIS");
        model.addColumn("TGL EXPIRED");
        model.addColumn("STOK");
        model.addColumn("HARGA");
    }

    public void clearForm() {
        txtID.setText("");
        txtNama.setText("");
        cmbKategori.setSelectedItem(null);
        txtKandungan.setText("");
        cmbJenis.setSelectedItem(null);
        cmbDosis.setSelectedItem(null);
        txtStok.setText("");
        txtHarga.setText("");
    }

    public void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mObat";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[9];
                obj[0] = connection.result.getString("id_obat");
                obj[1] = connection.result.getString("nama_obat");
                obj[2] = connection.result.getString("id_kategori");
                obj[3] = connection.result.getString("kandungan");
                obj[4] = connection.result.getString("jenis");
                obj[5] = connection.result.getString("dosis");
                obj[6] = connection.result.getString("exp_date");
                obj[7] = connection.result.getString("stok");
                obj[8] = connection.result.getString("harga");
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e4){
            System.out.println("Terjadi error saat load data Obat : " + e4);
        }
    }

    public void showbyID(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mObat WHERE id_obat LIKE '%"+txtID1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                Object[] obj = new Object[9];
                obj[0] = connection.result.getString("id_obat");
                obj[1] = connection.result.getString("nama_obat");
                obj[2] = connection.result.getString("id_kategori");
                obj[3] = connection.result.getString("kandungan");
                obj[4] = connection.result.getString("jenis");
                obj[5] = connection.result.getString("dosis");
                obj[6] = connection.result.getString("exp_date");
                obj[7] = connection.result.getString("stok");
                obj[8] = connection.result.getString("harga");
                model.addRow(obj);
            }

            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Obat tidak ditemukan.");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e5) {
            System.out.println("Terjadi error saat load data Obat : " + e5);
        }
    }

    public void showbyNama(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mObat WHERE nama_obat LIKE '%"+txtNama1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()){
                Object[] obj = new Object[9];
                obj[0] = connection.result.getString("id_obat");
                obj[1] = connection.result.getString("nama_obat");
                obj[2] = connection.result.getString("id_kategori");
                obj[3] = connection.result.getString("kandungan");
                obj[4] = connection.result.getString("jenis");
                obj[5] = connection.result.getString("dosis");
                obj[6] = connection.result.getString("exp_date");
                obj[7] = connection.result.getString("stok");
                obj[8] = connection.result.getString("harga");
                model.addRow(obj);
            }

            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Obat tidak ditemukan");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e6) {
            System.out.println("Terjadi error saat load data Obat : " + e6);
        }
    }

    public Obat1() {
        FrameConfig();
        FrameRadioButton();
        autokode();

        model = new DefaultTableModel();
        tbObat.setModel(model);

        addColomn();
        tampilKategori();

        cmbKategori.setSelectedItem(null);
        cmbJenis.setSelectedItem(null);
        cmbDosis.setSelectedItem(null);

        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                id = txtID.getText();
                nama = txtNama.getText();
                kategori = "";
                kandungan = txtKandungan.getText();
                jenis = cmbJenis.getSelectedItem().toString();
                dosis = cmbDosis.getSelectedItem().toString();
                tgl = formatter.format(datechoos.getDate());
                stok = txtStok.getText();
                harga = txtHarga.getText();

                if(validasi("tambah")) {
                    try {
                        connection.stat = connection.conn.createStatement();
                        String sql = "SELECT id_kategori FROM mKategori WHERE nama_kategori = '" + cmbKategori.getSelectedItem() + "'";
                        connection.result = connection.stat.executeQuery(sql);

                        while (connection.result.next()) {
                            kategori = (String) connection.result.getString("id_kategori");
                        }
                        String sql1 = "INSERT INTO mObat VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        connection.pstat = connection.conn.prepareStatement(sql1);
                        connection.pstat.setString(1, id);
                        connection.pstat.setString(2, nama);
                        connection.pstat.setString(3, kategori);
                        connection.pstat.setString(4, kandungan);
                        connection.pstat.setString(5, jenis);
                        connection.pstat.setString(6, dosis);
                        connection.pstat.setString(7, tgl);
                        connection.pstat.setString(8, stok);
                        connection.pstat.setString(9, harga);

                        connection.pstat.executeUpdate();
                        connection.pstat.close();
                        JOptionPane.showMessageDialog(null, "Input data Obat berhasil.");

                        clearForm();

                    } catch (Exception ex1) {
                        System.out.println("Terjadi error pada saat input data Obat : " + ex1);
                    }
                }
            }
        });
        tbObat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = tbObat.getSelectedRow();
                txtID.setText((String) model.getValueAt(i,0));
                txtNama.setText((String) model.getValueAt(i,1));
                cmbKategori.setSelectedItem((String) model.getValueAt(i,2));
                txtKandungan.setText((String) model.getValueAt(i,3));
                cmbJenis.setSelectedItem((String) model.getValueAt(i,4));
                cmbDosis.setSelectedItem((String) model.getValueAt(i,5));
                txtStok.setText((String) model.getValueAt(i,7));
                txtHarga.setText((String) model.getValueAt(i, 8));
            }
        });
        txtID1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                rbID.setEnabled(true);
                rbNama.setEnabled(false);
                txtNama1.setText("");
            }
        });
        txtNama1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                rbNama.setEnabled(true);
                rbID.setEnabled(false);
                txtID1.setText("");
            }
        });
        rbID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showbyID();
            }
        });
        rbNama.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showbyNama();
            }
        });
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtID1.setText("");
                txtNama1.setText("");
                loadData();
                autokode();
            }
        });
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                id = txtID.getText();
                nama = txtNama.getText();
                kategori = "";
                kandungan = txtKandungan.getText();
                jenis = cmbJenis.getSelectedItem().toString();
                dosis = cmbDosis.getSelectedItem().toString();
                tgl = formatter.format(datechoos.getDate());
                stok = txtStok.getText();
                harga = txtHarga.getText();

                if(validasi("update")) {
                    try {
                        connection.stat = connection.conn.createStatement();
                        String sql = "SELECT id_kategori FROM mKategori WHERE nama_kategori = '" + cmbKategori.getSelectedItem() + "'";
                        connection.result = connection.stat.executeQuery(sql);

                        while (connection.result.next()) {
                            kategori = (String) connection.result.getString("id_kategori");
                        }

                        String sql2 = "UPDATE mObat SET nama_obat=?, id_kategori=?, kandungan=?, jenis=?, dosis=?, exp_date=?, stok=?, harga=? WHERE id_obat=?";
                        connection.pstat = connection.conn.prepareStatement(sql2);
                        connection.pstat.setString(1, nama);
                        connection.pstat.setString(2, kategori);
                        connection.pstat.setString(3, kandungan);
                        connection.pstat.setString(4, jenis);
                        connection.pstat.setString(5, dosis);
                        connection.pstat.setString(6, tgl);
                        connection.pstat.setString(7, stok);
                        connection.pstat.setString(8, harga);
                        connection.pstat.setString(9, id);

                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Ubah data Obat berhasil.");

                        clearForm();
                    }
                    catch (Exception e2) {
                        System.out.println("Terjadi error pada saat ubah data Obat : " + e2);
                    }
                }
            }
        });
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = txtID.getText();
                int opsi = JOptionPane.showConfirmDialog(null, "Yakin data akan dihapus?");
                if (validasi("hapus") && opsi == 0) {
                    try {
                        String sql3 = "DELETE FROM mObat WHERE id_obat=?";
                        connection.pstat = connection.conn.prepareStatement(sql3);
                        connection.pstat.setString(1, id);

                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Hapus data Obat berhasil.");

                        clearForm();
                    }
                    catch (Exception e3) {
                        System.out.println("Terjadi error saat hapus data Obat : " + e3);
                    }
                }
            }
        });
        btnBatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Kasir.DashKasir dbKasir = new Kasir.DashKasir();
                dbKasir.setVisible(true);
            }
        });
        cmbKategori.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //tampilKategori();
            }
        });
    }
}
