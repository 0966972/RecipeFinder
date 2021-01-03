import {Ingredient} from "./ingredient";
import {Step} from "./step.model";

export class Recipe {
  name: String
  description: String
  preparationTime: number
  instructions: String
  servings: number
  ingredients: Ingredient[]
  pictures: any[]
  steps: Step[]
}
