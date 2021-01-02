import {Step} from "./step.model";
import {RecipeIngredient} from "./recipe-ingredient";

export class Recipe {
  name: String
  description: String
  instructions: String
  servings: number
  ingredients: RecipeIngredient[]
  pictures: any[]
  steps: Step[]
}
