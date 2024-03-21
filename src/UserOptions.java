import java.util.*;
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

    public void searchChoice(Scanner sc, List<LiteraturePrize> literaturePrizes) {
        System.out.println("Enter search term for writing genre > ");
        String searchTerm = sc.nextLine().trim().toUpperCase();

        List<Object[]> matchingLaureates = new ArrayList<>();

        for (LiteraturePrize prize : literaturePrizes) {
            int prizeYear = Integer.parseInt(prize.getYear());
            for (Laureate laureate : prize.getWinners()) {
                for (String genre : laureate.getGenres()) {
                    if (genre.toUpperCase().contains(searchTerm)) {
                        // Capitalize the matching portion of the genre string
                        String capitalizedGenre = capitalizeMatchingSubstring(genre, searchTerm);
                        // Add laureate information along with the year of the prize
                        matchingLaureates.add(new Object[]{laureate.getName(), capitalizedGenre, prizeYear});
                        break; // Exit the inner loop once a match is found
                    }
                }
            }
        }

        if (!matchingLaureates.isEmpty()) {
            Collections.sort(matchingLaureates, Comparator.comparing(obj -> (String) obj[0]));

            System.out.println("------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Name              | Genres                                    | Year |");
            System.out.println("------------------------------------------------------------------------------------------------------------------");

            for (Object[] laureateInfo : matchingLaureates) {
                String name = (String) laureateInfo[0];
                String genre = (String) laureateInfo[1];
                int year = (int) laureateInfo[2];

                System.out.printf("| %-17s | %-40s | %-4d |\n", name, genre, year);
            }

            System.out.println("------------------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("No matches found for the entered search term.");
        }
    }

    private String capitalizeMatchingSubstring(String original, String searchTerm) {
        // Find the index of the matching substring
        int index = original.toUpperCase().indexOf(searchTerm);
        if (index != -1) {
            // Capitalize the matching substring
            StringBuilder modifiedGenre = new StringBuilder(original);
            modifiedGenre.replace(index, index + searchTerm.length(), searchTerm);
            return modifiedGenre.toString();
        }
        return original;
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
                    List<String> languages = winner.getLanguages();
                    List<String> genres = winner.getGenres();

                    StringBuilder languagesStringBuilder = new StringBuilder();
                    StringBuilder genreStringBuilder = new StringBuilder();

                    for (int i = 0; i < languages.size(); i++) {
                        languagesStringBuilder.append(String.format("%-20s",languages.get(i)));
                    }
                    for (int i = 0; i < genres.size(); i++) {
                        genreStringBuilder.append(String.format("%-80s|",genres.get(i) + String.format("%-80s\n", "|")));
                    }

                    selectTable.append(String.format("| %-23s | %-7s | %-6s | %-23s | %-20s |\n", name, born, died, languagesStringBuilder, genreStringBuilder));

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
