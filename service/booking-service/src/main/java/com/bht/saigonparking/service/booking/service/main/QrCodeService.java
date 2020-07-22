package com.bht.saigonparking.service.booking.service.main;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import com.google.zxing.WriterException;

/**
 *
 * @author bht
 */
public interface QrCodeService {

    byte[] encodeContents(@NotNull String contents) throws WriterException, IOException;
}