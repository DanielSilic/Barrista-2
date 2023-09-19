import { useEffect, useState} from 'react';
import { useParams } from 'react-router-dom';
import { Card } from 'react-bootstrap';
import { BiSolidCoffeeBean } from 'react-icons/bi';
import axios from 'axios';
import { Link } from 'react-router-dom';


type Roesterei = {
    id: string;
    roestereiName: string;
    roestereiBeschreibung: string;
    fotoUrlRoesterei: string;
};

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
    fotoUrlKaffeesorte: string;
    geschmacksnotenHeiss: string;
    geschmacksnotenMedium: string;
    geschmacksnotenKalt: string;
    freezingDate: string;
};


function DetailseiteRoesterei() {
    const { roestereiName } = useParams<{ roestereiName: string }>();
    const [roesterei, setRoesterei] = useState<Roesterei | null>(null);
    const [kaffeesorten, setKaffeesorten] = useState<Kaffeesorte[]>([]);

    useEffect(() => {
        const apiUrlRoesterei = `http://localhost:8080/barista/roesterei/${roestereiName}`;
        const apiUrlKaffeesorten = `http://localhost:8080/barista/kaffeesorte/nachroesterei?roestereiName=${roestereiName}`;

        axios.get(apiUrlRoesterei)
            .then(response => {
                setRoesterei(response.data);
            })
            .catch(error => {
                console.error("There was an error:", error);
            });

        axios.get(apiUrlKaffeesorten)
            .then(response => {
                setKaffeesorten(response.data);
            })
            .catch(error => {
                console.error("There was an error:", error);
            });

    }, [roestereiName]);

    if (!roesterei || !kaffeesorten.length) {
        return <div>Loading...</div>;
    }

    return (
        <div className="text-center">
            <Card style={{ width: '30rem', margin: '0 auto' }}>
                {roesterei.fotoUrlRoesterei &&
                    <Card.Img variant="top" src={roesterei.fotoUrlRoesterei} style={{ objectFit: 'cover', height: '300px' }} />
                }
                <Card.Body>
                    <Card.Title>
                        <BiSolidCoffeeBean /> Roesterei: {roesterei.roestereiName}
                    </Card.Title>
                    <Card.Subtitle className="mb-2">
                        <BiSolidCoffeeBean /> Beschreibung: {roesterei.roestereiBeschreibung}
                    </Card.Subtitle>
                </Card.Body>
            </Card>

            <div className="kaffeesorten-list">
                {kaffeesorten.map((kaffeesorte) => (
                    <div className="kaffeesorte-card" key={kaffeesorte.id}>
                        {/* <BiSolidCoffeeBean className="coffee-icon" /> */}
                        <div className="kaffeesorte-details">
                            <h3>{kaffeesorte.kaffeesorteName}</h3>
                            <p>{kaffeesorte.roestereiName}</p>
                            <p>{kaffeesorte.aromenProfil}</p>
                        </div>
                        <div className="card-actions">
                            <Link to={`/Detailseiten/Kaffeesorte/detail/${kaffeesorte.id}`}>
                                <button className="btn btn-primary">Details</button>
                            </Link>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default DetailseiteRoesterei;
