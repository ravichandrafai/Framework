package org.fai.utils;

import com.asprise.ocr.Ocr;


public class ExtractImageText {
 
  /*Navigate to http://www.mythoughts.co.in/2013/10/extract-and-verify-text-from-image.html page
  * and get the image source attribute
  *  
  */  
// driver.get("http://www.mythoughts.co.in/2013/10/extract-and-verify-text-from-image.html");
// String imageUrl=driver.findElement(By.xpath("//*[@id='post-body-5614451749129773593']/div[1]/div[1]/div/a/img")).getAttribute("src");
// System.out.println("Image source path : \n"+ imageUrl);
//
// URL url = new URL(imageUrl);
// Image image = ImageIO.read(url);
// String s = new Ocr().recognize((RenderedImage) image);
// System.out.println("Text From Image : \n"+ s);
// System.out.println("Length of total text : \n"+ s.length());
// driver.quit();
    
 /* Use below code If you want to read image location from your hard disk   
  *   
   BufferedImage image = ImageIO.read(new File("Image location"));   
   String imageText = new OCR().recognizeCharacters((RenderedImage) image);  
   System.out.println("Text From Image : \n"+ imageText);  
   System.out.println("Length of total text : \n"+ imageText.length());   
      
   */ 
	public static void main(String[] args) {
// Ocr.setUp(); // one time setup
// Ocr ocr = new Ocr(); // create a new OCR engine
// ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
// String s = ocr.recognize(new URL[] {new URL("http://2.bp.blogspot.com/-42SgMHAeF8U/Uk8QlYCoy-I/AAAAAAAADSA/TTAVAAgDhio/s1600/love.jpg")},
//   Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT); // PLAINTEXT | XML | PDF | RTF
// System.out.println("Result: " + s);
// ocr.stopEngine();
		Ocr.main(args);
	}
}
