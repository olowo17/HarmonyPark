package com.harmonypark.harmonypark.controller;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.harmonypark.harmonypark.dto.DecodeQrResponse;
import com.harmonypark.harmonypark.dto.UserResponseDto;
import com.harmonypark.harmonypark.exception.InvalidQrCodeException;
import com.harmonypark.harmonypark.repositories.UserRepository;
import com.harmonypark.harmonypark.service.QrCodeService;
import com.harmonypark.harmonypark.service.UserServiceImp;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@AllArgsConstructor
@Data
@RestController
@RequestMapping("/api/qr/")
public class QrCodeResource {

    private final QrCodeService qrCodeService;
    private final UserServiceImp userService;
    private final UserRepository userRepository;

    @GetMapping("/generate/all")
    public ResponseEntity<Object> generateQr(HttpServletResponse response, Pageable pageable)
            throws IOException, WriterException {
        Page<UserResponseDto> users = userService.getAllUsers(pageable);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
        }

        ByteArrayOutputStream zipByteArrayOutputStream = qrCodeService.generateQrCodes(users);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "carParkQrCodes.zip");

        return ResponseEntity.ok()
                .headers(headers)
                .body(zipByteArrayOutputStream.toByteArray());
    }

    @PostMapping("/decode")
    public ResponseEntity<DecodeQrResponse> decodeQr(@RequestParam("qrCode") MultipartFile qrCode) {
        try {
            validateQrCodeFile(qrCode);

            String qrCodeString = qrCodeService.decodeQr(qrCode.getBytes());
            return ResponseEntity.ok(new DecodeQrResponse(qrCodeString));
        } catch (IOException | NotFoundException e) {
            // Handle exceptions appropriately, e.g., log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (InvalidQrCodeException e) {
            // Handle invalid QR code file
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private void validateQrCodeFile(MultipartFile qrCode) throws InvalidQrCodeException {
        if (qrCode.isEmpty()) {
            throw new InvalidQrCodeException("QR code file is empty");
        }
    }
}
