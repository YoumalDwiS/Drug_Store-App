package Admin;

import HaloTek.LoginForm;
import Transaksi.TransaksiPembelian;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.MultipleMaster;

public class DashAdmin extends JFrame {
    private JPanel dAdmin;
    private JButton btnUser;
    private JButton btnCustomer;
    private JButton btnSupplier;
    private JButton btnObat;
    private JButton btnKategori;
    private JButton btnPembelian;
    private JButton btnLogout;
    private JButton btnResep;

    public void FrameConfig() {
        add(dAdmin);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public DashAdmin() {
        FrameConfig();
        btnUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Master.User fmUser = new Master.User();
                fmUser.setVisible(true);
            }
        });
        btnCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Master.Customer fmCustomer = new Master.Customer();
                fmCustomer.setVisible(true);
            }
        });
        btnSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Master.Supplier fmSupplier = new Master.Supplier();
                fmSupplier.setVisible(true);
            }
        });
        btnObat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Master.Obat fmObat = new Master.Obat();
                fmObat.setVisible(true);
            }
        });
        btnPembelian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TransaksiPembelian trPembelian = new TransaksiPembelian();
                trPembelian.setVisible(true);
            }
        });
        btnKategori.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Master.Kategori fmKategori = new Master.Kategori();
                fmKategori.setVisible(true);
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
        btnResep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Master.Resep fmResep = new Master.Resep();
                fmResep.setVisible(true);
            }
        });
    }
}
