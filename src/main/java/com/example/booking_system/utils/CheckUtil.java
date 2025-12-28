package com.example.booking_system.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.booking_system.exception.BusinessException;

public class CheckUtil {

    public static void throwUniqueException(Exception e, Map<String, String> duplicateKeyException)
            throws BusinessException {
        Pattern pattern = Pattern.compile("\"(.*?)\""); // matches the double quote for extraction
        Matcher matcher = pattern.matcher(e.getMessage());
        if (matcher.find()) {
            throw new BusinessException(duplicateKeyException.get(matcher.group(1)));
        }
    }
}
