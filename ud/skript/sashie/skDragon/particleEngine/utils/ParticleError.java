package ud.skript.sashie.skDragon.particleEngine.utils;

import ud.skript.sashie.skDragonCore;

public class ParticleError extends RuntimeException {
   public ParticleError(String error) {
      super((String)null, (Throwable)null, true, false);
      skDragonCore.error(error);
   }
}
