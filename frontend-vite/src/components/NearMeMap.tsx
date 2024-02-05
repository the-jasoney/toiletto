import {CircleMarker, MapContainer, Marker, TileLayer, Tooltip} from "react-leaflet";
import 'leaflet/dist/leaflet.css';
import {useEffect, useState} from "react";
import {findToiletsNearLocation, Toilet} from "../api/toilet";

function getWindowDimensions() {
    const {innerWidth: width, innerHeight: height} = window;
    return {
        width, height
    }
}

export function NearMeMap() {
    const [latitude, setLatitude] = useState<number>();
    const [longitude, setLongitude] = useState<number>();
    const [windowDimensions, setWindowDimensions] = useState(getWindowDimensions());
    const [locationEnabled, setLocationEnabled] = useState(false);
    const [nearbyToilets, setNearbyToilets] = useState<Toilet[]>([]);

    // Get current location
    useEffect(() => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                setLatitude(position.coords.latitude);
                setLongitude(position.coords.longitude);
                setLocationEnabled(true);
            })
        } else {
            alert("Geolocation not supported")
        }
    }, []);

    // Get window size
    useEffect(() => {
        const handleResize = () => setWindowDimensions(getWindowDimensions());
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    // Get nearby toilets
    useEffect(() => {
        if (latitude && longitude) {
            findToiletsNearLocation(latitude, longitude, 50, 0, 20).then(v => setNearbyToilets(v))
        }
    }, [latitude, locationEnabled, longitude]);

    return (latitude !== undefined && longitude !== undefined) ? (
        <div>
            <MapContainer center={[latitude, longitude]} zoom={13} scrollWheelZoom={true} style = {{height: windowDimensions.height}}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <CircleMarker center={[latitude, longitude]}>
                    <Tooltip>Your location</Tooltip>
                </CircleMarker>
                {nearbyToilets.map(v => (
                    <Marker position={[v.location[0], v.location[1]]}>
                        <Tooltip>{v.location[0]} {v.location[1]}</Tooltip>
                    </Marker>
                ))}
            </MapContainer>
        </div>
    ) : (
        <div>
            <p>Please allow Toiletto to access your location.</p>
        </div>
    )
}