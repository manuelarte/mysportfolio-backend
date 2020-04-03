package org.manuel.mysportfolio.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@lombok.RequiredArgsConstructor
public class ExistsIterableValidator implements
    ConstraintValidator<Exists, Iterable<ObjectId>> {

  private final MongoTemplate mongoTemplate;
  private Class<?> entity;

  @Override
  public void initialize(final Exists exists) {
    this.entity = exists.entity();
  }

  @Override
  public boolean isValid(final Iterable<ObjectId> ids, final ConstraintValidatorContext cxt) {
    return (ids == null || ids.iterator().hasNext() == false)
        || mongoTemplate.exists(new Query(Criteria.where("_id").in(ids)), entity);
  }

}
