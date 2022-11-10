package activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {
    static ArrayList<String> list;

    @BeforeEach
    public void setUp() throws Exception {
        list = new ArrayList<String>();
        list.add("alpha");
        list.add("beta");
    }
    @Test
    public void insertTest() throws Exception {
        assertEquals(2, list.size(), "Wrong size");
        list.add("gamma");
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta", list.get(1), "Wrong element");
        assertEquals("gamma", list.get(2), "Wrong element");
    }
    @Test
    public void replaceTest() throws Exception {
        list.set(1, "maha");
        assertEquals(2, list.size(), "Wrong size");
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("maha", list.get(1), "Wrong element");
    }

}
