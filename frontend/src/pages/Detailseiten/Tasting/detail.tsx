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


type Tasting = {
    id: string;
    tastingName: string;
    kaffeesorteName: string;
    zubereitungsmethodeName: string;
    mahlgrad: string;
    wassersorte: string;
    wassertemperatur: string;
    ergebnis: string;
    bewertung: number;
    anmerkungen: string;
};

function DetailseiteTasting () {
    const { id } = useParams();
    const navigate = useNavigate();
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const [tasting, setTasting] = useState<Tasting | null>(null);

    useEffect(() => {
        const apiUrl = `/barista/tasting/${id}`;

        axios.get(apiUrl)
            .then(response => {
                setTasting(response.data);
            })
            .catch(error => {
                console.error("There was an error fetching the Tasting:", error);
            });
    }, [id]);

    if (!tasting) {
        return <div>Loading...</div>;
    }

    const openModal = () => {
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setModalIsOpen(false);
    };

    const deleteTasting = () => {
        axios.delete(`/barista/tasting/${id}`)
            .then((response) => {
                console.log('Die Tasse ist ausgeschüttet:', response);
                closeModal();
                navigate(`/ListenseiteKaffeesorten/index`);

            })
            .catch((error) => {
                console.error("Ich konnte das Tasting nicht löschen. Da sind noch ein paar Tropfen in der Tasse:", error);
            });
    };

    return (
        <div className="text-center">
            <Card style={{ width: '30rem', margin: '0 auto' }}>
                <div className="image-container">
                    <img src="/picture_for_brew_450_300.jpg" alt="Coffee Detail" className="coffee-image" />
                </div>
                <Card.Body>
                    <div className="kaffeesorte-header">
                        {tasting.tastingName}
                    </div>
                    <div className="card-indented-text">
                        Kaffeesorte: <span className="subtitle-text">{tasting.kaffeesorteName}</span>
                    </div>
                    <div className="card-indented-text">
                        Zubereitungsmethode: <span className="subtitle-text">{tasting.zubereitungsmethodeName}</span>
                    </div>
                    <div className="card-indented-text">
                        Mahlgrad: <span className="subtitle-text">{tasting.mahlgrad}</span>
                    </div>
                    <div className="card-indented-text">
                        Wassersorte: <span className="subtitle-text">{tasting.wassersorte}</span>
                    </div>
                    <div className="card-indented-text">
                        Wassertemperatur: <span className="subtitle-text">{tasting.wassertemperatur}</span>
                    </div>
                    <div className="card-indented-text">
                        Ergebnis: <span className="subtitle-text">{tasting.ergebnis}</span>
                    </div>
                    <div className="card-indented-text">
                        Bewertung: <span className="subtitle-text">{tasting.bewertung}</span>
                    </div>
                    <div className="card-indented-text">
                        Anmerkungen: <span className="subtitle-text">{tasting.anmerkungen}</span>
                    </div>
                </Card.Body>
            </Card>

            <div className="detail-button-container">
                <Link to={`/Form/Edit/Tasting/${tasting.id}`}>
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
                <button onClick={deleteTasting}>Ja</button>
                <button onClick={closeModal}>Nein</button>
            </Modal>
        </div>
    );
}

export default DetailseiteTasting;
