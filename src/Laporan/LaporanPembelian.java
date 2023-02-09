package Laporan;

import Admin.DashAdmin;
import Config.DBConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.sql.SQLException;

import Kepala.DashKepala;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;
import java.util.Map;

public class LaporanPembelian extends JFrame {
    private JPanel lpPembelian;
    private JButton btnKembali;
    private JComboBox cmbBulan;
    private JButton btnRefresh;
    private JButton btnPrint;
    private JTable tbPembelian;
    private JButton btnCari;
    private JComboBox cmbTahun;
    private JTextField txtPengeluaran;

    private DefaultTableModel model1;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DBConnect connection = new DBConnect();

    String total;
    int total1 = 0;
    Integer total2 = 0;

    public LaporanPembelian() {
        FrameConfig();
        addColomn1();
        loadData1();
        HitungHarga();
        tampilTahun();

        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bulan = 0;
                //menghapus seluruh data ditampilkan(jika ada) untuk tampilan pertama
                model1.getDataVector().removeAllElements();

                //memberi tahu data telah kosong
                model1.fireTableDataChanged();

                if(cmbBulan.getSelectedItem().equals("Januari")){
                    bulan = 1;
                }else if(cmbBulan.getSelectedItem().equals("Februari")){
                    bulan = 2;
                }else if(cmbBulan.getSelectedItem().equals("Maret")){
                    bulan = 3;
                }else if(cmbBulan.getSelectedItem().equals("April")){
                    bulan = 4;
                }else if(cmbBulan.getSelectedItem().equals("Mei")){
                    bulan = 5;
                }else if(cmbBulan.getSelectedItem().equals("Juni")){
                    bulan = 6;
                }else if(cmbBulan.getSelectedItem().equals("Juli")){
                    bulan = 7;
                }else if(cmbBulan.getSelectedItem().equals("Agustus")){
                    bulan = 8;
                }else if(cmbBulan.getSelectedItem().equals("September")){
                    bulan = 9;
                }else if(cmbBulan.getSelectedItem().equals("Oktober")){
                    bulan = 10;
                }else if(cmbBulan.getSelectedItem().equals("November")){
                    bulan = 11;
                }else if(cmbBulan.getSelectedItem().equals("Desember")){
                    bulan = 12;
                }


                try {
                    DBConnect connect = new DBConnect();
                    connect.stat = connect.conn.createStatement();
                    String query = "SELECT * FROM LaporanPembelian WHERE YEAR (tgl_pembelian) = '"+cmbTahun.getSelectedItem()+"' AND MONTH (tgl_pembelian) = '"+bulan+"'";
                    connect.result = connect.stat.executeQuery(query);

                    //lakukan perbaris data
                    while (connect.result.next()){
                        Object[] obj = new Object[4];
                        obj[0] = connect.result.getString("id_pembelian");
                        obj[1] = connect.result.getString("nama_supplier");
                        obj[2] = connect.result.getString("tgl_pembelian");
                        obj[3] = connect.result.getString("total_harga_beli");
                        model1.addRow(obj);
                    }

                    //jika judul tidak ada pada tabel
                    if(tbPembelian.getRowCount() == 0){
                        // JOptionPane.showMessageDialog(this, "Data Tidak Ditemukan");
                        //  txtTotal.setText("0");
                    }

                    connect.stat.close();
                    connect.result.close();

                    HitungHarga();

                }catch (Exception e1){
                    System.out.println("Terjadi error saat load data : " +e1);
                }
            }
        });
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                loadData1();
            }
        });
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilReport();
            }
        });
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Kepala.DashKepala dbKepala = new Kepala.DashKepala();
                dbKepala.setVisible(true);
            }
        });
        /*txtPengeluaran.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //HitungHarga();
            }
        });*/
    }
    public void tampilTahun(){
        try{
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String sql = "SELECT DISTINCT YEAR (tgl_pembelian) AS Tahun FROM LaporanPembelian";
            connection.result = connection.stat.executeQuery(sql);

            while (connection.result.next()){
                cmbTahun.addItem(connection.result.getString("Tahun"));
            }
            connection.stat.close();
            connection.result.close();
        }catch (SQLException ex){
            System.out.println("Terjadi error saat load data");
        }
    }



    public void HitungHarga(){
        int j = tbPembelian.getModel().getRowCount();
        for(int k = 0; k < j; k++) {
            total = (String) model1.getValueAt(k,3);
            total1 = Integer.parseInt(total);
            total2 = total2 + total1;
            txtPengeluaran.setText("Rp. " + total2.toString());
        }
    }

    public void tampilReport() {
        JasperPrint jp;
        Map param = new HashMap();

        try {
            jp = JasperFillManager.fillReport("Jasper/Pembelian.jasper", param, connection.conn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Laporan Pembelian");
            viewer.setVisible(true);
        }
        catch (JRException E) {
            System.out.println(E.getMessage());
        }
    }

    //Table View Transaksi
    public void addColomn1(){
        model1 = new DefaultTableModel();
        tbPembelian.setModel(model1);
        model1.addColumn("ID Pembelian");
        model1.addColumn("Nama Supplier");
        model1.addColumn("Tanggal Pembelian");
        model1.addColumn("Total Harga");
    }

    public void loadData1() {
        model1.getDataVector().removeAllElements();
        model1.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM LaporanPembelian";
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()){
                Object[] obj = new Object[4];
                obj[0] = connection.result.getString("id_pembelian");
                obj[1] = connection.result.getString("nama_supplier");
                obj[2] = connection.result.getString("tgl_pembelian");
                obj[3] = connection.result.getString("total_harga_beli");
                model1.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }
        catch (Exception e4){
            System.out.println("Terjadi error saat load data Transaksi : " + e4);
        }
    }

    public void dataTable(int indexBulan,String tahun){
        model1.getDataVector().removeAllElements();
        model1.fireTableDataChanged();

        String query = "SELECT * FROM LaporanPembelian";

        if(indexBulan > 0 && !(tahun.isEmpty())){
            query = "SELECT * FROM LaporanPembelian WHERE YEAR(tgl_pembelian) = '"+tahun+"' AND MONTH(tgl_pembelian) = '"+indexBulan+"'";
        }

        try{
            DBConnect connection = new DBConnect();
            connection.pstat = connection.conn.prepareStatement(query);
            connection.result = connection.pstat.executeQuery();;
            while(connection.result.next()){
                Object[] obj = new Object[4];
                obj[0] = connection.result.getString("id_pembelian");
                obj[1] = connection.result.getString("nama_supplier");
                obj[2] = connection.result.getString("tgl_pembelian");
                obj[3] = connection.result.getString("total_harga_beli");
                model1.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }catch(Exception ex){
            System.out.println("Terjadi error saat load data table "+ex);
        }
    }

    public void loadTable(int indexBulan,String tahun){
        model1 = new DefaultTableModel();
        tbPembelian.setModel(model1);
        addColomn1();
        dataTable(indexBulan,tahun);
    }
    public void clear(){
        cmbTahun.setSelectedItem(null);
        cmbTahun.setSelectedItem(null);
        cmbBulan.setSelectedItem(null);
        //loadTable(cmbBulan.getSelectedIndex(),txtTahun.getText());
    }

    public static void main(String[] args) {
        LaporanPembelian laporan = new LaporanPembelian();
        laporan.setVisible(true);
    }

    public void FrameConfig() {
        add(lpPembelian);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


}
