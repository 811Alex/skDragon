package ud.skript.sashie.skDragon.emotes;

public class NoSuchEmoteException extends Exception {
   public NoSuchEmoteException(String type) {
      super("There is no emote type or custom emote called '" + type + "' ");
   }
}
