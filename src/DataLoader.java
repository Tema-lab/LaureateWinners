import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLoader {
    public final File FILE;

    public DataLoader(File file) {
        this.FILE = file;
    }

    public List<LiteraturePrize> readDataFile() {
        List<LiteraturePrize> literaturePrizes = new ArrayList<>();
        LiteraturePrize currentPrize = null;
        Pattern yearPattern = Pattern.compile("\\d{4}");
        String endOfLiteratureInfo = "-----";

        try (Scanner sc = new Scanner(FILE)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.matches(yearPattern.pattern())) {
                    // If the line matches the year pattern, it's a new prize
                    currentPrize = new LiteraturePrize(line);
                    literaturePrizes.add(currentPrize); // Add current prize to the list
                } else if (line.equals("Not awarded")) {
                    // If the line indicates the prize was not awarded, move to the next line
                    continue;
                } else if (line.equals(endOfLiteratureInfo)) {
                    // If the line marks the end of laureate information, move to the next line
                    continue;
                } else {
                    // Extract laureate information
                    Matcher laureateMatcher = Pattern.compile("^(.+)\\((.+)\\)\\|(.+)$").matcher(line);
                    if (laureateMatcher.matches()) {
                        String laureateName = laureateMatcher.group(1).trim();
                        String birthDeath = laureateMatcher.group(2).trim();
                        List<String> laureateBirth_Death;
                        if (birthDeath.startsWith("b.")) {
                            String birthYear = birthDeath.substring(3, 7);
                            laureateBirth_Death = new ArrayList<>();
                            laureateBirth_Death.add(birthYear);
                            laureateBirth_Death.add("----");
                        } else {
                            // Otherwise, split birth_death normally
                            laureateBirth_Death = new ArrayList<>();
                            String[] birthDeathArray = birthDeath.split("-");
                            for (String year : birthDeathArray) {
                                laureateBirth_Death.add(year);
                            }
                        }
                        String nationLangString = laureateMatcher.group(3).trim();
                        // Extract nations
                        String[] nationLangArray = nationLangString.split("\\|");
                        String nationsString = nationLangArray[0];
                        String[] nationArray = nationsString.split(", ");
                        List<String> laureateNations = new ArrayList<>();
                        for (String nation : nationArray) {
                            laureateNations.add(nation.trim());
                        }

                        // Extract languages
                        String languagesString = nationLangArray[1];
                        String[] langArray = languagesString.split(",");
                        List<String> laureateLanguages = new ArrayList<>();
                        for (String lang : langArray) {
                            laureateLanguages.add(lang.trim());
                        }

                        // Read the citation
                        String laureateCitation = sc.nextLine().trim().replaceAll("^\"|\"$", "");

                        // Read the genres
                        List<String> laureateGenres = new ArrayList<>();
                        String genresLine = sc.nextLine().trim();
                        String[] genresArray = genresLine.split(", ");
                        for (String genre : genresArray) {
                            laureateGenres.add(genre.trim());
                        }

                        // Create Laureate object and add it to the current prize
                        Laureate laureate = new Laureate(laureateName, laureateBirth_Death, laureateNations, laureateLanguages, laureateGenres, laureateCitation);
                        if (currentPrize != null) {
                            currentPrize.addLaureate(laureate);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        return literaturePrizes;
    }
}
