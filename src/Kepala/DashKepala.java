package Kepala;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashKepala extends JFrame {
    private JPanel dKepala;
    private JButton btnLaporan1;
    private JButton btnLaporan2;
    private JButton btnLaporan3;
    private JButton btnLogout;

    public void FrameConfig() {
        add(dKepala);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public DashKepala() {
        FrameConfig();
        btnLaporan1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Laporan.LaporanPenjualan lpPenjualan = new Laporan.LaporanPenjualan();
                lpPenjualan.setVisible(true);
            }
        });
        btnLaporan2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Laporan.LaporanPembelian lpPembelian = new Laporan.LaporanPembelian();
                lpPembelian.setVisible(true);
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HaloTek.LoginForm fmLogin = new HaloTek.LoginForm();
                fmLogin.setVisible(true);
            }
        });

    }
}
