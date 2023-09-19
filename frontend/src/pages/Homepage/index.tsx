import './homepage.css'
import { Link } from 'react-router-dom';
import '../../index.css'
export default function Homepage() {

    return (
        <div className="homepage-grid-container">
            <div className="buttons-container">
                <Link className="btn" to="/ListenseiteKaffeesorten/index">
                    <div className="navi-card">
                        <h3>Kaffeesorten</h3>
                    </div>
                </Link>
                <Link className="btn" to="/ListenseiteRoestereien/index">
                    <div className="navi-card">
                        <h3>Kaffeeröstereien</h3>
                    </div>
                </Link>
                <Link className="btn" to="/ListenseiteTastings/index">
                    <div className="navi-card">
                        <h3>Brews</h3>
                    </div>
                </Link>
                <Link className="btn" to="/ListenseiteZubereitungsmethoden/index">
                    <div className="navi-card">
                        <h3>Zubereitungsmethoden</h3>
                    </div>
                </Link>
            </div>
            <div className="add-buttons-container">
                <Link className="add-btn" to="/Form/Add/Kaffeesorte">
                    <div className="navi-card">
                        <h3>Neue Bohnen in der Rösterei?</h3>
                    </div>
                </Link>
                <Link className="add-btn" to="/Form/Add/Tasting">
                    <div className="navi-card">
                        <h3>Frischer Kaffee in der Tasse?</h3>
                    </div>
                </Link>
            </div>
        </div>
    )
}







