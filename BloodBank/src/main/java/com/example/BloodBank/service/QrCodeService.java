package com.example.BloodBank.service;

import com.example.BloodBank.service.service_interface.IQrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class QrCodeService implements IQrCodeService {


    @Override
    public BufferedImage generateQRCodeImage(String barcodeText, int width, int height) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, width, height);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @Override
    public byte[] toByteArrayFromBufferedImage(BufferedImage image) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes= baos.toByteArray();
        baos.close();
        return imageBytes;
    }
}
