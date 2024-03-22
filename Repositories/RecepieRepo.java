package Repositories;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import models.Recepie; // Importation de la classe Recepie

public class RecepieRepo {
    private List<Recepie> recipes;

    public RecepieRepo() {
        this.recipes = new ArrayList<>();
    }

    // Méthode pour ajouter une recette à la collection
    public void addRecipe(Recepie recipe) {
        recipes.add(recipe);

    }

    // Méthode pour récupérer la liste des recettes
    public List<Recepie> getRecipes() {
        return this.recipes;
    }

    // Méthode pour lister les titres des recettes
    public List<String> listRecipeTitles() {
        return recipes.stream()
                .map(Recepie::getTitle)
                .collect(Collectors.toList());
    }

    // Méthode pour calculer le nombre total d’œufs utilisés
    public int calculateTotalEggsUsed() {
        return recipes.stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .filter(ingredient -> ingredient.toLowerCase().contains("egg")) // Rechercher "egg" dans les ingrédients
                .map(ingredient -> {
                    String amountString = ingredient.split(" ")[0]; // Extraire la quantité d'œufs
                    return amountString.equals("*") ? 1 : Integer.parseInt(amountString); // Retourner 1 si "*" est
                                                                                          // utilisé pour indiquer une
                                                                                          // quantité non spécifiée
                })
                .reduce(0, Integer::sum); // Somme des quantités d'œufs utilisées
    }

    // Méthode pour afficher le titre de la recette avec le nombre d'œufs utilisé
    // par recette
    public Map<String, Integer> displayRecipeTitleWithEggsUsedPerRecipe() {
        Map<String, Integer> recipeTitleWithEggsUsed = recipes.stream()
                .collect(Collectors.toMap(
                        Recepie::getTitle,
                        recipe -> recipe.getIngredients().stream()
                                .filter(ingredient -> ingredient.toLowerCase().contains("egg"))
                                .map(ingredient -> {
                                    String amountString = ingredient.split(" ")[0];
                                    return amountString.equals("*") ? 1 : Integer.parseInt(amountString);
                                })
                                .reduce(0, Integer::sum)));

        // Affichage du titre de la recette avec le nombre d'œufs utilisé par recette
        recipeTitleWithEggsUsed.forEach((title, eggsUsed) -> System.out.println(title + ": " + eggsUsed + " œufs"));

        return recipeTitleWithEggsUsed;
    }

