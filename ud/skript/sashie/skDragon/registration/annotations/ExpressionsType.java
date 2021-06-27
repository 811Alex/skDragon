package ud.skript.sashie.skDragon.registration.annotations;

import ch.njol.skript.lang.ExpressionType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExpressionsType {
   ExpressionType value();
}
