import {ChangeEvent, FormEvent, useState} from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";


type Props = {
    setUser: (user:string) => void
}
export default function LoginPage(loginPageProps: Props) {

    const [username, setUsername] = useState("");

    const [password, setPassword] = useState("");

    const nav = useNavigate();

    function onChangeHandlerUsername(event: ChangeEvent<HTMLInputElement>) {
        setUsername(event.target.value)
    }

    function onChangeHandlerPassword(event: ChangeEvent<HTMLInputElement>) {
        setPassword(event.target.value)
    }

    function login(event:FormEvent<HTMLFormElement>) {
        event.preventDefault()
        axios.post("/barista/user/login", undefined, {auth: {username, password}})
            .then((response) => loginPageProps.setUser(response.data))
            .then(() => nav("/home"))
            .catch((error) => console.log(error))
    }

    return (
        <div className="wrapper">
            <div className="card">
                <form onSubmit={login}>
                    <h1>LOGIN</h1>
                    <input type={"text"} id={username} placeholder={"Enter your username"} required={true} onChange={onChangeHandlerUsername}/>
                    <input type={"password"} id={password} placeholder={"Enter your password"} required={true} onChange={onChangeHandlerPassword}/>
                    <button>LOGIN</button>
                </form>
                <Link className="directionLink" to={"/register"}>New here? REGISTER NOW</Link>
            </div>
        </div>
    )
}