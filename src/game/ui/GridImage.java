package game.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class GridImage {
    private BufferedImage inputImage;
    private BufferedImage outputImage;
    private File file;
    private int outputX;
    private int outputY;
    private int size;
    private Icon[] outputArray;

    public GridImage(String path, int size) {
        this.file = new File(path);

        try {
            this.size = size;
            this.inputImage = ImageIO.read(this.file);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public Icon[] generate(){
        this.defineOutputImage();
        this.getOutputArray();
        return this.outputArray;
    }

    private void defineOutputImage() {
        int x = this.inputImage.getWidth();
        int y = this.inputImage.getHeight();
        if (x / size > y / size) {
            this.outputX = y / size * size;
            this.outputY = y / size * size;
        } else {
            this.outputX = x / size * size;
            this.outputY = x / size * size;
        }

        this.outputImage = this.inputImage.getSubimage(0, 0, this.outputX, this.outputY);
    }

    private void getOutputArray() {
        Icon[] list = new Icon[size*size];
        int counter = 0;

        for(int i = 0; i < size; ++i) {
            for(int i2 = 0; i2 < size; ++i2) {
                BufferedImage tempImage = this.outputImage.getSubimage(this.outputX / size * i2, this.outputY / size * i, this.outputX / size, this.outputY / size);
                list[counter] = new ImageIcon(tempImage);
                ++counter;
            }
        }

        this.outputArray = list;
    }
}
