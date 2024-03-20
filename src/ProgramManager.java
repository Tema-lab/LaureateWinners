import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProgramManager {
    UserMenu userMenu = new UserMenu();
    UserOptions userOptions = new UserOptions();
    Scanner sc;
    String fileLocation = System.getProperty("user.dir");
    String dataPath = fileLocation + File.separator + "literature-prizes.txt";
    DataLoader dataLoader = new DataLoader(new File(dataPath));
    List<LiteraturePrize> literaturePrizes = dataLoader.readDataFile();

    public void init(){
        Pattern userMenupattern = Pattern.compile("^[0-3]{1}$");
        sc = new Scanner(System.in);
        String choice;

        userMenu.printOptions();
        System.out.println("Enter choice > ");
        choice = sc.nextLine().trim();
        while(!userMenupattern.matcher(choice).matches()) {
            System.out.println("Invalid input! Please re-enter input.");
            choice = sc.nextLine().trim();
        }

        switch (choice){
            case "1":
                userOptions.listChoice(sc, literaturePrizes);
                break;
            case "2":
                userOptions.selectChoice(sc,literaturePrizes);
                break;
            case "3":
                userOptions.searchChoice();
                break;
            case "0":
                userOptions.exitChoice(sc);
                break;
        }

    }
}
