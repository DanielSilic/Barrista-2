import { useState, useEffect } from "react";
import { BiSolidCoffeeBean } from "react-icons/bi";
import "./listenseitekaffeesorten.css";
import axios, { AxiosError } from "axios";


type Kaffeesorte = {
    id: string;
    kaffeesorteName: string;
    roestereiName: string;
    variety: string;
    aufbereitung: string;
    herkunftsland: string;
    aromen: string;
    aromenProfil: string;
    koerper: string;
    suesse: string;
};

type FilteredKaffeesortenResponse = {
    content: Kaffeesorte[];
};

 export default function ListenseiteKaffeesorten () {
    const [kaffeesorten, setKaffeesorten] = useState<Kaffeesorte[]>([]);
    const [page, setPage] = useState(1);
    const [aromenProfil, setAromenProfil] = useState("");
    const [roestereiName, setRoestereiName] = useState("");
    const [roestereiOptions, setRoestereiOptions] = useState<string[]>([]);

     useEffect(() => {
         axios.get<string[]>("/barista/dropdown/roestereiname")
             .then((response: { data: string[] }) => setRoestereiOptions(response.data))
             .catch((err: AxiosError) => console.error(err.message));
     }, []);

     const loadKaffeesorten = () => {
         axios.get<FilteredKaffeesortenResponse>(`/barista/kaffeesorten/filter?page=${page}&RoestereiName=${roestereiName}&Aromenprofil=${aromenProfil}`)
             .then((response: { data: FilteredKaffeesortenResponse }) => {
                 setKaffeesorten(prevKaffeesorten => [...prevKaffeesorten, ...response.data.content]);
                 setPage(prevPage => prevPage + 1);
             })
             .catch((err: AxiosError) => console.error(err.message));
     };


     const handleFilter = () => {
        setPage(1);
        setKaffeesorten([]);
        loadKaffeesorten();
    };

    return (
        <div className="kaffeesorte-filter-liste">
            <div className="filter-dashboard">
                <select value={aromenProfil} onChange={(e) => setAromenProfil(e.target.value)}>
                    <option value="">-- Aromenprofil --</option>
                    {["blumig", "fruchtig", "komplex-fruchtig", "karamell", "klassisch-dunkel", "ausgewogen"].map(option => (
                        <option key={option} value={option}>{option}</option>
                    ))}
                </select>

                <select value={roestereiName} onChange={(e) => setRoestereiName(e.target.value)}>
                    <option value="">-- Rösterei --</option>
                    {roestereiOptions.map(option => (
                        <option key={option} value={option}>{option}</option>
                    ))}
                </select>

                <button onClick={handleFilter}>Filtern</button>
            </div>

            <div className="kaffeesorten-list">
                {kaffeesorten.map(kaffeesorte => (
                    <div className="kaffeesorte-card" key={kaffeesorte.id}>
                        <BiSolidCoffeeBean className="coffee-icon" />
                        <div className="kaffeesorte-details">
                            <h3>{kaffeesorte.kaffeesorteName}</h3>
                            <p>{kaffeesorte.roestereiName}</p>
                            <p>{kaffeesorte.aromenProfil}</p>
                        </div>
                        <div className="card-actions">
                            <button>Bearbeiten</button>
                            <button>Löschen</button>
                        </div>
                    </div>
                ))}
            </div>

            <button onClick={loadKaffeesorten}>Mehr anzeigen</button>
        </div>
    );
}



