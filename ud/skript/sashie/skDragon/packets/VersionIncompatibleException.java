package ud.skript.sashie.skDragon.packets;

public class VersionIncompatibleException extends RuntimeException {
   private static final long serialVersionUID = 3203085387160737484L;

   public VersionIncompatibleException(String message, Throwable cause) {
      super(message, cause);
   }
}
