import { Toilet } from "./api/toilet";

export interface AppState {
    latitude: number,
    longitude: number,
    toilets: Toilet[],
    selectedToilets: Toilet | null
}