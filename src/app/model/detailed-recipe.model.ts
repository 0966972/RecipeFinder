import {Mappable} from "./mappable.model";
import {Ingredient} from "./ingredient";
import {Step} from "./step.model";

export class DetailedRecipe implements Mappable {
  public id: bigint;
  public name: string;
  public description: string;
  public instructions: string;
  public steps: Step[];
  public servings: number = 0;
  public preparationTime: number = 0;
  public ingredients: Ingredient[];
  public pictures: string[][];
  public creator: string;

  map(input: any): this {
    Object.assign(this, input);
    //TODO place link to user to get creator name

    return this;
  }
}


