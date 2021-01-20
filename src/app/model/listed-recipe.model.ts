import {Review} from "./review.model";

export class ListedRecipe {
  id: number
  name: String
  description: String
  preparationTime: number
  servings: number
  pictures: any
  reviews: Review[]

  reviewCount: number
  averageScore: number
}
