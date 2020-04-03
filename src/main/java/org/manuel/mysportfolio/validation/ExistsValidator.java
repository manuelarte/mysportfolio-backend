package org.manuel.mysportfolio.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@lombok.RequiredArgsConstructor
public class ExistsValidator implements ConstraintValidator<Exists, ObjectId> {

  private final MongoTemplate mongoTemplate;
  private Class<?> entity;

  @Override
  public void initialize(final Exists exists) {
    this.entity = exists.entity();
  }

  @Override
  public boolean isValid(final ObjectId id, final ConstraintValidatorContext cxt) {
    return id == null || mongoTemplate.exists(new Query(Criteria.where("_id").is(id)), entity);
  }

}
