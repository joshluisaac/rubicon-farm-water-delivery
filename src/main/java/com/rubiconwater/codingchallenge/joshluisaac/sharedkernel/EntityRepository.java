package com.rubiconwater.codingchallenge.joshluisaac.sharedkernel;

import java.util.UUID;

public interface EntityRepository<T extends BaseEntity> {

  void delete(T t);

  void save(T t);

  void update(T t);

  void deleteById(UUID id);
}
