import { useState } from 'react';
import './App.css';
import { Registro } from './components/registro';

export function App() {

  const [isShow, setIsShow] = useState(false);

  const toogleBar = () => {
    setIsShow(prevState => !prevState);
  }

  return (
    <div className="App">
      <div className='header'>
        <h1>My App</h1>
        <button id='barInit' onClick={toogleBar}> X </button>
        {isShow && (
          <div id='bar'>
            <a className='itensNavigate' href='#'><button>Home</button></a>
            <a className='itensNavigate' href='#'><button>About</button></a>
            <a className='itensNavigate' href='#'><button>Contact</button></a>
          </div>
        )}
      </div >
      <div className='content-container'>
        <Registro />
        <div>
          <div className='buttons-options'>
            <button>Torne-se vendedor</button>
            <button>Teste anonimo</button>
          </div>

        </div>
      </div>


    </div>
  );
}

