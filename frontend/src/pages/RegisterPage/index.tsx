import {ChangeEvent, FormEvent, useState} from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";

export default function LoginPage(){

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const nav = useNavigate()
    function onChangeHandlerUsername(event:ChangeEvent<HTMLInputElement>){
        setUsername(event.target.value)
    }

    function onChangeHandlerPassword(event:ChangeEvent<HTMLInputElement>){
        setPassword(event.target.value)
    }

    function login(event:FormEvent<HTMLFormElement>){
        event.preventDefault();
        axios.post("/barista/user/login",undefined, {auth: {username, password}})
            .then(() => nav("/home"))
            .catch((error) => console.log(error))
    }

    return (
        <div>
            <h1>LOGIN</h1>
            <form onSubmit={login}>
                <input type={"text"} id={"username"} placeholder={"enter your username"} required={true}
                       onChange={onChangeHandlerUsername}/>
                <input type={"password"} id={"password"} placeholder={"enter your password"} required={true}
                       onChange={onChangeHandlerPassword}/>
                <button>Login</button>
            </form>
            <Link to={"/register"}>New here?-Register now!</Link>
        </div>
    )
}