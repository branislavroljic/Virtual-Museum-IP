export interface PurchaseRequest{
    name: string;
    surname: string;
    cardNumber: string;
    cardType: string;
    expirationDate: Date;
    pin: string;
    requestedAmount : Number;
}