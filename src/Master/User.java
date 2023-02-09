package Master;

import Config.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;

public class User extends JFrame {
    private JPanel mUser;
    private JTextField txtID;
    private JTextField txtNama;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox cmbJabatan;
    private JTextField txtTelepon;
    private JButton btnSimpan;
    private JButton btnBatal;
    private JCheckBox cbPasword;
    private JButton btnHapus;
    private JButton btnUbah;
    private JTable tbUser;
    private JRadioButton rbID;
    private JRadioButton rbNama;
    private JTextField txtID1;
    private JTextField txtNama1;
    private JButton btnRefresh;
    private JTextField txtStatus;
    private JButton btnKembali;
    private JPasswordField txtPassword2;
    private JRadioButton rbAktif;
    private JRadioButton rbTidak;
    private DefaultTableModel model;

    DBConnect connection = new DBConnect();
    String id, status;

    public void autokode() {

        try {
            String sql = "SELECT * FROM mUser ORDER BY id_user desc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            if (connection.result.next()) {
                id = connection.result.getString("id_user").substring(4);
                String AN = "" + (Integer.parseInt(id) + 1);
                String nol = "";

                if (AN.length() == 1) {
                    nol = "00";
                } else if (AN.length() == 2) {
                    nol = "0";
                } else if (AN.length() == 3) {
                    nol = "";
                }
                txtID.setText("USR" + nol + AN);
                txtID.requestFocus();

            } else {
                txtID.setText("USR001");
                txtID.requestFocus();
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Terjadi error pada kode data User : " + e1);
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
        if(!txtUsername.getText().isEmpty()){
            check[2]=true;
        }
        if(!txtPassword.getText().isEmpty()){
            check[3]=true;
        }
        if(!txtPassword2.getText().isEmpty()){
            check[4]=true;
        }
        if(!cmbJabatan.getSelectedItem().equals("")){
            check[5]=true;
        }
        if(!txtTelepon.getText().isEmpty()){
            check[6]=true;
        }
        if(rbAktif.isSelected() || rbTidak.isSelected()){
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
        add(mUser);
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

    public void FrameRadioButton1(){
        ButtonGroup grup = new ButtonGroup();
        grup.add(rbAktif);
        grup.add(rbTidak);
    }

    public void clearForm() {
        txtNama.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtPassword2.setText("");
        cmbJabatan.setSelectedItem(null);
        txtTelepon.setText("");
        //txtStatus.setText("");
    }

    public void addColomn(){
        model.addColumn("ID USER");
        model.addColumn("NAMA LENGKAP");
        model.addColumn("USERNAME");
        model.addColumn("PASSWORD");
        model.addColumn("JABATAN");
        model.addColumn("NO TELEPON");
        model.addColumn("STATUS");
    }

    public User() {
        FrameConfig();
        autokode();

        model = new DefaultTableModel();
        tbUser.setModel(model);

        FrameRadioButton();
        FrameRadioButton1();
        addColomn();

        cmbJabatan.setSelectedItem(null);

        cbPasword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbPasword.isSelected() == true)
                {
                    txtPassword.setEchoChar('\0');
                    txtPassword2.setEchoChar('\0');
                }else {
                    txtPassword.setEchoChar('*');
                    txtPassword2.setEchoChar('*');
                }
            }
        });
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(validasi("tambah")) {
                    if (rbAktif.isSelected()) {
                        status = rbAktif.getText();
                        try{

                            String query = "INSERT INTO mUser VALUES (?,?,?,?,?,?,?)";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, txtID.getText());
                            connection.pstat.setString(2, txtNama.getText());
                            connection.pstat.setString(3, txtUsername.getText());
                            connection.pstat.setString(4, txtPassword.getText());
                            connection.pstat.setString(5, cmbJabatan.getSelectedItem().toString());
                            connection.pstat.setString(6, txtTelepon.getText());
                            connection.pstat.setString(7, status);

                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Input data User berhasil.");

                            clearForm();
                        }
                        catch (Exception e1){
                            System.out.println("Terjadi error pada saat input data User :" +e1);
                        }
                    }
                    else if (rbTidak.isSelected()) {
                        status = rbTidak.getText();
                        try{

                            String query = "INSERT INTO mUser VALUES (?,?,?,?,?,?,?)";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, txtID.getText());
                            connection.pstat.setString(2, txtNama.getText());
                            connection.pstat.setString(3, txtUsername.getText());
                            connection.pstat.setString(4, txtPassword.getText());
                            connection.pstat.setString(5, cmbJabatan.getSelectedItem().toString());
                            connection.pstat.setString(6, txtTelepon.getText());
                            connection.pstat.setString(7, status);

                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Input data User berhasil.");

                            clearForm();
                        }
                        catch (Exception e1){
                            System.out.println("Terjadi error pada saat input data User :" +e1);
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
        tbUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = tbUser.getSelectedRow();
                txtID.setText((String) model.getValueAt(i,0));
                txtNama.setText((String) model.getValueAt(i,1));
                txtUsername.setText((String) model.getValueAt(i,2));
                txtPassword.setText((String) model.getValueAt(i,3));
                txtPassword2.setText((String) model.getValueAt(i, 3));
                cmbJabatan.setSelectedItem((String) model.getValueAt(i,4));
                txtTelepon.setText((String) model.getValueAt(i, 5));
                String status = (String) model.getValueAt(i, 6);
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

                if(validasi("update")) {
                    if (rbAktif.isSelected()) {
                        status = rbAktif.getText();
                        try {
                            String sql2 = "UPDATE mUser SET nama_user=?, username=?, password=?, jabatan=?, telp_user=?, status_user=? WHERE id_user=?";
                            connection.pstat = connection.conn.prepareStatement(sql2);
                            connection.pstat.setString(1, txtNama.getText());
                            connection.pstat.setString(2, txtUsername.getText());
                            connection.pstat.setString(3, txtPassword.getText());
                            connection.pstat.setString(4, cmbJabatan.getSelectedItem().toString());
                            connection.pstat.setString(5, txtTelepon.getText());
                            connection.pstat.setString(6, status);
                            connection.pstat.setString(7, txtID.getText());

                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Ubah data User berhasil.");

                            clearForm();
                        }
                        catch (Exception e2) {
                            System.out.println("Terjadi error pada saat ubah data User : " + e2);
                        }
                    }
                    else if (rbTidak.isSelected()) {
                        status = rbTidak.getText();
                        try {
                            String sql2 = "UPDATE mUser SET nama_user=?, username=?, password=?, jabatan=?, telp_user=?, status_user=? WHERE id_user=?";
                            connection.pstat = connection.conn.prepareStatement(sql2);
                            connection.pstat.setString(1, txtNama.getText());
                            connection.pstat.setString(2, txtUsername.getText());
                            connection.pstat.setString(3, txtPassword.getText());
                            connection.pstat.setString(4, cmbJabatan.getSelectedItem().toString());
                            connection.pstat.setString(5, txtTelepon.getText());
                            connection.pstat.setString(6, status);
                            connection.pstat.setString(7, txtID.getText());

                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                            JOptionPane.showMessageDialog(null, "Ubah data User berhasil.");

                            clearForm();
                        }
                        catch (Exception e2) {
                            System.out.println("Terjadi error pada saat ubah data User : " + e2);
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "User tidak ditemukan!");
                }
            }
        });
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opsi = JOptionPane.showConfirmDialog(null, "Yakin data akan dihapus?");
                if (validasi("hapus") && opsi == 0) {
                    try {
                        String sql3 = "DELETE FROM mUser WHERE id_user=?";
                        connection.pstat = connection.conn.prepareStatement(sql3);
                        connection.pstat.setString(1, txtID.getText());

                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Hapus data User berhasil.");

                        clearForm();
                    }
                    catch (Exception e3) {
                        System.out.println("Terjadi error saat hapus data User : " + e3);
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
        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(txtUsername.getText().length() >= 20){
                    e.consume();
                }
            }
        });
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(txtPassword.getText().length() >= 20){
                    e.consume();
                }
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
            String query = "SELECT * FROM mUser";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[7];
                obj[0] = connection.result.getString("id_user");
                obj[1] = connection.result.getString("nama_user");
                obj[2] = connection.result.getString("username");
                obj[3] = connection.result.getString("password");
                obj[4] = connection.result.getString("jabatan");
                obj[5] = connection.result.getString("telp_user");
                obj[6] = connection.result.getString("status_user");
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e4){
            System.out.println("Terjadi error saat load data User: " + e4);
        }
    }

    public void showbyID(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mUser WHERE id_user LIKE '%"+txtID1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                Object[] obj = new Object[7];
                obj[0] = connection.result.getString("id_user");
                obj[1] = connection.result.getString("nama_user");
                obj[2] = connection.result.getString("username");
                obj[3] = connection.result.getString("password");
                obj[4] = connection.result.getString("jabatan");
                obj[5] = connection.result.getString("telp_user");
                obj[6] = connection.result.getString("status_user");

                model.addRow(obj);
            }

            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data User tidak ditemukan.");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e5) {
            System.out.println("Terjadi error saat load data User : " + e5);
        }
    }

    public void showbyNama(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mUser WHERE nama_user LIKE '%"+txtNama1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[7];
                obj[0] = connection.result.getString("id_user");
                obj[1] = connection.result.getString("nama_user");
                obj[2] = connection.result.getString("username");
                obj[3] = connection.result.getString("password");
                obj[4] = connection.result.getString("jabatan");
                obj[5] = connection.result.getString("telp_user");
                obj[6] = connection.result.getString("status_user");
                model.addRow(obj);
            }
            //jika judul tidak ada pada tabel
            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data User tidak ditemukan");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e6) {
            System.out.println("Terjadi error saat load data User : " + e6);
        }
    }
}
