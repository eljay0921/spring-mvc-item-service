package hello.itemservice;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        log.info("[Init] DataInitializer");

        // item
        itemRepository.save(new Item("itemA", 1000, 150));
        itemRepository.save(new Item("itemB", 3000, 50));

        // member
        Member member = new Member();
        member.setLoginId("admin");
        member.setPassword("admin!");
        member.setName("테스트어드민");
        memberRepository.save(member);
    }
}
