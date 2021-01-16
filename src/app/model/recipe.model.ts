import {Step} from "./step.model";
import {RecipeIngredient} from "./recipe-ingredient.model";
import {User} from "./user.model";

export class Recipe {
  id: null
  name: String
  description: String
  preparationTime: number
  instructions: String
  servings: number
  user: User
  ingredients: RecipeIngredient[]
  pictures: any[]
  dummy: any[]
  steps: Step[]
  reviews: any[]
}
