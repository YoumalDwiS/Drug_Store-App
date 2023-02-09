package Transaksi;

import Config.DBConnect;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class TransaksiPembelian extends JFrame {
    private JPanel trPembelian;
    private JTextField txtID;
    private JComboBox cmbSupplier;
    private JTable tbObat;
    private JTable tbSupplier;
    private JTable tbTransaksi;
    private JButton btnTambah;
    private JButton btnBatal;
    private JButton btnBeli;
    private JButton btnClear;
    private JPanel jpCald;
    private JButton btnKembali;
    private JTextField txtSubTotal;
    private JComboBox cmbObat;
    private JTextField txtHarga;
    private JTextField txtJumlah;
    private JTextField txtTotal;
    private JButton btnRefresh1;
    private JButton btnRefresh2;
    private JButton btnCek;
    private JTextField txtStok;
    private JButton btnCari;
    private DefaultTableModel model1;
    private DefaultTableModel model2;
    private DefaultTableModel model3;

    DBConnect connection = new DBConnect();
    JDateChooser datechoos = new JDateChooser();

    String IDObat = "", Obat = "";
    int HargaSatuan;
    String HargaSatuan1;
    int harga = 0;
    int jumlah;
    Integer stok = 0;
    Integer stok2 = 0;
    Integer harga1 = 0;
    Integer hargaobat;
    Integer hargaobat1;
    String tgl, idsupplier;
    String id;

    public void FrameConfig() {
        add(trPembelian);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        jpCald.add(datechoos);
    }

    public void autokode() {
        try {
            String sql = "SELECT * FROM tbTrPembelian ORDER BY id_pembelian desc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            if (connection.result.next()) {
                id = connection.result.getString("id_pembelian").substring(4);
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
                txtID.setText("TRB" + nol + AN);
                txtID.requestFocus();

            } else {
                txtID.setText("TRB0001");
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
        Boolean[] check = {false,false,false,false,false,false};
        if(!txtID.getText().isEmpty()){
            check[0]=true;
        }
        if(!cmbSupplier.getSelectedItem().equals("")){
            check[1]=true;
        }
        if(!cmbObat.getSelectedItem().equals("")){
            check[2]=true;
        }
        if(!txtHarga.getText().isEmpty()){
            check[3]=true;
        }
        if(!txtJumlah.getText().isEmpty()){
            check[4]=true;
        }
        if(!txtTotal.getText().isEmpty()){
            check[5]=true;
        }

        if(action=="tambah"&&check[1]&&check[2]&&check[3]&&check[4]&&check[5]){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Transaksi.TransaksiPembelian form = new Transaksi.TransaksiPembelian();
        form.setVisible(true);
    }

    public void tampilSupplier(){
        try{
            connection.stat = connection.conn.createStatement();
            String sql = "SELECT id_supplier, nama_supplier FROM mSupplier";
            connection.result = connection.stat.executeQuery(sql);

            while (connection.result.next()){
                cmbSupplier.addItem(connection.result.getString("nama_supplier"));
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (SQLException ex) {
            System.out.println("Terjadi error saat load data Supplier : " + ex);
        }
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

    public void clearForm() {
        txtID.setText("");
        cmbSupplier.setSelectedItem(null);
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

    public void addColomn1(){
        model1 = new DefaultTableModel();
        tbObat.setModel(model1);
        model1.addColumn("ID OBAT");
        model1.addColumn("NAMA OBAT");
        model1.addColumn("EXP_DATE");
        model1.addColumn("STOK");
        model1.addColumn("HARGA");
    }

    public void addColomn2(){
        model2 = new DefaultTableModel();
        tbSupplier.setModel(model2);
        model2.addColumn("ID SUPPLIER");
        model2.addColumn("NAMA SUPPLIER");
        model2.addColumn("ALAMAT");
        model2.addColumn("NO TELEPON");
    }

    public void addColomn3(){
        model3.addColumn("ID OBAT");
        model3.addColumn("NAMA OBAT");
        model3.addColumn("HARGA SATUAN");
        model3.addColumn("JUMLAH");
        model3.addColumn("TOTAL HARGA");
    }

    public void HitungHarga(){
        int j = tbTransaksi.getModel().getRowCount(); //knowing how many row on tabelBuku dilayar
        harga1 = 0;
        for(int k = 0; k < j; k++) {
            harga = (int) model3.getValueAt(k,4);
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
    
    public TransaksiPembelian() {
        FrameConfig();

        tampilSupplier();
        tampilObat();

        model3 = new DefaultTableModel();
        tbTransaksi.setModel(model3);
        addColomn1();
        addColomn2();
        addColomn3();

        loadData1();
        loadData2();
        clearForm();

        autokode();

        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Admin.DashAdmin dbAdmin = new Admin.DashAdmin();
                dbAdmin.setVisible(true);
            }
        });
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validasi("tambah")) {
                    try {
                        String Jumlah = txtJumlah.getText();
                        String Total = txtTotal.getText();

                        connection.stat = connection.conn.createStatement();
                        String sql = "SELECT * FROM mObat WHERE nama_obat = '" + cmbObat.getSelectedItem() + "'";
                        connection.result = connection.stat.executeQuery(sql);

                        while (connection.result.next()) {
                            harga = 0;
                            Object[] obj = new Object[5];
                            obj[0] = IDObat;
                            obj[1] = Obat;
                            obj[2] = hargaobat1;
                            obj[3] = Integer.parseInt(Jumlah);
                            obj[4] = Integer.parseInt(Total);

                            int j = tbTransaksi.getModel().getRowCount(); //knowing how many row on tabelBuku dilayar

                            if(tbTransaksi.getModel().getRowCount() == 0){
                                model3.addRow(obj);
                            } else {
                                model3.addRow(obj);
                            }

                            for(int i = 0; i < j; i++){
                                boolean cek = false;
                                if(model3.getValueAt(i, 0).equals(IDObat) && cek == false){
                                    int temp1 = (int) model3.getValueAt(i, 3);
                                    int temp2 = Integer.parseInt(Jumlah);
                                    model3.setValueAt((temp1+temp2), i, 3);
                                    hapusBaris();
                                    JOptionPane.showMessageDialog(null,"Jumlah obat bertambah");
                                    HitungHarga();
                                    cek = true;
                                    return;
                                }
                            }
                        }
                        connection.stat.close(); //close connection

                        //JOptionPane.showMessageDialog(null, "Data telah masuk keranjang");

                        txtJumlah.setText(null);
                        cmbObat.setSelectedItem(null);
                        HitungHarga();

                        clearForm1();

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
        btnBeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                tgl = formatter.format(datechoos.getDate());

                int j = tbTransaksi.getModel().getRowCount(); //knowing how many row on tabelBuku dilayar
                try {

                    String sql0 = "SELECT * FROM tbDetailPembelian WHERE id_pembelian = '" + txtID.getText() + "'";
                    connection.pstat = connection.conn.prepareStatement(sql0);

                    String sql5 = "SELECT id_supplier FROM mSupplier WHERE nama_supplier = '" + cmbSupplier.getSelectedItem() + "'";
                    connection.result = connection.stat.executeQuery(sql5);

                    while (connection.result.next()) {
                        idsupplier = (String) connection.result.getString("id_supplier");
                    }

                    //INSERT ke tabel master
                    String sql2 = "INSERT INTO tbTrPembelian VALUES (?, ?, ?, ?)";
                    connection.pstat = connection.conn.prepareStatement(sql2);
                    connection.pstat.setString(1, txtID.getText());
                    connection.pstat.setString(2, idsupplier);
                    connection.pstat.setString(3, tgl);
                    connection.pstat.setInt(4, harga1);
                    connection.pstat.executeUpdate(); //insert ke tabel master

                    for(int k = 0; k < j; k++) {

                        String sql1 = "INSERT INTO tbDetailPembelian VALUES (?, ?, ?, ?)";
                        //JOptionPane.showMessageDialog(null,"Terjadi error saat insert " +harga);

                        connection.pstat = connection.conn.prepareStatement(sql1);
                        connection.pstat.setString(1, txtID.getText());
                        connection.pstat.setString(2, (String) model3.getValueAt(k, 0));
                        connection.pstat.setInt(3, (Integer) model3.getValueAt(k, 3)); //kodebuku
                        connection.pstat.setInt(4, (Integer) model3.getValueAt(k, 4)); //jumlah beli
                        connection.pstat.executeUpdate(); //insert tabel detilBeli

                        //mencari nilai stock ditabel buku saat ini dan mengurangi dengan nilai di inputan
                        String sql4 = "SELECT stok FROM mObat WHERE id_obat = '" + (String) model3.getValueAt(k, 0) + "'";
                        connection.result = connection.stat.executeQuery(sql4);
                        while (connection.result.next()) {
                            stok = connection.result.getInt("stok");
                            stok2 = stok + ((int) model3.getValueAt(k, 3));
                        }

                        //update stock di tabel buku
                        //  System.out.println("Nilai stock setelah dikurang : " + String.valueOf(stock2));
                        String sql6 = "UPDATE mObat SET stok =? WHERE id_obat =?";
                        connection.pstat = connection.conn.prepareStatement(sql6);
                        connection.pstat.setInt(1, stok2);
                        connection.pstat.setString(2, (String) model3.getValueAt(k, 0));
                        connection.pstat.executeUpdate(); //update tabel buku
                    }
                    connection.pstat.close(); //close connection
                    JOptionPane.showMessageDialog(null, "Input Transaksi Pembelian Obat berhasil");

                    clearForm();
                    autokode();

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"Terjadi error saat input Transaksi : " +ex);

                }
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
                            ttl1 = hargaobat1;
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
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusBaris();
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
                        txtHarga.setText(" "+ (int) connection.result.getInt("harga"));
                        HargaSatuan = (int) connection.result.getInt("harga");
                        hargaobat1 = 85 * HargaSatuan / 100;
                        HargaSatuan1 = Integer.toString(hargaobat1);
                        txtHarga.setText(HargaSatuan1);
                    }

                }
                catch (Exception e1){
                    System.out.println("Terjadi error pada saat load data obat:"+e1);
                }
            }
        });
        btnRefresh1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData1();
            }
        });
        btnRefresh2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData2();
            }
        });
        btnCek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showbyStok();
            }
        });
    }

    public void showbyStok(){
        model1.getDataVector().removeAllElements();
        model1.fireTableDataChanged();

        try {
            String sql = "SELECT * FROM mObat ORDER BY stok asc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            while (connection.result.next()){
                hargaobat = connection.result.getInt("harga");
                Object[] obj = new Object[5];
                obj[0] = connection.result.getString("id_obat");
                obj[1] = connection.result.getString("nama_obat");
                obj[2] = connection.result.getString("exp_date");
                obj[3] = connection.result.getString("stok");
                obj[4] = Integer.toString(hargaobat);
                model1.addRow(obj);
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
                obj[2] = connection.result.getString("exp_date");
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
            String query = "SELECT * FROM mSupplier";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[4];
                obj[0] = connection.result.getString("id_supplier");
                obj[1] = connection.result.getString("nama_supplier");
                obj[2] = connection.result.getString("alamat");
                obj[3] = connection.result.getString("telp_supplier");
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
