package ud.skript.sashie.skDragon.packets;

public class PacketSendingException extends RuntimeException {
   private static final long serialVersionUID = 3203085387160737484L;

   public PacketSendingException(String message, Throwable cause) {
      super(message, cause);
   }
}
