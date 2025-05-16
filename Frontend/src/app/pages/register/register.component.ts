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
          this.errorMessage = err.error?.message || 'Registration failed.';
          console.error(err);
          alert(this.errorMessage);
        },
      });
  }
}
