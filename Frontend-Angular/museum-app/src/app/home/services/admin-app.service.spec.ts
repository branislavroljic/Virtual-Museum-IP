import { TestBed } from '@angular/core/testing';

import { AdminAppService } from './admin-app.service';

describe('AdminAppService', () => {
  let service: AdminAppService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminAppService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
