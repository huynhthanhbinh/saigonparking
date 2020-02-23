package com.bht.parkingmap.android.util;

import com.google.protobuf.ByteString;

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
     */
    public static ByteString encodeImage(byte[] imageData) {
        return ByteString.copyFrom(imageData);
    }

    /**
     *
     * using from receive's side
     */
    public static byte[] decodeImage(ByteString imageData) {
        return imageData.toByteArray();
    }
}