import java.util.ArrayList;
import java.util.List;

public class LiteraturePrize {
    private String year;
    private ArrayList<Laureate> winners;

    public LiteraturePrize(String year) {
        this.year = year;
        this.winners = new ArrayList<>();
    }

    public String getYear(){
        return year;
    }
    public void addLaureate(Laureate laureate) {
        winners.add(laureate);
    }
    public List<Laureate> getWinners() {
        return winners;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LiteraturePrize{");
        sb.append("year=").append(year);
        sb.append(", winners=").append(winners);
        sb.append('}');
        return sb.toString();
    }
}
