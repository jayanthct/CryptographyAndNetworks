package LabTask6;

import java.math.BigInteger;

public class BigIntegerOperations {
    public static void main(String[] args) {
        BigInteger a = new BigInteger("182841384165841685416854134135");
        BigInteger b = new BigInteger("135481653441354138548413384135");

       //operations
        BigInteger sum = a.add(b);
        BigInteger difference = a.subtract(b);
        BigInteger product = a.multiply(b);
        BigInteger division = a.divide(b);

        //modulor division gives remainder % operation
        BigInteger remainder = a.remainder(b);

        BigInteger gcd = a.gcd(b);
        BigInteger modinverse = null;
        if(gcd.equals(BigInteger.ONE)) {
            modinverse = a.modInverse(b);
        }else{
            BigInteger reducedA = a.divide(gcd);
            BigInteger reducedB = b.divide(gcd);

            if (reducedA.gcd(reducedB).equals(BigInteger.ONE)) {
                //reduced inverse
                modinverse = reducedA.modInverse(reducedB);
            }{
                System.out.println("NO MOD INVERSE FOUND FOR THIS");
            }
        }

        System.out.println("Adding BigIntegers "+ sum);
        System.out.println("Subracting BigIntegers "+ difference);
        System.out.println("Product of BigIntegers "+ product);
        System.out.println("Division BigIntegers "+ division);
        System.out.println("modulo BigIntegers "+ remainder);
        System.out.println("modulo inverse BigIntegers "+ modinverse);
    }
}
