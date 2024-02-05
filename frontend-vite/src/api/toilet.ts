import {Review} from "./review";
import {Message} from "./message";

export interface Toilet {
    id: string,
    reviews: Review[],
    location: number[],
    accessibilityScore?: number,
    cleanlinessScore?: number,
    privacyScore?: number,
}

export async function getToilet(id: string): Promise<Toilet | Message> {
    let response = await fetch(new URL(`toilets/get/${id}`, process.env.BACKEND_URL));
    if (response.ok)
        return await response.json() as Toilet;

    if (response.status === 404)
        return {
            message: "Toilet not found",
            devinfo: `Toilet with id ${id} was not found on the database`
        } as Message;
    else if (response.status === 500)
        return {
            message: "Internal server error",
            devinfo: "Check logs for details"
        } as Message;
    else
        throw new Error(`Unexpected status code: api/toilets/get/${id} returned status code ${response.status}`);
}

export async function createToilet(token: string, latitude: number, longitude: number): Promise<String | Message> {
    let response = await fetch(new URL(`toilets/create`, process.env.BACKEND_URL), {
        method: 'POST',
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            latitude,
            longitude
        })
    });

    if (response.status === 201)
        return await response.json() as string;
    else if (response.status === 401)
        return {
            message: "Bad token, refresh page",
            devinfo: `Token ${token} is no longer/is not valid`
        }
    else if (response.status === 500)
        return {
            message: "Internal server error",
            devinfo: "See logs for more info"
        }
    else
        throw new Error(`Unexpected status code: api/toilets/create returned status code ${response.status}`);
}

export async function findToiletsNearLocation(
    latitude: number,
    longitude: number,
    maxDist: number,
    skip: number,
    take: number
): Promise<Toilet[]> {
    const queryParams = new URLSearchParams({
        latitude: latitude.toString(),
        longitude: longitude.toString(),
        maxDist: maxDist.toString(),
        skip: skip.toString(),
        take: take.toString()
    })
    let response = await fetch(new URL(`toilets/nearMe?${queryParams}`, process.env.BACKEND_URL));
    if (response.ok)
        return await response.json() as Toilet[]
    else throw new Error("Unexpected non-ok status code from /toilets/nearMe")
}