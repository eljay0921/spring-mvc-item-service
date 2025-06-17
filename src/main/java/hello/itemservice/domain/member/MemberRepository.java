package hello.itemservice.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long seq = 0L;

    public Member save(Member member) {
        member.setId(++seq);
        store.put(member.getId(), member);
        log.info("[Member] saved!: {}", member);
        return member;
    }

    public Member findById(long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {

        // as-is
        /*
        List<Member> members = findAll();
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();
        */

        // to-be
        return findAll()
                .stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<Member>(store.values());
    }
}
