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

    @PostMapping("/add")
    public String save(@ModelAttribute Item item, Model model) {
        Item savedItem = repository.save(item);
        model.addAttribute("item", savedItem);
        return "basic/item";
    }
}
