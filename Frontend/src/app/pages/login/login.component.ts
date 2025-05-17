import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']

})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';
  constructor(private authService: AuthService, private router: Router) {}
  onSubmit() {
    if (!this.username || !this.password) {
      this.errorMessage = 'Please enter both username and password';
      return;
    }
    this.authService.login({ username: this.username, password: this.password }).subscribe({
      next: (response) => {

        this.authService.saveToken(response.token);
        const role = this.authService.getUserRole();
        console.log('User role:', role?.name);
        if (role?.name === 'ADMIN') {
          this.router.navigate(['/admin-dashboard']);
        } else if (role?.name === 'USER') {
          this.router.navigate(['/user-dashboard']);
        }

      },
      error: (error) => {
        this.errorMessage = (error.error?.message || error.message);
        console.error('Login error:', this.errorMessage);
      }
    });
  }
}
