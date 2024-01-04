package com.harmonypark.harmonypark.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.harmonypark.harmonypark.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@Service
public class QrCodeService {
    private final UserServiceImp userServiceImp;

    public ByteArrayOutputStream generateQrCodes(Page<UserResponseDto> users) throws IOException, WriterException {
        ByteArrayOutputStream zipByteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(zipByteArrayOutputStream)) {
            for (UserResponseDto user : users) {
                String fileName = user.getFirstName() + "_" + user.getLastName() + ".png";
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(zipEntry);
                generateQr(user, zipOutputStream);
                zipOutputStream.closeEntry();
            }
        }

        return zipByteArrayOutputStream;
    }
    public void generateQr(UserResponseDto data, OutputStream outputStream) throws WriterException, IOException {
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(String.valueOf(data), BarcodeFormat.QR_CODE, 200, 200
        );
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
    }
    public String decodeQr(byte [] data) throws IOException, NotFoundException{
        Result result = new MultiFormatReader()
                .decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(
                        ImageIO.read( new ByteArrayInputStream(data))
                ))));
        return result  != null ?result.getText():null;
    }
}
