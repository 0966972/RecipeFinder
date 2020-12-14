import {Mappable} from "./mappable.model";

export class Step implements Mappable {
  public number: bigint;
  public name: string;

  map(input: any): this {
    Object.assign(this, input);

    return this;
  }
}
