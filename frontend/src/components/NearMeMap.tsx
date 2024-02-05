import {CircleMarker, MapContainer, Marker, Popup, TileLayer, Tooltip} from "react-leaflet";
import {useCallback, useEffect, useState} from "react";
import {findToiletsNearLocation, Toilet} from "../api/toilet.ts";
import { Spinner } from "flowbite-react";
import coordsDist from '../util/haversine';

import 'leaflet/dist/leaflet.css';
import './NearMeMap.css'

function getWindowDimensions() {
    const {innerWidth: width, innerHeight: height} = window;
    return {
        width, height
    }
}

export function NearMeMap() {
    const [latitude, setLatitude] = useState<number | null>(null);
    const [longitude, setLongitude] = useState<number | null>(null);
    const [windowDimensions, setWindowDimensions] = useState(getWindowDimensions());
    const [locationEnabled, setLocationEnabled] = useState(false);
    const [nearbyToilets, setNearbyToilets] = useState<Toilet[]>([]);

    // Get cached location (navigator.geolocation can take up to 30 seconds!)
    useEffect(() => {
        const cachedLatitude = localStorage.getItem('latitude');
        const cachedLongitude = localStorage.getItem('longitude');

        if (cachedLatitude && cachedLongitude) {
        setLatitude(Number.parseFloat(cachedLatitude) as number);
            setLongitude(Number.parseFloat(cachedLongitude) as number);
        }
    }, []);

    // Get current location
    useEffect(() => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                setLatitude(position.coords.latitude);
                setLongitude(position.coords.longitude);
                setLocationEnabled(true);
                localStorage.setItem('latitude', position.coords.latitude.toString());
                localStorage.setItem('longitude', position.coords.longitude.toString());
                console.info('geolocated')
            })
        } else {
            alert("Geolocation not supported")
        }
    }, []);

    // Get window size
    useEffect(() => {
        const handleResize = () => setWindowDimensions(getWindowDimensions());
        window.addEventListener('resize', handleResize);
        console.info('window size found')
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    // Get nearby toilets
    useEffect(() => {
        if (latitude && longitude)
            findToiletsNearLocation(latitude, longitude, 50000000, 0, 20).then(v => setNearbyToilets(v));
    }, [latitude, locationEnabled, longitude]);

    return (latitude != null && longitude != null) ? (
        <div>
            <MapContainer center={[latitude, longitude]} zoom={13} scrollWheelZoom={true} style = {{height: windowDimensions.height}}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <CircleMarker center={[latitude, longitude]}>
                    <Tooltip>Your location</Tooltip>
                </CircleMarker>
                {nearbyToilets.map(v => {
                    const distance = coordsDist(latitude, longitude, v.location[0], v.location[1]);
                    return (
                        <Marker position={[v.location[0], v.location[1]]}>
                            <Tooltip>{distance} km</Tooltip>
                            <Popup>
                                
                            </Popup>
                        </Marker>
                    )
                })}
            </MapContainer>
        </div>
    ) : (
        <div className="flex flex-wrap items-center gap-2 waiting-screen">
            <div className="spinner">
                <Spinner size="xs" />
            </div>
            <p>Please allow toiletto to access your location if prompted</p>
        </div>
    )
}
