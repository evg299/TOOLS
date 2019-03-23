import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

/**
 * Created by Evgeny(e299792459@gmail.com) on 22.02.14.
 */
public class TesseractOcrImageParse {

    public static void main(String[] args) throws IOException, TesseractException {
        Tesseract1 instance = new Tesseract1();
        // для этого нужно залить данные в папку tessdata (но для цифр это не обязательно)
        instance.setLanguage("rus");

        BufferedImage img = ImageIO.read(new File(SeleniumAvitoDownload.savedImgPath));
        // Маленькие изображения не распознает, увеличиваем
        BufferedImage resizedImg = resize(img, 400, 70);

        String result = instance.doOCR(resizedImg);

        // Вывод результата
        System.out.println(result);
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH)
            throws IOException {
        return Thumbnails.of(img).size(newW, newH).asBufferedImage();
    }

}
