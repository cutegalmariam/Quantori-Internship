package src.Peptides;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Peptide {

    public static final int DEFAULT_PEPTIDE_SIZE = 8;

    private String protein;
    private int peptideSize;

    public HashMap<String, List<Integer>> kmers = new LinkedHashMap<>();

    private List<String> library;

    public Peptide(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        this.library = library;
        createKMersDictionary();
    }

    void createKMersDictionary() {
        for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            List<Integer> positions = kmers.get(kmer);
            if (positions == null) {
                positions = new ArrayList<>();
                kmers.put(kmer, positions);
            }
            positions.add(i);
        }
    }

//    approach 2
    void reverseSearch() {
        boolean found = false;
        for (int i = 0; i < protein.length(); i++) {
            for (String kmer : kmers.keySet()) {
                int endOfKmer = i + kmer.length();

                if (endOfKmer <= protein.length()) {
                    String substring = protein.substring(i, endOfKmer);

                    if (substring.equals(kmer)) {
                        List<Integer> positions = kmers.get(kmer);
                        for (int position : positions) {
                            if (position == i) {
                                System.out.println("Found: " + kmer + " at index " + position);
                                found = true;
                            }
                        }
                    }
                }
            }
        }

        if (!found) {
            System.out.println("No kmers from the map were found in the protein.");
        }
    }








}
