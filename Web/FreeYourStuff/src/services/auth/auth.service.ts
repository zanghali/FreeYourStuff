import { Injectable } from '@angular/core';
// import { Router } from '@angular/router';
import 'rxjs/add/operator/filter';
import * as auth0 from 'auth0-js';

@Injectable()
export class AuthService {

  auth0 = new auth0.WebAuth({
    clientID: 'M9RVH0f-KJ7Q6Ogex6bOH2o404VZ4avN',
    domain: 'freeyourstuff.eu.auth0.com',
    responseType: 'token id_token',
    audience: 'https://freeyourstuff.eu.auth0.com/userinfo',
    redirectUri: 'http://localhost:4200',
    scope: 'openid profile email'
  });
  userProfile: any;

  constructor(/*public router: Router*/) { }

  public login(): void {
    this.auth0.authorize();
  }

  public handleAuthentication(): void {
    // looks for the result of authentication in the URL hash
    this.auth0.parseHash((err, authResult) => {
      if (authResult && authResult.accessToken && authResult.idToken) {
        window.location.hash = '';
        this.setSession(authResult);
        // this.router.navigate(['/home']);
      } else if (err) {
        // this.router.navigate(['/home']);
        console.log(err);
      }
    });
  }

  private setSession(authResult): void {
    // Set the time that the access token will expire at
    const expiresAt = JSON.stringify((authResult.expiresIn * 1000) + new Date().getTime());
    localStorage.setItem('access_token', authResult.accessToken);
    localStorage.setItem('id_token', authResult.idToken);
    localStorage.setItem('expires_at', expiresAt);
  }

  public logout(): void {
    // Remove tokens and expiry time from localStorage
    localStorage.removeItem('access_token');
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');

    localStorage.removeItem('firstname');
    localStorage.removeItem('lastname');
    localStorage.removeItem('nickname');
    localStorage.removeItem('phone');
    localStorage.removeItem('photo');
    localStorage.removeItem('email');
    localStorage.removeItem('address');
    // Go back to the home route
    location.reload();
    // this.router.navigate(['/']);

  }

  public isAuthenticated(): boolean {
    // Check whether the current time is past the
    // access token's expiry time
    const expiresAt = JSON.parse(localStorage.getItem('expires_at'));
    return new Date().getTime() < expiresAt;
  }

  public getProfile(callback): void {
    const accessToken = localStorage.getItem('access_token');
    if (!accessToken) {
      throw new Error('Access token must exist to fetch profile');
    }

    const self = this;
    this.auth0.client.userInfo(accessToken, (err, profile) => {
      if (profile) {
        self.userProfile = profile;
      }
      localStorage.setItem('firstname', profile.given_name);
      localStorage.setItem('lastname', profile.family_name);
      localStorage.setItem('nickname', profile.nickname);
      localStorage.setItem('photo', profile.picture);
      localStorage.setItem('email', profile.email);

      callback(err);
    });
  }
}
