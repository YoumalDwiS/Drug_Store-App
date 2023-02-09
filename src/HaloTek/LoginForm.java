package HaloTek;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Admin.DashAdmin;
import Config.DBConnect;
import Kasir.DashKasir;
import Kepala.DashKepala;

public class LoginForm extends JFrame{
    private JPanel fmLogin;
    private JLabel bgLogo;
    private JButton btnLogin;
    private JTextField txtUsername;
    private JButton btnBatal;
    private JCheckBox cbPassword;
    private JPasswordField txtPassword;
    private JButton btnExit;
    private JButton btnMin;

    DBConnect connection = new DBConnect();
    String jabatan1;

    public void FrameConfig() {
        add(fmLogin);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public String[] validasi() {
        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(fmLogin,"Username / Password Kosong","Peringatan",JOptionPane.WARNING_MESSAGE);
        }
        else {
            try {
                DBConnect connection = new DBConnect();
                connection.stat = connection.conn.createStatement();
                String query = "SELECT * FROM mUser WHERE username = '" +txtUsername.getText()+"' and password = '" +txtPassword.getText()+"'";
                connection.result = connection.stat.executeQuery(query);

                if(!connection.result.next()) {
                    throw new Exception("Pengguna Tidak Ditemukan");
                }

                String username = connection.result.getString(3);
                String password = connection.result.getString(4);
                String jabatan = connection.result.getString(5);

                return new String[] {"true",username,password,jabatan};
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(fmLogin,ex.getMessage(),"Peringatan",JOptionPane.WARNING_MESSAGE);
            }
        }
        return new String[] {"false"};
    }

    public LoginForm() {
        FrameConfig();
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] value = validasi();
                Boolean valid = Boolean.parseBoolean(value[0]);

                if(valid) {
                    JOptionPane.showMessageDialog(fmLogin,"Selamat Datang di Aplikasi HaloTek","Information",JOptionPane.INFORMATION_MESSAGE);
                    if(value[3].equals("Admin")) {
                        dispose();
                        DashAdmin form = new DashAdmin();
                        form.setVisible(true);

                    }
                    else if(value[3].equals("Kasir")) {
                        dispose();
                        DashKasir form = new DashKasir();
                        form.setVisible(true);
                    }
                    else if(value[3].equals("Kepala")) {
                        dispose();
                        DashKepala form = new DashKepala();
                        form.setVisible(true);
                    }
                }
            }
        });
        btnBatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtUsername.setText("");
                txtPassword.setText("");
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
        cbPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbPassword.isSelected() == true)
                {
                    txtPassword.setEchoChar('\0');
                }else {
                    txtPassword.setEchoChar('*');
                }
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnMin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(JFrame.ICONIFIED); // Another way
            }
        });
    }
    public static void main(String[] args) {
        LoginForm fmInduk = new LoginForm();
        fmInduk.setVisible(true);
    }
}
