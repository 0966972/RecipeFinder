import {TestBed} from '@angular/core/testing';

import {ListedRecipeService} from './listed-recipe.service';

describe('ListedRecipeService', () => {
  let service: ListedRecipeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListedRecipeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
