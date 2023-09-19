import { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Form, Alert } from 'react-bootstrap';
import { useParams, useNavigate } from 'react-router-dom';

function EditTastingForm (){
    const { id } = useParams();
    const navigate = useNavigate();

    //required
    const [tastingName, setTastingName] = useState('');
    const [kaffeesorteNames, setKaffeesorteNames] = useState([]);
    const [selectedKaffeesorteName, setSelectedKaffeesorteName] = useState('');
    const [zubereitungsmethodeNames, setZubereitungsmethodeNames] = useState([]);
    const [selectedZubereitungsmethodeName, setSelectedZubereitungsmethodeName] = useState('');

    //optional
    const [mahlgrad, setMahlgrad] = useState('');
    const [wassersorte, setWassersorte] = useState('');
    const [wassertemperatur, setWassertemperatur] = useState('');
    const [ergebnis, setErgebnis] = useState('');
    const [bewertung, setBewertung] = useState(0);
    const [anmerkungen, setAnmerkungen] = useState('');

    //messages
    const [errorMsg, setErrorMsg] = useState<string | null>(null);
    const [successMsg, setSuccessMsg] = useState<string | null>(null);

    useEffect(() => {
        axios.get('http://localhost:8080/barista/dropdown/kaffeesortename')
            .then(response => {
                setKaffeesorteNames(response.data);
            })
    }, []);

    useEffect(() => {
        axios.get("http://localhost:8080/barista/dropdown/zubereitungsmethodennamen")
            .then(response => {
                setZubereitungsmethodeNames(response.data);
            })
    },[]);

    useEffect(() => {
        axios.get(`http://localhost:8080/barista/tasting/${id}`)
            .then(response => {
                const TastingData = response.data;

                setAnmerkungen(TastingData.anmerkungen);
                setErgebnis(TastingData.ergebnis);
                setBewertung(TastingData.bewertung);
                setMahlgrad(TastingData.mahlgrad);
                setWassersorte(TastingData.wassersorte);
                setSelectedKaffeesorteName(TastingData.kaffeesorteName);
                setSelectedZubereitungsmethodeName(TastingData.zubereitungsmethodeName);
                setWassertemperatur(TastingData.wassertemperatur);
                setTastingName(TastingData.tastingName);
            })
            .catch(error => {
                console.error("Die Daten konnten nicht geladen werden", error);
            });
    }, [id]);

    const handleSubmit = () => {
        if (!tastingName ||
            !selectedKaffeesorteName ||
            !selectedZubereitungsmethodeName) {
            setErrorMsg('Bitte fülle alle Felder mit * aus.');
            return;
        }

        const updatedTasting = {
            tastingName,
            kaffeesorteName: selectedKaffeesorteName,
            zubereitungsmethodeName: selectedZubereitungsmethodeName,
            mahlgrad,
            wassersorte,
            wassertemperatur,
            ergebnis,
            bewertung,
            anmerkungen
        };

        axios.put(`http://localhost:8080/barista/updatedtasting/${id}`, updatedTasting)
            .then(() => {
                setSuccessMsg('Bearbeitung erfolgreich gespeichert');
            })
            .catch(() => {
                setErrorMsg('Die Bearbeitung konnte nicht gespeichert werden');
            });
    };

    const navigateToDetailPage = () => {
        navigate(`/Detailseiten/Tasting/detail/${id}`);
    };

    return (
        <div className="container">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <Form className="container">
                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="tastingName">
                            <Form.Label className="data-block">Brew *</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={tastingName}
                                onChange={e => setTastingName(e.target.value)}
                                required
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi formgroup-select-custom" controlId="kaffeesorteName">
                            <Form.Label className="data-block">Kaffeesorte *</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                as="select"
                                value={selectedKaffeesorteName}
                                onChange={e => setSelectedKaffeesorteName(e.target.value)}
                                required
                            >
                                {kaffeesorteNames.map(name => <option key={name} value={name}>{name}</option>)}
                            </Form.Control>
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi formgroup-select-custom" controlId="zubereitungsmethodeName">
                            <Form.Label className="data-block">Methode *</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                as="select"
                                value={selectedZubereitungsmethodeName}
                                onChange={e => setSelectedZubereitungsmethodeName(e.target.value)}
                                required
                            >
                                {zubereitungsmethodeNames.map(name => <option key={name} value={name}>{name}</option>)}
                            </Form.Control>
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="mahlgrad">
                            <Form.Label className="data-block">Mahlgrad</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={mahlgrad}
                                onChange={e => setMahlgrad(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="wassersorte">
                            <Form.Label className="data-block">Wassersorte</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={wassersorte}
                                onChange={e => setWassersorte(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="wassertemperatur">
                            <Form.Label className="data-block">Wassertemperatur</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={wassertemperatur}
                                onChange={e => setWassertemperatur(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="ergebnis">
                            <Form.Label className="data-block">Ergebnis</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={ergebnis}
                                onChange={e => setErgebnis(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="bewertung">
                            <Form.Label className="bewertung-label">Bewertung</Form.Label>
                            <div className="inline-radio-container">
                                {Array.from({ length: 10 }, (_, i) => i + 1).map(num => (
                                    <div className="inline-radio" key={num}>
                                        <Form.Check
                                            inline
                                            type="radio"
                                            label={<span className="radio-label">{num}</span>}
                                            name="bewertung"
                                            value={num}
                                            onChange={e => setBewertung(parseInt(e.target.value, 10))}
                                        />
                                    </div>
                                ))}
                            </div>
                        </Form.Group>


                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="anmerkungen">
                            <Form.Label className="data-block">Anmerkungen</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                as="textarea"
                                value={anmerkungen}
                                onChange={e => setAnmerkungen(e.target.value)}
                            />
                        </Form.Group>

                        <div className="detail-button-container">
                            <Button className={`detail-button`} onClick={handleSubmit}>
                                Bearbeitung speichern
                            </Button>
                            <Button variant="secondary" className="detail-button" onClick={navigateToDetailPage}>
                                zurück
                            </Button>
                        </div>
                        {errorMsg &&
                            <div className="centered-alert">
                                <Alert variant="danger">{errorMsg}</Alert>
                            </div>
                        }
                        {successMsg &&
                            <div className="centered-alert">
                                <Alert variant="success">{successMsg}</Alert>
                            </div>
                        }
                    </Form>
                </div>
            </div>
        </div>
    );
}

export default EditTastingForm;
