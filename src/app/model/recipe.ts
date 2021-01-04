import {Step} from "./step.model";
import {RecipeIngredient} from "./recipe-ingredient";

export class Recipe {
  id: null
  name: String
  description: String
  instructions: String
  servings: number
  ingredients: RecipeIngredient[]
  pictures: any[]
  steps: Step[]
  reviews: any[]
}
