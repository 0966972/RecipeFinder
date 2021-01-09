import {Mappable} from "./mappable.model";

export class User implements Mappable {
  public id: number;
  public username: string;
  public role: string;
  public isWarned: boolean;
  public isActive: boolean;

  constructor() {}

  map(input: any): this {
    Object.assign(this, input);

    return this;
  }
}
