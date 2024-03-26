import java.util.ArrayList;
import java.util.List;

public class Laureate {
    private String name;
    private List<String> birth_death;
    private List<String> nations;
    private List<String> languages;
    private List<String> genres;
    private String citation;

    public String getName() {
        return name;
    }

    public List<String> getBirth_death() {
        return birth_death;
    }

    public List<String> getNations() {
        return nations;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getCitation() {
        return citation;
    }

    public String getYearOfPrize(LiteraturePrize prize) {
        return prize.getYear(); // Retrieve the year from the LiteraturePrize
    }

    public Laureate(String name, List<String>birth_death, List<String> nations, List<String> languages, List<String> genres, String citation) {
        this.name = name;
        this.birth_death = birth_death;
        this.nations = nations;
        this.languages = languages;
        this.genres = genres;
        this.citation = citation;
    }

    public static String center(String text, int len) {
        if (len <= text.length()) {
            return text;
        }
        int before = (len - text.length()) / 2;
        int after = len - before - text.length();
        return String.format("%" + before + "s%s%" + after + "s", "", text, "");
    }

    public static List<String> splitIntoLines(String text, int maxLineWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > maxLineWidth) {
                lines.add(currentLine.toString().trim());
                currentLine.setLength(0);
            }
            currentLine.append(word).append(" ");
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }

        return lines;
    }

    public static StringBuilder printCenteredCitation(String citation, int maxLineWidth) {
        StringBuilder centeredCitation = new StringBuilder();
        List<String> lines = splitIntoLines(citation, maxLineWidth);
        for (String line : lines) {
            centeredCitation.append("|").append(center(line, maxLineWidth)).append("|\n");
        }
        return centeredCitation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String name = this.getName();
        String born = this.birth_death.get(0);
        String died = this.getBirth_death().size() > 1 ? this.getBirth_death().get(1) : "----";
        List<String> languages = this.getLanguages();
        List<String> genres =this.getGenres();

        int maxGroupSize = Math.max(languages.size(), genres.size());

        sb.append(String.format("| %-23s | %-4s | %-4s | %-23s | %-20s |\n", name, born, died.trim(), languages.get(0), genres.get(0)));

        for (int i = 1; i < maxGroupSize; i++) {
            if(i < languages.size() && i < genres.size()){
                sb.append(String.format("| %-23s | %-4s | %-4s | %-23s | %-20s |\n", "", "", "", languages.get(i), genres.get(i)));
            }
            else if (i >= languages.size()) {
                sb.append(String.format("| %-23s | %-4s | %-4s | %-23s | %-20s |\n", "", "", "", "", genres.get(i)));
            }
            else {
                sb.append(String.format("| %-23s | %-4s | %-4s | %-23s | %-20s |\n", "", "", "", languages.get(i),""));
            }
        }

        // Append citation for each laureate
        String citation = this.getCitation();
        List<String> citationLines = splitIntoLines(citation, 65); // Split citation into lines

        sb.append("------------------------------------------------------------------------------------------\n");
        sb.append(String.format("|%-38s %-9s %-39s|\n", "", "Citation:", ""));

        // Append citation lines with padding to align with the rest of the table
        for (String line : citationLines) {
            sb.append(String.format("| %-81s |\n", center(line, 85)));
        }

        sb.append("------------------------------------------------------------------------------------------\n");
        return sb.toString();
    }
}
