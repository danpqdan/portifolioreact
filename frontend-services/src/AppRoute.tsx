import { Routes, Route } from "react-router-dom";
import { Login } from "./components-global/Login";
import { Registro } from "./components-global/Registro";

const AppRoutes = () => (
  <Routes>
    <Route path="/" element={<Login />} />
    <Route path="/login" element={<Login />} />
    <Route path="/registro" element={<Registro />} />
  </Routes>
);

export default AppRoutes;
