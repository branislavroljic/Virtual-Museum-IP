import { TestBed } from '@angular/core/testing';

import { MuseumDialogService } from './museum-dialog.service';

describe('MuseumDialogService', () => {
  let service: MuseumDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MuseumDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
