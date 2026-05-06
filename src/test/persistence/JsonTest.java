package persistence;

import model.Item;
import static org.junit.jupiter.api.Assertions.*;

abstract class JsonTest {

    protected void checkItem(String name, Item item) {
        assertEquals(name, item.getName());
        // Check other attributes if needed
    }
}
