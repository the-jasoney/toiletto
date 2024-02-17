import { LatLng } from "leaflet";
import { Toilet } from "../../api/toilet";
import coordsDist from "../../util/haversine";

export function SidePanelToiletElement({toilet, location, key}: {toilet: Toilet, location: LatLng, key: number}) {
    return (
        <div className="toilet" key={key}>
            <p>{coordsDist(toilet.location[0], toilet.location[1], location.lat, location.lng).toFixed(1)} km away</p>
            <p>accessibilityScore: {toilet.accessibilityScore}</p>
            <p>privacyScore: {toilet.privacyScore}</p>
        </div>
    )
}