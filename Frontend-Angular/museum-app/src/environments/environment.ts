// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  API_URL: 'http://localhost:8081/api',
  JWT_KEY : "jwt-museum",
  REFRESH_TOKEN_KEY : 'refresh-museum',
  USER_KEY : 'user-museum',
  RSS_URL : 'http://www.huffpost.com/section/arts/feed',
  ADMIN_APP_URL : 'http://localhost:9000/admin_app_war_exploded/login.jsp?token='
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
