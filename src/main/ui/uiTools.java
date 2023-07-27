/**
 * Collection of methods to make creating and modifying UI elements easier.
 *
 * @author Raymond Li
 */

package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static main.ui.MainWindow.HEIGHT;
import static main.ui.MainWindow.WIDTH;

public class uiTools {

    // EFFECTS: Returns an empty component container to put ui components in
    public static JPanel makePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, WIDTH, HEIGHT);
        panel.setOpaque(false);
        return panel;
    }

    // EFFECTS: Returns a screen-sized component of given color
    public static Component makeBG(Color color) {
        JLabel bg = new JLabel();
        bg.setOpaque(true);
        bg.setBackground(color);
        bg.setBounds(0, 0, WIDTH, HEIGHT);
        return bg;
    }

    // REQUIRES: img is not null
    // EFFECTS: Returns a screen-sized image component of given image
    public static Component makeBG(Image img) {
        JLabel bg = new JLabel(new ImageIcon(img));
        bg.setOpaque(false);
        bg.setBounds(0, 0, WIDTH, HEIGHT);
        return bg;
    }

    // EFFECTS: Returns image component with given image and screen positioning
    public static Component makePicture(Image img, int x, int y, int width, int height) {
        JLabel picture = new JLabel(new ImageIcon((img)));
        picture.setOpaque(false);
        picture.setBounds(x, y, width, height);
        return picture;
    }

    // EFFECTS: Returns text component with given text, text size, and text color
    public static Component makeText(String text, int fontSize, Color color) {
        JLabel txt = new JLabel(text);
        Font f = new Font(txt.getFont().getFontName(), Font.PLAIN, fontSize);
        txt.setFont(f);
        txt.setForeground(color);
        txt.setOpaque(false);
        txt.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txt.setHorizontalAlignment(JLabel.CENTER);
        return txt;
    }

    // EFFECTS: Returns text component with given text, text size, text color, and screen positioning
    public static Component makeText(String text, int fontSize, Color color, int x, int y, int width, int height) {
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

    // EFFECTS: Returns button with given text, sizes, and action listener
    public static JButton makeButton(String text, int fontSize, ActionListener al,
                                        int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font(button.getFont().getFontName(), Font.PLAIN, fontSize));
        button.setOpaque(false);
        button.setBounds(x, y, width, height);
        button.addActionListener(al);

        return button;
    }

    // REQUIRES: img is not null
    // EFFECTS: Makes image dimmer
    public static void reduceColor(Image img) {
        BufferedImage i = (BufferedImage) img;
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

    // EFFECTS: Returns image scaled smoothly to given width and height
    public static Image scaleImage(Image img, int width, int height) {
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
