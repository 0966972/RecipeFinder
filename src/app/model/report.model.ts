import {Mappable} from "./mappable.model";
import {User} from "./user.model";
import {Recipe} from "./recipe";

export class Report implements Mappable {
  public reportingUser: User;
  public message: string;
  public reportedUser: User;
  public reportedRecipe: Recipe;

  constructor() {}

  map(input: any): this {
    Object.assign(this, input);

    return this;
  }
}
