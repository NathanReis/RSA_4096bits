package security;

import java.util.Random;

public class CesarCipher {
  private int _evenKey = 0;
  private int _oddKey = 0;

  public String cipher(String message) {
    StringBuilder cipheredMessage = new StringBuilder();
    int amountChars = message.length();
    int evenKey = this.getEvenKey();
    int oddKey = this.getOddKey();

    for (int i = 0; i < amountChars; i++) {
      if (i % 2 == 0) {
        cipheredMessage.append((char) (message.charAt(i) + evenKey));
      } else {
        cipheredMessage.append((char) (message.charAt(i) + oddKey));
      }
    }

    Log.log("Cifrado pelo César: " + cipheredMessage);

    return cipheredMessage.toString();
  }

  public String decipher(String cipheredMessage) {
    StringBuilder decipheredMessage = new StringBuilder();
    int amountChars = cipheredMessage.length();
    int evenKey = this.getEvenKey();
    int oddKey = this.getOddKey();

    for (int i = 0; i < amountChars; i++) {
      if (i % 2 == 0) {
        decipheredMessage.append((char) (cipheredMessage.charAt(i) - evenKey));
      } else {
        decipheredMessage.append((char) (cipheredMessage.charAt(i) - oddKey));
      }
    }

    Log.log("Decifrado pelo César: " + decipheredMessage);

    return decipheredMessage.toString();
  }

  private int generateKey() {
    int key = 0;
    Random random = new Random();

    while (key == 0) {
      key = random.nextInt(11);
    }

    return key;
  }

  private int getEvenKey() {
    while (this._evenKey == 0 || this._evenKey % 2 == 0) {
      this._evenKey = this.generateKey();

      Log.log("Chave par do César: " + this._evenKey);
    }

    return this._evenKey;
  }

  private int getOddKey() {
    while (this._oddKey == 0 || this._oddKey % 2 != 0) {
      this._oddKey = this.generateKey();

      Log.log("Chave ímpar do César: " + this._oddKey);
    }

    return this._oddKey;
  }
}
