package hello.cafemate.service;

import hello.cafemate.domain.Member;
import hello.cafemate.web.dto.user.MemberDto;
import hello.cafemate.dto.update_dto.MemberUpdateDto;
import hello.cafemate.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberDto dto) {
        Member member = dtoToEntity(dto);
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<MemberDto> findAll() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberDto> memberDtoList =
                memberList.stream().map(this::entityToDto).collect(Collectors.toList());

        return memberDtoList;
    }

    public MemberDto findOne(String memberId) {
        return entityToDto(memberRepository.findByMemberId(memberId).get(0));
    }

    public void updateOne(Long id, MemberUpdateDto updateParam) {
        memberRepository.update(id, updateParam);
    }

    public void deleteOne(Member member) {
        memberRepository.deleteById(member.getId());
    }


    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByMemberId(member.getMemberId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회웝입니다.");
        }
    }

    private Member dtoToEntity(MemberDto formDto) {
        return new Member(formDto.getMemberId(), formDto.getPassword(), formDto.getName(),
                formDto.getPhoneNumber(), formDto.getEMail(), formDto.isAdmin());
    }

    private MemberDto entityToDto(Member member) {
        return new MemberDto(member.getMemberId(), member.getPassword(),
                member.getName(), member.getPhoneNumber(), member.getEMail(), member.isIsAdmin());
    }

}
