package br.com.zupacademy.propostas.validacao;

import br.com.zupacademy.propostas.erro.RegraNegocioException;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private Class<?> klass;
    private String domainAttribute;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.domainAttribute = constraintAnnotation.fieldName();
        this.klass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query sql = entityManager.createQuery("select 1 from " + klass.getName() + " where " + domainAttribute + " = " +
                ":value");
        sql.setParameter("value", value);

        List<?> retorno = sql.getResultList();

        if (!retorno.isEmpty() && klass.getSimpleName().equals("Proposta")) {
            throw new RegraNegocioException("Documento já consta no sistema, não é possível adicionar registro duplicado.",
                    domainAttribute, value);
        }

        return retorno.isEmpty();
    }
}