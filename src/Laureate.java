import java.lang.reflect.Array;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Laureate{");
        sb.append("name='").append(name).append('\'');
        sb.append(", birth_death='").append(birth_death).append('\'');
        sb.append(", nations=").append(nations);
        sb.append(", languages=").append(languages);
        sb.append(", genres=").append(genres);
        sb.append(", citation='").append(citation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
