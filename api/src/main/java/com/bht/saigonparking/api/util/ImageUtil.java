package com.bht.saigonparking.api.util;

import com.google.protobuf.ByteString;
import com.google.protobuf.Internal;

/**
 * using this util class to encode & decode
 * to communicate between server and client
 * using protocol buffer of gRPC
 *
 * @author bht
 */
public final class ImageUtil {

    private ImageUtil() {
    }

    /**
     *
     * using from send's side
     * to encode image data
     * for sending image purpose
     */
    public static ByteString encodeImage(byte[] imageData) {
        return ByteString.copyFrom((imageData != null) ? imageData : Internal.EMPTY_BYTE_ARRAY);
    }

    /**
     *
     * using from receive's side
     * to decode image data
     * for reading image purpose
     */
    public static byte[] decodeImage(ByteString imageData) {
        return imageData.toByteArray();
    }

    /**
     *
     * using from receive's side
     * to check if the imageData received is empty or not
     */
    public static boolean isDecodedImageEmpty(byte[] imageData) {
        return imageData.length == 0;
    }
}