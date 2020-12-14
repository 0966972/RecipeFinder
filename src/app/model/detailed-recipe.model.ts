import {Mappable} from "./mappable.model";

export class DetailedRecipe implements Mappable {
  public id: bigint;
  public name: string;
  public description: string;
  public instructions: string;
  public steps: string[];
  public servings: number = 0;
  public duration: number = 0;
  public ingredients: string[][];
  public pictures: string[][];
  public creator: string;

  map(input: any): this {
    Object.assign(this, input);
    //TODO place link to user to get creator name

    return this;
  }
}


