package Master;

import Config.DBConnect;
import Transaksi.TransaksiPenjualan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputCustomer extends JFrame {
    private JPanel ipCustomer;
    private JTextField txtID;
    private JTextField txtNama;
    private JTextField txtTelepon;
    private JButton btnSimpan;
    private JButton btnBatal;
    private JTextField txtStatus;
    private JRadioButton rbAktif;
    private JRadioButton rbTidak;

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
        return false;
    }

    public void FrameConfig() {
        add(ipCustomer);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void clearForm() {
        txtID.setText("");
        txtNama.setText("");
        txtTelepon.setText("");
        //txtStatus.setText("");
    }

    public void FrameRadioButton1(){
        ButtonGroup grup = new ButtonGroup();
        grup.add(rbAktif);
        grup.add(rbTidak);
    }

    public InputCustomer() {
        FrameRadioButton1();
        FrameConfig();
        autokode();
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

                            clearForm();
                            JOptionPane.showMessageDialog(null, "Input data Customer berhasil.");

                            dispose();
                            Transaksi.TransaksiPenjualan trPenjualan = new Transaksi.TransaksiPenjualan();
                            trPenjualan.setVisible(true);
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
                            dispose();
                            Transaksi.TransaksiPenjualan trPenjualan = new Transaksi.TransaksiPenjualan();
                            trPenjualan.setVisible(true);
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
    }
}
