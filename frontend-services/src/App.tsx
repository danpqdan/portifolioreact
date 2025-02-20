import { useEffect, useState } from "react";
import { FaExpeditedssl } from "react-icons/fa";
import { GiPadlock } from "react-icons/gi";
import { useNavigate } from "react-router-dom";
import "./App.css";
import AppRoutes from "./AppRoute";
import { useAuth } from "./hooks/authContext";

export function App() {
  const navigate = useNavigate();
  const [isShow, setIsShow] = useState(false);
  const { isLoggedIn, logout } = useAuth();

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    if (!!token) {
      logout();
    } else {
      navigate('/login');
    }
  }, []);

  const toogleBar = () => {
    setIsShow((prevState) => !prevState);
  };

  return (
    <div className="App">
      <div className="header">
        <h1>My App</h1>
        <button id="barInit" onClick={toogleBar}>
          X
        </button>
        {isShow && (
          <div id="bar">
            <button type="button" onClick={() => navigate("/login")}>
              Home
            </button>
            <button type="button" onClick={() => navigate("/login")}>
              Login
            </button>
            <button type="button" onClick={() => navigate("/registro")}>
              Registre-se
            </button>
          </div>
        )}
      </div>
      <div className="content-container">
        <AppRoutes />

        {!isLoggedIn && (
          <footer className="footer">
            <div className="buttons-options">
              <button>Torne-se vendedor</button>
              <button>Teste anônimo</button>
            </div>
            <div className="security-info">
              <FaExpeditedssl size={24} />
              <GiPadlock size={24} />
              <p>
                Suas informações estão protegidas e seguem os padrões de
                segurança da indústria e sua comunicação é criptografada.
              </p>
            </div>
          </footer>
        )}
      </div>
    </div>
  );
}
