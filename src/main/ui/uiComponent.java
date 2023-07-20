package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static main.persistence.LocalLoader.getLocalImage;
import static main.ui.MainWindow.HEIGHT;
import static main.ui.MainWindow.WIDTH;

public class uiComponent {

    protected static Component makeBG(String file_name) {
        JLabel bg = new JLabel(new ImageIcon(getLocalImage(file_name)));
        bg.setOpaque(true);
        bg.setBounds(0, 0, WIDTH, HEIGHT);
        return bg;
    }

    protected static Component makeText(String text, int fontSize, Color color, int x, int y, int width, int height) {
        JLabel txt = new JLabel(text);
        txt.setFont(new Font(txt.getFont().getFontName(), Font.PLAIN, fontSize));
        txt.setForeground(color);
        txt.setOpaque(false);
        txt.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txt.setHorizontalAlignment(JTextField.CENTER);
        txt.setBounds(x, y, width, height);
        return txt;
    }

    protected static JPanel makePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, WIDTH, HEIGHT);
        panel.setOpaque(false);
        return panel;
    }

    protected static JButton makeButton(String text, int fontSize, ActionListener al,
                                        int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font(button.getFont().getFontName(), Font.PLAIN, fontSize));
        button.setOpaque(false);
        button.setBounds(x, y, width, height);
        button.addActionListener(al);

        return button;
    }
}
