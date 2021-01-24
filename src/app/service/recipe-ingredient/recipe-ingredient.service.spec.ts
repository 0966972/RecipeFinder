import { TestBed } from '@angular/core/testing';
import { RecipeIngredientService } from './recipe-ingredient.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('RecipeIngredientService', () => {
  let service: RecipeIngredientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(RecipeIngredientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
