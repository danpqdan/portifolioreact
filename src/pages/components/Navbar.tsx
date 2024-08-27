import { useEffect, useState } from 'react';
import { FaGithub } from 'react-icons/fa';

import "../css/style.module.navbar.css";

const Navbar = () => {
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
        <div className='navbar' >
            {windowWidth <= 480 && (
                <nav className='navbar' >
                    <a href="https://github.com/danpqdan" className='gitnavbar'>
                        <FaGithub className='giticon' />
                        <span>Daniel Santos</span>
                    </a>
                </nav>
            )}
            {windowWidth > 480 && windowWidth <= 1024 && (
                <nav className='navbar' >
                    <a href="https://github.com/danpqdan" className='gitnavbar'>
                        <FaGithub className='giticon' />
                        <span>Daniel Santos</span>
                    </a>
                </nav>

            )}
            {windowWidth > 1025 && (
                <nav className='navbar' >
                    <a href="https://github.com/danpqdan" target='_blank' className='gitnavbar'>
                        <FaGithub className='giticon' />
                        <span>Daniel Santos</span>
                    </a>
                </nav>

            )}
        </div>
    );
}

export default Navbar;
