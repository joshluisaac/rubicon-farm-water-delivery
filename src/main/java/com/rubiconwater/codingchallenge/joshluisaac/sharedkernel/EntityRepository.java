package com.rubiconwater.codingchallenge.joshluisaac.sharedkernel;

public interface EntityRepository<T extends BaseEntity> {

  void delete(T t);

  T save(T t);

  void update(T t);
}
