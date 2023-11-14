package himedia.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import himedia.project.domain.Member;
import himedia.project.repository.MemberRepository;

@Service
public class MemberService {
//  service클래스 내에서 객체를 생성하면 service 클래스 안에서 관리되기 때문에 결합도가 올라간다.
//  의존성 주입으로 외부에서 생성된 객체를 사용하는 형태로 만든다.
//	private final MemberRepository repo = new MemoryMemberRepository();
	
	//[문제] constructor D.I
	private final MemberRepository repo;
	
	public MemberService(MemberRepository repo) {
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
