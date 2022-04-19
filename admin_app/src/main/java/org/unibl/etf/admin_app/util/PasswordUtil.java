package org.unibl.etf.admin_app.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PasswordUtil {

    public static String generateRandomPassword() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String nums = "1234567890";

        StringBuilder sb = new StringBuilder();
        sb.append(RandomStringUtils.random(5, chars));
        sb.append(RandomStringUtils.random(5, CHARS));
        sb.append(RandomStringUtils.random(5, nums));
        List<String> list = new ArrayList<String>(Arrays.asList(sb.toString().split("")));
        Collections.shuffle(list);

        return String.join("", list);
    }

    public static String getPasswordBCryptHash(String password){
        return BCrypt.withDefaults().hashToString(6, password.toCharArray());
    }
}
