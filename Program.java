import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import security.CesarCipher;
import security.Log;
import security.RSA;

public class Program {
  private static CesarCipher cesarCipher = new CesarCipher();
  private static RSA rsa = new RSA();
  private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static String lastEncryptedMessage = "";

  public static void main(String[] args) {
    try {
      menuState();
    } catch (Exception e) {
      System.out.println("! Erro inesperado, programa será encerrado !");
    }
  }

  private static void menuState() {
    System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
    System.out.println("MENU");
    System.out.println("----");
    System.out.println("Escolha uma das opções:\n");
    System.out.println("1) " + (Log.getIsActive() ? "Desa" : "A") + "tivar logs");
    System.out.println("2) Criptografar uma mensagem");
    System.out.println("3) Decriptografar última mensagem");
    System.out.println("4) Decriptografar uma mensagem");
    System.out.println("0) Sair\n");

    try {
      String option = reader.readLine().trim();

      switch (option) {
        case "0":
          System.out.println("Bye bye");
          break;

        case "1":
          Log.setIsActive(!Log.getIsActive());
          System.out.println("! Os logs foram " + (Log.getIsActive() ? "" : "des") + "ativados !");
          menuState();
          break;

        case "2":
          encryptState();
          break;

        case "3":
          decryptLastState();
          break;

        case "4":
          decryptState();
          break;

        default:
          System.out.println("! Opção inválida !");
          menuState();
          break;
      }
    } catch (IOException e) {
      System.out.println("! Entrada de dados inválida !");
      menuState();
    }
  }

  private static void encryptState() {
    System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
    System.out.println("Informe uma mensagem:");

    try {
      String message = reader.readLine().trim();
      String cipheredMessage = cesarCipher.cipher(message);
      lastEncryptedMessage = rsa.encrypt(cipheredMessage);

      System.out.println("\n\nSua mensagem criptografada");
      System.out.println(lastEncryptedMessage + "\n\n");

      menuState();
    } catch (IOException e) {
      System.out.println("! Entrada de dados inválida !");
      encryptState();
    }
  }

  private static void decryptLastState() {
    System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");

    if (lastEncryptedMessage.isBlank()) {
      System.out.println("! Ainda foi foi feita uma criptografia !");
    } else {
      String decryptedMessage = rsa.decrypt(lastEncryptedMessage);
      String decipheredMessage = cesarCipher.decipher(decryptedMessage);

      System.out.println("\n\nSua mensagem decriptografada");
      System.out.println(decipheredMessage + "\n\n");
    }

    menuState();
  }

  private static void decryptState() {
    System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
    System.out.println("Informe uma mensagem criptografada:");

    try {
      String encryptedMessage = reader.readLine().trim();
      String decryptedMessage = rsa.decrypt(encryptedMessage);
      String decipheredMessage = cesarCipher.decipher(decryptedMessage);

      System.out.println("\n\nSua mensagem decriptografada");
      System.out.println(decipheredMessage + "\n\n");

      menuState();
    } catch (IOException e) {
      System.out.println("! Entrada de dados inválida !");
      decryptState();
    } catch (NumberFormatException e) {
      System.out.println("! Mensagem criptografada deve conter apenas números !");
      decryptState();
    }
  }
}
