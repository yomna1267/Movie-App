import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';
  errorMessage = '';
  successMessage = '';
  constructor(private authService: AuthService, private router: Router) {}
  onSubmit(){
    if (!this.username || !this.email || !this.password) {
      this.errorMessage = 'Please fill in all fields.';
      return;
    }

    this.authService
      .register({
        username: this.username,
        email: this.email,
        password: this.password,
      })
      .subscribe({
        next: (res) => {
          this.authService.saveToken(res.token);
          this.successMessage = res.message;
          this.router.navigate(['/user-dashboard']);
        },
        error: (err) => {
          const errorData = err.error;

          const extractValidationErrors = (data: any) => {
            const fields = ['username', 'email', 'password'];
            return fields
              .filter(field => data[field])
              .map(field => data[field])
              .join(' | ');
          };

          if (errorData && typeof errorData === 'object') {
            const validationMessage = extractValidationErrors(errorData);
            if (validationMessage) {
              this.errorMessage = validationMessage;
            }
            else if (typeof errorData.message === 'string') {
              this.errorMessage = errorData.message;
            }
            else if (typeof errorData.error === 'string') {
              this.errorMessage = errorData.error;
            }
            else {
              this.errorMessage = 'Registration failed.';
            }
          } else {
            this.errorMessage = 'Registration failed.';
          }
        },
      });
  }
}
