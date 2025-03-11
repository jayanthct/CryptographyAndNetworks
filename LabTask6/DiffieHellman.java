package LabTask6;
import java.math.BigInteger;
import java.util.Random;
public class DiffieHellman {
    public static void main(String[] args) {

                // Generate two large prime numbers g and n
                BigInteger g = new BigInteger("23");  // Primitive root
                BigInteger n = new BigInteger("563"); // Prime number

                // Private keys chosen by Alice and Bob
                BigInteger a = new BigInteger(1024, new Random());  // Alice's private key
                BigInteger b = new BigInteger(1024, new Random());  // Bob's private key

                // Step 3: Compute public keys
                BigInteger A = g.modPow(a, n);  // A = g^a mod n
                BigInteger B = g.modPow(b, n);  // B = g^b mod n

                // Exchange of public keys between Alice and Bob
                System.out.println("Public Key of Alice (A): " + A);
                System.out.println("Public Key of Bob (B): " + B);

                // Step 5: Compute shared secret key
                BigInteger K1 = B.modPow(a, n);  // K1 = B^a mod n (Alice computes)
                BigInteger K2 = A.modPow(b, n);  // K2 = A^b mod n (Bob computes)

                // Step 6: Check if both keys are equal
                if (K1.equals(K2)) {
                    System.out.println("Shared Secret Key (K): " + K1);
                } else {
                    System.out.println("Error: Keys do not match!");
                }
            }
        }