import java.util.List;
import Repositories.RecepieRepo;
import java.util.Map;
import models.Recepie;

public class Main {
    public static void main(String[] args) {
        // Initialiser le repository des recettes
        RecepieRepo recepieRepo = new RecepieRepo();
        recepieRepo.init(); // Charger les données à partir du fichier XML

        // Afficher les titres des recettes
        System.out.println("Titres des recettes :");
        List<String> recipeTitles = recepieRepo.listRecipeTitles();
        recipeTitles.forEach(System.out::println);
        System.out.println();

        // Afficher le nombre total d'œufs utilisés
        int totalEggsUsed = recepieRepo.calculateTotalEggsUsed();
        System.out.println("Nombre total d'œufs utilisés : " + totalEggsUsed);
        System.out.println();

        // Afficher le titre de la recette avec le nombre d'œufs utilisé par recette
        System.out.println("Recette avec le nombre d'œufs utilisé par recette :");
        Map<String, Integer> recipeTitleWithEggsUsed = recepieRepo.displayRecipeTitleWithEggsUsedPerRecipe();
        System.out.println();

        // Afficher les recettes utilisant de l'huile d'olive
        System.out.println("Recettes utilisant de l'huile d'olive :");
        List<Recepie> recipesUsingOliveOil = recepieRepo.getRecipesUsingOliveOil();
        recipesUsingOliveOil.forEach(recipe -> System.out.println(recipe.getTitle()));
        System.out.println();

        // Afficher les recettes fournissant moins de 500 calories
        System.out.println("Recettes fournissant moins de 500 calories :");
        List<Recepie> recipesWithLessThan500Calories = recepieRepo.getRecipesWithLessThan500Calories();
        recipesWithLessThan500Calories.forEach(recipe -> System.out.println(recipe.getTitle()));
        System.out.println();

        // Afficher les recettes avec plus de 5 étapes
        System.out.println("Recettes avec plus de 5 étapes :");
        List<Recepie> recipesWithMoreThan5Steps = recepieRepo.getRecipesWithMoreThan5Steps();
        recipesWithMoreThan5Steps.forEach(recipe -> System.out.println(recipe.getTitle()));
        System.out.println();

        // Afficher la quantité de sucre utilisée par une recette spécifique
        String specificRecipeTitle = "Zuppa Inglese"; // Mettez le titre de la recette spécifique ici
        double sugarAmountForRecipe = recepieRepo.getSugarAmountForRecipe(specificRecipeTitle);
        System.out.println(
                "Quantité de sucre utilisée par la recette '" + specificRecipeTitle + "' : " + sugarAmountForRecipe);
        System.out.println();

        // Afficher les deux premières étapes de la recette spécifiée
        System.out.println("Deux premières étapes de la recette '" + specificRecipeTitle + "' :");
        List<String> firstTwoSteps = recepieRepo.displayFirstTwoSteps(specificRecipeTitle);
        firstTwoSteps.forEach(System.out::println);
        System.out.println();

        // Afficher les recettes sans beurre
        System.out.println("Recettes sans beurre :");
        List<Recepie> recipesWithoutButter = recepieRepo.getRecipesWithoutButter();
        recipesWithoutButter.forEach(recipe -> System.out.println(recipe.getTitle()));
        System.out.println();

        // Afficher les recettes avec des ingrédients communs à une recette spécifique
        System.out.println("Recettes avec des ingrédients communs à '" + specificRecipeTitle + "' :");
        List<Recepie> recipesWithCommonIngredients = recepieRepo.getRecipesWithCommonIngredients(specificRecipeTitle);
        recipesWithCommonIngredients.forEach(recipe -> System.out.println(recipe.getTitle()));
        System.out.println();

        // Afficher la recette la plus calorique
        System.out.println("Recette la plus calorique :");
        Recepie mostCaloricRecipe = recepieRepo.displayMostCaloricRecipe();
        System.out.println(mostCaloricRecipe != null ? mostCaloricRecipe.getTitle() : "Aucune recette trouvée");
        System.out.println();

        // Afficher l'unité la plus fréquente parmi les ingrédients
        String mostFrequentUnit = recepieRepo.getMostFrequentUnit();
        System.out.println("Unité la plus fréquente parmi les ingrédients : " + mostFrequentUnit);
        System.out.println();

        // Afficher la répartition des recettes par étape de réalisation
        System.out.println("Recettes triées par nombre d'étapes :");
        List<Recepie> recipesSortedBySteps = recepieRepo.displayRecipesSortedBySteps();
        System.out.println();

        // Afficher la recette la plus facile (avec le moins d'étapes)
        System.out.println("Recette la plus facile (avec le moins d'étapes) :");
        Recepie easiestRecipe = recepieRepo.getEasiestRecipe();
        System.out.println(easiestRecipe != null ? easiestRecipe.getTitle() : "Aucune recette trouvée");
        System.out.println();

        // Afficher les recettes triées par nombre d'ingrédients
        System.out.println("Recettes triées par nombre d'ingrédients :");
        recepieRepo.displayRecipesSortedByIngredientCount();
        System.out.println();

        // Afficher l'ingrédient le plus utilisé
        System.out.println("Ingrédient le plus utilisé : " + recepieRepo.getMostUsedIngredient());
        System.out.println();

        // Afficher les recettes par ingrédient
        System.out.println("Recettes par ingrédient :");
        Map<String, List<String>> recipesByIngredient = recepieRepo.displayRecipesByIngredient();
        System.out.println();
    }
}
