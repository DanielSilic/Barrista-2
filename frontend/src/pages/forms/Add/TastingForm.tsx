import { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Alert } from 'react-bootstrap';

function TastingForm () {

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
        axios.get('/barista/dropdown/kaffeesortename')
            .then(response => {
                setKaffeesorteNames(response.data);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }, []);

    useEffect(() => {
        axios.get("/barista/dropdown/zubereitungsmethodennamen")
            .then(response => {
                setZubereitungsmethodeNames(response.data);
            })
            .catch(error => {
                console.error('Error fetching data:', error)
            })
    },[]);

    const handleSubmit = () => {
        if (!tastingName || !selectedKaffeesorteName || !selectedZubereitungsmethodeName) {
            setErrorMsg("Bitte fülle alle Felder aus, die mit * markiert sind.");
            return;
        }

        const newTasting = {
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

        axios.post('/barista/tasting', newTasting)
            .then(response => {
                console.log('Ist in der DB', response.data);

                setTastingName('');
                setSelectedKaffeesorteName('');
                setSelectedZubereitungsmethodeName('');
                setMahlgrad('');
                setWassersorte('');
                setWassertemperatur('');
                setErgebnis('');
                setBewertung(0);
                setAnmerkungen('');

                setSuccessMsg('Danke! Dein Brew ist nun in der Tasse.');
            })
            .catch(error => {
                setErrorMsg('Ups, da ist leider was schief gelaufen. Versuche es später noch einmal!');
                console.error('Ups, da ist leider was schief gelaufen. Versuche es später noch einmal!', error);
            });
    }

    return (
        <Form>
            <Form.Group controlId="tastingName">
                <Form.Label>Tasting Name *</Form.Label>
                <Form.Control
                    type="text"
                    value={tastingName}
                    onChange={e => setTastingName(e.target.value)}
                    required
                />
            </Form.Group>

            <Form.Group controlId="kaffeesorteName">
                <Form.Label>Kaffeesorte Name *</Form.Label>
                <Form.Control
                    as="select"
                    value={selectedKaffeesorteName}
                    onChange={e => setSelectedKaffeesorteName(e.target.value)}
                    required
                >
                    <option value="">--Select--</option>
                    {kaffeesorteNames.map((name, index) => (
                        <option key={index} value={name}>
                            {name}
                        </option>
                    ))}
                </Form.Control>
            </Form.Group>

            <Form.Group controlId="zubereitungsmethodeName">
                <Form.Label>Zubereitungsmethode Name *</Form.Label>
                <Form.Control
                    as="select"
                    value={selectedZubereitungsmethodeName}
                    onChange={e => setSelectedZubereitungsmethodeName(e.target.value)}
                    required
                >
                    <option value="">--Select--</option>
                    {zubereitungsmethodeNames.map((name, index) => (
                        <option key={index} value={name}>
                            {name}
                        </option>
                    ))}
                </Form.Control>
            </Form.Group>


            <Form.Group controlId="mahlgrad">
                <Form.Label>Mahlgrad</Form.Label>
                <Form.Control
                    type="text"
                    value={mahlgrad}
                    onChange={e => setMahlgrad(e.target.value)}
                />
            </Form.Group>

            <Form.Group controlId="wassersorte">
                <Form.Label>Wassersorte</Form.Label>
                <Form.Control
                    type="text"
                    value={wassersorte}
                    onChange={e => setWassersorte(e.target.value)}
                />
            </Form.Group>

            <Form.Group controlId="wassertemperatur">
                <Form.Label>Wassertemperatur</Form.Label>
                <Form.Control
                    type="text"
                    value={wassertemperatur}
                    onChange={e => setWassertemperatur(e.target.value)}
                />
            </Form.Group>

            <Form.Group controlId="ergebnis">
                <Form.Label>Ergebnis</Form.Label>
                <Form.Control
                    type="text"
                    value={ergebnis}
                    onChange={e => setErgebnis(e.target.value)}
                />
            </Form.Group>

            <Form.Group controlId="bewertung">
                <Form.Label>Bewertung</Form.Label>
                <div>
                    {Array.from({ length: 10 }, (_, i) => i + 1).map(num => (
                        <Form.Check
                            inline
                            type="radio"
                            key={num}
                            label={num}
                            name="bewertung"
                            value={num}
                            onChange={e => setBewertung(parseInt(e.target.value, 10))}
                        />
                    ))}
                </div>
            </Form.Group>

            <Form.Group controlId="anmerkungen">
                <Form.Label>Anmerkungen</Form.Label>
                <Form.Control
                    as="textarea"
                    value={anmerkungen}
                    onChange={e => setAnmerkungen(e.target.value)}
                />
            </Form.Group>

            <Button onClick={handleSubmit}>Add</Button>
            {errorMsg && <Alert variant="danger">{errorMsg}</Alert>}
            {successMsg && <Alert variant="success">{successMsg}</Alert>}
        </Form>
    );

}

export default TastingForm;