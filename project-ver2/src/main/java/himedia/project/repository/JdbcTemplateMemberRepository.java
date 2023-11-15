package himedia.project.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import himedia.project.domain.Member;

//
@Primary
@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public JdbcTemplateMemberRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		System.out.println("jdbc생성자");
		
	}
	
	private RowMapper<Member> memberRowMapper() {
		// [문제] 람다로 바꾸기
//		return new RowMapper<Member>() {
//			@Override
//			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Member member = new Member();
//				member.setId(rs.getLong("id"));
//				member.setName(rs.getString("name"));
//				return member;
//			}
//		};
		return (rs, rowNum) -> {
			Member member = new Member();
			member.setId(rs.getLong("id"));
			member.setName(rs.getString("name"));
			return member;
		};
	}

	@Override
	public Member save(Member member) {
		// [SQL] insert문
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
		
		// DB Table에 저장할 데이터를 HashMap에 추가
		// parameters.put(컬럼명, 추가할 값);
		Map<String , Object> parameters = new HashMap<>();
		parameters.put("name", member.getName());
		
// HashMap에 저장한 데이터로 SQL 실행 후, 생성된 key(member 테이블 id컬럼 값) 리턴
		Number key = jdbcInsert.executeAndReturnKey(parameters);
		// return받은 key를 member에 저장
		member.setId(key.longValue());
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		List<Member> result = jdbcTemplate.query("select * from member where id=?", memberRowMapper(), id);
		return result.stream().findAny();
	}

	@Override
	public Optional<Member> findByName(String name) {		
		List<Member> result = jdbcTemplate.query("select * from member where name=?", memberRowMapper(), name);
		return result.stream().findAny();
	}

	@Override
	public List<Member> findAll() {
		System.out.println("findAll() 실행 시작");
		return jdbcTemplate.query("select * from member", memberRowMapper());
	}
}
