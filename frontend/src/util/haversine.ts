// finds the distance between two coordinates
export default function coordsDist(lat1: number, lon1: number, lat2: number, lon2: number) {
     const R = 6371; // km
     //has a problem with the .toRad() method below.
     const x1 = lat2-lat1;
     const dLat = x1 * Math.PI / 180;
     const x2 = lon2-lon1;
     const dLon = x2 * Math.PI / 180;
     const a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                     Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                     Math.sin(dLon/2) * Math.sin(dLon/2);
     const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
     const d = R * c;
     
     return d;
}
