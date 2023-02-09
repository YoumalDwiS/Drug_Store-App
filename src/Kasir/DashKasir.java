package Kasir;

import HaloTek.LoginForm;
import Transaksi.TransaksiPenjualan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashKasir extends JFrame {
    private JPanel dKasir;
    private JButton btnObat;
    private JButton btnResep;
    private JButton btnCustomer;
    private JButton btnPenjualan;
    private JButton btnLogout;

    public void FrameConfig() {
        add(dKasir);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public DashKasir() {
        FrameConfig();
        btnObat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Master.Obat1 fmObat = new Master.Obat1();
                fmObat.setVisible(true);
            }
        });
        btnResep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Master.Resep1 fmResep = new Master.Resep1();
                fmResep.setVisible(true);
            }
        });
        btnCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Master.Customer1 fmCustomer = new Master.Customer1();
                fmCustomer.setVisible(true);
            }
        });
        btnPenjualan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TransaksiPenjualan trPenjualan = new TransaksiPenjualan();
                trPenjualan.setVisible(true);
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm fmLogin = new LoginForm();
                fmLogin.setVisible(true);
            }
        });
    }
}
