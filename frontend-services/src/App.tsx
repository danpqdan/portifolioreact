import { useEffect, useState } from "react";
import { FaExpeditedssl } from "react-icons/fa";
import { GiPadlock } from "react-icons/gi";
import { useNavigate } from "react-router-dom"; // Remova BrowserRouter
import "./App.css";
import AppRoutes from "./AppRoute";

export function App() {
  const navigate = useNavigate();
  const [isShow, setIsShow] = useState(false);
  const [isLogin, setIsLogin] = useState(true);

  
  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    if(!!token) {
    setIsLogin(false);
    } else {
    setIsLogin(true);
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
        <AppRoutes /> {/* Não precisa de <Router> aqui */}

        {isLogin && (
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
