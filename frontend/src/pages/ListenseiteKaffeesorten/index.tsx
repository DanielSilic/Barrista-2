import { useState, useEffect } from "react";
import { MdWarehouse } from "react-icons/md";
import { BsFillCupHotFill } from "react-icons/bs"
import "./listenseitekaffeesorten.css";
import axios from "axios";
import { Link } from 'react-router-dom';


interface Kaffeesorte {
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
}

function KaffeesortenList () {
    const [kaffeesorten, setKaffeesorten] = useState<Kaffeesorte[]>([]);
    const [roestereiOptions, setRoestereiOptions] = useState<string[]>([]);
    const [aromenProfil, setAromenProfil] = useState<string>('');
    const [roestereiName, setRoestereiName] = useState<string>('');

    const [totalItems, setTotalItems] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);


    // Fetch initial data
    useEffect(() => {
        axios.get('http://localhost:8080/barista/kaffeesorte').then((res) => {
            setKaffeesorten(res.data.content);
        });
        axios.get('http://localhost:8080/barista/dropdown/roestereiname').then((res) => {
            setRoestereiOptions(res.data);
        });
    }, []);


    const handleFilter = () => {

        const params: Record<string, string | number> = {
            page: 0,
            size: 10
        };

        if (roestereiName) {
            params.roestereiName = roestereiName;
        }

        if (aromenProfil) {
            params.aromenProfil = aromenProfil;
        }

        console.log("Sending params:", params);  // Debug line

        axios.get('http://localhost:8080/barista/kaffeesorten/filter', {params})
            .then(response => {
                setKaffeesorten(response.data.kaffeesorten);
                setTotalItems(response.data.totalItems);
                setCurrentPage(0);
            });

    };

    const loadMore = () => {
        const nextPage = currentPage + 1;

        const params: Record<string, string | number> = {
            page: nextPage,
            size: 10
        };

        if (roestereiName) {
            params.roestereiName = roestereiName;
        }

        if (aromenProfil) {
            params.aromenProfil = aromenProfil;
        }

        axios.get('/barista/kaffeesorten/filter', { params })
            .then((res) => {
                setKaffeesorten((prevKaffeesorten) => [...prevKaffeesorten, ...res.data.kaffeesorten]);
                setTotalItems(res.data.totalItems);  // new line
                setCurrentPage(nextPage);
            });
    };

    return (
        <div>
            <div className="filter-dashboard">
                <select value={aromenProfil} onChange={(e) => setAromenProfil(e.target.value)}>
                    <option value="">Aromenprofil</option>
                    {['blumig', 'fruchtig', 'komplex-fruchtig', 'karamell', 'klassisch-dunkel', 'ausgewogen'].map((option) => (
                        <option key={option} value={option}>
                            {option}
                        </option>
                    ))}
                </select>

                <select value={roestereiName} onChange={(e) => setRoestereiName(e.target.value)}>
                    <option value="">Rösterei</option>
                    {roestereiOptions.map((option) => (
                        <option key={option} value={option}>
                            {option}
                        </option>
                    ))}
                </select>

                <button className="filter-button" onClick={handleFilter}>Filtern</button>
            </div>

            <div className="kaffeesorten-list">
                {kaffeesorten.map((kaffeesorte) => (
                    <div className="kaffeesorte-card" key={kaffeesorte.id}>
                        <div className="kaffeesorte-details">
                            <h3>{kaffeesorte.kaffeesorteName}</h3>
                            <p>
                                <MdWarehouse /> {kaffeesorte.roestereiName}
                            </p>
                            <p>
                                <BsFillCupHotFill /> {kaffeesorte.aromenProfil}
                            </p>
                        </div>
                        <div className="card-actions">
                            <Link to={`/Detailseiten/Kaffeesorte/detail/${kaffeesorte.id}`}>
                                <button className="border-button">Details</button>
                            </Link>
                        </div>
                    </div>
                ))}
            </div>
            <div className="load-more-container">
                <button
                    disabled={totalItems <= kaffeesorten.length}
                    onClick={loadMore}
                    className={`load-more ${totalItems <= kaffeesorten.length ? 'load-more-secondary' : 'load-more-primary'}`}
                >
                    Mehr anzeigen
                </button>
            </div>
        </div>
    );
}

export default KaffeesortenList;
