package com.litovchenko.carsapp.service;

import org.apache.commons.validator.routines.EmailValidator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginService {

    public static String validateData(String login, String email, String password, String confirmPassword) {
        if(!password.equals(confirmPassword)){
            return "Passwords don't match.";
        }
        if(!login.matches("([A-Za-z0-9_]+){5,15}")){
         return "Login is not valid.";
        }
        if(!EmailValidator.getInstance().isValid(email)){
            return "Email is not valid.";
        }
        if(!password.matches("([A-Za-z0-9_]+){5,15}")){
            return "Password is not valid.";
        }
        return "OK";
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
        return generatedPassword;
    }

}
