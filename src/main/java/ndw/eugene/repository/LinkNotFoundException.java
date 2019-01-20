package ndw.eugene.repository;

public class LinkNotFoundException extends RuntimeException {
  public LinkNotFoundException(String message) {
    super(message);
  }
}
