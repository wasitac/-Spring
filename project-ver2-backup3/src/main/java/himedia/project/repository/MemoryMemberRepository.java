package himedia.project.repository;

import java.util.ArrayList;
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

	public MemoryMemberRepository() {
		System.out.println("MemoryMemberRepository 생성자 실행");
	}
	@Override
	public Member save(Member member) {
		member.setId(++sequence);
		
		// 동시성 제어. setId와 put에서의 sequence가 같은 값이 아닐 수도 있기 때문에 getter 사용.
//		store.put(sequence, member);
		store.put(member.getId(), member);
		return member;
	}
	
	@Override
	public Optional<Member> findById(Long id) {
			//문제
			return Optional.ofNullable(store.get(id));
	}
	
	@Override
	public Optional<Member> findByName(String name) {
			//문제
//		for ( Entry<Long, Member> s : store.entrySet()) {
//			if (s.getValue().getName().equals(name)) {
//				return Optional.ofNullable(store.get(s.getKey()));
//			}			
//		}
		// 더 쉽게 할 수 있었는데
//		for (Member m : store.values()) {
//			if (m.getName().equals(name)) {
//				return Optional.ofNullable(m);
//			}			
//		}
//		return Optional.empty();
		
		// [문제] 위 코드를 stream으로 변경
		return store.values().stream()
		.filter(m -> m.getName().equals(name))
		.findAny();
	}
	
	@Override
	public List<Member> findAll() {
			//문제
		return new ArrayList<>(store.values());
	}
	
}
