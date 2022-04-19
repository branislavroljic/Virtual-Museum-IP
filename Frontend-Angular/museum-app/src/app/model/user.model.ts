export class User {
    name: string;
    surname: string;
    username: string;
    email:string;
    role : string;


  constructor(
    name: string, 
    surname: string, 
    username: string, 
    email: string, 
    role: string,
) {
    this.name = name
    this.surname = surname
    this.username = username
    this.email = email
    this.role = role
  }
}