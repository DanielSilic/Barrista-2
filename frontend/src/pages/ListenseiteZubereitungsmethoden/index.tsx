import React, { useEffect, useState } from 'react';
import axios from 'axios';
import "./listenseiteZM.css";

interface Zubereitungsmethode {
    id: string;
    zubereitungsmethodeName: string;
    barista: string;
    methodenType: string;
    methodenBeschreibung: string;
    fotoUrlZubereitungsmethode: string;
}

const ZubereitungsmethodeList: React.FC = () => {
    const [zubereitungsmethoden, setZubereitungsmethoden] = useState<Zubereitungsmethode[]>([]);
    const [methodenType, setMethodenType] = useState<string>("");
    const [methodenTypeOptions, setMethodenTypeOptions] = useState<string[]>([]);

    useEffect(() => {
        axios.get('http://localhost:8080/barista/dropdown/methodentype').then(response => {
            setMethodenTypeOptions(response.data);
        });
        axios.get("http://localhost:8080/barista/zubereitungsmethoden").then((response) => {
            setZubereitungsmethoden(response.data);
        });
    }, []);

    const handleFilter = () => {
        axios.get(`http://localhost:8080/barista/zubereitungsmethoden/methodentype?methodenType=${methodenType}`).then(response => {
            setZubereitungsmethoden(response.data);
        });
    };

    return (
        <div>
            <div className="filter-dashboard">
                <select value={methodenType} onChange={(e) => setMethodenType(e.target.value)}>
                    <option value="">-- Equipment --</option>
                    {methodenTypeOptions.map(option => (
                        <option key={option} value={option}>{option}</option>
                    ))}
                </select>
                <button className="filter-button" onClick={handleFilter}>Filtern</button>
            </div>

            <div className="zubereitungsmethoden-list">
                {zubereitungsmethoden.map(method => (
                    <div className="zubereitungsmethode-card" key={method.id}>
                        {/* icon */}
                        <div className="zubereitungsmethode-details">
                            <h3>{method.zubereitungsmethodeName}</h3>
                            <p>{method.barista}</p>
                            <p>{method.methodenBeschreibung}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default ZubereitungsmethodeList;
