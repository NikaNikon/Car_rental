package com.litovchenko.carsapp.service;

import org.apache.commons.validator.routines.EmailValidator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginService {

    public static boolean isValid(String email, String password) {
        boolean isEmailValid = EmailValidator.getInstance().isValid(email);
        return false;
    }

    public static String hashPassword(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Cannot find algorithm to hash the password" + System.lineSeparator() + e);
        }
        System.out.println(generatedPassword);
        return generatedPassword;
    }

}
