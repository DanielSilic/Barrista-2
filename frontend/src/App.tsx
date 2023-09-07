import { Homepage } from './pages/Homepage';
import ListenseiteKaffeesorten from './pages/ListenseiteKaffeesorten';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/" element={<Homepage />} />
                    <Route path="/ListenseiteKaffeesorten/index" element={<ListenseiteKaffeesorten />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;

