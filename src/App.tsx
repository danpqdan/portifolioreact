


import { useEffect, useState } from 'react';
import './App.css';
import CarrouselPrimary from "./pages/CarouselPage";
import MainProfile from "./pages/components/MainProfile";
import Navbar from "./pages/components/Navbar";

function App() {

  const [windowWidth, setWindowWidth] = useState(window.innerWidth);

  // Função para atualizar o estado do tamanho da janela
  const handleResize = () => {
    setWindowWidth(window.innerWidth);
  };

  // Usar useEffect para adicionar e limpar o evento de resize
  useEffect(() => {
    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);
  return (
    <>
      {windowWidth > 1025 && (
        <div id='container'>
          <Navbar />
          <div className='info'>
            <MainProfile />
            <div className='coluna-detalhes'>
              <CarrouselPrimary />
            </div>
          </div>
        </div>
      )}
      {windowWidth <= 1024 && (
        <div id='container'>
          <Navbar />
          <CarrouselPrimary />
        </div>
      )}
    </>
  )
}

export default App
