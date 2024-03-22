import javax.swing.*;
import java.awt.*;

import java.util.List;
import Repositories.RecepieRepo;
import models.Recepie;
import java.util.Map;

public class RecipeGUI extends JFrame {
    private JList<String> recipeList;
    private JTextArea displayArea;
    private RecepieRepo recipeRepo;

    public RecipeGUI() {
        recipeRepo = new RecepieRepo();
        recipeRepo.init(); // Initialisation des données depuis le fichier XML

        setTitle("Recettes");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création de la liste des recettes
        recipeList = new JList<>();
        recipeList.setOpaque(false); // Rendre la liste des recettes transparente
        JScrollPane scrollPane = new JScrollPane(recipeList);

        // Création de la zone d'affichage
        displayArea = new JTextArea();
        displayArea.setOpaque(false);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.BOLD, 14)); // Utilisation d'une police différente et taille de
                                                               // police plus grande

        JScrollPane displayScrollPane = new JScrollPane(displayArea);

        // Panneau pour afficher l'image de fond et la zone d'affichage
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayScrollPane, BorderLayout.CENTER);

        // Ajout des boutons
        JButton displayButton = createButton("deux étapes de la Recette", new Color(135, 206, 250)); // Bleu clair
        JButton calculateEggsButton = createButton("Nombre Total d'Oeufs", new Color(144, 238, 144)); // Vert clair
        JButton displayEggsPerRecipeButton = createButton(" Nombre d'Oeufs Par Recette", new Color(255, 165, 0)); // Orange
                                                                                                                  // clair
        JButton displayOliveOilRecipesButton = createButton("Recettes Utilisant de l'Huile d'Olive",
                new Color(255, 215, 0)); // Jaune clair
        JButton displayLessThan500CaloriesButton = createButton("Recettes < 500 Calories", new Color(255, 182, 193)); // Rose
                                                                                                                      // clair
        JButton displayMostCaloricRecipeButton = createButton("Recette la Plus Calorique", new Color(218, 112, 214)); // Violet
                                                                                                                      // clair
        JButton displayMoreThan5StepsButton = createButton("Recettes Avec > 5 Etapes", new Color(64, 224, 208)); // Turquoise
                                                                                                                 // clair
        JButton displayMostUsedIngredientButton = createButton("l'Ingrédient le Plus Utilisé",
                new Color(255, 105, 180)); // Rouge clair

        // Création du panneau pour les boutons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        buttonPanel.add(displayButton);
        buttonPanel.add(calculateEggsButton);
        buttonPanel.add(displayEggsPerRecipeButton);
        buttonPanel.add(displayOliveOilRecipesButton);
        buttonPanel.add(displayLessThan500CaloriesButton);
        buttonPanel.add(displayMoreThan5StepsButton);
        buttonPanel.add(displayMostCaloricRecipeButton);
        buttonPanel.add(displayMostUsedIngredientButton);

        // Création du panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.WEST);
        mainPanel.add(displayPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Ajout du panneau principal à la fenêtre
        getContentPane().add(mainPanel);

        // Rafraîchissement de la liste des recettes
        refreshRecipeList();

        // Définition des actions des boutons
        displayButton.addActionListener(e -> displayFirstTwoSteps());
        calculateEggsButton.addActionListener(e -> calculateTotalEggs());
        displayEggsPerRecipeButton.addActionListener(e -> displayEggsPerRecipe());
        displayOliveOilRecipesButton.addActionListener(e -> displayRecipesUsingOliveOil());
        displayLessThan500CaloriesButton.addActionListener(e -> displayRecipesLessThan500Calories());
        displayMostCaloricRecipeButton.addActionListener(e -> displayMostCaloricRecipe());
        displayMoreThan5StepsButton.addActionListener(e -> displayRecipesWithMoreThan5Steps());
        displayMostUsedIngredientButton.addActionListener(e -> displayMostUsedIngredient());
    }

    // Méthodes pour les actions des boutons
    private void displayFirstTwoSteps() {
        String selectedRecipeTitle = recipeList.getSelectedValue();
        if (selectedRecipeTitle != null) {
            List<String> recipeInfo = recipeRepo.displayFirstTwoSteps(selectedRecipeTitle);
            displayArea.setText(String.join("\n", recipeInfo));
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une recette.");
        }
    }

    private void calculateTotalEggs() {
        int totalEggs = recipeRepo.calculateTotalEggsUsed();
        displayArea.setText("Nombre total d'œufs utilisés : " + totalEggs);
    }

    private void displayEggsPerRecipe() {
        Map<String, Integer> result = recipeRepo.displayRecipeTitleWithEggsUsedPerRecipe();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            stringBuilder.append(entry.getKey()).append(" : ").append(entry.getValue()).append(" œufs\n");
        }
        displayArea.setText(stringBuilder.toString());
    }

    private void displayRecipesUsingOliveOil() {
        List<Recepie> recipes = recipeRepo.getRecipesUsingOliveOil();
        displayRecipes(recipes, "Recettes utilisant de l'Huile d'Olive");
    }

    private void displayRecipesLessThan500Calories() {
        List<Recepie> recipes = recipeRepo.getRecipesWithLessThan500Calories();
        displayRecipes(recipes, "Recettes avec moins de 500 Calories");
    }

    private void displayMostCaloricRecipe() {
        Recepie mostCaloricRecipe = recipeRepo.displayMostCaloricRecipe();
        if (mostCaloricRecipe != null) {
            displayArea.setText("Recette la plus calorique : " + mostCaloricRecipe.getTitle());
        } else {
            displayArea.setText("Aucune recette trouvée.");
        }
    }

    private void displayRecipesWithMoreThan5Steps() {
        List<Recepie> recipes = recipeRepo.getRecipesWithMoreThan5Steps();
        displayRecipes(recipes, "Recettes avec plus de 5 Etapes");
    }

    private void displayMostUsedIngredient() {
        String mostUsedIngredient = recipeRepo.getMostUsedIngredient();
        displayArea.setText("Ingrédient le plus utilisé : " + mostUsedIngredient);
    }

    private void displayRecipes(List<Recepie> recipes, String title) {
        StringBuilder message = new StringBuilder();
        message.append(title).append("\n\n");
        for (Recepie recipe : recipes) {
            message.append(recipe.getTitle()).append("\n");
        }
        displayArea.setText(message.toString());
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        return button;
    }

    private void refreshRecipeList() {
        List<String> recipeTitles = recipeRepo.listRecipeTitles();
        String[] recipeTitlesArray = recipeTitles.toArray(new String[0]);
        recipeList.setListData(recipeTitlesArray);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecipeGUI gui = new RecipeGUI();
            gui.setVisible(true);
        });
    }
}
