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
            System.out.print("File to read: ");
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
        System.out.println("Commands:\nlist - lists the recipes\nstop - stops the program\n" +
                "find name - searches the recipes by name\nfind cooking time - " +
                "searches recipes by cooking time\nfind ingredient - searches recipes by" +
                " ingredient");
        while(true) {
            System.out.print("\nEnter command:");
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("stop")) {
                break;
            } else if(input.equalsIgnoreCase("list")) {
                System.out.println("\nRecipes:");
                printRecipeObjects(recipeObjects);
            } else if(input.equalsIgnoreCase("find name")) {
                System.out.print("Searched word: ");
                String searchName = scanner.nextLine();
                ArrayList<Recipe> searchResults = searchedRecipe(recipeObjects,searchName);
                if(searchResults.isEmpty()) {
                    System.out.println("NO RESULTS FOUND!");
                } else {
                    System.out.println("\nRecipes:");
                    printRecipeObjects(searchResults);
                }

            } else if(input.equalsIgnoreCase("find cooking time")) {
                System.out.print("Max cooking time:");
                int maxTime = Integer.valueOf(scanner.nextLine());
                ArrayList<Recipe> searchResults = searchRecipeByTime(recipeObjects,maxTime);
                if(searchResults.isEmpty()) {
                    System.out.println("NO RESULTS FOUND!");
                } else {
                    System.out.println("\nRecipes:");
                    printRecipeObjects(searchResults);
                }
            } else if(input.equalsIgnoreCase("find ingredient")) {
                System.out.print("Ingredient: ");
                String ingredient = scanner.nextLine();
                ArrayList<Recipe> searchResults = searchRecipeByIngredient(recipeObjects,ingredient);
                if(searchResults.isEmpty()) {
                    System.out.println("NO RESULTS FOUND!");
                } else {
                    System.out.println("\nRecipes:");
                    printRecipeObjects(searchResults);
                }
            } else {
                System.out.println("INVALID COMMAND, TRY AGAIN");
                continue;
            }
        }
    }

    public ArrayList<Recipe> searchRecipeByIngredient(ArrayList<Recipe> recipeList, String word) {
        ArrayList<Recipe> searchResults = new ArrayList<>();
        for(Recipe recipe: recipeList) {
            for(String ingredient: recipe.getIngredients()) {
                if(word.equalsIgnoreCase(ingredient)) {
                    searchResults.add(recipe);
                }
            }
        }

        return searchResults;
    }

    public ArrayList<Recipe> searchedRecipe(ArrayList<Recipe> recipeList, String word) {
        ArrayList<Recipe> searchResults = new ArrayList<>();
        for(Recipe recipe: recipeList) {
            if(recipe.getName().contains(word)) {
                searchResults.add(recipe);
            }
        }
        return searchResults;
    }

    public ArrayList<Recipe> searchRecipeByTime(ArrayList<Recipe> recipeList, int time) {
        ArrayList<Recipe> searchResults = new ArrayList<>();
        for(Recipe recipe : recipeList) {
            if(recipe.getCookTime()<=time) {
                searchResults.add(recipe);
            }
        }
        return searchResults;
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
            System.out.println(recipe);
        }
    }
}
