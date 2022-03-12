package security;

public class Log {
  private static boolean _isActive = false;

  public static boolean getIsActive() {
    return _isActive;
  }

  public static void setIsActive(boolean isActive) {
    _isActive = isActive;
  }

  public static void log(String message) {
    if (_isActive) {
      System.out.println("Log >> " + message);
    }
  }
}
