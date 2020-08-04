package com.bht.saigonparking.service.contact.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.service.contact.service.QrCodeService;
import com.google.protobuf.Internal;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class QrCodeServiceImpl implements QrCodeService {

    private final QRCodeWriter qrCodeWriter;

    @Value("${qr-code.width}")
    private Integer qrCodeWidth;

    @Value("${qr-code.height}")
    private Integer qrCodeHeight;

    @Override
    public byte[] encodeContents(@NotNull String contents) throws WriterException, IOException {
        if (!contents.isEmpty()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BitMatrix bitMatrix = qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        return Internal.EMPTY_BYTE_ARRAY;
    }
}