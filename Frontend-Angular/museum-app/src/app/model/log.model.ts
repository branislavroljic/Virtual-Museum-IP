export class Log{

    user : string;
    type : string;
    message : string;
    dateTime : string;


  constructor(
    user: string, 
    type: string, 
    message: string, 
    dateTime: string
) {
    this.user = user
    this.type = type
    this.message = message
    this.dateTime = dateTime
  }
  
}