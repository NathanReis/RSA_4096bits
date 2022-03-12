package security;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
  private final int BIT_LENGTH = 4096;
  private final SecureRandom RANDOM = new SecureRandom();

  private BigInteger _p = BigInteger.ZERO;
  private BigInteger _q = BigInteger.ZERO;
  private BigInteger _n = BigInteger.ZERO;
  private BigInteger _phiN = BigInteger.ZERO;
  private BigInteger _e = BigInteger.ZERO;
  private BigInteger _d = BigInteger.ZERO;

  public String encrypt(String message) {
    String cipheredMessage = new BigInteger(message.getBytes()).modPow(this.getPublicKey(), this.getN()).toString();

    Log.log("Criptografado pelo RSA: " + cipheredMessage);

    return cipheredMessage;
  }

  public String decrypt(String cipheredMessage) {
    String decipheredMessage = new String(
        new BigInteger(cipheredMessage).modPow(this.getPrivateKey(), this.getN()).toByteArray());

    Log.log("Decriptografado pelo RSA: " + decipheredMessage);

    return decipheredMessage;
  }

  private BigInteger generatePrimeNumber() {
    return new BigInteger(this.BIT_LENGTH / 2, 100, this.RANDOM);
  }

  private BigInteger getP() {
    if (this._p.compareTo(BigInteger.ZERO) == 0) {
      this._p = this.generatePrimeNumber();

      Log.log("p: " + this._p);
    }

    return this._p;
  }

  private BigInteger getQ() {
    if (this._q.compareTo(BigInteger.ZERO) == 0) {
      this._q = this.generatePrimeNumber();

      while (this._q.compareTo(this._p) == 0) {
        this._q = this.generatePrimeNumber();
      }

      Log.log("q: " + this._q);
    }

    return this._q;
  }

  private BigInteger calcN(BigInteger p, BigInteger q) {
    return p.multiply(q);
  }

  private BigInteger getN() {
    if (this._n.compareTo(BigInteger.ZERO) == 0) {
      this._n = this.calcN(this.getP(), this.getQ());

      Log.log("N: " + this._n);
    }

    return this._n;
  }

  private BigInteger calcPhiN(BigInteger p, BigInteger q) {
    return (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
  }

  private BigInteger getPhiN() {
    if (this._phiN.compareTo(BigInteger.ZERO) == 0) {
      this._phiN = this.calcPhiN(this.getP(), this.getQ());

      Log.log("phi(N): " + this._phiN);
    }

    return this._phiN;
  }

  private BigInteger calcPublicKey(BigInteger phiN) {
    // BigInteger e = BigInteger.TWO;

    // for (BigInteger i = phiN; i.compareTo(BigInteger.TWO) >= 0; i =
    // i.subtract(BigInteger.ONE)) {
    // if (phiN.mod(i).compareTo(BigInteger.ZERO) != 0 && i.isProbablePrime(100)) {
    // e = i;
    // break;
    // }
    // }

    BigInteger e = new BigInteger(this.BIT_LENGTH / 4, 100, this.RANDOM);

    while (phiN.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phiN) < 0) {
      e.add(BigInteger.ONE);
    }

    return e;
  }

  private BigInteger getPublicKey() {
    if (this._e.compareTo(BigInteger.ZERO) == 0) {
      this._e = this.calcPublicKey(this.getPhiN());

      Log.log("Public key: " + this._e);
    }

    return this._e;
  }

  private BigInteger calcPrivateKey(BigInteger e, BigInteger phiN) {
    return e.modInverse(phiN);
  }

  private BigInteger getPrivateKey() {
    if (this._d.compareTo(BigInteger.ZERO) == 0) {
      this._d = this.calcPrivateKey(this.getPublicKey(), this.getPhiN());

      Log.log("Private key: " + this._d);
    }

    return this._d;
  }
}
