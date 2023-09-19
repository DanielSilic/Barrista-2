import { useState, useEffect } from "react";
import axios from "axios";
import './listenseiteRoestereien.css'
import { Link } from 'react-router-dom';


interface Roesterei {
    id: string;
    roestereiName: string;
    roestereiBeschreibung: string;
    fotoUrlRoesterei: string;
}
function ListenseiteR () {
    const [roesterei, setRoesterei] = useState<Roesterei[]>([]);

    useEffect(() => {
        axios.get("http://localhost:8080/barista/roestereien").then((response) => {
            setRoesterei(response.data);
        });
    }, []);

    return (
        <div className="container">
            <div className="roesterei-list">
                {roesterei.map((roesterei) => (
                    <div className="roesterei-card" key={roesterei.id} style={{ maxWidth: "100%" }}>
                        <div className="row roesterei">
                            <div className="col-md-roesterei">
                                <img
                                    src={roesterei.fotoUrlRoesterei || "placeholder.jpg"}
                                    alt={roesterei.roestereiName}
                                    className="img-fluid"
                                />
                            </div>
                            <div className="col-md-10-roesterei">
                                <div className="card-body">
                                    <h5 className="card-title">{roesterei.roestereiName}</h5>
                                    <p className="card-text">{roesterei.roestereiBeschreibung}</p>
                                </div>
                            </div>
                            <div className="card-actions">
                                <Link to={`/Detailseiten/Roesterei/detail/${roesterei.roestereiName}`}>
                                    <button className="btn btn-primary">Details</button>
                                </Link>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ListenseiteR;
