package com.rubiconwater.codingchallenge.joshluisaac.sharedkernel;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface EntityRepository<T extends BaseEntity> {

  Optional<T> findById(UUID id);

  Collection<T> findAll();

  void delete(T t);

  void save(T t);

  void update(T t);

  void deleteById(UUID id);
}
