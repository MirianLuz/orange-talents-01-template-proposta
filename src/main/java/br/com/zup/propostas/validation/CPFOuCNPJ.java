package br.com.zup.propostas.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Documented
@Constraint(validatedBy = { })
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)
public @interface CPFOuCNPJ {

  String message() default "Precisa ser um CPF ou CNPJ válido.";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}