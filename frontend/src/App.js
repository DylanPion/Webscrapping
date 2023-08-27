import "./App.css";
import { Routes, Route } from "react-router-dom";
import Home from "./Component/Home";
import FormUrl from "./Component/FormUrl";
import Navbar from "./Component/Navbar";

function App() {
  return (
    <div className="App">
      <Navbar />
      <Routes>
        <Route path="*" element={<Home />} />
        <Route path="/" element={<Home />} />
        <Route path="/formurl" element={<FormUrl />} />
      </Routes>
    </div>
  );
}

export default App;