    // Méthode pour retourner les recettes utilisant l'huile d'olive
    public List<Recepie> getRecipesUsingOliveOil() {
        return recipes.stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .anyMatch(ingredient -> ingredient.toLowerCase().contains("olive oil")))
                .collect(Collectors.toList());
    }

    // Méthode pour retourner les recettes fournissant moins de 500 calories
    public List<Recepie> getRecipesWithLessThan500Calories() {
        return recipes.stream()
                .filter(recipe -> recipe.getCalories() < 500)
                .collect(Collectors.toList());
    }

    public List<Recepie> getRecipesWithMoreThan5Steps() {
        return recipes.stream()
                .filter(recipe -> recipe.getPreparation().size() > 5)
                .collect(Collectors.toList());
    }

    // Méthode pour retourner la quantité de sucre utilisée par une recette
    // spécifique
    public double getSugarAmountForRecipe(String recipeTitle) {
        return recipes.stream()
                .filter(recipe -> recipe.getTitle().equalsIgnoreCase(recipeTitle))
                .flatMap(recipe -> recipe.getIngredients().stream())
                .filter(ingredient -> ingredient.toLowerCase().contains("sugar"))
                .mapToDouble(ingredient -> {
                    String amountString = ingredient.split(" ")[0];
                    return amountString.equals("*") ? 0 : Double.parseDouble(amountString);
                })
                .sum();
    }

    // Méthode pour afficher les deux premières étapes de la recette spécifiée
    public List<String> displayFirstTwoSteps(String recipeTitle) {
        List<String> firstTwoSteps = new ArrayList<>();
        recipes.stream()
                .filter(recipe -> recipe.getTitle().equalsIgnoreCase(recipeTitle))
                .findFirst() // Trouver la première recette correspondant au titre spécifié
                .ifPresent(recipe -> {
                    List<String> steps = recipe.getPreparation();
                    if (steps.size() >= 2) { // Vérifier s'il y a au moins deux étapes
                        firstTwoSteps.add(steps.get(0));
                        firstTwoSteps.add(steps.get(1));
                    } else {
                        firstTwoSteps.add("La recette " + recipeTitle + " n'a pas assez d'étapes.");
                    }
                });
        return firstTwoSteps;
    }

    // Méthode pour retourner les recettes qui ne contiennent pas de beurre
    public List<Recepie> getRecipesWithoutButter() {
        return recipes.stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .noneMatch(ingredient -> ingredient.toLowerCase().contains("butter")))
                .collect(Collectors.toList());
    }

    public List<Recepie> getRecipesWithCommonIngredients(String recipeTitle) {
        // Récupérer les ingrédients de la recette spécifiée
        Optional<Recepie> optionalRecipe = recipes.stream()
                .filter(recipe -> recipe.getTitle().equalsIgnoreCase(recipeTitle))
                .findFirst();

        if (optionalRecipe.isPresent()) {
            Recepie specifiedRecipe = optionalRecipe.get();
            List<String> specifiedIngredientNames = specifiedRecipe.getIngredients().stream()
                    .map(ingredient -> ingredient.split(" ")[2])
                    .collect(Collectors.toList());

            // Retourner les recettes avec des ingrédients en commun avec la recette
            // spécifiée
            return recipes.stream()
                    .filter(recipe -> !recipe.getTitle().equalsIgnoreCase(recipeTitle)) // Exclure la recette spécifiée
                    .filter(recipe -> recipe.getIngredients().stream()
                            .map(ingredient -> ingredient.split(" ")[2]) // Récupérer le nom de l'ingrédient
                            .anyMatch(specifiedIngredientNames::contains))
                    .collect(Collectors.toList());
        } else {
            // Retourner une liste vide si la recette spécifiée n'est pas trouvée
            return Collections.emptyList();
        }
    }

    public Recepie displayMostCaloricRecipe() {
        Optional<Recepie> mostCaloricRecipe = recipes.stream()
                .max(Comparator.comparingInt(Recepie::getCalories));

        return mostCaloricRecipe.orElse(null);
    }

    // Méthode pour retourner l’unité la plus fréquente parmi les ingrédients de
    // toutes les recettes
    // Méthode pour retourner l'unité la plus fréquente parmi les ingrédients de
    public String getMostFrequentUnit() {
        // Compter l'occurrence de chaque unité dans tous les ingrédients
        Map<String, Long> unitCount = recipes.stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .map(ingredient -> {
                    // Diviser l'ingrédient en parties séparées par l'espace et récupérer l'unité
                    String[] parts = ingredient.split(" ");
                    if (parts.length > 1) {
                        return parts[1].trim(); // Extraire l'unité de chaque ingrédient et supprimer les espaces
                                                // inutiles
                    } else {
                        return ""; // Si l'unité est absente, retourner une chaîne vide
                    }
                })
                .filter(unit -> !unit.isEmpty()) // Filtrer les chaînes vides
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Afficher le contenu de la carte unitCount
        System.out.println("Contenu de la carte unitCount : " + unitCount);

        // Trouver l'unité avec le comptage le plus élevé
        Map.Entry<String, Long> maxEntry = unitCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        // Retourner l'unité la plus fréquente
        if (maxEntry != null) {
            return maxEntry.getKey();
        } else {
            return "Aucune unité trouvée";
        }
    }

    // Méthode pour calculer le nombre d’ingrédients par recette
    public Map<String, Integer> calculateIngredientsPerRecipe() {
        Map<String, Integer> ingredientsPerRecipe = new HashMap<>();

        for (Recepie recipe : recipes) {
            List<String> ingredients = recipe.getIngredients();
            int ingredientCount = ingredients.size();
            ingredientsPerRecipe.put(recipe.getTitle(), ingredientCount);
        }
        return ingredientsPerRecipe;
    }

    public Recepie getRecipeWithMostFat(List<Recepie> recipes) {
        // Utiliser map pour créer une carte de recettes avec leur pourcentage de
        // graisses
        Map<Recepie, String> recipeFatMap = recipes.stream()
                .collect(Collectors.toMap(Function.identity(), Recepie::getFatPercentage));

        // Utiliser reduce pour trouver la recette avec le pourcentage de graisses le
        // plus élevé
        Optional<Map.Entry<Recepie, String>> maxFatRecipe = recipeFatMap.entrySet().stream()
                .reduce((entry1, entry2) -> {
                    if (parsePercentage(entry1.getValue()) > parsePercentage(entry2.getValue())) {
                        return entry1;
                    } else {
                        return entry2;
                    }
                });

        // Récupérer la recette avec le plus de graisses
        return maxFatRecipe.map(Map.Entry::getKey).orElse(null);
    }

    // Méthode utilitaire pour convertir le pourcentage de chaîne en entier
    private int parsePercentage(String percentage) {
        return Integer.parseInt(percentage.replaceAll("[^0-9]", ""));
    }

    // Méthode pour afficher les recettes triées par nombre d'ingrédients
    public List<Recepie> displayRecipesSortedByIngredientCount() {
        // Clone la liste des recettes pour éviter de modifier l'original
        List<Recepie> sortedRecipes = new ArrayList<>(recipes);

        // Trie la liste des recettes en fonction du nombre d'ingrédients
        sortedRecipes.sort(Comparator.comparingInt(recipe -> recipe.getIngredients().size()));

        // Affiche les recettes triées
        System.out.println("Recettes triées par nombre d'ingrédients :");
        for (Recepie recipe : sortedRecipes) {
            System.out.println(recipe.getTitle() + " - Nombre d'ingrédients : " + recipe.getIngredients().size());
        }

        return sortedRecipes;
    }

    public String getMostUsedIngredient() {
        // Compter l'occurrence de chaque ingrédient
        Map<String, Long> ingredientCount = recipes.stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .map(ingredient -> {
                    // Diviser l'ingrédient en parties séparées par l'espace
                    String[] parts = ingredient.split(" ");
                    // Join les parties restantes de l'ingrédient (après l'unité)
                    return String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Trouver l'ingrédient avec le comptage le plus élevé
        Map.Entry<String, Long> maxEntry = ingredientCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        // Retourner l'ingrédient le plus fréquent ou un message si aucun n'est trouvé
        return maxEntry != null ? maxEntry.getKey() : "Aucun ingrédient trouvé";
    }
    // 20 Méthode qui affiche pour chaque ingrédient, les recettes qui l’utilisent.

    public Map<String, List<String>> displayRecipesByIngredient() {
        // Créer une carte pour stocker les recettes par ingrédient
        Map<String, List<String>> recipesByIngredient = new HashMap<>();

        // Parcourir toutes les recettes
        for (Recepie recipe : recipes) {
            // Récupérer les ingrédients de la recette
            List<String> ingredients = recipe.getIngredients();

            // Parcourir les ingrédients de la recette
            for (String ingredient : ingredients) {
                // Extraire le nom de l'ingrédient sans le montant ni l'unité
                String ingredientName = extractIngredientName(ingredient);

                // Vérifier si l'ingrédient est déjà présent dans la carte
                if (recipesByIngredient.containsKey(ingredientName)) {
                    // Si oui, ajouter la recette à la liste des recettes de cet ingrédient
                    recipesByIngredient.get(ingredientName).add(recipe.getTitle());
                } else {
                    // Sinon, créer une nouvelle liste de recettes pour cet ingrédient
                    List<String> recipeList = new ArrayList<>();
                    recipeList.add(recipe.getTitle());
                    recipesByIngredient.put(ingredientName, recipeList);
                }
            }
        }

        // Afficher les recettes par ingrédient
        for (Map.Entry<String, List<String>> entry : recipesByIngredient.entrySet()) {
            System.out.println("Ingrédient : " + entry.getKey());
            System.out.println("Recettes : " + entry.getValue());
            System.out.println();
        }

        return recipesByIngredient;
    }

    // Méthode utilitaire pour extraire le nom de l'ingrédient sans le montant ni
    // l'unité
    private String extractIngredientName(String ingredient) {
        // Supprimer le montant et l'unité de l'ingrédient
        String[] parts = ingredient.split(" ", 2);
        // Retourner uniquement le nom de l'ingrédient
        return parts.length > 1 ? parts[1] : parts[0];
    }

    // 21 Méthode pour calculer la répartition des recettes par étape de réalisation
    public List<Recepie> displayRecipesSortedBySteps() {
        // Copiez la liste des recettes pour ne pas modifier l'original
        List<Recepie> sortedRecipes = new ArrayList<>(recipes);

        // Triez la liste des recettes en utilisant une comparaison basée sur le nombre
        // de steps
        sortedRecipes.sort(Comparator.comparingInt(recipe -> recipe.getPreparation().size()));

        // Affichez les recettes triées
        sortedRecipes.forEach(recipe -> {
            System.out.println("Recette : " + recipe.getTitle());
            System.out.println("Nombre d'étapes : " + recipe.getPreparation().size());
            System.out.println("Liste des étapes :");
            List<String> steps = recipe.getPreparation();
            for (int i = 0; i < steps.size(); i++) {
                System.out.println((i + 1) + ". " + steps.get(i));
            }
            System.out.println(); // Ajoute une ligne vide pour séparer les recettes
        });

        return sortedRecipes;
    }

    public Recepie getEasiestRecipe() {
        // Utiliser la méthode min avec Comparator pour trouver la recette avec le moins
        // d'étapes
        return recipes.stream()
                .min(Comparator.comparingInt(recipe -> recipe.getPreparation().size()))
                .orElse(null);
    }

    // Méthode pour initialiser les données à partir du fichier XML
    public void init() {
        try {
            // Charger le fichier XML
            File xmlFile = new File("recipes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Parcourir les éléments "recipe" dans le fichier XML
            NodeList recipeList = doc.getElementsByTagName("rcp:recipe");
            for (int i = 0; i < recipeList.getLength(); i++) {
                Element recipeElement = (Element) recipeList.item(i);
                String id = recipeElement.getAttribute("id");
                String title = recipeElement.getElementsByTagName("rcp:title").item(0).getTextContent();
                String date = recipeElement.getElementsByTagName("rcp:date").item(0).getTextContent();

                // Récupérer les ingrédients
                NodeList ingredientList = recipeElement.getElementsByTagName("rcp:ingredient");
                List<String> ingredients = new ArrayList<>();
                for (int j = 0; j < ingredientList.getLength(); j++) {
                    Element ingredientElement = (Element) ingredientList.item(j);
                    String ingredientName = ingredientElement.getAttribute("name");
                    String amount = ingredientElement.getAttribute("amount");
                    String unit = ingredientElement.getAttribute("unit");
                    String ingredient = amount + " " + unit + " " + ingredientName;
                    ingredients.add(ingredient);
                }

                // Récupérer les étapes de préparation
                NodeList preparationList = recipeElement.getElementsByTagName("rcp:step");
                List<String> preparation = new ArrayList<>();

                for (int k = 0; k < preparationList.getLength(); k++) {
                    preparation.add(preparationList.item(k).getTextContent());
                }
                // Récupérer les informations nutritionnelles
                Element nutritionElement = (Element) recipeElement.getElementsByTagName("rcp:nutrition").item(0);
                int calories = Integer.parseInt(nutritionElement.getAttribute("calories"));
                String fatPercentage = nutritionElement.getAttribute("fat");
                String carbohydratesPercentage = nutritionElement.getAttribute("carbohydrates");
                String proteinPercentage = nutritionElement.getAttribute("protein");

                // Ajouter d'autres informations nutritionnelles si nécessaire
                // Créer et ajouter la recette à la collection
                Recepie recipe = new Recepie(id, title, date, ingredients, preparation, calories, fatPercentage,
                        carbohydratesPercentage, proteinPercentage);
                addRecipe(recipe);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
