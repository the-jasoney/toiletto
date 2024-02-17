import {Review} from "./review.ts";
import {Message} from "./message.ts";
import {default as axiosDefault} from 'axios';

export interface Toilet {
    id: string,
    reviews: Review[],
    location: [number, number],
    accessibilityScore?: number,
    cleanlinessScore?: number,
    privacyScore?: number,
}

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;
const axios = axiosDefault.create({
    baseURL: BACKEND_URL + '/toilets',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json'
    }
});

export async function getToilet(id: string): Promise<Toilet | Message> {
    const response = await axios.get(`/get/${id}`).catch(error => {throw error})

    if (response.status === 200)
        return await response.data as Toilet;
    else if (response.status === 404)
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

export async function createToilet(token: string, latitude: number, longitude: number): Promise<string | Message> {
    const response = await axios.post('/create', {longitude, latitude}, {
        headers: {
            "Authorization": `Bearer ${token}`
        },
    });

    if (response.status === 201)
        return await response.data as string;
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
    const response = await axios.get('/nearMe', {params: {
        latitude, longitude, maxDist, skip, take
    }})
    console.log(response.data);
    if (response.status === 200)
        return await response.data as Toilet[]
    else throw new Error("Unexpected non-ok status code from /toilets/nearMe")
}
