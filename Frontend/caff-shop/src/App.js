import "@fortawesome/fontawesome-free/css/all.min.css";
import 'bootstrap-css-only/css/bootstrap.min.css';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Registration from './pages/registration.js';
import Hello from './pages/hello';
import Login from './pages/login.js';
import Dashboard from "./pages/dashboard.js";


const App = () => {
  return (
    <Router>
        <Routes>
          <Route exact path="/register" element={<Registration />} />
          <Route exact path='/' element={<Hello />} />
          <Route exact path='/login' element={<Login />} />
          <Route exact path='/dashboard' element={<Dashboard />} />
        </Routes>
    </Router>
  );
}

export default App;
