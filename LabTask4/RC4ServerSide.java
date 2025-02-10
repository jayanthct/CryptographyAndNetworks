package LabTask4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class RC4ServerSide {

    private static int[] S = new int[256]; // S-Box
    private static int[] K = new int[256]; // Key Array

    public static void KSA(String key) {
        int keyLength = key.length();
        int j = 0;

        for (int i = 0; i < 256; i++) {
            S[i] = i;
            K[i] = key.charAt(i % keyLength);
        }

        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + K[i]) % 256;
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
    }

    public static int[] PRGA(int length) {
        int i = 0, j = 0;
        int[] keystream = new int[length];

        for (int k = 0; k < length; k++) {
            i = (i + 1) % 256;
            j = (j + S[i]) % 256;

            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;

            keystream[k] = S[(S[i] + S[j]) % 256];
        }
        return keystream;
    }

    public static byte[] decrypt(byte[] encryptedText, int[] keystream) {
        int length = encryptedText.length;
        byte[] decryptedText = new byte[length];

        for (int i = 0; i < length; i++) {
            decryptedText[i] = (byte) (encryptedText[i] ^ keystream[i]);
        }

        System.out.println("Decrypted Text: " + new String(decryptedText));
        return decryptedText;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server is listening on port 5000...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        DataInputStream input = new DataInputStream(socket.getInputStream());

        // Read encrypted data from client
        byte[] encryptedData = input.readAllBytes();
        System.out.println("Received Encrypted Data: " + Arrays.toString(encryptedData));

        // Save encrypted text to encrypt.txt
        File encryptFile = new File("src/LabTask4/encrypt.txt");
        try (FileOutputStream f = new FileOutputStream(encryptFile)) {
            f.write(encryptedData);
        }
        System.out.println("Encrypted text saved to encrypt.txt");

        // Decrypt
        String key = "JayanthCT"; // Same key as client
        KSA(key);
        int[] keystream = PRGA(encryptedData.length);
        byte[] decryptedData = decrypt(encryptedData, keystream);

        // Save decrypted text to decrypt.txt
        File decryptFile = new File("src/LabTask4/decrypt.txt");
        try (FileOutputStream f = new FileOutputStream(decryptFile)) {
            f.write(decryptedData);
        }
        System.out.println("Decrypted text saved to decrypt.txt");

        // Close resources
        input.close();
        socket.close();
        serverSocket.close();
    }
}
