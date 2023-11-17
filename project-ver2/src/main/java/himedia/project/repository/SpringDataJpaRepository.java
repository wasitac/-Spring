package himedia.project.repository;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import himedia.project.domain.Member;

@Primary
public interface SpringDataJpaRepository extends JpaRepository<Member, Long>, MemberRepository {
	// [이름 검색] 동명이인 검색 시 Error 발생
	// [문제] 메소드명을 기준으로 쿼리를 자동 생성해 주는데, 쿼리를 수정하기
//	@Query(value = "select * from Member m where name = :name limit 1", nativeQuery = true)
// 	Optional<Member> findByName(@Param("name") String name);
	@Query(value = "select * from Member m where m.name = :name limit 1", nativeQuery = true)
	Optional<Member> findByName(@Param("name") String name);
}
