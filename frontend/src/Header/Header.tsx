import { Link } from 'react-router-dom';
import { GiCoffeePot } from 'react-icons/gi';

const Header = () => {
    return (
        <nav className="header-grid">
            <Link to="/home" className="home-button-grid-item">
                <GiCoffeePot size={36} />
            </Link>
        </nav>
    );
};

export default Header;



