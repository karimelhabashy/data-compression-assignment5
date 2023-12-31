import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;


public class DataCompressionDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCompress;
    private JButton buttonDecompress;
    private JTextField textField2;
    private JTextField textField1;

    public DataCompressionDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCompress);
        this.setLocation(300, 200);
        this.getContentPane().setPreferredSize(new Dimension(500, 350));

        buttonCompress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                compress();
            }
        });

        buttonDecompress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                decompress();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                decompress();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    int quantize(int number){
        if (number >= 0 && number <= 10) {
            return 0;
        } else if (number > 10 && number <= 20) {
            return 1;
        } else if (number > 20 && number <= 30) {
            return 2;
        } else if (number > 30 && number <= 40) {
            return 3;
        } else if (number > 40 && number <= 50) {
            return 4;
        } else if (number > 50 && number <= 60) {
            return 5;
        } else if (number > 60 && number <= 70) {
            return 6;
        } else if (number > 70 && number <= 80) {
            return 7;
        } else if (number > 80 && number <= 90) {
            return 8;
        } else if (number > 90 && number <= 100) {
            return 9;
        } else if (number > 100 && number <= 110) {
            return 10;
        } else if (number > 110 && number <= 120) {
            return 11;
        } else if (number > 120 && number <= 130) {
            return 12;
        } else if (number > 130 && number <= 140) {
            return 13;
        } else if (number > 140 && number <= 150) {
            return 14;
        } else if (number > 150 && number <= 160) {
            return 15;
        } else if (number > 160 && number <= 170) {
            return 16;
        } else if (number > 170 && number <= 180) {
            return 17;
        } else if (number > 180 && number <= 190) {
            return 18;
        } else if (number > 190 && number <= 200) {
            return 19;
        } else if (number > 200 && number <= 210) {
            return 20;
        } else if (number > 210 && number <= 220) {
            return 21;
        } else if (number > 220 && number <= 230) {
            return 22;
        } else if (number > 230 && number <= 240) {
            return 23;
        } else if (number > 240 && number <= 250) {
            return 24;
        } else {
            return 25;
        }
    }

    int dequantize(int number){
        if (number == 0) {
            return 5;
        } else if (number == 1) {
            return 15;
        } else if (number == 2) {
            return 25;
        } else if (number == 3) {
            return 35;
        } else if (number == 4) {
            return 45;
        } else if (number == 5) {
            return 55;
        } else if (number == 6) {
            return 65;
        } else if (number == 7) {
            return 75;
        } else if (number == 8) {
            return 85;
        } else if (number == 9) {
            return 95;
        } else if (number == 10) {
            return 105;
        } else if (number == 11) {
            return 115;
        } else if (number == 12) {
            return 125;
        } else if (number == 13) {
            return 135;
        } else if (number == 14) {
            return 145;
        } else if (number == 15) {
            return 155;
        } else if (number == 16) {
            return 165;
        } else if (number == 17) {
            return 175;
        } else if (number == 18) {
            return 185;
        } else if (number == 19) {
            return 195;
        } else if (number == 20) {
            return 205;
        } else if (number == 21) {
            return 215;
        } else if (number == 22) {
            return 225;
        } else if (number == 23) {
            return 235;
        } else if (number == 24) {
            return 245;
        } else {
            return 255;
        }
    }


    private int[][] readImage(String filePath) {
        File file = new File(filePath);
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int[][] imagePixels = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                imagePixels[y][x] = red;
            }
        }

        return imagePixels;
    }

    private void compress() {
        int[][] img = readImage(textField1.getText());
        int[][] predicted = readImage(textField1.getText());

        for (int i=1; i<img.length;i++)
        {
            for (int j=1;j<img[0].length;j++)
            {
                if(img[i-1][j-1]<=Math.min(img[i-1][j],img[i][j-1]))
                {
                    predicted[i][j] = Math.max(img[i-1][j],img[i][j-1]);
                }
                else if(img[i-1][j-1]>=Math.max(img[i-1][j],img[i][j-1])){
                    predicted[i][j] = Math.min(img[i-1][j],img[i][j-1]);
                }
                else {
                    predicted[i][j] = (img[i-1][j]) + (img[i][j-1]) - (img[i-1][j-1]);
                }
            }
        }

        int[][] difference = readImage(textField1.getText());
        for (int i=1; i<difference.length;i++) {
            for (int j = 1; j < difference[0].length; j++) {
                difference[i][j] = img[i][j] - predicted[i][j];
            }
        }

        int[][] quantized_difference = readImage(textField1.getText());

        for (int i=1; i<difference.length;i++) {
            for (int j = 1; j < difference[0].length; j++) {
                quantized_difference[i][j] = quantize(difference[i][j]);
            }
        }





        try {
            BufferedImage image2 = new BufferedImage(predicted[0].length, predicted.length, BufferedImage.TYPE_BYTE_GRAY);
            for (int row = 0; row < predicted.length; row++){
                for (int col = 0; col < predicted[0].length; col++){
                    int pixel = predicted[row][col];
                    image2.setRGB(col, row, (pixel << 16) | (pixel << 8) | pixel);
                }
            }
            ImageIO.write(image2, "bmp", new File("decompressed_image.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void decompress() {



    }

    public static void main(String[] args) {
        DataCompressionDialog dialog = new DataCompressionDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
