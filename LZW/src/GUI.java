import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JPanel panel1;
    private JButton btn1;
    private JTextField input;
    private JButton btn2;
    private JLabel compressed;
    private JLabel decompressed;

    public GUI() {
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var a = input.getText();
                compressed.setText(LZW.compress(a));
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var a = compressed.getText();
                decompressed.setText(LZW.deCompress(a));
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LZW (Lossless Compression Technique)");
        frame.setContentPane(new GUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}