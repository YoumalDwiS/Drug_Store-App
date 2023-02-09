package Master;

import Config.DBConnect;
import com.toedter.calendar.JDateChooser;
//import org.omg.IOP.TAG_MULTIPLE_COMPONENTS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Resep extends JFrame {
    private JPanel mResep;
    private JTextField txtID;
    private JTextField txtDokter;
    private JTextField txtPraktek;
    private JComboBox cmbNama;
    private JButton btnSimpan;
    private JButton btnBatal;
    private JButton btnHapus;
    private JButton btnUbah;
    private JPanel jpCald;
    private JTable tbResep;
    private JRadioButton rbID;
    private JRadioButton rbNama;
    private JTextField txtID1;
    private JTextField txtNama1;
    private JButton btnRefresh;
    private JTextArea txtDesc;
    private JTextField txtStatus;
    private JButton btnKembali;
    private DefaultTableModel model;

    DBConnect connection = new DBConnect();
    JDateChooser datechoos = new JDateChooser();
    String id, nama_customer, nama_dokter, praktek, desc, tgl, status;

    public void autokode() {

        try {
            String sql = "SELECT * FROM mResep ORDER BY id_resep desc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            if (connection.result.next()) {
                id = connection.result.getString("id_resep").substring(4);
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
                txtID.setText("RSP" + nol + AN);
                txtID.requestFocus();

            } else {
                txtID.setText("RSP0001");
                txtID.requestFocus();
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Terjadi error pada kode data Resep : " + e1);
        }
    }

    public boolean validasi(String action){
        Boolean[] check = {false,false,false,false,false,false};
        if(!txtID.getText().isEmpty()){
            check[0]=true;
        }
        /*if(!cmbNama.getSelectedItem().equals("")){
            check[1]=true;
        }*/
        if(!txtDokter.getText().isEmpty()){
            check[1]=true;
        }
        if(!txtPraktek.getText().isEmpty()){
            check[2]=true;
        }
        if(!txtDesc.getText().isEmpty()){
            check[3]=true;
        }

        if(action=="tambah"&&check[1]&&check[2]&&check[3]){
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

    public void FrameConfig() {
        add(mResep);
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

    public void tampilCustomer(){
        try {
            connection.stat = connection.conn.createStatement();
            String sql = "SELECT id_customer, nama_customer FROM mCustomer";
            connection.result = connection.stat.executeQuery(sql);

            while (connection.result.next()){
                cmbNama.addItem(connection.result.getString("nama_customer"));
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (SQLException ex) {
            System.out.println("Terjadi error saat load data Customer : " + ex);
        }
    }

    public void addColomn(){
        model.addColumn("ID RESEP");
        model.addColumn("ID CUSTOMER");
        model.addColumn("NAMA DOKTER");
        model.addColumn("TEMPAT PRAKTEK");
        model.addColumn("KANDUNGAN");
        model.addColumn("TGL INPUT");
    }

    public void clearForm() {
        txtID.setText("");
        cmbNama.setSelectedItem(null);
        txtDokter.setText("");
        txtPraktek.setText("");
        txtDesc.setText("");
    }

    public void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mResep";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[6];
                obj[0] = connection.result.getString("id_resep");
                obj[1] = connection.result.getString("id_customer");
                obj[2] = connection.result.getString("nama_dokter");
                obj[3] = connection.result.getString("praktek");
                obj[4] = connection.result.getString("kandungan");
                obj[5] = connection.result.getString("tgl_input");
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e4){
            System.out.println("Terjadi error saat load data Resep : " + e4);
        }
    }

    public void showbyID(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mResep WHERE id_resep LIKE '%"+txtID1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                Object[] obj = new Object[6];
                obj[0] = connection.result.getString("id_resep");
                obj[1] = connection.result.getString("id_customer");
                obj[2] = connection.result.getString("nama_dokter");
                obj[3] = connection.result.getString("praktek");
                obj[4] = connection.result.getString("kandungan");
                obj[5] = connection.result.getString("tgl_input");
                model.addRow(obj);
            }

            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Resep tidak ditemukan.");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e5) {
            System.out.println("Terjadi error saat load data Resep : " + e5);
        }
    }

    public void showbyNama(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mResep WHERE nama_dokter LIKE '%"+txtNama1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()){
                Object[] obj = new Object[6];
                obj[0] = connection.result.getString("id_resep");
                obj[1] = connection.result.getString("id_customer");
                obj[2] = connection.result.getString("nama_dokter");
                obj[3] = connection.result.getString("praktek");
                obj[4] = connection.result.getString("kandungan");
                obj[5] = connection.result.getString("tgl_input");
                model.addRow(obj);;
            }

            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Resep tidak ditemukan");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e6) {
            System.out.println("Terjadi error saat load data Resep : " + e6);
        }
    }

    public Resep() {
        FrameConfig();
        FrameRadioButton();
        autokode();

        model = new DefaultTableModel();
        tbResep.setModel(model);

        addColomn();
        tampilCustomer();
        cmbNama.setSelectedItem(null);

        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                id = txtID.getText();
                nama_customer = "";
                nama_dokter = txtDokter.getText();
                praktek = txtPraktek.getText();
                desc = txtDesc.getText();
                tgl = formatter.format(datechoos.getDate());

                if(validasi("tambah")) {
                    try {
                        connection.stat = connection.conn.createStatement();
                        String sql = "SELECT id_customer FROM mCustomer WHERE nama_customer = '" + cmbNama.getSelectedItem() + "'";
                        connection.result = connection.stat.executeQuery(sql);

                        while (connection.result.next()) {
                            nama_customer = (String) connection.result.getString("id_customer");
                        }
                        String sql1 = "INSERT INTO mResep VALUES (?, ?, ?, ?, ?, ?)";
                        connection.pstat = connection.conn.prepareStatement(sql1);
                        connection.pstat.setString(1, id);
                        connection.pstat.setString(2, nama_customer);
                        connection.pstat.setString(3, nama_dokter);
                        connection.pstat.setString(4, praktek);
                        connection.pstat.setString(5, desc);
                        connection.pstat.setString(6, tgl);

                        connection.pstat.executeUpdate();
                        connection.pstat.close();
                        JOptionPane.showMessageDialog(null, "Input data Resep berhasil.");

                        clearForm();

                    } catch (Exception ex1) {
                        System.out.println("Terjadi error pada saat input data Resep : " + ex1);
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
        tbResep.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = tbResep.getSelectedRow();
                txtID.setText((String) model.getValueAt(i,0));
                cmbNama.setSelectedItem((String) model.getValueAt(i,1));
                txtDokter.setText((String) model.getValueAt(i,2));
                txtPraktek.setText((String) model.getValueAt(i,3));
                txtDesc.setText((String) model.getValueAt(i, 4));
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
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                id = txtID.getText();
                nama_customer = "";
                nama_dokter = txtDokter.getText();
                praktek = txtPraktek.getText();
                desc = txtDesc.getText();
                tgl = formatter.format(datechoos.getDate());

                if(validasi("update")) {
                    try {
                        connection.stat = connection.conn.createStatement();
                        String sql = "SELECT id_customer FROM mCustomer WHERE nama_customer = '" + cmbNama.getSelectedItem() + "'";
                        connection.result = connection.stat.executeQuery(sql);

                        while (connection.result.next()) {
                            nama_customer = (String) connection.result.getString("id_customer");
                        }

                        String sql2 = "UPDATE mResep SET id_customer=?, nama_dokter=?, praktek=?, kandungan=?, tgl_input=? WHERE id_resep=?";
                        connection.pstat = connection.conn.prepareStatement(sql2);
                        connection.pstat.setString(1, nama_customer);
                        connection.pstat.setString(2, nama_dokter);
                        connection.pstat.setString(3, praktek);
                        connection.pstat.setString(4, desc);
                        connection.pstat.setString(5, tgl);
                        connection.pstat.setString(6, id);

                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Ubah data Resep berhasil.");

                        clearForm();
                    }
                    catch (Exception e2) {
                        System.out.println("Terjadi error pada saat ubah data Resep : " + e2);
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
                        String sql3 = "DELETE FROM mResep WHERE id_resep=?";
                        connection.pstat = connection.conn.prepareStatement(sql3);
                        connection.pstat.setString(1, id);

                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Hapus data Resep berhasil.");

                        clearForm();
                    }
                    catch (Exception e3) {
                        System.out.println("Terjadi error saat hapus data Resep : " + e3);
                    }
                }
            }
        });
        cmbNama.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //tampilCustomer();
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
}
