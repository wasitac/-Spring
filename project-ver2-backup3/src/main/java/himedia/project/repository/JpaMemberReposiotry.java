package himedia.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import himedia.project.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Primary
@Repository
@Transactional
public class JpaMemberReposiotry implements MemberRepository{
	private final EntityManager em;
	
	JpaMemberReposiotry(EntityManager em){
		this.em = em;
		
	}

	@Override
	public Member save(Member member) {
		em.persist(member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		Member member = em.find(Member.class, id);
		if(member != null) {
			System.out.println("member >> " +  member);
			System.out.println("member.getId() >> " + member.getId());
			System.out.println("member.getName() >> " + member.getName());
		}
		return Optional.ofNullable(member);
	}

	@Override
	public Optional<Member> findByName(String name) {
		String jpqlQuery = "select m from Member m where name = :name";
		Query query = em.createQuery(jpqlQuery, Member.class).setParameter("name", name);
		return query.getResultStream().findAny();
	}

	@Override
	public List<Member> findAll() {
		//[JPQL]
		//[일반 Query]
		// 테이블명과 클래스명이 같아야 한다.
		Query query = em.createQuery("select m from Member m");
//		// [문제] query에 저장된 Member 객체 출력
//		// 아이디 이름
		for (Object m : query.getResultList()) {
			System.out.print(((Member)m).getId() + " ");
			System.out.println(((Member)m).getName());
		}
		
		//[Typed Query]
		return em.createQuery("select m from Member m", Member.class).getResultList();
		
	}
}
