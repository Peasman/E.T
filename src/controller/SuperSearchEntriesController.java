package controller;

import java.util.ArrayList;

import model.Drug;
import model.Food;
import model.HealthProblem;
import model.Ingredient;

public class SuperSearchEntriesController {

	protected static ArrayList<Food> checkFood(Food food, String keyword) {
		ArrayList<Food> suggestions = new ArrayList<Food>();
		
		if(keyword == null || 
				keyword.isEmpty() || 
				food.getName().toLowerCase().startsWith(keyword)) {
			if(!contains(suggestions, food)) {
				suggestions.add(food);
			}
		}
		
		for(Food subFood : food.getIngredientsOfFoodList()) {
			suggestions.addAll(checkFood(subFood, keyword));
		}
		
		return suggestions;
	}
	
	protected static boolean checkDrug(Drug drug, String keyword) {		
		if(keyword == null || 
				keyword.isEmpty() || 
				drug.getName().toLowerCase().startsWith(keyword) || 
				drug.getDescription().toLowerCase().startsWith(keyword)) {
			return true;
		}
		
		return false;
	}
	
	protected static boolean checkIngredient(Ingredient ingredient, String keyword) {
		if(keyword == null || 
				keyword.isEmpty() || 
				ingredient.getName().toLowerCase().startsWith(keyword) || 
				ingredient.getInformation().toLowerCase().startsWith(keyword)) {
			return true;
		}
		
		return false;
	}
	
	protected static ArrayList<Ingredient> getIngredients(Food food) {
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		ingredients.addAll(food.getIngredients());
		
		for(Food subFood : food.getIngredientsOfFoodList()) {
			ingredients.addAll(getIngredients(subFood));
		}
		
		return ingredients;
	}
	
	protected static boolean checkHealthProblem(HealthProblem problem, String keyword) {
		if(keyword == null || 
				keyword.isEmpty() || 
				problem.getName().toLowerCase().startsWith(keyword) || 
				problem.getDescription().toLowerCase().startsWith(keyword)) {
			return true;
		}
		
		return false;
	}
	
	protected static boolean contains(ArrayList<Food> foods, Food food) {
		for(Food listFood : foods) {
			if(listFood.getName().equalsIgnoreCase(food.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	protected static boolean contains(ArrayList<Ingredient> ingredients, Ingredient ingredient) {
		for(Ingredient listIngredient : ingredients) {
			if(listIngredient.getName().equalsIgnoreCase(ingredient.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	protected static boolean contains(ArrayList<HealthProblem> healthProblems, HealthProblem healthProblem) {
		for(HealthProblem listHealthProblem : healthProblems) {
			if(listHealthProblem.getName().equalsIgnoreCase(healthProblem.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	protected static boolean contains(ArrayList<Drug> drugs, Drug drug) {
		for(Drug listDrug : drugs) {
			if(listDrug.getName().equalsIgnoreCase(drug.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
}
