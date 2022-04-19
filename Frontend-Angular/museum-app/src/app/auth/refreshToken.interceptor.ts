import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Route, Router } from "@angular/router";
//import { request } from "http";
import { BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError } from "rxjs";
import { environment } from "src/environments/environment";
import { AuthService } from "./services/auth.service";
import { TokenStorageService } from "./services/token-storage.service";


@Injectable()
export class RefreshTokenInterceptor implements HttpInterceptor {

    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

    constructor(private tokenService: TokenStorageService, private authService: AuthService, private router : Router) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<Object>> {

        let user = this.tokenService.getUser();
        const isApiURL = req.url.startsWith(environment.API_URL);
        if(user && isApiURL && !req.url.includes('auth/refresh_token')){
            req = req.clone({
                setHeaders: {Authorization : `Bearer ${this.tokenService.getJwt()}`}
            });
        }
        return next.handle(req).pipe(catchError(error => {
            if (error instanceof HttpErrorResponse && !req.url.includes('auth/login') && !req.url.includes('auth/register')  && error.status === 401) {
                if (!this.isRefreshing) {
                    this.isRefreshing = true;
                    this.refreshTokenSubject.next(null);
                    const token = this.tokenService.getRefreshToken();
                    if (token)
                        return this.authService.refreshToken(token).pipe(
                            switchMap((token: any) => {
                                this.isRefreshing = false;
                                this.tokenService.storeJwt(token.jwtToken);
                                this.refreshTokenSubject.next(token.jwtToken);

                                return next.handle(req = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + token.jwtToken) }));
                            }),
                            catchError((err) => {
                                this.isRefreshing = false;
                                if(error instanceof HttpErrorResponse){
                                 this.tokenService.logout();
                                 this.router.navigate(['/']);
                                }
                                return throwError(err);
                            })
                        );
                }
                return this.refreshTokenSubject.pipe(
                    filter(token => token !== null),
                    take(1),
                    switchMap((token) => next.handle(req = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + token) })))
                );

            }
            return throwError(error);
        }
        ));
    }
}

