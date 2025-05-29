package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemRepositoryTest {

    ItemRepository repository = new ItemRepository();

    @AfterEach
    void afterEach() {
        repository.clearStore();
        System.out.println("OK, afterEach!!");
    }

    // save(), findById()
    @Test
    void save() {
        // given
        Item item1 = new Item("itemName-A", 1000, 100);
        Item item2 = new Item("itemName-B", 1200, 200);

        // when
        Item newItem1 = repository.save(item1);
        Item newItem2 = repository.save(item2);
        Item findItem1 = repository.findById(newItem1.getId());
        Item findItem2 = repository.findById(newItem2.getId());

        // then
        Assertions.assertThat(newItem1.getItemName()).isEqualTo("itemName-A");
        Assertions.assertThat(newItem1.getPrice()).isEqualTo(1000);
        Assertions.assertThat(newItem1.getQuantity()).isEqualTo(100);
        Assertions.assertThat(newItem1.getId()).isEqualTo(findItem1.getId());
        Assertions.assertThat(newItem1.getItemName()).isEqualTo(findItem1.getItemName());
        Assertions.assertThat(newItem1.getPrice()).isEqualTo(findItem1.getPrice());
        Assertions.assertThat(newItem1.getQuantity()).isEqualTo(findItem1.getQuantity());

        Assertions.assertThat(newItem2.getId()).isGreaterThan(newItem1.getId());
        Assertions.assertThat(newItem2.getItemName()).isEqualTo("itemName-B");
        Assertions.assertThat(newItem2.getPrice()).isEqualTo(1200);
        Assertions.assertThat(newItem2.getQuantity()).isEqualTo(200);
        Assertions.assertThat(newItem2.getId()).isEqualTo(findItem2.getId());
        Assertions.assertThat(newItem2.getItemName()).isEqualTo(findItem2.getItemName());
        Assertions.assertThat(newItem2.getPrice()).isEqualTo(findItem2.getPrice());
        Assertions.assertThat(newItem2.getQuantity()).isEqualTo(findItem2.getQuantity());
    }

    @Test
    void findAll() {
        // given
        Item item1 = new Item("itemName-A", 1000, 100);
        Item item2 = new Item("itemName-B", 1200, 200);
        repository.save(item1);
        repository.save(item2);

        // when
        List<Item> items = repository.findAll();

        // then
        Assertions.assertThat(items.size()).isEqualTo(2);
        Assertions.assertThat(items).contains(item1, item2);
    }

    @Test
    void update() {
        // given
        Item item1 = new Item("itemName-A", 1000, 100);
        repository.save(item1);

        // when
        Item itemForUpdate = new Item("itemName-A-2", 1500, 100);
        repository.update(item1.getId(), itemForUpdate);
        Item item1Updated = repository.findById(item1.getId());

        // then
        Assertions.assertThat(item1Updated.getItemName()).isNotEqualTo("itemName-A");
        Assertions.assertThat(item1Updated.getItemName()).isEqualTo("itemName-A-2");
        Assertions.assertThat(item1Updated.getPrice()).isEqualTo(1500);
        Assertions.assertThat(item1Updated.getQuantity()).isEqualTo(100);
    }
}