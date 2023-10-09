import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    ArrayList recipeObjects;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
        this.recipeObjects = new ArrayList<>();
    }

    public void ui() {
        while(true) {
            System.out.println("File to read: ");
            String fileName = scanner.nextLine();
            if(fileName.isEmpty()) {
                System.out.println("No filename inputted! Try again!");
                continue;
            } else if(fileName.equalsIgnoreCase("End")) {
                System.out.println("ENDING PROGRAM");
                break;
            }
            try(Scanner fileScanner = new Scanner(Paths.get(fileName))) {
                // Scanning file
                if(!fileScanner.hasNextLine()) {
                    System.out.println("FILE CONTAINS NO DATA. PLEASE USE ANOTHER FILE!");
                    continue;
                } else {
                    while(fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        if(line.isEmpty()) {
                            System.out.println("OBJECT DONE");
                            continue;
                        }
                        System.out.println(line);
                    }
                }
            } catch(Exception error) {
                System.out.println("ERROR! Filename not found! Error " + error);
            }
        }
    }

    /*public Recipe parseRecipeObject() {

    }*/
}
