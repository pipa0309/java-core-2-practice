package collection;

import java.util.*;

public class AmountNumberWords {
    public static void main(String[] args) {
        List<String> words = getAndPrintListWords();

        Map<String, Integer> mapWords = new HashMap<>();

        getAndPrintSetUniqueWords(words);

        getAndPrintCountWordsGetOrDefault(words, mapWords);
//        getAndPrintCountWordsMerge(words, mapWords);
    }

    private static List<String> getAndPrintListWords() {
        List<String> listWords = new ArrayList<>(
                Arrays.asList(
                        "word1",
                        "word2", "word2",
                        "word3", "word3", "word3",
                        "word4", "word4", "word4", "word4",
                        "word5", "word5", "word5", "word5", "word5",
                        "word6", "word6", "word6", "word6", "word6", "word6",
                        "word7", "word7", "word7", "word7", "word7", "word7", "word7",
                        "word8", "word8", "word8", "word8", "word8", "word8", "word8", "word8"
                )
        );

        System.out.println("List all words: " + listWords);
        return listWords;
    }

    private static void getAndPrintSetUniqueWords(List<String> words) {
        Set<String> uniqueWords = new HashSet<>(words);
        System.out.println("Unique words: " + uniqueWords);
    }

    private static void getAndPrintCountWordsGetOrDefault(List<String> words, Map<String, Integer> mapWords) {
        for (String word : words) {
            Integer orDefault = mapWords.getOrDefault(word, 0);
            mapWords.put(word, ++orDefault);
        }
        System.out.println("Map result = " + mapWords);
    }

    private static void getAndPrintCountWordsMerge(List<String> words, Map<String, Integer> mapWords) {
        words.forEach(wordElement -> mapWords.merge(wordElement, 1, (oldVal, newVal) -> oldVal + newVal));
        System.out.println("Map result = " + mapWords);
    }
}
