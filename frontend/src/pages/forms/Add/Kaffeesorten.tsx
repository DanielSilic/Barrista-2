import { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Form, Alert } from 'react-bootstrap';

function AddKaffeesorteForm () {
    // required
    const [kaffeesorteName, setKaffeesorteName] = useState<string>('');
    const [roestereiNames, setRoestereiNames] = useState<string[]>([]);
    const [selectedRoestereiName, setSelectedRoestereiName] = useState<string>('');
    const [variety, setVariety] = useState<string>('');
    const [aufbereitung, setAufbereitung] = useState<string>('');
    const [herkunftsland, setHerkunftsland] = useState<string>('');
    const [aromenProfil, setAromenProfil] = useState<string>('');

    // optional
    const [aromen, setAromen] = useState<string>('');
    const [koerper, setKoerper] = useState<string>('');
    const [suessse, setSuessse] = useState<string>('');
    const [geschmacksnotenHeiss, setGeschmacksnotenHeiss] = useState<string>('');
    const [geschmacksnotenMedium, setGeschmacksnotenMedium] = useState<string>('');
    const [geschmacksnotenKalt, setGeschmacksnotenKalt] = useState<string>('');
    const [freezingDate, setFreezingDate] = useState<string>('');
    const [fotoUrlKaffeesorte, setFotoUrlKaffeesorte] = useState<string>('');

    //messages
    const [errorMsg, setErrorMsg] = useState('');
    const [successMsg, setSuccessMsg] = useState<string>('');

    useEffect(() => {
        axios.get('/barista/dropdown/roestereiname')
            .then(response => {
                setRoestereiNames(response.data);
            });
    }, []);

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

        const newKaffeesorte = {
            kaffeesorteName,
            roestereiName: selectedRoestereiName,
            variety,
            aufbereitung,
            herkunftsland,
            aromenProfil,
            aromen,
            koerper,
            suessse,
            geschmacksnotenHeiss,
            geschmacksnotenMedium,
            geschmacksnotenKalt,
            freezingDate,
            fotoUrlKaffeesorte,
        };

        axios.post('/barista/newkaffeesorte', newKaffeesorte)
            .then(response => {
                console.log('erfolgreich hinzugefügt', response.data);

                setKaffeesorteName('');
                setSelectedRoestereiName('');
                setVariety('');
                setAufbereitung('');
                setHerkunftsland('');
                setAromenProfil('');
                setAromen('');
                setKoerper('');
                setSuessse('');
                setGeschmacksnotenHeiss('');
                setGeschmacksnotenMedium('');
                setGeschmacksnotenKalt('');
                setFreezingDate('');
                setFotoUrlKaffeesorte('');

                setSuccessMsg('Danke! Deine Kaffeesorte ist jetzt in der Rösterei.');
            })
            .catch(error => {
                console.error('Ein Fehler ist aufgetreten:', error);
                setErrorMsg('Ups, da ist leider was schief gelaufen. Versuche es später noch einmal!');
            });
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

                        <Form.Group className="formgroup-add-kaffee-kombi" controlId="suessse">
                            <Form.Label className="data-block">Süsse</Form.Label>
                            <Form.Control
                                className="fixed-width-control"
                                type="text"
                                value={suessse}
                                onChange={e => setSuessse(e.target.value)}
                            />
                        </Form.Group>

                        <Button className="add-button-form" onClick={handleSubmit}>Hinzufügen</Button>
                        {errorMsg && <Alert variant="danger">{errorMsg}</Alert>}
                        {successMsg && <Alert variant="success">{successMsg}</Alert>}
                    </Form>
                </div>
            </div>
        </div>

    );
}

export default AddKaffeesorteForm;
