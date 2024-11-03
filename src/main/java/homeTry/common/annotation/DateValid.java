package homeTry.common.annotation;

import homeTry.common.annotation.validator.DateValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface DateValid {
    String message() default ""; // 4

    Class[] groups() default {};

    Class[] payload() default {};
}
