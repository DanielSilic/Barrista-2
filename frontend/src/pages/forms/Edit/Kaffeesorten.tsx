import { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Form, Alert } from 'react-bootstrap';
import { useParams, useNavigate } from 'react-router-dom';


function EditKaffeesorteForm() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [kaffeesorteName, setKaffeesorteName] = useState<string>('');
    const [roestereiNames, setRoestereiNames] = useState<string[]>([]);
    const [selectedRoestereiName, setSelectedRoestereiName] = useState<string>('');
    const [variety, setVariety] = useState<string>('');
    const [aufbereitung, setAufbereitung] = useState<string>('');
    const [herkunftsland, setHerkunftsland] = useState<string>('');
    const [aromenProfil, setAromenProfil] = useState<string>('');

    const [aromen, setAromen] = useState<string>('');
    const [koerper, setKoerper] = useState<string>('');
    const [suesse, setSuesse] = useState<string>('');
    const [geschmacksnotenHeiss, setGeschmacksnotenHeiss] = useState<string>('');
    const [geschmacksnotenMedium, setGeschmacksnotenMedium] = useState<string>('');
    const [geschmacksnotenKalt, setGeschmacksnotenKalt] = useState<string>('');
    const [freezingDate, setFreezingDate] = useState<string>('');
    const [fotoUrlKaffeesorte, setFotoUrlKaffeesorte] = useState<string>('');

    const [errorMsg, setErrorMsg] = useState<string>('');
    const [successMsg, setSuccessMsg] = useState<string>('');

    useEffect(() => {
        axios.get('/barista/dropdown/roestereiname')
            .then(response => {
                setRoestereiNames(response.data);
            });
    }, []);

    useEffect(() => {
        axios.get(`/barista/kaffeesorte/${id}`)
            .then(response => {
                const kaffeesorteData = response.data;

                setKaffeesorteName(kaffeesorteData.kaffeesorteName);
                setSelectedRoestereiName(kaffeesorteData.roestereiName);
                setVariety(kaffeesorteData.variety);
                setAufbereitung(kaffeesorteData.aufbereitung);
                setHerkunftsland(kaffeesorteData.herkunftsland);
                setAromenProfil(kaffeesorteData.aromenProfil);
                setAromen(kaffeesorteData.aromen);
                setKoerper(kaffeesorteData.koerper);
                setSuesse(kaffeesorteData.suesse);
                setGeschmacksnotenHeiss(kaffeesorteData.geschmacksnotenHeiss);
                setGeschmacksnotenMedium(kaffeesorteData.geschmacksnotenMedium);
                setGeschmacksnotenKalt(kaffeesorteData.geschmacksnotenKalt);
                setFreezingDate(kaffeesorteData.freezingDate);
                setFotoUrlKaffeesorte(kaffeesorteData.fotoUrlKaffeesorte);
            })
            .catch(error => {
                console.error("Die Daten konnten nicht geladen werden", error);
            });
    }, [id]);

    const handleSubmit = () => {
        if (!kaffeesorteName ||
            !selectedRoestereiName ||
            !variety ||
            !aufbereitung ||
            !herkunftsland ||
            !aromenProfil) {
            setErrorMsg('Bitte fülle alle Felder mit * aus.');
            return;
        }

        const updatedKaffeesorte = {
            kaffeesorteName,
            roestereiName: selectedRoestereiName,
            variety,
            aufbereitung,
            herkunftsland,
            aromenProfil,
            aromen,
            koerper,
            suesse,
            geschmacksnotenHeiss,
            geschmacksnotenMedium,
            geschmacksnotenKalt,
            freezingDate,
            fotoUrlKaffeesorte,
        };

        axios.put(`/barista/updatedkaffeesorte/${id}`, updatedKaffeesorte)
            .then(() => {
                setSuccessMsg('Bearbeitung erfolgreich gespeichert');
            })
            .catch(() => {
                setErrorMsg('Die Bearbeitung konnte nicht gespeichert werden');
            });
    };

    const navigateToDetailPage = () => {
        navigate(`/Detailseiten/Kaffeesorte/detail/${id}`);
    };

    return (
        <div className="container">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <Form className="container">
                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="kaffeesorteName">
                            <Form.Label className="data-block">Kaffeesorte*</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={kaffeesorteName}
                                onChange={e => setKaffeesorteName(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi formgroup-select-custom" controlId="roestereiName">
                            <Form.Label className="data-block">Rösterei*</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                as="select"
                                value={selectedRoestereiName}
                                onChange={e => setSelectedRoestereiName(e.target.value)}
                            >
                                {roestereiNames.map(name => <option key={name} value={name}>{name}</option>)}
                            </Form.Control>
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="variety">
                            <Form.Label className="data-block">Variety*</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={variety}
                                onChange={e => setVariety(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="aufbereitung">
                            <Form.Label className="data-block">Aufbereitung*</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={aufbereitung}
                                onChange={e => setAufbereitung(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="herkunftsland">
                            <Form.Label className="data-block">Herkunftsland*</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={herkunftsland}
                                onChange={e => setHerkunftsland(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="aromenProfil">
                            <Form.Label className="data-block">Aromenprofil*</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={aromenProfil}
                                onChange={e => setAromenProfil(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="aromen">
                            <Form.Label className="data-block">Aromen</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={aromen}
                                onChange={e => setAromen(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="koerper">
                            <Form.Label className="data-block">Körper</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={koerper}
                                onChange={e => setKoerper(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="suesse">
                            <Form.Label className="data-block">Süße</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={suesse}
                                onChange={e => setSuesse(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="geschmacksnotenHeiss">
                            <Form.Label className="data-block">Geschmacksnoten (Heiss)</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={geschmacksnotenHeiss}
                                onChange={e => setGeschmacksnotenHeiss(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="geschmacksnotenMedium">
                            <Form.Label className="data-block">Geschmacksnoten (Medium)</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={geschmacksnotenMedium}
                                onChange={e => setGeschmacksnotenMedium(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="geschmacksnotenKalt">
                            <Form.Label className="data-block">Geschmacksnoten (Kalt)</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={geschmacksnotenKalt}
                                onChange={e => setGeschmacksnotenKalt(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="freezingDate">
                            <Form.Label className="data-block">Freezing Date</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={freezingDate}
                                onChange={e => setFreezingDate(e.target.value)}
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
export default EditKaffeesorteForm;
