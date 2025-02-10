package LabTask4;

import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class RC4ClientSide {

    private static int[] S = new int[256]; // S-Box
    private static int[] K = new int[256]; // Key Array

    public static void KSA(String key) {
        int keyLength = key.length();
        int j = 0;

        // Initialize S-box
        for (int i = 0; i < 256; i++) {
            S[i] = i;
            K[i] = key.charAt(i % keyLength);
        }

        // Key Scheduling
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + K[i]) % 256;
            // Swap S[i] and S[j]
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }

        // Print KSA Array (S-box)
        System.out.println("KSA S-Box: " + Arrays.toString(S));
    }

    public static int[] PRGA(int length) {
        int i = 0, j = 0;
        int[] keystream = new int[length];

        for (int k = 0; k < length; k++) {
            i = (i + 1) % 256;
            j = (j + S[i]) % 256;

            // Swap S[i] and S[j]
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;

            // Generate keystream byte
            keystream[k] = S[(S[i] + S[j]) % 256];
        }

        // Print PRGA (Keystream)
        System.out.println("PRGA Keystream: " + Arrays.toString(keystream));
        return keystream;
    }

    public static byte[] encrypt(String plaintext) {
        int length = plaintext.length();
        int[] keystream = PRGA(length);
        byte[] ciphertext = new byte[length];

        for (int i = 0; i < length; i++) {
            ciphertext[i] = (byte) (plaintext.charAt(i) ^ keystream[i]); // XOR operation
        }

        System.out.println("Encrypted Ciphertext: " + Arrays.toString(ciphertext));
        return ciphertext;
    }

    public static void main(String[] args) throws IOException {

        Socket s = new Socket("localhost", 5000);
        System.out.println("Connected to the server.");

        DataOutputStream output = new DataOutputStream(s.getOutputStream());

        // Read plaintext from file
        File file = new File("src/LabTask4/plaintext.txt");
        String plainText = new String(Files.readAllBytes(file.toPath()));

        // RC4 encryption

        String key = "JayanthCT"; // Define your encryption key
        KSA(key);
        byte[] encryptedData = encrypt(plainText);

        //sent
        output.write(encryptedData);

        System.out.println("Encrypted text has been sent to the server.");

        // Close resources
        output.close();
        s.close();
    }
}
