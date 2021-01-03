import {Mappable} from "./mappable.model";

export class User implements Mappable {
  public id: number;
  public username: string;
  public role: string;

  constructor() {}

  map(input: any): this {
    Object.assign(this, input);

    return this;
  }
}
