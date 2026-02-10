package com.example.booking_system.qr_generator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QrGeneratorServiceImpl implements QrGeneratorService {

    public byte[] generateImageQRCode(String text, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            var qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream pgnOutputStream = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", pgnOutputStream);
            return pgnOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public String decodeQrCode(MultipartFile file) throws Exception {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Result result = new MultiFormatReader().decode(bitmap);
        return result.getText();
    }

}
