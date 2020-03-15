import java.util.*;

public class Trie {
    public final static int ALPHABET_SIZE = 26;
    public final static char TERMINATING_CHARACTER = '\0';
    // \u200C is a zero-width space
    public final static char BEGINNING_CHARACTER = '\u200C';

    private static class TrieNode {
        // One more spot for terminating char
        private final TrieNode[] letters = new TrieNode[ALPHABET_SIZE + 1];
        private final char value;

        public TrieNode(char value) {
            this.value = value;
        }

        private char getValue() {
            return value;
        }

        private void add(CharSequence word) {
            if (word.length() == 0) {
                letters[charIndex(TERMINATING_CHARACTER)] = new TrieNode(TERMINATING_CHARACTER);
                return;
            }

            char firstLetter = Character.toLowerCase(word.charAt(0));
            if (!Character.isAlphabetic(firstLetter))
                throw new IllegalArgumentException("Not a letter");
            int letterIndex = charIndex(firstLetter);

            if (letters[letterIndex] == null)
                letters[letterIndex] = new TrieNode(firstLetter);

            letters[letterIndex].add(word.subSequence(1, word.length()));
        }

        private List<CharSequence> complete(CharSequence word) {
            if (word.length() == 0)
                return getAllSubWords();

            char firstLetter = Character.toLowerCase(word.charAt(0));
            if (!Character.isAlphabetic(firstLetter))
                throw new IllegalArgumentException("Not a letter");
            int letterIndex = charIndex(firstLetter);

            if (letters[letterIndex] == null)
                return new ArrayList<>();

            List<CharSequence> words = letters[letterIndex].complete(word.subSequence(1, word.length()));

            if(value != BEGINNING_CHARACTER)
                words.replaceAll(s -> value + s.toString());
            return words;
        }

        private List<CharSequence> getAllSubWords() {
            if(value == TERMINATING_CHARACTER)
                return List.of("");

            List<CharSequence> words = new ArrayList<>();

            for (TrieNode node : letters)
                if (node != null)
                    for (CharSequence word: node.getAllSubWords())
                        words.add(value + word.toString());

            return words;
        }
    }

    private final TrieNode root = new TrieNode(BEGINNING_CHARACTER);

    public void addWord(CharSequence word) {
        if (word.length() == 0)
            return;

        root.add(word);
    }

    public List<CharSequence> autoComplete(CharSequence word) {

        return root.complete(word);
    }

    // Returns the index of the char in the array
    // public for testing
    public static int charIndex(char c) {
        if (c == '\0')
            return 26;

        return Character.toLowerCase(c) - 'a';
    }
}
