import {Step} from "./step.model";
import {RecipeIngredient} from "./recipe-ingredient";

export class Recipe {
  id: null
  name: String
  description: String
  preparationTime: number
  instructions: String
  servings: number
  ingredients: RecipeIngredient[]
  pictures: any[]
  dummy: any[]
  steps: Step[]
  reviews: any[]
}
