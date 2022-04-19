import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidatorFn, ValidationErrors, AbstractControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RegistrationRequest } from 'src/app/model/requests/registration.request';
import { AuthService } from '../services/auth.service';

export const passwordMatchingValidatior: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');

  return password?.value === confirmPassword?.value ? null : { notMatched: true };
};

export const passwordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const password = control.get('password')?.value;

    if (!password) {
      return null;
    }

    const hasUpperCase = /[A-Z]+/.test(password);

    const hasLowerCase = /[a-z]+/.test(password);

    const hasNumeric = /[0-9]+/.test(password);

    const passwordValid = password.length >= 15 && hasUpperCase && hasLowerCase && hasNumeric;

    return !passwordValid ? { invalidPassword: true } : null;
};

export const usernameValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const username = control.get('username')?.value;

    if (!username) {
      return null;
    }

    const hasInvalidChars = /[@#\/]+/.test(username);

    const usernameValid = username.length >= 12 && !hasInvalidChars;

    return usernameValid ? null : {invalidUsername : true}
};

// export function passwordValidator(): ValidatorFn {
//   return (control: AbstractControl): ValidationErrors | null => {

//     const password = control.value;

//     if (!password) {
//       return null;
//     }

//     const hasUpperCase = /[A-Z]+/.test(password);

//     const hasLowerCase = /[a-z]+/.test(password);

//     const hasNumeric = /[0-9]+/.test(password);

//     const passwordValid = password.length >= 15 && hasUpperCase && hasLowerCase && hasNumeric;

//     return !passwordValid ? { invalidPassword: true } : null;
//   }
// }


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public registerForm: FormGroup;
  public hide = true;
  public errorMessage!: string;


  constructor(private formBuilder: FormBuilder, private authService: AuthService, private snackBar: MatSnackBar) {
    this.registerForm = formBuilder.group({
      name: [null, Validators.required],
      surname: [null, Validators.required],
      username: [null, Validators.required],
      password: [null, Validators.required],
      confirmPassword: [null, Validators.required],
      email: [null, Validators.required]
    }, { validators: [passwordMatchingValidatior, passwordValidator, usernameValidator]});
  }

  ngOnInit(): void {
  }



  public registerClick() {
    const request: RegistrationRequest = {
      "name": this.registerForm.value.name,
      "surname": this.registerForm.value.name,
      "username": this.registerForm.value.username,
      "email": this.registerForm.value.email,
      "password": this.registerForm.value.password
    }

    this.authService.register(request).subscribe(
      data => {
        this.registerForm.reset();
        this.snackBar.open('Account created sucessfully!', undefined, { duration: 4000 });
      },
      error => {
        this.snackBar.open('An error occured!', undefined, { duration: 2000 });
      }
    );
  }
}


