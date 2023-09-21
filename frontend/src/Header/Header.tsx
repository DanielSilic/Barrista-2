import {Link, useNavigate} from 'react-router-dom';
import { GiCoffeePot } from 'react-icons/gi';
import axios from "axios";
import {IoArrowBackOutline, IoLogOutOutline} from "react-icons/io5";

const Header = () => {
    const navigate = useNavigate();

    const handleLogout = async () => {
        try {
            await axios.post('/barista/user/logout');
            navigate('/');
        } catch (error) {
            console.error('Logout failed:', error);
        }
    };

    const goBack = () => {
        navigate(-1);
    };


    return (
        <div className="header-grid">
            <div className="menu-item" onClick={goBack}>
                <IoArrowBackOutline size={24} />
            </div>
            <nav className="menu-item">
                <Link to="/home" className="home-button-grid-item">
                    <GiCoffeePot size={36} />
                </Link>
            </nav>
            <div className="menu-item" onClick={handleLogout}>
                <IoLogOutOutline size={24} />
            </div>
        </div>
    );
};

export default Header;



