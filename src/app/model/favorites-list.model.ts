import {Recipe} from "./recipe.model";

export class FavoritesList {
  public id: bigint;
  public name: string;
  public recipes: Recipe[];
}
