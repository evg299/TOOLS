import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Evgeny(e299792459@gmail.com) on 22.02.14.
 */
public class SeleniumAvitoDownload {
    public static final String savedImgPath = "c:/tmp/phone.png";

    public static void main(String[] args) throws InterruptedException, IOException {
        // Firefox driver встроен по умолчанию (браузер Firefox должен быть установлен)
        FirefoxDriver driver = new FirefoxDriver();

        // Идем на страницу с объявлением
        driver.get("http://www.avito.ru/kirovskaya_oblast_kirov/avtomobili_s_probegom/lada_kalina_2012_284882258");

        // Находим элемент "показать номер"
        WebElement element = driver.findElements(By.cssSelector("div#phone A.icon-link.pseudo-link")).get(0);
        element.click();

        // Ждем загрузки (вообще говоря можно сделать так чтобы непосредственно после загрузки начиналась обработка, но для примера и этого хватит)
        Thread.sleep(1234);

        // Возвращается не текст, а изображение текста (при чем второй раз загружать нельзя, каждый раз у картинки новый URL)
        WebElement imgElement = driver.findElement(By.cssSelector("div#phone SPAN.phone img"));
        Point loc = imgElement.getLocation();
        Dimension size = imgElement.getSize();

        // Т.к. второй раз загружать нельзя, делаем скриншот страницы и вырезаем изображение
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(scrFile);
        BufferedImage croped = img.getSubimage(loc.x, loc.y, size.width, size.height);

        // Сохраняем изображение
        ImageIO.write(croped, "png", new File(savedImgPath));

        // Закрываем браузер
        driver.close();
        driver.quit();

        // Пример ожидания появления нужного нам элемента
		/*
		long end = System.currentTimeMillis() + 500;
		while (System.currentTimeMillis() < end) {
			WebElement resultsDiv = driver.findElement(By.cssSelector("div#phone SPAN.phone img"));

			// Элемент появился
			if (resultsDiv.isDisplayed()) {
				break;
			}
		}
        */

    }
}
