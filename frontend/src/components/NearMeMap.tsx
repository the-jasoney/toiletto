import {CircleMarker, MapContainer, Marker, Popup, TileLayer, Tooltip, useMap} from "react-leaflet";
import {useEffect, useState} from "react";
import { Toilet } from "../api/toilet.ts";
import { Spinner } from "@material-tailwind/react";
import coordsDist from '../util/haversine';

import 'leaflet/dist/leaflet.css';
import './NearMeMap.css';
import {LatLng} from "leaflet";

function getWindowDimensions() {
    const {innerWidth: width, innerHeight: height} = window;
    return {
        width, height
    }
}

type NearMeMapProps = {
    location: LatLng | null,
    toilets: Toilet[],
    selectedToilet: number | null,
    setSelectedToilet: (idx: number | null) => void | null
}

function SetView({ location }: { location: LatLng }) {
    const map = useMap();
    map.flyTo(location, 10, { animate: true, duration: 2 });
}

export function NearMeMap({ location, toilets, selectedToilet, setSelectedToilet }: NearMeMapProps) {
    const [windowDimensions, setWindowDimensions] = useState(getWindowDimensions());
    const [center, setCenter] = useState<LatLng | null>(location);

    // Get window size
    useEffect(() => {
        const handleResize = () => setWindowDimensions(getWindowDimensions());
        window.addEventListener('resize', handleResize);
        console.info('window size found')
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    useEffect(() => {
        if (selectedToilet != null && toilets.length > 0) {
            const newCenter = new LatLng(toilets[selectedToilet].location[0], toilets[selectedToilet].location[1]);
            setCenter(newCenter);
        }
        else if (location) {
            setCenter(location);
        }
    }, [location, selectedToilet]);

    useEffect(() => {
        console.log(`center: ${center}`)
    }, [center])

    return (center && location) ? (
        <div className="h-screen map float-right">
            <noscript>Toiletto neads JavaScript enabled to work properly.</noscript>
            <MapContainer center={center} zoom={13} scrollWheelZoom={true} style={{height: windowDimensions.height}}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <CircleMarker center={location ?? [0,0]} eventHandlers={{ click: () => {setCenter(location); setSelectedToilet(null)} }}>
                    <Tooltip>Your location</Tooltip>
                </CircleMarker>
                {toilets.map((v, i) => {
                    const distance = coordsDist(location.lat, location.lng, v.location[0], v.location[1]);
                    const onClick = () => {console.debug(`toilet number ${i} clicked`); setSelectedToilet(i)};

                    return (
                        <Marker position={v.location} eventHandlers={{ click: onClick }} key={i}>
                            <Tooltip>{distance} km</Tooltip>
                        </Marker>
                    )
                })}
                <SetView location={center} />
            </MapContainer>
        </div>
    ) : (
        <div className="map h-screen float-right items-center justify-center flex">
            <div className="items-center justify-center flex flex-wrap">
                <h1>
                    Map loading...
                </h1>
                <div className="break"></div>
                <Spinner className="h-12 w-12" />
                <div className="break"></div>
                <p>
                    Please allow toiletto to access your location if prompted
                </p>
            </div>
        </div>
    )
}
