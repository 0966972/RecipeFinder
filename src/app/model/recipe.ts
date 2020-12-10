import {Step} from "./step.model";

export class Recipe {
  name: String
  description: String
  instructions: String
  servings: number
  steps: Step[]
}
