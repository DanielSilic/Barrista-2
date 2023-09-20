import {ChangeEvent, FormEvent, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";

export default function RegisterPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const nav = useNavigate();

    function onChangeUsername(event: ChangeEvent<HTMLInputElement>) {
        setUsername(event.target.value)
    }

    function onChangePassword(event: ChangeEvent<HTMLInputElement>) {
        setPassword(event.target.value)
    }

    function register(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        axios.post("/barista/user/register", {username, password})
            .then(() => nav("/"))
            .catch((error) => console.log(error))
    }

    return (
        <div className="wrapper">
            <div className="card">
                <form onSubmit={register}>
                    <h1>REGISTER</h1>
                    <input type={"text"} required={true} id={username} placeholder={"username"} onChange={onChangeUsername}/>
                    <input type={"password"} required={true} id={password} placeholder={"password"} onChange={onChangePassword}/>
                    <button>Register</button>
                </form>
                <Link className="directionLink" to={"/"}>Schon registriert? Hier geht's lang</Link>

            </div>
        </div>
    )
}