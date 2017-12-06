package controller;

import java.util.ArrayList;

import model.DiaryEntry;
import model.Drug;
import model.DrugEntry;
import model.FactorEntry;
import model.Food;
import model.FoodEntry;
import model.HealthProblem;
import model.Ingredient;

public class SearchDiaryEntriesController extends SuperSearchEntriesController{
	
	public static ArrayList<Food> searchFoods(ArrayList<DiaryEntry> entries, String keyword) {
		ArrayList<Food> suggestions = new ArrayList<Food>();
		
		for(DiaryEntry entry : entries) {
			if(entry instanceof FoodEntry) {
				suggestions.addAll(checkFood(((FoodEntry) entry).getFood(), keyword.toLowerCase()));
			}
		}
		
		return suggestions;
	}
	
	public static ArrayList<Drug> searchDrugs(ArrayList<DiaryEntry> entries, String keyword) {
		ArrayList<Drug> suggestions = new ArrayList<Drug>();
		
		for(DiaryEntry entry : entries) {
			if(entry instanceof DrugEntry) {
				Drug drug = ((DrugEntry) entry).getDrug();
						
				if(checkDrug(drug, keyword.toLowerCase()) && !contains(suggestions, drug)) {
					suggestions.add(((DrugEntry) entry).getDrug());
				}
			}
		}
		
		return suggestions;
	}

	public static ArrayList<Ingredient> searchIngredients(ArrayList<DiaryEntry> entries, String keyword) {
		ArrayList<Ingredient> suggestions = new ArrayList<Ingredient>();
		
		for(DiaryEntry entry : entries) {
			if(entry instanceof FoodEntry) {
				ArrayList<Food> foods = checkFood(((FoodEntry) entry).getFood(), null);
				
				for(Food food : foods) {
					for(Ingredient ingredient : food.getIngredients()) {
						if(checkIngredient(ingredient, keyword.toLowerCase()) && !contains(suggestions, ingredient)) {
							suggestions.add(ingredient);
						}
					}
				}
			}
		}
		
		return suggestions;
	}

	public static ArrayList<HealthProblem> searchHealthProblems(ArrayList<DiaryEntry> entries, String keyword) {
		ArrayList<HealthProblem> suggestions = new ArrayList<HealthProblem>();
		String keywordLower = keyword.toLowerCase();
		
		for(DiaryEntry entry : entries) {
			if(entry instanceof FoodEntry) {
				ArrayList<Ingredient> ingredients = getIngredients(((FoodEntry) entry).getFood());
				
				for(Ingredient ingredient : ingredients) {
					for(HealthProblem problem : ingredient.getHealthProblemList()) {
						if(checkHealthProblem(problem, keywordLower) && !contains(suggestions, problem)) {
							suggestions.add(problem);
						}
					}
				}
			} else if(entry instanceof DrugEntry) {
				for(HealthProblem problem : ((DrugEntry) entry).getDrug().getHealthProblemList()) {
					if(checkHealthProblem(problem, keywordLower) && !contains(suggestions, problem)) {
						suggestions.add(problem);
					}
				}
			}
		}
		
		return suggestions;
	}

	public static ArrayList<FactorEntry> searchFactors(ArrayList<DiaryEntry> entries, String keyword) {
		ArrayList<FactorEntry> suggestions = new ArrayList<FactorEntry>();
		String keywordLower = keyword.toLowerCase();
		
		for(DiaryEntry entry : entries) {
			if(entry instanceof FactorEntry) {
				if(keywordLower == null || keywordLower.isEmpty() || entry.getComment().startsWith(keywordLower)) {
					suggestions.add((FactorEntry) entry);
				}
			}
		}
		
		return suggestions;
	}
	
	public static boolean contains(ArrayList<FactorEntry> factors, FactorEntry factor) {
		for(FactorEntry listFactor : factors) {
			if(listFactor.getDescription().equalsIgnoreCase(factor.getDescription())) {
				return true;
			}
		}
		
		return false;
	}
}
