import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-menu-auth',
  templateUrl: './menu-auth.component.html',
  styleUrls: ['./menu-auth.component.css']
})
export class MenuAuthComponent implements OnInit {

  constructor(public auth: AuthService) { }

  ngOnInit() {
  }

}
