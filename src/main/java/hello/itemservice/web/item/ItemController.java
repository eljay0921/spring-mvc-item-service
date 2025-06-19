package hello.itemservice.web.item;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository repository;

    @GetMapping()
    public String items(Model model) {
        List<Item> items = repository.findAll();
        model.addAttribute("items", items);
        return "items/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = repository.findById(itemId);
        model.addAttribute("item", item);
        return "items/item";
    }

    @GetMapping("/{itemId}/edit")
    public String itemEdit(@PathVariable long itemId, Model model) {
        Item item = repository.findById(itemId);
        model.addAttribute("item", item);
        return "items/editForm";
    }

    // v1 - 내가 작성한 버전
//    @PostMapping("/{itemId}/edit")
//    public String edit(Item item) {
//        repository.update(item.getId(), item);
//        return "/item";
//    }

    // v2 - 강의 버전 (리다이렉션)
    @PostMapping("/{itemId}/edit")
    public String editV2(@PathVariable long itemId, @ModelAttribute Item item) {
        repository.update(itemId, item);
        return "redirect:/items/{itemId}";    // {itemId} -> @PathVariable 값을 가져와서 적용한다
    }


    @GetMapping("/add")
    public String addForm() {
        return "items/addForm";
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
//        return "/item";
//    }

    // v2 - @ModelAttribute 적용 -> model.addAttribute("item", savedItem);를 자동으로 처리해 준다.
//    @PostMapping("/add")
//    public String save(@ModelAttribute("item") Item item, Model model) {
//        repository.save(item);
//        return "/item";
//    }

    // v3 - v2로 인해, Model 파라미터가 불필요하다.
//    @PostMapping("/add")
//    public String save(@ModelAttribute("item") Item item) {
//        repository.save(item);
//        return "/item";
//    }

    // v4 - @ModelAttribute의 파라미터도 생략 가능하다. (규칙=클래스 이름의 첫 문자를 소문자로 변환시킴)
//    @PostMapping("/add")
//    public String save(@ModelAttribute Item item) {
//        repository.save(item);
//        return "/item";
//    }

    // v5 - 최종. @ModelAttribute까지 생략한다.
    // as-is) 새로고침 문제가 있는 버전
//    @PostMapping("/add")
//    public String save(Item item) {
//        repository.save(item);
//        return "/item";
//    }

    // to-be) 새로고침 문제를 해결
    // v6 - Redirect 버전
//    @PostMapping("/add")
//    public String save(Item item, RedirectAttributes redirectAttributes) {
//        repository.save(item);
//        redirectAttributes.addAttribute("itemId", item.getId());
//        return "redirect:/items/{itemId}";
//    }

    // v7 - Redirect 버전 + 속성 추가
    @PostMapping("/add")
    public String save(Item item, RedirectAttributes redirectAttributes) {
        repository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

}
