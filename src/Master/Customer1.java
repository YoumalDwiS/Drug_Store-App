package Master;

import Config.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class Customer1 extends JFrame {
    private JPanel mCustomer1;
    private JTextField txtID;
    private JTextField txtNama;
    private JTextField txtTelepon;
    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBatal;
    private JTable tbCustomer;
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
    String id, nama, telepon, status;

    public void autokode() {

        try {
            String sql = "SELECT * FROM mCustomer ORDER BY id_customer desc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            if (connection.result.next()) {
                id = connection.result.getString("id_customer").substring(4);
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
                txtID.setText("CUS" + nol + AN);
                txtID.requestFocus();

            } else {
                txtID.setText("CUS0001");
                txtID.requestFocus();
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Terjadi error pada kode data User : " + e1);
        }
    }

    public boolean validasi(String action) {
        Boolean[] check = {false, false, false, false};
        if (!txtID.getText().isEmpty()) {
            check[0] = true;
        }
        if (!txtNama.getText().isEmpty()) {
            check[1] = true;
        }
        if (!txtTelepon.getText().isEmpty()) {
            check[2] = true;
        }
        if(rbAktif.isSelected() || rbTidak.isSelected()){
            check[3]=true;
        }

        if (action == "tambah" && check[1] && check[2] && check[3]) {
            return true;
        }
        if(action=="update"&&check[0]&&check[1]&&check[2]&&check[3]){
            return true;
        }
        if(action=="hapus"&&check[0]){
            return true;
        }
        return false;
    }

    public void FrameRadioButton(){
        ButtonGroup grup = new ButtonGroup();
        grup.add(rbID);
        grup.add(rbNama);
    }

    public void FrameRadioButton1(){
        ButtonGroup grup = new ButtonGroup();
        grup.add(rbAktif);
        grup.add(rbTidak);
    }

    public void FrameConfig() {
        add(mCustomer1);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void clearForm() {
        txtID.setText("");
        txtNama.setText("");
        txtTelepon.setText("");
        txtStatus.setText("");
    }

    public void addColomn(){
        model.addColumn("ID CUSTOMER");
        model.addColumn("NAMA LENGKAP");
        model.addColumn("NO TELEPON");
        model.addColumn("STATUS");
    }

    public Customer1() {
        FrameConfig();
        autokode();

        model = new DefaultTableModel();
        tbCustomer.setModel(model);

        FrameRadioButton();
        FrameRadioButton1();
        addColomn();

        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = txtID.getText();
                nama = txtNama.getText();
                telepon = txtTelepon.getText();

                if(validasi("tambah")) {
                    if (rbAktif.isSelected()) {
                        status = rbAktif.getText();
                        try {
                            String sql1 = "INSERT INTO mCustomer VALUES (?,?,?,?)";
                            connection.pstat = connection.conn.prepareStatement(sql1);
                            connection.pstat.setString(1, id);
                            connection.pstat.setString(2, nama);
                            connection.pstat.setString(3, telepon);
                            connection.pstat.setString(4, status);
                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Input data Customer berhasil.");

                            clearForm();
                        }
                        catch (Exception e1) {
                            System.out.println("Terjadi error pada saat input data Customer : " + e1);
                        }
                    }
                    else if (rbTidak.isSelected()) {
                        status = rbTidak.getText();
                        try {
                            String sql1 = "INSERT INTO mCustomer VALUES (?,?,?,?)";
                            connection.pstat = connection.conn.prepareStatement(sql1);
                            connection.pstat.setString(1, id);
                            connection.pstat.setString(2, nama);
                            connection.pstat.setString(3, telepon);
                            connection.pstat.setString(4, status);
                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Input data Customer berhasil.");

                            clearForm();
                        }
                        catch (Exception e1) {
                            System.out.println("Terjadi error pada saat input data Customer : " + e1);
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Lengkapi data Customer!.");
                }
            }
        });
        btnBatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        tbCustomer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = tbCustomer.getSelectedRow();
                txtID.setText((String) model.getValueAt(i, 0));
                txtNama.setText((String) model.getValueAt(i, 1));
                txtTelepon.setText((String) model.getValueAt(i, 2));
                String status = (String) model.getValueAt(i, 3);
                if (status == "Aktif") {
                    rbAktif.isSelected();
                }
                else if (status == "Tidak Aktif") {
                    rbTidak.isSelected();
                }
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
                id = txtID.getText();
                nama = txtNama.getText();
                telepon = txtTelepon.getText();

                if(validasi("update")) {
                    if (rbAktif.isSelected()) {
                        status = rbAktif.getText();
                        try {
                            String sql2 = "UPDATE mCustomer SET nama_customer=?, telp_customer=?, status_customer=? WHERE id_customer=?";
                            connection.pstat = connection.conn.prepareStatement(sql2);
                            connection.pstat.setString(1, nama);
                            connection.pstat.setString(2, telepon);
                            connection.pstat.setString(3, status);
                            connection.pstat.setString(4, id);

                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Ubah data Customer berhasil.");

                            clearForm();
                        } catch (Exception e2) {
                            System.out.println("Terjadi error pada saat ubah data Customer : " + e2);
                        }
                    } else if (rbTidak.isSelected()) {
                        status = rbTidak.getText();
                        try {
                            String sql2 = "UPDATE mCustomer SET nama_customer=?, telp_customer=?, status_customer=? WHERE id_customer=?";
                            connection.pstat = connection.conn.prepareStatement(sql2);
                            connection.pstat.setString(1, nama);
                            connection.pstat.setString(2, telepon);
                            connection.pstat.setString(3, status);
                            connection.pstat.setString(4, id);

                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Ubah data Customer berhasil.");

                            clearForm();
                        } catch (Exception e2) {
                            System.out.println("Terjadi error pada saat ubah data Customer : " + e2);
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
                        String sql3 = "UPDATE mCustomer SET status_customer=? WHERE id_customer=?";
                        connection.pstat = connection.conn.prepareStatement(sql3);
                        connection.pstat.setString(1,"Tidak Aktif");
                        connection.pstat.setString(2, id);

                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Hapus data Customer berhasil.");

                        clearForm();
                    }
                    catch (Exception e3) {
                        System.out.println("Terjadi error saat hapus data Customer : " + e3);
                    }
                }
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
    }

    public void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mCustomer";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[4];
                obj[0] = connection.result.getString("id_customer");
                obj[1] = connection.result.getString("nama_customer");
                obj[2] = connection.result.getString("telp_customer");
                obj[3] = connection.result.getString("status_customer");
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e4){
            System.out.println("Terjadi error saat load data Customer: " + e4);
        }
    }

    public void showbyID(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mCustomer WHERE id_customer LIKE '%"+txtID1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                Object[] obj = new Object[4];
                obj[0] = connection.result.getString("id_customer");
                obj[1] = connection.result.getString("nama_customer");
                obj[2] = connection.result.getString("telp_customer");
                obj[3] = connection.result.getString("status_customer");
                model.addRow(obj);
            }

            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Customer tidak ditemukan.");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e5) {
            System.out.println("Terjadi error saat load data Customer : " + e5);
        }
    }

    public void showbyNama(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mCustomer WHERE nama_customer LIKE '%"+txtNama1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[4];
                obj[0] = connection.result.getString("id_customer");
                obj[1] = connection.result.getString("nama_customer");
                obj[2] = connection.result.getString("telp_customer");
                obj[3] = connection.result.getString("status_customer");
                model.addRow(obj);
            }
            //jika judul tidak ada pada tabel
            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Customer tidak ditemukan");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e6) {
            System.out.println("Terjadi error saat load data Customer : " + e6);
        }
    }
}
