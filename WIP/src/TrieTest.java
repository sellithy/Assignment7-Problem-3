import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class TrieTest {

    @Test
    public void charIndexTest(){
        assertEquals(Trie.charIndex('a'), 0);
        assertEquals(Trie.charIndex('b'), 1);
        assertEquals(Trie.charIndex('g'), 6);
        assertEquals(Trie.charIndex('m'), 12);
        assertEquals(Trie.charIndex('r'), 17);
        assertEquals(Trie.charIndex('s'), 18);
        assertEquals(Trie.charIndex('z'), 25);
    }

    @Test
    public void addAndAutoCompleteTest(){
        Trie trie = new Trie();
        List<CharSequence> inputWords = List.of("hello", "high",
                "seattle", "seatac", "see", "hollow", "how",
                "sea", "however", "Shehab", "Ellithy");
        for (CharSequence word: inputWords)
            trie.addWord(word);

        List<CharSequence> testWords = List.of("h", "se", "sea", "ho", "s", "xyz", "e");
        List<List<CharSequence>> expectedList = List.of(
                List.of("hello", "high", "hollow", "how", "however"),
                List.of("seattle", "see", "seatac", "sea"),
                List.of("seattle", "seatac", "sea"),
                List.of("how", "hollow", "however"),
                List.of("seattle", "seatac", "see", "sea", "shehab"),
                List.of(),
                List.of("ellithy")
        );

        for (int i = 0; i < testWords.size(); i++) {
            List<CharSequence> actual = trie.autoComplete(testWords.get(i));
            List<CharSequence> expected = expectedList.get(i);
            assertTrue(actual.containsAll(expected));
        }
    }
}
