import java.lang.reflect.Array;
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

    public void fileSearch() {
        while(true) {
            System.out.println("File to read: ");
            String fileName = scanner.nextLine();
            if(fileName.isEmpty()) {
                System.out.println("No filename inputted! Try again!");
                continue;
            } else if(fileName.equalsIgnoreCase("End")) {
                System.out.println("Ending file search");
                break;
            }
            try(Scanner fileScanner = new Scanner(Paths.get(fileName))) {
                // Scanning file
                if(!fileScanner.hasNextLine()) {
                    System.out.println("FILE CONTAINS NO DATA. PLEASE USE ANOTHER FILE!");
                    continue;
                } else {
                    ArrayList<String> rawRecipeData = new ArrayList<>();

                    while(fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        if(line.isEmpty()) {
                            Recipe recipeObje = parseRecipeObject(rawRecipeData);
                            recipeObjects.add(recipeObje);
                            rawRecipeData = new ArrayList<>();
                            continue;
                        }
                        rawRecipeData.add(line);
                    }
                    Recipe recipeObj = parseRecipeObject(rawRecipeData);
                    recipeObjects.add(recipeObj);
                }
            } catch(Exception error) {
                System.out.println("ERROR! Filename not found! Error " + error);
            }
        }

        //printRecipeObjects(recipeObjects);
        commandUI();
    }

    public void commandUI() {
        System.out.println("Commands:\nlist - lists the recipes\nstop - stops the program");
        while(true) {
            System.out.println("Enter command");
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("stop")) {
                break;
            } else if(input.equalsIgnoreCase("list")) {

            }
        }
    }

    public Recipe parseRecipeObject(ArrayList<String> recipeData) {
        ArrayList<String> ingredients = new ArrayList<>();
        String recipeName = "";
        int cookTime = 0;
        for(int i=0; i<recipeData.size(); i++) {
            String dataElement = recipeData.get(i);
            if(i==0) {
                recipeName = dataElement;
            } else if(i==1) {
                cookTime = Integer.valueOf(dataElement);
            } else {
                ingredients.add(dataElement);
            }
        }

        Recipe recipe = new Recipe(recipeName,cookTime,ingredients);
        return recipe;
    }

    public void printRecipeObjects(ArrayList<Recipe> recipeList) {
        //System.out.println("PRINT");
        for(Recipe recipe : recipeList) {
            System.out.println(recipe.getName());
        }
    }
}
