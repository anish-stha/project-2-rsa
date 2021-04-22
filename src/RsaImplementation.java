import java.util.*;
import java.math.BigInteger;


import static java.lang.Math.pow;

public class RsaImplementation {
    BigInteger p;
    BigInteger q;
    BigInteger n, phi, d, e;
    private int  bitlength = 1024;

    public boolean isPrime(int num) {
        boolean flag = false;
        for (int i = 2; i <= num / 2; ++i) {
            // condition for non prime number
            if (num % i == 0) {
                flag = true;
                break;
            }
        }
        return !flag;
    }

    public RsaImplementation() {
        Random r = new Random();
        List<Integer> primes = new ArrayList();
        for (int i = 1000; i < 10000; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        this.p = BigInteger.valueOf(primes.get(9)); //  The 10th prime between 1000 and 10000
        this.q = BigInteger.valueOf(primes.get(18)); // The 19th prime between 1000 and 10000
        System.out.println("the value of p: " + p);
        System.out.println("the value of q: " + q);
        this.n = p.multiply(q);
        // compute the totient, phi
        this.phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
        {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
        // find an 'e' which is > 1 and is a co-prime of phi.

        System.out.println("the value of p: " + p);
        System.out.println("the value of q: " + q);
        System.out.println("the value of n: " + n);
        System.out.println("the value of phi: " + phi);
        System.out.println("the value of e: " + e);
        System.out.println("the value of d: " + d);

    }

    // Encrypt message
    public BigInteger encrypt(long message) {
        return BigInteger.valueOf(message).modPow(e, n);
    }

    // Decrypt message
    public BigInteger decrypt(BigInteger message) {
        return message.modPow(d, n);
    }

    static int gcd(int e, int z) {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }

    public static void main(String[] args) {
        RsaImplementation rsaImplementation = new RsaImplementation();
        System.out.println("-----------------------------------------------------------");
        System.out.print("Enter message to be encrypted and decrypted:");
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        System.out.println("Equivalent number representation [0-25] is " + getNumbersFromLetters(message));
        BigInteger encrypted = rsaImplementation.encrypt(getNumbersFromLetters("rsa"));
        System.out.println("Encrypted: " + encrypted);
        BigInteger decrypted = rsaImplementation.decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);
        System.out.println("Decrypted into plain text: " + getLettersFromNumbers(decrypted.longValue()));
    }

    public static long getNumbersFromLetters(String message) {
        message = message.toUpperCase();
        long numberRepresentation = 0;
        for (int i = 0; i < message.length(); i++)
            numberRepresentation = numberRepresentation * 100 + (message.charAt(i) - 'A');
        return numberRepresentation;
    }

    public static String getLettersFromNumbers(long numberRepresentation) {
        String message = "";
        while (numberRepresentation > 0) {
            message = message + (char) (numberRepresentation % 100 + 65) ;
            numberRepresentation = numberRepresentation / 100;
        }
        return new StringBuilder(message).reverse().toString().toLowerCase();
    }
}
