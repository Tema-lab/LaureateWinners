import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserOptions {
    final Pattern YEAR_ALLOWED_PATTERN = Pattern.compile("(19[0-9]{2}|20[0-1][0-9]|202[0-2])");
    public void listChoice(Scanner sc, List<LiteraturePrize> literaturePrizes){
        String startYear = validateUserStartYearInput(sc);
        String endYear = validateUserEndYearInput(sc);

        int start = Integer.parseInt(startYear);
        int end = Integer.parseInt(endYear);

        while(start > end){
            System.out.println("End year must be larger than start year! Please re-enter start year and end year again.");
            startYear = validateUserStartYearInput(sc);
            endYear = validateUserEndYearInput(sc);

            start = Integer.parseInt(startYear);
            end = Integer.parseInt(endYear);
        }

        StringBuilder listTable = listChoiceTable(start,end,literaturePrizes);
        System.out.println(listTable);
    }
    public void selectChoice(Scanner sc,List<LiteraturePrize> literaturePrizes){
        String yearOfPrize = validateUserChoiceInput(sc);
        int year = Integer.parseInt(yearOfPrize);
        StringBuilder selectTable = selectChoiceTable(year, literaturePrizes);
        System.out.println(selectTable);

    }
    public void searchChoice(){
        System.out.println("Select choice");
    }

    public void exitChoice(Scanner sc){
        System.out.println("Exit choice");
        sc.close();
    }

    public String validateUserStartYearInput(Scanner sc){
        System.out.println("Enter start year > ");
        String choice = sc.nextLine().trim();
        while(!YEAR_ALLOWED_PATTERN.matcher(choice).matches()){
            System.out.println("Invalid input! Please re-enter starting year");
            choice = sc.nextLine().trim();
        }
        return choice;
    }
    public String validateUserEndYearInput(Scanner sc){
        System.out.println("Enter end year > ");
        String choice = sc.nextLine().trim();
        while(!YEAR_ALLOWED_PATTERN.matcher(choice).matches()){
            System.out.println("Invalid input! Please re-enter end year");
            choice = sc.nextLine().trim();
        }
        return choice;
    }

    public String validateUserChoiceInput(Scanner sc){
        System.out.println("Enter year of prize > ");
        String choice = sc.nextLine().trim();
        while(!YEAR_ALLOWED_PATTERN.matcher(choice).matches()){
            System.out.println("Invalid input. Please re-enter year of prize > ");
            choice = sc.nextLine().trim();
        }
        return choice;
    }

    public StringBuilder listChoiceTable(int startYear, int endYear, List<LiteraturePrize> literaturePrizes){
        StringBuilder listTable = new StringBuilder();
        String yearStr = "Year";
        String prizeWinnersStr = "Prize winners (and associated nations)";
        listTable.append("-----------------------------------------------------------------------------------\n");
        listTable.append(String.format("| %-5s | %-71s |\n", yearStr, prizeWinnersStr));
        listTable.append("-----------------------------------------------------------------------------------\n");

        for(LiteraturePrize prize : literaturePrizes){
            int year = Integer.parseInt(prize.getYear());

            if (year >= startYear && year <= endYear) {
                List<Laureate> winners = prize.getWinners();
                String winnerInfo = getWinnerInfo(winners);
                listTable.append(String.format("| %-5s | %-71s |\n", year, winnerInfo));
            }
        }
        listTable.append("-----------------------------------------------------------------------------------\n");
        return listTable;
    }

    public StringBuilder selectChoiceTable(int yearOfPrize, List<LiteraturePrize> literaturePrizes){
        StringBuilder selectTable = new StringBuilder();

        String winnerHeader = "Winner(s)";
        String bornHeader = "Born";
        String diedHeader = "Died";
        String languagesHeader = "Language(s)";
        String genresHeader = "Genre(s)";
        String citationHeader = "Citation:";


        selectTable.append("----------------------------------------------------------------------------------------------\n");
        selectTable.append(String.format("| %-23s | %-7s | %-6s | %-23s | %-20s |\n", winnerHeader, bornHeader, diedHeader, languagesHeader, genresHeader));
        selectTable.append("----------------------------------------------------------------------------------------------\n");

        boolean isYearFound = false;
        for (LiteraturePrize prize : literaturePrizes) {
            int year = Integer.parseInt(prize.getYear());

            if (year == yearOfPrize) {
                isYearFound = true;
                List<Laureate> winners = prize.getWinners();
                for (Laureate winner : winners) {
                    String name = winner.getName();
                    String born = winner.getBirth_death().get(0);
                    String died = winner.getBirth_death().size() > 1 ? winner.getBirth_death().get(1) : "";
                    String languages = String.join(", ", winner.getLanguages());

                    List<String> genres = winner.getGenres();
                    System.out.println(genres);
                    StringBuilder genreStringBuilder = new StringBuilder();
                    for (int i = 0; i < genres.size(); i++) {
                        genreStringBuilder.append(genres.get(i)).append(i == genres.size() - 1 ? "" : "\n                                                            ");
                    }
                    selectTable.append(String.format("| %-23s | %-7s | %-6s | %-23s | %-20s |\n", name, born, died, languages, genreStringBuilder));

                    // Append citation for each laureate
                    String citation = winner.getCitation();
                    selectTable.append("----------------------------------------------------------------------------------------------\n");
                    selectTable.append("| Citation: |\n");
                    selectTable.append(String.format("| %s |\n", citation));
                    selectTable.append("----------------------------------------------------------------------------------------------\n");
                }
                break;
            }
        }
        if (!isYearFound) {
            selectTable.append("No prize found for the entered year.\n");
        }

        return selectTable;
    }

    public String getWinnerInfo(List<Laureate> winners) {
        StringBuilder winnerInfo = new StringBuilder();

        if (winners.isEmpty()) {
            winnerInfo.append("NOT AWARDED");
        } else {
            for (Laureate winner : winners) {
                winnerInfo.append(winner.getName()).append(" ").append(winner.getNations()).append(",  ");
            }
            // Remove the trailing comma and space
            winnerInfo.setLength(winnerInfo.length() - 2);
        }

        return winnerInfo.toString();
    }
}
