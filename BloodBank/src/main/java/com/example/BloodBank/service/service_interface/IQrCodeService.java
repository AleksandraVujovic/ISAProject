package com.example.BloodBank.service.service_interface;

import java.awt.image.BufferedImage;

public interface IQrCodeService {

    BufferedImage generateQRCodeImage(String barcodeText, int width, int height) throws Exception;

    byte[] toByteArrayFromBufferedImage(BufferedImage image) throws Exception;
}
