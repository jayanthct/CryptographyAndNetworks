package LABTask5;

public class RSAKeyGeneration {

        private int p = 23;
        private int q = 31;
        public int n = p * q;
        private int phi = (p - 1) * (q - 1);
        public int e;
        public int d;

        public RSAKeyGeneration() {
            e = findE(phi);
            d = findD(e, phi);
            System.out.println("Public Key: (" + e + ", " + n + ")");
            System.out.println("Private Key: (" + d + ", " + n + ")");
        }

        private int findE(int phi) {
            int e = 2;
            while (e < phi) {
                if (gcd(e, phi) == 1) {
                    return e;
                }
                e++;
            }
            return -1;
        }

        private int gcd(int a, int b) {
            if (b == 0) return a;
            return gcd(b, a % b);
        }

        private int findD(int e, int phi) {
            int d = 1;
            while ((d * e) % phi != 1) {
                d++;
            }
            return d;
        }

    public static int modExp(int base, int exp, int mod) {
        int result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }

    public static char Encrypt(int e, int n, char p) {
        return (char) modExp(p, e, n);
    }

    public static char Decrypt(int d, int n, char c) {
        return (char) modExp(c, d, n);
    }

    public static void main(String[] args) {
        RSAKeyGeneration RSA = new RSAKeyGeneration();
        char plaintext = 'A';
        char ciphertext = Encrypt(RSA.e, RSA.n, plaintext);
        System.out.println("Plain text : "+plaintext);
        System.out.println("Encrypted: " + (int) ciphertext);
        System.out.println("Decrypted: " + Decrypt(RSA.d, RSA.n, ciphertext));
    }
}
