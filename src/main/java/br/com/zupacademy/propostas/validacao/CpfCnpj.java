package br.com.zupacademy.propostas.validacao;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)
@ReportAsSingleViolation
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfCnpj {
    String message() default "DOCUMENTO INVÁLIDO: o documento precisa ser um cpf ou um cnpj";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}