export class Arifact {
    name : string;
    type : string;
    bytes : any;
    uri : any;

  constructor(
    name: string, 
    type: string, 
    bytes: any, 
    uri: any
) {
    this.name = name
    this.type = type
    this.bytes = bytes
    this.uri = uri
  }

}