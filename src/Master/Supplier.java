package Master;

import Config.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class Supplier extends JFrame {
    private JPanel mSupplier;
    private JTextField txtID;
    private JTextField txtNama;
    private JTextField txtTelepon;
    private JTextArea txtAlamat;
    private JButton btnSimpan;
    private JButton btnBatal;
    private JButton btnHapus;
    private JButton btnUbah;
    private JTable tbSupplier;
    private JRadioButton rbID;
    private JRadioButton rbNama;
    private JTextField txtID1;
    private JTextField txtNama1;
    private JButton btnRefresh;
    private JTextField txtStatus;
    private JButton btnKembali;
    private JRadioButton rbAktif;
    private JRadioButton rbTidak;
    private DefaultTableModel model;

    DBConnect connection = new DBConnect();
    String id, nama, telepon, alamat, status;

    public void autokode() {

        try {
            String sql = "SELECT * FROM mSupplier ORDER BY id_supplier desc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            if (connection.result.next()) {
                id = connection.result.getString("id_supplier").substring(4);
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
                txtID.setText("SUP" + nol + AN);
                txtID.requestFocus();

            } else {
                txtID.setText("SUP0001");
                txtID.requestFocus();
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Terjadi error pada kode data Supplier : " + e1);
        }
    }

    public boolean validasi(String action){
        Boolean[] check = {false,false,false,false,false};
        if(!txtID.getText().isEmpty()){
            check[0]=true;
        }
        if(!txtNama.getText().isEmpty()){
            check[1]=true;
        }
        if(!txtTelepon.getText().isEmpty()){
            check[2]=true;
        }
        if(!txtAlamat.getText().isEmpty()){
            check[3]=true;
        }
        if(rbAktif.isSelected() || rbTidak.isSelected()){
            check[4]=true;
        }

        if(action=="tambah"&&check[1]&&check[2]&&check[3]&&check[4]){
            return true;
        }
        if(action=="update"&&check[0]&&check[1]&&check[2]&&check[3]&&check[4]){
            return true;
        }
        if(action=="hapus"&&check[0]){
            return true;
        }
        return false;
    }

    public void FrameRadioButton1(){
        ButtonGroup grup = new ButtonGroup();
        grup.add(rbAktif);
        grup.add(rbTidak);
    }

    public void FrameRadioButton(){
        ButtonGroup grup = new ButtonGroup();
        grup.add(rbID);
        grup.add(rbNama);
    }


    public void FrameConfig() {
        add(mSupplier);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void clearForm() {
        txtID.setText("");
        txtNama.setText("");
        txtTelepon.setText("");
        txtAlamat.setText("");
    }

    public void addColomn(){
        model.addColumn("ID SUPPLIER");
        model.addColumn("NAMA SUPPLIER");
        model.addColumn("ALAMAT");
        model.addColumn("NO TELEPON");
        model.addColumn("STATUS");
    }

    public Supplier() {
        FrameConfig();
        autokode();

        FrameRadioButton();
        FrameRadioButton1();
        model = new DefaultTableModel();
        tbSupplier.setModel(model);

        addColomn();

        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = txtID.getText();
                nama = txtNama.getText();
                telepon = txtTelepon.getText();
                alamat = txtAlamat.getText();

                if(validasi("tambah")) {
                    if (rbAktif.isSelected()) {
                        status = rbAktif.getText();
                        try {
                            String sql1 = "INSERT INTO mSupplier VALUES (?,?,?,?,?)";
                            connection.pstat = connection.conn.prepareStatement(sql1);
                            connection.pstat.setString(1, id);
                            connection.pstat.setString(2, nama);
                            connection.pstat.setString(3, telepon);
                            connection.pstat.setString(4, alamat);
                            connection.pstat.setString(5, status);

                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Input data Supplier berhasil.");

                            clearForm();
                        }
                        catch (Exception e1) {
                            System.out.println("Terjadi error pada saat input data Supplier : " + e1);
                        }
                    }
                    else if (rbTidak.isSelected()) {
                        status = rbTidak.getText();
                        try {
                            String sql1 = "INSERT INTO mSupplier VALUES (?,?,?,?,?)";
                            connection.pstat = connection.conn.prepareStatement(sql1);
                            connection.pstat.setString(1, id);
                            connection.pstat.setString(2, nama);
                            connection.pstat.setString(3, telepon);
                            connection.pstat.setString(4, alamat);
                            connection.pstat.setString(5, status);

                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Input data Supplier berhasil.");

                            clearForm();
                        }
                        catch (Exception e1) {
                            System.out.println("Terjadi error pada saat input data Supplier : " + e1);
                        }
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
        tbSupplier.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = tbSupplier.getSelectedRow();
                txtID.setText((String) model.getValueAt(i,0));
                txtNama.setText((String) model.getValueAt(i,1));
                txtTelepon.setText((String) model.getValueAt(i,2));
                txtAlamat.setText((String) model.getValueAt(i,3));
                String status = (String) model.getValueAt(i, 4);
                if (status == "Aktif") {
                    rbAktif.isSelected();
                }
                else if (status == "Tidak Aktif") {
                    rbTidak.isSelected();
                }
            }
        });
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = txtID.getText();
                nama = txtNama.getText();
                telepon = txtTelepon.getText();
                alamat = txtAlamat.getText();

                if (validasi("update")) {
                    if (rbAktif.isSelected()) {
                        status = rbAktif.getText();
                        try {
                            String sql2 = "UPDATE mSupplier SET nama_supplier=?, telp_supplier=?, alamat=?, status_supplier=? WHERE id_supplier=?";
                            connection.pstat = connection.conn.prepareStatement(sql2);
                            connection.pstat.setString(1, nama);
                            connection.pstat.setString(2, telepon);
                            connection.pstat.setString(3, alamat);
                            connection.pstat.setString(4, status);
                            connection.pstat.setString(5, id);

                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Ubah data Supplier berhasil.");

                            clearForm();
                        } catch (Exception e2) {
                            System.out.println("Terjadi error pada saat ubah data Supplier : " + e2);
                        }
                    }
                    else if (rbTidak.isSelected()) {
                        status = rbTidak.getText();
                        try {
                            String sql2 = "UPDATE mSupplier SET nama_supplier=?, telp_supplier=?, alamat=?, status_supplier=? WHERE id_supplier=?";
                            connection.pstat = connection.conn.prepareStatement(sql2);
                            connection.pstat.setString(1, nama);
                            connection.pstat.setString(2, telepon);
                            connection.pstat.setString(3, alamat);
                            connection.pstat.setString(4, status);
                            connection.pstat.setString(5, id);

                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Ubah data Supplier berhasil.");

                            clearForm();
                        } catch (Exception e2) {
                            System.out.println("Terjadi error pada saat ubah data Supplier : " + e2);
                        }
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
                        String sql3 = "UPDATE mSupplier SET status_supplier=? WHERE id_supplier=?";
                        connection.pstat = connection.conn.prepareStatement(sql3);
                        connection.pstat.setString(1,"Tidak Aktif");
                        connection.pstat.setString(2, id);

                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Hapus data Supplier berhasil.");

                        clearForm();
                    }
                    catch (Exception e3) {
                        System.out.println("Terjadi error saat hapus data Supplier : " + e3);
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
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Admin.DashAdmin dbAdmin = new Admin.DashAdmin();
                dbAdmin.setVisible(true);
            }
        });
    }

    public void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mSupplier";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[5];
                obj[0] = connection.result.getString("id_supplier");
                obj[1] = connection.result.getString("nama_supplier");
                obj[2] = connection.result.getString("alamat");
                obj[3] = connection.result.getString("telp_supplier");
                obj[4] = connection.result.getString("status_supplier");
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e4){
            System.out.println("Terjadi error saat load data Supplier: " + e4);
        }
    }

    public void showbyID(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mSupplier WHERE id_supplier LIKE '%"+txtID1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                Object[] obj = new Object[5];
                obj[0] = connection.result.getString("id_supplier");
                obj[1] = connection.result.getString("nama_supplier");
                obj[2] = connection.result.getString("telp_supplier");
                obj[3] = connection.result.getString("alamat");
                obj[4] = connection.result.getString("status_supplier");
                model.addRow(obj);
            }

            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Supplier tidak ditemukan.");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e5) {
            System.out.println("Terjadi error saat load data Supplier : " + e5);
        }
    }

    public void showbyNama(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mSupplier WHERE nama_supplier LIKE '%"+txtNama1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[5];
                obj[0] = connection.result.getString("id_supplier");
                obj[1] = connection.result.getString("nama_supplier");
                obj[2] = connection.result.getString("telp_supplier");
                obj[3] = connection.result.getString("alamat");
                obj[4] = connection.result.getString("status_supplier");
                model.addRow(obj);
            }
            //jika judul tidak ada pada tabel
            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Supplier tidak ditemukan");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e6) {
            System.out.println("Terjadi error saat load data Supplier : " + e6);
        }
    }
}
