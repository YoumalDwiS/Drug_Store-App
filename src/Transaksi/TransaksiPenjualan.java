package Transaksi;

import Config.DBConnect;
import Master.InputCustomer;
import Master.InputResep;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class TransaksiPenjualan extends JFrame {
    private JPanel trPenjualan;
    private JTextField txtID;
    private JComboBox cmbObat;
    private JTable tbObat;
    private JTable tbResep;
    private JButton btnCustomer;
    private JButton btnResep;
    private JTextField txtTotal;
    private JButton btnTambah;
    private JButton btnBatal;
    private JTable tbTransaksi;
    private JButton btnBeli;
    private JButton btnClear;
    private JButton btnRefresh1;
    private JButton btnRefresh2;
    private JTextField txtSubTotal;
    private JPanel jpCald;
    private JTextField txtHarga;
    private JTextField txtJumlah;
    private JRadioButton rbYa;
    private JRadioButton rbTidak;
    private JComboBox cmbResep;
    private JButton btnKembali;
    private JTextField txtID1;
    private JButton btnCari;
    private JTextField txtKandungan;
    private JButton btnCari1;
    private DefaultTableModel model1;
    private DefaultTableModel model2;
    private DefaultTableModel model3;

    DBConnect connection = new DBConnect();
    JDateChooser datechoos = new JDateChooser();

    String IDObat = "", Obat = "";
    int HargaSatuan;
    int harga = 0;
    int jumlah;
    String idResep;
    Integer harga1 = 0;
    String tgl;
    String id;
    Integer hargaobat;

    public void FrameConfig() {
        add(trPenjualan);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        jpCald.add(datechoos);
    }

    public void autokode() {
        try {
            String sql = "SELECT * FROM tbTrPenjualan ORDER BY id_penjualan desc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            if (connection.result.next()) {
                id = connection.result.getString("id_penjualan").substring(4);
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
                txtID.setText("TRJ" + nol + AN);
                txtID.requestFocus();

            } else {
                txtID.setText("TRJ0001");
                txtID.requestFocus();
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Terjadi error pada kode data Transaksi : " + e1);
        }
    }

    public boolean validasi(String action){
        Boolean[] check = {false,false,false,false,false,false,false};
        if(!txtID.getText().isEmpty()){
            check[0]=true;
        }
        if(rbYa.isSelected() || rbTidak.isSelected()){
            check[1]=true;
        }
        if(!cmbResep.getSelectedItem().equals("")){
            check[2]=true;
        }
        if(!cmbObat.getSelectedItem().equals("")){
            check[3]=true;
        }
        if(!txtHarga.getText().isEmpty()){
            check[4]=true;
        }
        if(!txtJumlah.getText().isEmpty()){
            check[5]=true;
        }
        if(!txtTotal.getText().isEmpty()){
            check[6]=true;
        }

        if(action=="tambah"&&check[1]&&check[2]&&check[3]&&check[4]&&check[5]&&check[6]){
            return true;
        }
        return false;
    }

    public void tampilObat(){
        try{
            connection.stat = connection.conn.createStatement();
            String sql = "SELECT id_obat, nama_obat FROM mObat";
            connection.result = connection.stat.executeQuery(sql);

            while (connection.result.next()){
                cmbObat.addItem(connection.result.getString("nama_obat"));
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (SQLException ex) {
            System.out.println("Terjadi error saat load data Obat : " + ex);
        }
    }

    public void tampilResep(){
        try{
            connection.stat = connection.conn.createStatement();
            String sql = "SELECT id_resep FROM mResep";
            connection.result = connection.stat.executeQuery(sql);

            while (connection.result.next()){
                cmbResep.addItem(connection.result.getString("id_resep"));
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (SQLException ex) {
            System.out.println("Terjadi error saat load data Resep : " + ex);
        }
    }
    //Table View Obat
    public void addColomn1(){
        model1 = new DefaultTableModel();
        tbObat.setModel(model1);
        model1.addColumn("ID Obat");
        model1.addColumn("Nama Obat");
        model1.addColumn("Kandungan");
        model1.addColumn("Stok");
        model1.addColumn("Harga");
    }

    //Table View Resep
    public void addColomn2(){
        model2 = new DefaultTableModel();
        tbResep.setModel(model2);
        model2.addColumn("ID Resep ");
        model2.addColumn("ID Customer");
        model2.addColumn("Kandungan");
    }

    public void addColomn3(){
        model3 = new DefaultTableModel();
        tbTransaksi.setModel(model3);
        model3.addColumn("ID Resep");
        model3.addColumn("ID Obat");
        model3.addColumn("Nama Obat ");
        model3.addColumn("Harga Satuan");
        model3.addColumn("Jumlah");
        model3.addColumn("Total Harga");
    }
    public void HitungHarga(){
        int j = tbTransaksi.getModel().getRowCount(); //knowing how many row on tabelBuku dilayar
        harga1 = 0;
        for(int k = 0; k < j; k++) {
            harga = (int) model3.getValueAt(k,3) * (int) model3.getValueAt(k,4);
            harga1 = harga1 + harga;

            txtSubTotal.setText("Rp. " + harga1.toString());
        }
    }

    public void hapusBaris(){
        int x = tbTransaksi.getSelectedRow();
        int j = tbTransaksi.getModel().getRowCount();

        if(x==0) {
            ((DefaultTableModel) tbTransaksi.getModel()).removeRow(x);

            if (x == 0) {
                harga = 0;
                harga1 = 0;

                HitungHarga();
                if (tbTransaksi.getModel().getRowCount() == 0) {
                    txtSubTotal.setText(null);
                }
                return;
            } else {
                txtSubTotal.setText(null);
                return;
            }
        }
        else if(tbTransaksi.getModel().getRowCount() != 0){
            for (int i = 0; i <= j; i++) {
                if (i == j) {
                    i--;
                    ((DefaultTableModel) tbTransaksi.getModel()).removeRow(i);

                    harga = 0; harga1 = 0;

                    HitungHarga();
                    if(tbTransaksi.getModel().getRowCount() == 0){
                        txtSubTotal.setText(null);
                    }
                    //remove(tblRestock, i);
                    return;
                }
            }
        }
        else{
            txtSubTotal.setText(null);
            return;
        }
    }

    public static void main(String[] args) {
        Transaksi.TransaksiPenjualan form = new TransaksiPenjualan();
        form.setVisible(true);
    }

    public void clearForm() {
        txtID.setText("");
        rbYa.isSelected();
        rbTidak.isSelected();
        cmbResep.setSelectedItem(null);
        cmbObat.setSelectedItem(null);
        txtHarga.setText("");
        txtJumlah.setText("");
        txtTotal.setText("");
    }

    public void clearForm1() {
        cmbObat.setSelectedItem(null);
        txtHarga.setText("");
        txtJumlah.setText("");
        txtTotal.setText("");
    }


    public TransaksiPenjualan() {
        FrameConfig();
        tampilResep();
        tampilObat();

        model3 = new DefaultTableModel();
        tbTransaksi.setModel(model3);
        addColomn1();
        addColomn2();
        addColomn3();

        loadData1();
        loadData2();
        autokode();

        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //rb ya
                if(validasi("tambah")) {
                    try {
                        String Jumlah = txtJumlah.getText();
                        String Total = txtTotal.getText();

                        connection.stat = connection.conn.createStatement();
                        String sql = "SELECT * FROM mObat WHERE nama_obat = '" + cmbObat.getSelectedItem() + "'";
                        connection.result = connection.stat.executeQuery(sql);
                        if (rbYa.isSelected()) {
                            while (connection.result.next()) {
                                harga = 0;
                                Object[] obj = new Object[6];
                                obj[0] = cmbResep.getSelectedItem();
                                obj[1] = IDObat;
                                obj[2] = cmbObat.getSelectedItem();
                                obj[3] = HargaSatuan;
                                obj[4] = Integer.parseInt(Jumlah);
                                obj[5] = Integer.parseInt(Total);

                                int j = tbTransaksi.getModel().getRowCount(); //knowing how many row on tabelBuku dilayar

                                if (tbTransaksi.getModel().getRowCount() == 0) {
                                    model3.addRow(obj);
                                } else {
                                    model3.addRow(obj);
                                }

                                for (int i = 0; i < j; i++) {
                                    boolean cek = false;
                                    if (model3.getValueAt(i, 0).equals(IDObat) && cek == false) {
                                        int temp1 = (int) model3.getValueAt(i, 4);
                                        int temp2 = Integer.parseInt(Jumlah);
                                        model3.setValueAt((temp1 + temp2), i, 4);
                                        hapusBaris();
                                        JOptionPane.showMessageDialog(null, "Jumlah obat bertambah");
                                        HitungHarga();
                                        cek = true;
                                        return;
                                    }
                                }
                            }
                        }else if (rbTidak.isSelected()){
                            while (connection.result.next()) {
                                harga = 0;
                                Object[] obj = new Object[6];
                                obj[0] = null;
                                obj[1] = IDObat;
                                obj[2] = cmbObat.getSelectedItem();
                                obj[3] = HargaSatuan;
                                obj[4] = Integer.parseInt(Jumlah);
                                obj[5] = Integer.parseInt(Total);

                                int j = tbTransaksi.getModel().getRowCount(); //knowing how many row on tabelBuku dilayar

                                if (tbTransaksi.getModel().getRowCount() == 0) {
                                    model3.addRow(obj);
                                } else {
                                    model3.addRow(obj);
                                }

                                for (int i = 0; i < j; i++) {
                                    boolean cek = false;
                                    if (model3.getValueAt(i, 1).equals(IDObat) && cek == false) {
                                        int temp1 = (int) model3.getValueAt(i, 4);
                                        int temp2 = Integer.parseInt(Jumlah);
                                        model3.setValueAt((temp1 + temp2), i, 4);
                                        hapusBaris();
                                        JOptionPane.showMessageDialog(null, "Jumlah obat bertambah");
                                        HitungHarga();
                                        cek = true;
                                        return;
                                    }
                                }
                            }

                        }
                        connection.stat.close(); //close connection

                        //JOptionPane.showMessageDialog(null, "Data telah masuk keranjang");

                        txtJumlah.setText(null);
                        cmbObat.setSelectedItem(null);
                        HitungHarga();

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,"Terjadi error saat insert " +ex);
                    }
                }
                txtSubTotal.setEnabled(true);
                btnBeli.setEnabled(true);

            }
        });
        btnBatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
                autokode();
            }
        });
        btnCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputCustomer fm = new InputCustomer();
                fm.setVisible(true);
                dispose();
            }
        });
        btnResep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputResep fm = new InputResep();
                fm.setVisible(true);
                dispose();

            }
        });

        txtJumlah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ttl1 = 0;
                int ttl2 = 0;
                if(txtJumlah.getText().equals(""))
                {
                    return;
                }
                else {
                    //set tanggal keluar
                    jumlah = Integer.parseInt(txtJumlah.getText());
                    txtTotal.setEnabled(false);

                    //set harga sewa
                    try {
                        //mencari kode supplier dari supplier yang ada di tabel, karena yang masuk ke tabel master adalah kode supplier
                        connection.stat = connection.conn.createStatement();
                        String sql = "SELECT harga FROM mObat WHERE nama_obat = '" + cmbObat.getSelectedItem() + "'";
                        connection.result = connection.stat.executeQuery(sql);

                        while (connection.result.next()) {
                            ttl1 = connection.result.getInt("harga");
                            ttl2 = ttl1 * Integer.parseInt(txtJumlah.getText());
                            String ttl = Integer.toString(ttl2);
                            txtTotal.setText((ttl));
                        }
                    } catch (Exception e1) {
                        System.out.println("Terjadi error pada saat load data kost:" + e1);
                    }
                }
            }
        });
        cmbObat.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    txtHarga.setEnabled(false);
                    //mencari kode supplier dari supplier yang ada di tabel, karena yang masuk ke tabel master adalah kode supplier
                    connection.stat = connection.conn.createStatement();
                    String sql = "SELECT * FROM mObat WHERE nama_obat = '" + cmbObat.getSelectedItem() + "'";
                    connection.result = connection.stat.executeQuery(sql);

                    while (connection.result.next()) {
                        IDObat = ((String) connection.result.getString("id_obat"));
                        Obat = ((String) connection.result.getString("nama_obat"));
                        txtHarga.setText(" "+(int) connection.result.getInt("harga"));
                        HargaSatuan = connection.result.getInt("harga");
                    }

                }
                catch (Exception e1){
                    System.out.println("Terjadi error pada saat load data obat:"+e1);
                }
            }

        });

        rbTidak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cmbResep.setEnabled(false);
                cmbResep.removeItem(0);
            }
        });
        btnBeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                tgl = formatter.format(datechoos.getDate());
                int stok = 0, stok2 =0;

                int j = tbTransaksi.getModel().getRowCount(); //knowing how many row on tabelBuku dilayar

                if (rbYa.isSelected()) {

                    try {

                        String sql0 = "SELECT * FROM tbDetailPenjualan WHERE id_penjualan = '" + txtID.getText() + "'";
                        connection.pstat = connection.conn.prepareStatement(sql0);

                        String sql5 = "SELECT id_resep FROM mResep WHERE id_resep = '" + cmbResep.getSelectedItem() + "'";
                        connection.result = connection.stat.executeQuery(sql5);

                        while (connection.result.next()) {
                            idResep = (String) connection.result.getString("id_resep");
                        }

                        //INSERT ke tabel master
                        String sql2 = "INSERT INTO tbTrPenjualan VALUES (?, ?, ?,?)";
                        connection.pstat = connection.conn.prepareStatement(sql2);
                        connection.pstat.setString(1, txtID.getText());
                        connection.pstat.setString(2, idResep);
                        connection.pstat.setString(3, tgl);
                        connection.pstat.setInt(4, harga1);
                        connection.pstat.executeUpdate(); //insert ke tabel master

                        for (int k = 0; k < j; k++) {

                            String sql1 = "INSERT INTO tbDetailPenjualan VALUES (?, ?, ?, ?)";
                            //JOptionPane.showMessageDialog(null,"Terjadi error saat insert " +harga);

                            connection.pstat = connection.conn.prepareStatement(sql1);
                            connection.pstat.setString(1, txtID.getText());
                            connection.pstat.setString(2, (String) model3.getValueAt(k, 1));
                            connection.pstat.setInt(3, (Integer) model3.getValueAt(k, 4));
                            connection.pstat.setInt(4, (Integer) model3.getValueAt(k, 5));
                            connection.pstat.executeUpdate(); //insert tabel detilBeli

                            //mencari nilai stock ditabel buku saat ini dan mengurangi dengan nilai di inputan
                            String sql4 = "SELECT stok FROM mObat WHERE id_obat = '" + (String) model3.getValueAt(k, 1) + "'";
                            connection.result = connection.stat.executeQuery(sql4);
                            while (connection.result.next()) {
                                stok = connection.result.getInt("stok");
                                stok2 = stok - ((int) model3.getValueAt(k, 4));
                            }

                            //update stock di tabel buku
                            //  System.out.println("Nilai stock setelah dikurang : " + String.valueOf(stock2));
                            String sql6 = "UPDATE mObat SET stok =? WHERE id_obat =?";
                            connection.pstat = connection.conn.prepareStatement(sql6);
                            connection.pstat.setInt(1, stok2);
                            connection.pstat.setString(2, (String) model3.getValueAt(k, 1));
                            connection.pstat.executeUpdate(); //update tabel buku


                        }
                        connection.pstat.close(); //close connection
                        JOptionPane.showMessageDialog(null, "Input Transaksi Pembelian Obat berhasil");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Terjadi error saat input Transaksi : " + ex);

                    }

                }else if (rbTidak.isSelected()){
                    try {

                        String sql0 = "SELECT * FROM tbDetailPenjualan WHERE id_penjualan = '" + txtID.getText() + "'";
                        connection.pstat = connection.conn.prepareStatement(sql0);


                        //INSERT ke tabel master
                        String sql2 = "INSERT INTO tbTrPenjualan VALUES (?, ?, ?, ?)";
                        connection.pstat = connection.conn.prepareStatement(sql2);
                        connection.pstat.setString(1, txtID.getText());
                        connection.pstat.setString(2, null);
                        connection.pstat.setString(3, tgl);
                        connection.pstat.setInt(4, harga1);
                        connection.pstat.executeUpdate(); //insert ke tabel master

                        for (int k = 0; k < j; k++) {

                            String sql1 = "INSERT INTO tbDetailPenjualan VALUES (?, ?, ?, ?)";
                            //JOptionPane.showMessageDialog(null,"Terjadi error saat insert " +harga);

                            connection.pstat = connection.conn.prepareStatement(sql1);
                            connection.pstat.setString(1, txtID.getText());
                            connection.pstat.setString(2, (String) model3.getValueAt(k, 1));
                            connection.pstat.setInt(3, (Integer) model3.getValueAt(k, 4));
                            connection.pstat.setInt(4, (Integer) model3.getValueAt(k, 5));
                            connection.pstat.executeUpdate(); //insert tabel detilBeli


                            //mencari nilai stock ditabel buku saat ini dan mengurangi dengan nilai di inputan
                            String sql4 = "SELECT stok FROM mObat WHERE id_obat = '" + (String) model3.getValueAt(k, 1) + "'";
                            connection.result = connection.stat.executeQuery(sql4);
                            while (connection.result.next()) {
                                stok = connection.result.getInt("stok");
                                stok2 = stok - ((int) model3.getValueAt(k, 4));
                            }

                            //update stock di tabel buku
                            //  System.out.println("Nilai stock setelah dikurang : " + String.valueOf(stock2));
                            String sql6 = "UPDATE mObat SET stok =? WHERE id_obat =?";
                            connection.pstat = connection.conn.prepareStatement(sql6);
                            connection.pstat.setInt(1, stok2);
                            connection.pstat.setString(2, (String) model3.getValueAt(k, 1));
                            connection.pstat.executeUpdate(); //update tabel buku

                        }
                        connection.pstat.close(); //close connection
                        JOptionPane.showMessageDialog(null, "Input Transaksi Pembelian Obat berhasil");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Terjadi error saat input Transaksi : " + ex);

                    }
                }
            }




        });

        rbYa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbResep.setEnabled(true);
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusBaris();
            }
        });

        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Kasir.DashKasir fmKasir = new Kasir.DashKasir();
                fmKasir.setVisible(true);
            }
        });
        txtID1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                btnCari.setEnabled(true);
            }
        });
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showbyID();
            }
        });
        txtKandungan.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                btnCari1.setEnabled(true);
            }
        });
        btnCari1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showbyKD();
            }
        });
        btnRefresh2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData1();
            }
        });
    }

    public void showbyID(){
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mResep WHERE id_resep LIKE '%"+ txtID1.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()){
                Object[] obj = new Object[3];
                obj[0] = connection.result.getString("id_resep");
                obj[1] = connection.result.getString("id_customer");
                obj[2] = connection.result.getString("kandungan");
                model2.addRow(obj);
            }

            if(model2.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Resep tidak ditemukan");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e6) {
            System.out.println("Terjadi error saat load data Resep : " + e6);
        }
    }

    public void showbyKD(){
        model1.getDataVector().removeAllElements();
        model1.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mObat WHERE kandungan LIKE '%"+ txtKandungan.getText()+"%'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()){
                hargaobat = connection.result.getInt("harga");
                Object[] obj = new Object[5];
                obj[0] = connection.result.getString("id_obat");
                obj[1] = connection.result.getString("nama_obat");
                obj[2] = connection.result.getString("kandungan");
                obj[3] = connection.result.getString("stok");
                obj[4] = Integer.toString(hargaobat);
                model1.addRow(obj);
            }

            if(model1.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"Data Obat tidak ditemukan");
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e6) {
            System.out.println("Terjadi error saat load data Resep : " + e6);
        }
    }

    public void loadData1() {
        model1.getDataVector().removeAllElements();
        model1.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mObat";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                hargaobat = connection.result.getInt("harga");
                Object[] obj = new Object[5];
                obj[0] = connection.result.getString("id_obat");
                obj[1] = connection.result.getString("nama_obat");
                obj[2] = connection.result.getString("kandungan");
                obj[3] = connection.result.getString("stok");
                obj[4] = Integer.toString(hargaobat);
                model1.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e4){
            System.out.println("Terjadi error saat load data Obat : " + e4);
        }
    }

    public void loadData2() {
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM mResep";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[3];
                obj[0] = connection.result.getString("id_resep");
                obj[1] = connection.result.getString("id_customer");
                obj[2] = connection.result.getString("kandungan");
                model2.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e4){
            System.out.println("Terjadi error saat load data Supplier: " + e4);
        }
    }
}
