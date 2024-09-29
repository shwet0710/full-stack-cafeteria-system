import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { SignupComponent } from '../signup/signup.component';
import { ForgotpasswordComponent } from '../forgotpassword/forgotpassword.component';
import { LoginComponent } from '../login/login.component';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private matdialog: MatDialog, public userService:UserService, private router:Router) { }

  ngOnInit(): void {
    this.userService.checktoken().subscribe((response:any)=>{
      this.router.navigate(['/cafe/dashboard']);
    },(error:any)=>{
      console.log(error);
    })
  }

  handlesignup() {
    const dialogconfig = new MatDialogConfig();
    dialogconfig.width = "550px";
    this.matdialog.open(SignupComponent, dialogconfig); 
  }

  handleforgotpassword() {
    const dialogconfig = new MatDialogConfig();
    dialogconfig.width = "550px";
    this.matdialog.open(ForgotpasswordComponent, dialogconfig);
  }

  handlelogin() {
    const dialogconfig = new MatDialogConfig();
    dialogconfig.width = "550px";
    this.matdialog.open(LoginComponent, dialogconfig);
  }
}
