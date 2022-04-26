package com.example.whatsappbackendtest.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@UtilityClass
public class DecodeImageAsBase64 {

    public static byte[] decodeAsByteArray(String base64Image) {
        if (StringUtils.isBlank(base64Image)) return null;
        byte[] content = base64Image.getBytes(StandardCharsets.UTF_8);
        return Base64.getDecoder().decode(content);
    }

}
