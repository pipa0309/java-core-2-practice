package collection;

import java.util.*;

public class Book {
    private final Map<String, Set<Integer>> map;

    public Book() {
        this.map = new HashMap<>();
    }

    public static void main(String[] args) {
        Book book = new Book();
        book.add("маша", 1);
        book.add("петя", 2);
        book.add("коля", 3);
        book.add("маша", 4);

        book.get("маша");
        book.get("петя");
        book.get("коля");
    }

    private Map<String, Set<Integer>> add(String name, Integer number) {
        Set<Integer> orDefault = map.getOrDefault(name, new HashSet<>());
        orDefault.add(number);
        map.put(name, orDefault);
        return map;
    }

    private Set<Integer> get(String name) {
        Set<Integer> v = map.get(name);
        System.out.println(name + " = " + v);
        return map.get(name);
    }
}
