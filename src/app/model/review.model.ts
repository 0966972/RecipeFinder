import {Mappable} from "./mappable.model";
import {User} from "./user.model";

export class Review implements Mappable {
  public id: number;
  public score: number;
  public title: string;
  public message: string;
  public pictures: string[];
  public user: User;

  constructor(id?: number, score?: number, title?: string, message?: string, pictures?: string[], user?: User) {
    this.id = id;
    this.score = score;
    this.title = title;
    this.message = message;
    this.pictures = pictures;
    this.user = user;
  }

  map(input: any): this {
    Object.assign(this, input);

    return this;
  }
}
