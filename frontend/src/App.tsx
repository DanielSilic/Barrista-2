import "./index.css";
import Homepage from './pages/Homepage';
import ListenseiteKaffeesorten from './pages/ListenseiteKaffeesorten';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AddKaffeesorteForm from "./pages/forms/Add/Kaffeesorten.tsx";
import TastingForm from "./pages/forms/Add/TastingForm.tsx";
import TastingListe from "./pages/ListenseiteTastings/ListenseiteTastings.tsx";
import ListenseiteZM from "./pages/ListenseiteZubereitungsmethoden";
import ListenseiteR from "./pages/ListenseiteRoestereien";
import DetailseiteKaffeesorte from "./pages/Detailseiten/Kaffeesorte/detail.tsx";
import DetailseiteTasting from "./pages/Detailseiten/Tasting/detail.tsx";
import DetailseiteRoesterei from "./pages/Detailseiten/Roesterei/detail.tsx";
import EditKaffeesorteForm from "./pages/forms/Edit/Kaffeesorten.tsx";
import EditTastingForm from "./pages/forms/Edit/Tastings.tsx";
import Header from "./Header/Header.tsx";
import ProtectedRoute from "./ProtectedRoutes.tsx";
import RegisterPage from "./pages/RegisterPage";
import LoginPage from "./pages/LoginPage";
import { useState } from "react";
import ScrollToTop from './assets/ScrollToTop.tsx';
import MainLayout from "./assets/MainLayout.tsx";

function App() {
    const [user, setUser] = useState("")

    return (
        <Router>
            <Header />
            <ScrollToTop>
            <Routes>
                <Route path="/" element={<LoginPage setUser={setUser} />} />
                <Route path="/register" element={<RegisterPage />} />
                    <Route element={<ProtectedRoute user={user} />}>
                        <Route path="/home" element={<Homepage />} />
                        <Route path="/test" element={<MainLayout />} />
                        <Route path="/ListenseiteKaffeesorten/index" element={<ListenseiteKaffeesorten />} />
                        <Route path="/ListenseiteTastings/index" element={<TastingListe />} />
                        <Route path="/ListenseiteZubereitungsmethoden/index" element={<ListenseiteZM />} />
                        <Route path="/ListenseiteRoestereien/index" element={<ListenseiteR />} />
                        <Route path="/Form/Add/Kaffeesorte" element={<AddKaffeesorteForm />} />
                        <Route path="/Form/Edit/Kaffeesorte/:id" element={<EditKaffeesorteForm />} />
                        <Route path="/Form/Add/Tasting" element={<TastingForm />} />
                        <Route path="/Form/Edit/Tasting/:id" element={<EditTastingForm />} />
                        <Route path="/Detailseiten/Kaffeesorte/detail/:id" element={<DetailseiteKaffeesorte />} />
                        <Route path="/Detailseiten/Tasting/detail/:id" element={<DetailseiteTasting />} />
                        <Route path="/Detailseiten/Roesterei/detail/:roestereiName" element={<DetailseiteRoesterei />} />
                    </Route>
            </Routes>
            </ScrollToTop>
        </Router>
    );
}

export default App;
