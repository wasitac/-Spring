package himedia.project.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import himedia.project.domain.Member;

@Repository
public class MemoryMemberRepository implements MemberRepository{
	
	private static Map<Long, Member> store = new HashMap<>();
	private static Long sequence = 0L;
	
	@Override
	public Member save(Member member) {
		System.out.println("[id 설정 전] id >> " + member.getId());
		System.out.println("[name 설정 전] name >> " + member.getName());
		member.setId(++sequence);
		System.out.println("[id 설정 후] id >> " + member.getId());
		System.out.println("[id 설정 후] name >> " + member.getName());
		
		// 동시성 제어. setId와 put에서의 sequence가 같은 값이 아닐 수도 있기 때문에 getter 사용.
//		store.put(sequence, member);
		store.put(member.getId(), member);
		return member;
	}
	
	@Override
	public Optional<Member> findById(Long id) {
			//문제
		return Optional.empty();
	}
	
	@Override
	public Optional<Member> findByName(String name) {
			//문제
		return Optional.empty();
	}
	@Override
	public List<Member> findAll() {
			//문제
		//return new ArrayList<>(인수);
		return null;
	}
	
}
