package himedia.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import himedia.project.domain.Member;
import himedia.project.repository.MemberRepository;

@Service
public class MemberService {
	
//	private final MemberRepository repo = new MemoryMemberRepository();
	//[문제] constructor D.I
	private final MemberRepository repo;
	
	public MemberService(/* @Qualifier("jdbcTemplateMemberRepository") */ MemberRepository repo) {
		this.repo = repo;
	}
	/**
	 * [저장] 회원 가입 
	 */
	public Long join(Member member) {
		repo.save(member);
		return member.getId();
	}
	
	/**
	 * [조회] 아이디로 조회
	 */
	public Optional<Member> findId(Long id) {
		return repo.findById(id);
	}
	
	/**
	 * [조회] 이름으로 조회
	 */
	public Optional<Member> findName(String name) {
		return repo.findByName(name);
	}
	
	/**
	 * [조회] 전체 조회
	 */
	public List<Member> findMember() {
		return repo.findAll();
	}
	
}
