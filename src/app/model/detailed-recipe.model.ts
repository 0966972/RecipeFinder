import {Mappable} from "./mappable.model";
import {Ingredient} from "./ingredient";
import {Step} from "./step.model";
import {RecipeIngredient} from "./recipe-ingredient";
import {Review} from "./review.model";

export class DetailedRecipe implements Mappable {
  public id: bigint;
  public name: string;
  public description: string;
  public instructions: string;
  public steps: Step[];
  public servings: number = 0;

  public preparationTime: number = 0;
  public duration: number = 0;
  public ingredients: RecipeIngredient[];
  public pictures: any[];
  public creator: string;
  public reviews: Review[];

  map(input: any): this {
    Object.assign(this, input);
    //TODO place link to user to get creator name

    return this;
  }
}


