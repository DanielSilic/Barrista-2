import { IoHomeOutline, IoLogOutOutline } from 'react-icons/io5';

function BottomMenuBar ()  {
    return (
        <div className="bottom-menu-bar">
            <div className="menu-item">
                <IoHomeOutline size={24} />
                <p>Home</p>
            </div>
            <div className="menu-item" style={{ marginLeft: 'auto' }}>
                <IoLogOutOutline size={24} />
                <p>Logout</p>
            </div>
        </div>
    );
}
export default BottomMenuBar;
