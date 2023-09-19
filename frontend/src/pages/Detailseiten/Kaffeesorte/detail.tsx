import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Card } from 'react-bootstrap';
import axios from 'axios';
import { Link } from 'react-router-dom';
import Modal from 'react-modal';

const customStyles = {
    content: {
        top: '50%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        marginRight: '-50%',
        transform: 'translate(-50%, -50%)',
        padding: '20px',
        backgroundColor: '#383535',
        border: '1px solid #ccc',
        borderRadius: '10px',
    },
    overlay: {
        backgroundColor: 'rgba(0, 0, 0, 0.75)',
    }
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

function DetailseiteKaffeesorte () {
    const navigate = useNavigate();
    const {id} = useParams();
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const [kaffeesorte, setKaffeesorte] = useState<Kaffeesorte | null>(null);

    useEffect(() => {
        const apiUrl = `http://localhost:8080/barista/kaffeesorte/${id}`;

        axios.get(apiUrl)
            .then(response => {
                setKaffeesorte(response.data);
            })
            .catch(error => {
                console.error("Da ist was schiefgegangen", error);
            });
    }, [id]);

    if (!kaffeesorte) {
        return <div>Loading...</div>;
    }

    const openModal = () => {
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setModalIsOpen(false);
    };

    const deleteKaffeesorte = () => {
        axios.delete(`http://localhost:8080/barista/kaffeesorte/${id}`)
            .then((response) => {
                // Handle successful deletion here (e.g., redirect to another page)
                console.log('Die Bohnen haben nun ausgebohnt:', response);
                closeModal();
                navigate(`/ListenseiteKaffeesorten/index`);

            })
            .catch((error) => {
                console.error("Ich konnte die Kaffeesorte nicht löschen. Da hängen noch ein paar Bohnen im Röster:", error);
            });
    };

    return (
        <div className="text-center">
            <Card style={{ width: '30rem', margin: '0 auto' }}>
                <div className="image-container"></div>
                <Card.Body>
                    <div className="kaffeesorte-header">
                        {kaffeesorte.kaffeesorteName}
                    </div>
                    <div className="card-indented-text">
                        Roesterei Name: <span className="subtitle-text">{kaffeesorte.roestereiName}</span>
                    </div>
                    <div className="card-indented-text">
                        Variety: <span className="subtitle-text">{kaffeesorte.variety}</span>
                    </div>
                    <div className="card-indented-text">
                        Aufbereitung: <span className="subtitle-text">{kaffeesorte.aufbereitung}</span>
                    </div>
                    <div className="card-indented-text">
                        Herkunftsland: <span className="subtitle-text">{kaffeesorte.herkunftsland}</span>
                    </div>
                    <div className="card-indented-text">
                        Aromen: <span className="subtitle-text">{kaffeesorte.aromen}</span>
                    </div>
                    <div className="card-indented-text">
                        Aromen Profil: <span className="subtitle-text">{kaffeesorte.aromenProfil}</span>
                    </div>
                    <div className="card-indented-text">
                        Koerper: <span className="subtitle-text">{kaffeesorte.koerper}</span>
                    </div>
                    <div className="card-indented-text">
                        Suesse: <span className="subtitle-text">{kaffeesorte.suesse}</span>
                    </div>
                    <div className="card-indented-text">
                        Geschmacksnoten Heiss: <span className="subtitle-text">{kaffeesorte.geschmacksnotenHeiss}</span>
                    </div>
                    <div className="card-indented-text">
                        Geschmacksnoten Medium: <span className="subtitle-text">{kaffeesorte.geschmacksnotenMedium}</span>
                    </div>
                    <div className="card-indented-text">
                        Geschmacksnoten Kalt: <span className="subtitle-text">{kaffeesorte.geschmacksnotenKalt}</span>
                    </div>
                    <div className="card-indented-text">
                        Freezing Date: <span className="subtitle-text">{kaffeesorte.freezingDate}</span>
                    </div>
                </Card.Body>
            </Card>
            <div className="detail-button-container">
                <Link to={`/Form/Edit/Kaffeesorte/${kaffeesorte.id}`}>
                    <button className="btn detail-button">Bearbeiten</button>
                </Link>
                <button className="btn detail-button" onClick={openModal}>
                    Löschen
                </button>
            </div>
            <Modal
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                contentLabel="Delete Confirmation"
                style={customStyles}
            >
                <h2>Möchtest Du das wirklich tun?</h2>
                <button onClick={deleteKaffeesorte}>Ja</button>
                <button onClick={closeModal}>Nein</button>
            </Modal>
        </div>
    );
}

export default DetailseiteKaffeesorte;

