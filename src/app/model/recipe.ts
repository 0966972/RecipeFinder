import {Ingredient} from "./ingredient";
import {Step} from "./step.model";

export class Recipe {
  name: String
  description: String
  instructions: String
  servings: number
  ingredients: Ingredient[]
  pictures: any[]
  dummy: any[]
  steps: Step[]

}
