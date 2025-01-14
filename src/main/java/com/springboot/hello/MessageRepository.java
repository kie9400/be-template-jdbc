package com.springboot.hello;

import org.springframework.data.repository.CrudRepository;

//CrudRepository<어떤 엔티티 계층을 다루는지, 기본키의 데이터 타입>
public interface MessageRepository extends CrudRepository<Message, Long> {
}
