package com.example.booking_system.qr_generator;

import org.springframework.web.multipart.MultipartFile;

public interface QrGeneratorService {
    public byte[] generateImageQRCode(String text, int width, int height) throws Exception;

    public String decodeQrCode(MultipartFile file) throws Exception;
}
