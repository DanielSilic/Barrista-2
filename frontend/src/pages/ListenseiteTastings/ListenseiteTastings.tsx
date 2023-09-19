import { useState, useEffect } from "react";
import axios from "axios";
import { Link } from 'react-router-dom';
import "./ListenseiteTastings.css";

interface Tasting {
    id: string;
    tastingName: string;
    kaffeesorteName: string;
    zubereitungsmethodeName: string;
    bewertung: string;
}

function TastingListe() {
    const [tastings, setTastings] = useState<Tasting[]>([]);
    const [kaffeesorteOptions, setKaffeesorteOptions] = useState<string[]>([]);
    const [methodeOptions, setMethodeOptions] = useState<string[]>([]);
    const [bewertungFrontend, setBewertungFrontend] = useState<string>('');
    const [kaffeesorteName, setKaffeesorteName] = useState<string>('');
    const [zubereitungsmethodeName, setZubereitungsmethodeName] = useState<string>('');

    const [totalItems, setTotalItems] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);

    useEffect(() => {
        axios.get('/barista/tastings').then((res) => {
            setTastings(res.data.content);
        });
        axios.get('/barista/dropdown/kaffeesortename').then((res) => {
            setKaffeesorteOptions(res.data);
        });
        axios.get('/barista/dropdown/zubereitungsmethodennamen').then((res) => {
            setMethodeOptions(res.data);
        });
    }, []);

    const handleFilter = () => {
        const params: Record<string, string | number> = {
            page: 0,
            size: 10
        };

        if (kaffeesorteName) {
            params.kaffeesorteName = kaffeesorteName;
        }

        if (zubereitungsmethodeName) {
            params.zubereitungsmethodeName = zubereitungsmethodeName;
        }

        if (bewertungFrontend) {
            params.bewertungFrontend = bewertungFrontend;
        }

        axios.get('http://localhost:8080/barista/tastings/filter', { params })
            .then(response => {
                setTastings(response.data.tastings);
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

        if (kaffeesorteName) {
            params.kaffeesorteName = kaffeesorteName;
        }

        if (zubereitungsmethodeName) {
            params.zubereitungsmethodeName = zubereitungsmethodeName;
        }

        if (bewertungFrontend) {
            params.bewertungFrontend = bewertungFrontend;
        }

        axios.get('http://localhost:8080/barista/tastings/filter', { params })
            .then((res) => {
                setTastings((prevTastings) => [...prevTastings, ...res.data.tastings]);
                setTotalItems(res.data.totalItems);
                setCurrentPage(nextPage);
            });
    };

    return (
        <div>
            <div className="filter-dashboard">
                <select value={kaffeesorteName} onChange={(e) => setKaffeesorteName(e.target.value)}>
                    <option value="">Kaffeesorte</option>
                    {kaffeesorteOptions.map((option) => (
                        <option key={option} value={option}>
                            {option}
                        </option>
                    ))}
                </select>

                <select value={zubereitungsmethodeName} onChange={(e) => setZubereitungsmethodeName(e.target.value)}>
                    <option value="">Methode</option>
                    {methodeOptions.map((option) => (
                        <option key={option} value={option}>
                            {option}
                        </option>
                    ))}
                </select>

                <select value={bewertungFrontend} onChange={(e) => setBewertungFrontend(e.target.value)}>
                    <option value="">Bewertung</option>
                    {['schlecht', 'mittel', 'gut', 'top'].map((option) => (
                        <option key={option} value={option}>
                            {option}
                        </option>
                    ))}
                </select>

                <button className="filter-button" onClick={handleFilter}>Filtern</button>
            </div>

            <div className="tasting-list">
                {tastings.map((tasting) => (
                    <div className="tasting-card" key={tasting.id}>
                        <div className="tasting-details">
                            <h3>{tasting.tastingName}</h3>
                            <p>{tasting.kaffeesorteName}</p>
                            <p>{tasting.zubereitungsmethodeName}</p>
                        </div>
                        <div className="card-actions">
                            <Link to={`/Detailseiten/Tasting/detail/${tasting.id}`}>
                                <button className="border-button">Details</button>
                            </Link>
                        </div>
                    </div>
                ))}
            </div>
            <div className="load-more-container">
                <button
                    disabled={totalItems <= tastings.length}
                    onClick={loadMore}
                    className={`load-more ${totalItems <= tastings.length ? 'load-more-secondary' : 'load-more-primary'}`}
                >
                    Mehr anzeigen
                </button>
            </div>
        </div>
    );
}

export default TastingListe;
