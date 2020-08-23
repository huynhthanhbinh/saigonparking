package com.bht.saigonparking.service.booking.service.main;

import java.io.IOException;

import com.bht.saigonparking.common.annotation.UuidStringValidation;
import com.google.zxing.WriterException;

/**
 *
 * @author bht
 */
public interface QrCodeService {

    byte[] encodeContents(@UuidStringValidation String contents) throws WriterException, IOException;
}