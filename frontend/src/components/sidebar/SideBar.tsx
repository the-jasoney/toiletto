import {
    Tabs,
    TabsHeader,
    TabsBody,
    Tab,
    TabPanel
} from '@material-tailwind/react'
import { FaLocationDot } from 'react-icons/fa6';
import { FaSearch, FaPlusCircle } from 'react-icons/fa';
import './SideBar.css';
import { Toilet } from '../api/toilet';
import { useState } from 'react';
import { LatLng } from 'leaflet';
import { SidePanelToiletElement } from './SidePanelToiletElement';

export type SidebarProps = {
    toilets: Toilet[] | null,
    selectedToilet: number | null,
    location: LatLng | null,
};

export function SideBar({ toilets, selectedToilet, location }: SidebarProps) {
    const [activeTab, setActiveTab] = useState<'search' | 'location' | 'new'>('search');
    
    return (
        <div className="sidebar float-left h-screen">
            <Tabs orientation="horizontal" value={activeTab}>
                <TabsHeader
                    className="rounded-none border-b border-blue-gray-50 bg-transparent p-0"
                    indicatorProps={{
                        className:"bg-transparent border-b-2 border-gray-900 shadow-none rounded-none",
                    }}
                    >
                    <Tab value="search" onClick={() => setActiveTab('search')}>
                        <FaSearch size={25} />
                    </Tab>
                    <Tab value="location" onClick={() => setActiveTab('location')}>
                        <FaLocationDot size={25} />
                    </Tab>
                    <Tab value="new" onClick={() => setActiveTab('new')}>
                        <FaPlusCircle size={25} />
                    </Tab>
                </TabsHeader>
                <TabsBody>
                    <TabPanel value="search">
                        {toilets.map((v, i) => (
                            <SidePanelToiletElement toilet={v} location={location} key={i}/>
                        ))}
                    </TabPanel>
                    <TabPanel value="location">
                        asfawe
                    </TabPanel>
                </TabsBody>
            </Tabs>
        </div>
    )
}
