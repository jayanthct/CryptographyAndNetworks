package LabTask8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512 {

    public static String SHA512Generator(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String PlainText = "JayanthCT";
        System.out.println("HashCode using SHA512 : "+SHA512Generator(PlainText));
        System.out.println("HashCode using SHA512 : "+SHA512Generator(PlainText).length());
    }
}
