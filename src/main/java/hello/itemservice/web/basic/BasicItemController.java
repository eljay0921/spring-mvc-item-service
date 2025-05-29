package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository repository;

    /**
     * 테스트용 임시 데이터
     */
    @PostConstruct
    public void init() {
        repository.save(new Item("itemA", 1000, 200));
        repository.save(new Item("itemB", 2000, 100));
    }

    @GetMapping()
    public String items(Model model) {
        List<Item> items = repository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = repository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String itemEdit(@PathVariable long itemId, Model model) {
        Item item = repository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // v1 - @RequestParam
//    @PostMapping("/add")
//    public String save(@RequestParam String itemName,
//                       @RequestParam Integer price,
//                       @RequestParam Integer quantity,
//                       Model model) {
//
//        Item item = new Item(itemName, price, quantity);
//        Item savedItem = repository.save(item);
//
//        model.addAttribute("item", savedItem);
//        return "basic/item";
//    }

    // v2 - @ModelAttribute 적용 -> model.addAttribute("item", savedItem);를 자동으로 처리해 준다.
//    @PostMapping("/add")
//    public String save(@ModelAttribute("item") Item item, Model model) {
//        repository.save(item);
//        return "basic/item";
//    }

    // v3 - v2로 인해, Model 파라미터가 불필요하다.
//    @PostMapping("/add")
//    public String save(@ModelAttribute("item") Item item) {
//        repository.save(item);
//        return "basic/item";
//    }

    // v4 - @ModelAttribute의 파라미터도 생략 가능하다. (규칙=클래스 이름의 첫 문자를 소문자로 변환시킴)
//    @PostMapping("/add")
//    public String save(@ModelAttribute Item item) {
//        repository.save(item);
//        return "basic/item";
//    }

    // v5 - 최종. @ModelAttribute까지 생략한다.
    @PostMapping("/add")
    public String save(Item item) {
        repository.save(item);
        return "basic/item";
    }
}
