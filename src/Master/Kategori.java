package Master;

import Config.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class Kategori extends JFrame {
    private JPanel mKategori;
    private JTextField txtID;
    private JTextField txtNama;
    private JTextArea txtKeterangan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBatal;
    private JButton btnSimpan;
    private JTable tbKategori;
    private JButton btnRefresh;
    private JRadioButton rbID;
    private JRadioButton rbNama;
    private JTextField txtID1;
    private JTextField txtNama1;
    private JButton btnKembali;
    private DefaultTableModel model;

    DBConnect connection = new DBConnect();
    String id, nama, keterangan;

    public void autokode() {

        try {
            String sql = "SELECT * FROM mKategori ORDER BY id_kategori desc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            if (connection.result.next()) {
                id = connection.result.getString("id_kategori").substring(4);
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
                txtID.setText("KTG" + nol + AN);
                txtID.requestFocus();

            } else {
                txtID.setText("KTG0001");
                txtID.requestFocus();
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Terjadi error pada kode data Kategori Obat : " + e1);
        }
    }

    public boolean validasi(String action){
        Boolean[] check = {false,false,false};
        if(!txtID.getText().isEmpty()){
            check[0]=true;
        }
        if(!txtNama.getText().isEmpty()){
            check[1]=true;
        }
        if(!txtKeterangan.getText().isEmpty()){
            check[2]=true;
        }

        if(action=="tambah"&&check[1]&&check[2]){
            return true;
        }
        if(action=="update"&&check[0]&&check[1]&&check[2]){
            return true;
        }
        if(action=="hapus"&&check[0]){
            return true;
        }
        return false;
    }

    public void FrameConfig() {
        add(mKategori);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void FrameRadioButton(){
        ButtonGroup grup = new ButtonGroup();
        grup.add(rbID);
        grup.add(rbNama);
    }

    public void clearForm() {
        txtID.setText("");
        txtNama.setText("");
        txtKeterangan.setText("");
    }

    public Kategori() {
        FrameConfig();
        FrameRadioButton();
        autokode();
        model = new DefaultTableModel();
        tbKategori.setModel(model);
        addColomn();

        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = txtID.getText();
                nama = txtNama.getText();
                keterangan = txtKeterangan.getText();

                if(validasi("tambah")) {
                    try {
                        String sql1 = "INSERT INTO mKategori VALUES (?,?,?)";
                        connection.pstat = connection.conn.prepareStatement(sql1);
                        connection.pstat.setString(1, id);
                        connection.pstat.setString(2, nama);
                        connection.pstat.setString(3, keterangan);
                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Input data Kategori Obat berhasil.");

                        clearForm();
                    }
                    catch (Exception e1) {
                        System.out.println("Terjadi error pada saat input data Kategori Obat : " + e1);
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
        tbKategori.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = tbKategori.getSelectedRow();
                if(i == 1) {
                    return;
                }
                txtID.setText((String) model.getValueAt(i, 0));
                txtNama.setText((String) model.getValueAt(i, 1));
                txtKeterangan.setText((String) model.getValueAt(i, 2));
            }
        });
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = txtID.getText();
                nama = txtNama.getText();
                keterangan = txtKeterangan.getText();

                if(validasi("update")) {
                    try {
                        String sql2 = "UPDATE mKategori SET nama_kategori=?, keterangan=? WHERE id_kategori=?";
                        connection.pstat = connection.conn.prepareStatement(sql2);
                        connection.pstat.setString(1, nama);
                        connection.pstat.setString(2, keterangan);
                        connection.pstat.setString(3, id);

                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Ubah data Kategori Obat berhasil.");

                        clearForm();
                    }
                    catch (Exception e2) {
                        System.out.println("Terjadi error pada saat ubah data Kategori Obat : " + e2);
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
                        String sql3 = "DELETE FROM mKategori WHERE id_kategori=?";
                        connection.pstat = connection.conn.prepareStatement(sql3);
                        connection.pstat.setString(1, id);

                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Hapus data Kategori Obat berhasil.");

                        clearForm();
                    }
                    catch (Exception e3) {
                        System.out.println("Terjadi error saat hapus data Kategori Obat : " + e3);
                    }
                }
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
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Admin.DashAdmin dbAdmin = new Admin.DashAdmin();
                dbAdmin.setVisible(true);
            }
        });
    }

    public void addColomn(){
        model.addColumn("ID KATEGORI");
        model.addColumn("NAMA KATEGORI");
        model.addColumn("KETERANGAN");
    }

    public void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mKategori";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[3];
                obj[0] = connection.result.getString("id_kategori");
                obj[1] = connection.result.getString("nama_kategori");
                obj[2] = connection.result.getString("keterangan");
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e4){
            System.out.println("Terjadi error saat load data Kategori Obat: " + e4);
        }
    }

    public void showbyID(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mKategori WHERE id_kategori LIKE '%"+txtID1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                Object[] obj = new Object[3];
                obj[0] = connection.result.getString("id_kategori");
                obj[1] = connection.result.getString("nama_kategori");
                obj[2] = connection.result.getString("keterangan");
                model.addRow(obj);
            }

            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Kategori Obat tidak ditemukan.");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e5) {
            System.out.println("Terjadi error saat load data Kategori Obat : " + e5);
        }
    }

    public void showbyNama(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mKategori WHERE nama_kategori LIKE '%"+txtNama1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[3];
                obj[0] = connection.result.getString("id_kategori");
                obj[1] = connection.result.getString("nama_kategori");
                obj[2] = connection.result.getString("keterangan");
                model.addRow(obj);
            }
            //jika judul tidak ada pada tabel
            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Kategori Obat tidak ditemukan");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e6) {
            System.out.println("Terjadi error saat load data Kategori Obat : " + e6);
        }
    }
}
