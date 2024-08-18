package src.Peptides;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;

public class PeptideTest {

    private String protein;
    private Peptide peptide;
    private LinkedHashMap<String, List<Integer>> kmers;
    private final int kmerLength = 8;

    @BeforeEach
    void setUp() {
        protein = "ACGTACGTGACGTACGTGACGTGACGTACGTTGACGT";
        kmers = new LinkedHashMap<>();

        addKmer("ACGTACGT", 0);
        addKmer("ACGTACGT", 8);
        addKmer("ACGTGACG", 4);
        addKmer("GACGTACG", 10);
        addKmer("GACGTGAC", 18);
        addKmer("GTTGACGT", 26);
    }

    private void addKmer(String kmer, int position) {
        List<Integer> positions = kmers.getOrDefault(kmer, new ArrayList<>());
        positions.add(position);
        kmers.put(kmer, positions);
    }

    @Test
    void testReverseSearch() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        peptide.reverseSearch();

        String output = outputStream.toString().trim();

        String expectedOutput = "Found: ACGTACGT at index 0\n" +
                "Found: ACGTACGT at index 8\n" +
                "Found: ACGTGACG at index 4\n" +
                "Found: GACGTACG at index 10\n" +
                "Found: GACGTGAC at index 18\n" +
                "Found: GTTGACGT at index 26";

        assertEquals(expectedOutput, output);
    }
}