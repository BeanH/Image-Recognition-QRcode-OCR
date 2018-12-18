package QRcode;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRcodeRecognizer {

    /**
     * The method demonstrates how to write some data a QRCode to jpg file
     * And then decode the jpg file to get the encoded data
     */
    public static void main(String[] args) {
        try {

            String qrCodeData = "https://beanho.wixsite.com/bean/blog-1";     // encoded data, this is a URL
            String filePath = "MyQRCode.jpg";     // The file path of the QRCode
            String charset = "UTF-8";  // the charset of the encode and decode

            createQRCode(qrCodeData, filePath, charset, 200, 200);
            System.out.println("QR Code image created successfully!");


            File file = new File(filePath);
            String decodedText = decodeQRCode(file);
            if (decodedText == null) {
                System.out.println("No QR Code found in the image");
            } else {
                System.out.println("Decoded text = " + decodedText);
            }
        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        } catch (WriterException e) {
            System.out.println("Could not write QR Code to file, IOException :: " + e.getMessage());

        }
    }


    /**
     * create a QRCode with the provided data and size, and generate a file to the given path
     *
     * @param qrCodeData   the encoded data, could be any String
     * @param filePath     the output file path
     * @param charset      the encoding charset, like "UTF-8"
     * @param qrCodeheight the heightOf the QRCode
     * @param qrCodewidth  the width the QRCode
     */
    public static void createQRCode(String qrCodeData, String filePath, String charset, int qrCodeheight, int qrCodewidth)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight);
        MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                .lastIndexOf('.') + 1), new File(filePath));
    }

    /**
     * decode the data within the QRCode
     *
     * @param qrCodeimage the file path
     * @return the decoded String
     */
    public static String decodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }




}
