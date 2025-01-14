package com.springboot.member.repository;

import com.springboot.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//스프링에서 조회하는 기능은 보통 Optional로 래핑되서 나온다.
//스프링은 네이밍 규칙만 지킨다면 쿼리를 만들어준다.
public interface MemberRepository extends CrudRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
