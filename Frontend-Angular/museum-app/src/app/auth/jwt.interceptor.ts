// import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
// import { Injectable } from "@angular/core";
// import { Observable } from "rxjs";
// import { environment } from "src/environments/environment";
// import { AuthService } from "./services/auth.service";
// import { TokenStorageService } from "./services/token-storage.service";

// @Injectable()
// export class JwtInterceptor implements HttpInterceptor{

//     constructor(private tokenStorageService: TokenStorageService){}

//     intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//         let user = this.tokenStorageService.getUser();
//         const isApiURL = req.url.startsWith(environment.API_URL);
//         if(user && isApiURL){
//             req = req.clone({
//                 setHeaders: {Authorization : `Bearer ${this.tokenStorageService.getJwt()}`}
//             });
//         }
//         return next.handle(req);
//     }

// }