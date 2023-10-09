import java.nio.file.Paths;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
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
                while(fileScanner.hasNextLine()) {

                }
            } catch(Exception error) {
                System.out.println("ERROR! Filename not found! Error " + error);
            }
        }
    }
}
