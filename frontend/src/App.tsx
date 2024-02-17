import './App.css';
import { NearMeMap } from './components/NearMeMap';
import { useEffect, useState } from 'react';
import { Toilet, findToiletsNearLocation } from './api/toilet';
import {LatLng} from "leaflet";
import { SideBar } from './components/sidebar/SideBar';

function App() {
  const [location, setLocation] = useState<LatLng | null>(null);
  const [nearbyToilets, setNearbyToilets] = useState<Toilet[]>([]);
  const [selectedToilet, setSelectedToilet] = useState<number | null>(null);

  // Get cached location (navigator.geolocation can take up to 30 seconds!)
  useEffect(() => {
    const cachedLatitude = localStorage.getItem('latitude');
    const cachedLongitude = localStorage.getItem('longitude');

    if (cachedLatitude && cachedLongitude) {
      const latitude = Number.parseFloat(cachedLatitude) as number;
      const longitude = Number.parseFloat(cachedLongitude) as number;
      setLocation(new LatLng(latitude, longitude));
    }
  }, []);

  // Get current location
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(position => {
        setLocation(new LatLng(position.coords.latitude, position.coords.longitude));
        localStorage.setItem('latitude', position.coords.latitude.toString());
        localStorage.setItem('longitude', position.coords.longitude.toString());
        console.info('geolocated')
      })
    } else alert("Geolocation not supported");
  }, []);

  // Get nearby toilets
  useEffect(() => {
    if (location)
      findToiletsNearLocation(location.lat, location.lng, 50000000, 0, 20).then(v => setNearbyToilets(v));
  }, [location]);

  return (
    <div className="main flow-root">
      <SideBar location={location} toilets={nearbyToilets} selectedToilet={selectedToilet} />
      <NearMeMap location={location} toilets={nearbyToilets} selectedToilet={selectedToilet} setSelectedToilet={setSelectedToilet} />
    </div>
  )
}

export default App
