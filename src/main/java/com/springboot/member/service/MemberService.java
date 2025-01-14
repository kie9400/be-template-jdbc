package com.springboot.member.service;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * V2
 *  - 메서드 구현
 *  - DI 적용
 */
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        // TODO should business logic
        verifyExistsEmail(member.getEmail());
        //save 메서드는 db에 저장하면서도 return한다.
        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        //Optional로 만든다.
        //만약 이름값이 있다면 이름을 수정한다.
        //null값이라면 수정하지 않는다는 것
        //즉, member의 name이 null이 아니라면 수정한다는 것
        //메서드 체이닝 사용
        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));

        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));
        //이해하기쉽게 if문으로 예시 (optional을 무조건써야하는데 설명하기 위해)
        //가독성을 좋게하기 위해서 Optional를 사용
        //null처리는 Optional이 기본이다.
//        if(member.getName() != null){
//            findMember.setName(member.getName());
//        }

        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId) {
        // TODO should business logic
        Member findMember = findVerifiedMember(memberId);
        return findMember;
    }

    public List<Member> findMembers() {
        return (List<Member>)memberRepository.findAll();
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);
        //findMember에는 찾은 데이터가 있기에 넣으면된다.
        memberRepository.delete(findMember);
    }

    private void verifyExistsEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        //이메일 중복여부를 검사
        if(optionalMember.isPresent()){
            //이미 이메일이 존재할 경우
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
    //멤버 id를 찾기 위한 메서드
    public Member findVerifiedMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        //만약 없다면 Throw를 던지겠다. 있다면 그 값을 변수에 저장하겠다.
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }
}
