import { Injectable } from '@angular/core';
import 'rxjs/add/operator/filter';
import Auth0Lock from 'auth0-lock';
import { ServerService } from '../server/server.service';

@Injectable()
export class AuthService {
  lock = new Auth0Lock(
    'M9RVH0f-KJ7Q6Ogex6bOH2o404VZ4avN',
    'freeyourstuff.eu.auth0.com', {
      autoclose: true,
      auth: {
        redirectUrl: 'http://localhost:4200',
        responseType: 'token id_token',
        audience: `https://freeyourstuff.eu.auth0.com/userinfo`,
        params: {
          scope: 'openid profile email'
        }
      }
    });
  userProfile: any;

  constructor(public server: ServerService) { }

  public login(): void {
    this.lock.show({
      language: 'fr',
      languageDictionary: {
        title: "Free Your Stuff"
      },
      theme: {
        logo: 'http://s3-us-west-2.amazonaws.com/downtownfortcollins/events/santa-2013-logo.png',
        primaryColor: '#c62828'
      }
    });
  }

  public handleAuthentication(): void {
    this.lock.on('authenticated', (authResult) => {
      if (authResult && authResult.accessToken && authResult.idToken) {
        this.setSession(authResult);
      }
    });
  }

  private setSession(authResult): void {
    const expiresAt = JSON.stringify((authResult.expiresIn * 1000) + new Date().getTime());
    localStorage.setItem('access_token', authResult.accessToken);
    localStorage.setItem('id_token', authResult.idToken);
    localStorage.setItem('expires_at', expiresAt);
  }

  public logout(): void {
    localStorage.removeItem('access_token');
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');

    localStorage.removeItem('id');
    localStorage.removeItem('firstname');
    localStorage.removeItem('lastname');
    localStorage.removeItem('nickname');
    localStorage.removeItem('phone');
    localStorage.removeItem('photo');
    localStorage.removeItem('email');
    localStorage.removeItem('address');

    location.reload();
  }

  public isAuthenticated(): boolean {
    const expiresAt = JSON.parse(localStorage.getItem('expires_at'));
    return new Date().getTime() < expiresAt;
  }

  public getProfile(callback): void {
    const accessToken = localStorage.getItem('access_token');
    if (!accessToken) {
      throw new Error('Access token must exist to fetch profile');
    }

    const that = this;
    this.lock.getUserInfo(accessToken, function (err, profile) {
      if (profile) {
        that.userProfile = profile;
      }

      var firstname = (profile.given_name != undefined) ? profile.given_name : '';
      var lastname = (profile.family_name != undefined) ? profile.family_name : '';

      localStorage.setItem('firstname', firstname);
      localStorage.setItem('lastname', lastname);
      localStorage.setItem('nickname', profile.nickname);
      localStorage.setItem('photo', profile.picture);
      localStorage.setItem('email', profile.email);

      that.server.getUserByEmail(profile.email, (error, data) => {
        if (error)
          console.log(error);
        // No User found in db
        else if ((!Object.keys(data).length)) {
          that.server.addUser(lastname, firstname, profile.email, (error, result) => {
            if (error)
              console.log(error);
            else
              // We store the user id sent back
              localStorage.setItem('id', (result[0]).id_user);
          });
        }
        // User found in db
        else {
          // We store the user id sent back
          localStorage.setItem('id', (data[0]).id_user);
        }
      });

      callback(err);
    });
  }
}
