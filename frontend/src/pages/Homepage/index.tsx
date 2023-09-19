import { Button } from 'react-bootstrap';
import './homepage.css'
import { Link } from 'react-router-dom';



export function Homepage() {
    return (
        <div className="buttons-container">
            <Link to="/ListenseiteKaffeesorten/index">
                <Button variant="outline-light" size="lg" className="responsive-text">
                Kaffeesorten
                </Button>
            </Link>
            <Link to="/ListenseiteRoestereien/index">
                <Button variant="outline-light" size="lg" className="responsive-text">
                Kaffeeröstereien
                </Button>
            </Link>
            <Link to="/ListenseiteTaistings/index">
                <Button variant="outline-light" size="lg" className="responsive-text">
                Tastings
                </Button>
            </Link>
            <Link to="ListenseiteZubereitungsmethoden/index">
                <Button variant="outline-light" size="lg" className="responsive-text">
                Zubereitungs<br />methoden
                </Button>
            </Link>    
        </div>
    );
}




