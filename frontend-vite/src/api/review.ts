export interface Review {
    id: string,
    author: string,
    toilet: string,
    creationDate: Date,
    content: string,
    status: Status,
    bathroomType: string,
    accessibilityRating?: number,
    cleanlinessRating?: number,
    privacyRating?: number,
    paymentRequired?: boolean,
    singleStall?: boolean,
    hasBabyChangingStation?: boolean
}

export enum Status {
    OPEN = "OPEN",
    CLOSED = "CLOSED",
}

export enum BathroomType {
    MEN = "MEN",
    WOMEN = "WOMEN",
    FAMILY = "FAMILY",
    OTHER = "OTHER"
}