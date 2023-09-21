import {Navigate, Outlet} from "react-router-dom";

type Props = {
    user: string|undefined
}
export default function ProtectedRoute(props: Props) {
    const authenticated:boolean = props.user !== undefined && props.user !== "AnonymousUser" && props.user !== "";

    return (
        authenticated ? <Outlet/> : <Navigate to={"/"}/>
    )
}