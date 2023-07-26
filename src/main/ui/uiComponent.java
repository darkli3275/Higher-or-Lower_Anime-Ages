package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static main.persistence.LocalLoader.getLocalImage;
import static main.persistence.NetLoader.getNetImage;
import static main.ui.MainWindow.HEIGHT;
import static main.ui.MainWindow.WIDTH;

public class uiComponent {

    protected static Component makeBG(boolean isNet, boolean desaturate, boolean scale, String file_name,
                                      int x, int y, int width, int height) {
        JLabel bg;
        BufferedImage i;
        if (isNet) i = (BufferedImage) getNetImage(file_name);
        else i = (BufferedImage) getLocalImage(file_name);
        if (desaturate) reduceColor(i);

        Image img = i;
        if (scale) img = i.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        bg = new JLabel(new ImageIcon(img));
        bg.setOpaque(false);
        bg.setBounds(x, y, width, height);
        return bg;
    }

    protected static Component makeText(String text, int fontSize, Color color, int x, int y, int width, int height) {
        JLabel txt = new JLabel(text);
        Font f = new Font(txt.getFont().getFontName(), Font.PLAIN, fontSize);
        txt.setFont(f);
        txt.setForeground(color);
        txt.setOpaque(false);
        txt.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txt.setHorizontalAlignment(JLabel.CENTER);
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

    public static void reduceColor(BufferedImage i) {
        int channelReduction = 50;
        for (int x = 0; x < i.getWidth(); x++) {
            for (int y = 0; y < i.getHeight(); y++) {
                int color = i.getRGB(x, y);

                int blue = (color & 0xff) - channelReduction;
                if (blue < 0) blue = 0;

                int green = ((color & 0xff00) >> 8) - channelReduction;
                if (green < 0) green = 0;

                int red = ((color & 0xff0000) >> 16) - channelReduction;
                if (red < 0) red = 0;

                int c = (red << 16) + (green << 8) + blue;
                i.setRGB(x, y, c);
            }
        }
    }
}
