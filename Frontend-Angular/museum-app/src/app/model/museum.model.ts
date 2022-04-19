export class Museum{
    id: number;
    name: string;
    address: string;
    tel: string;
    city: string;
    country: string;
    geolat: number;
    geolng: number;
    type: string;


  constructor(
    id: number, 
    name: string, 
    address: string, 
    tel: string, 
    city: string, 
    country: string, 
    geolat: number, 
    geolng: number, 
    type: string
) {
    this.id = id
    this.name = name
    this.address = address
    this.tel = tel
    this.city = city
    this.country = country
    this.geolat = geolat
    this.geolng = geolng
    this.type = type
  }

}