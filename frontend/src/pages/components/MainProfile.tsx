import { useEffect, useState } from 'react';
import { DiAtom } from 'react-icons/di';
import { FaGithub, FaInstagram, FaLinkedinIn, FaWhatsapp } from 'react-icons/fa';
import { FcLike } from 'react-icons/fc';
import imgdan from '../../assets/danimg.jpg';

import "../css/style.module.mainprofile.css";
import { useGetLikes, usePostLike } from '../../hook/useHookLike';
import { Modal } from './ModalReview';

const MainProfile = () => {

    const { likes } = useGetLikes();
    const { postLike } = usePostLike();
    const [showModal, setShowModal] = useState(false);
    const openModal = () => setShowModal(true);
    const closeModal = () => {
        setShowModal(false);
    }


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
            {windowWidth <= 480 && (

                <div className='info-coluna'>
                    <img src={imgdan} className='imgperfil' />
                    <div className='info-coluna-header'>
                        <DiAtom id='icon-perfil' />
                        <h2>Daniel Santos</h2>
                        <DiAtom id='icon-perfil' />
                    </div>
                    <div className='info-coluna-foot'>
                        <h3>Desenvolvedor de Sotware</h3>
                        <h3>27 anos</h3>
                        <h3>São Paulo SP</h3>
                        <h3>Analise e desenvolvimento de software</h3>
                    </div>
                    <div className='info-coluna-btn'>

                        <button><a href="https://www.instagram.com/danpqpdan/" target='_blank' ><FaInstagram /></a></button>
                        <button><a href="https://www.linkedin.com/in/daniel-santos-694578333s" target='_blank' ><FaLinkedinIn /></a></button>
                        <button ><a href="https://github.com/danpqdan" target='_blank' ><FaGithub /></a></button>
                        <button><a href="tel:+5511962696757"><FaWhatsapp /></a></button>
                        <button onClick={openModal}><form><FcLike /> {likes}</form><label></label></button>
                    </div>
                    <Modal showModal={showModal} closeModal={closeModal} postLike={postLike} />
                </div>
            )}
            {windowWidth > 480 && windowWidth <= 1024 && (
                <div className='info-coluna'>
                    <div className='container-image'>
                        <img src={imgdan} className='imgperfil' />
                    </div>
                    <div className='container-info'>
                        <div className='info-coluna-header'>
                            <DiAtom id='icon-perfil' />
                            <h2>Daniel Santos</h2>
                            <DiAtom id='icon-perfil' />
                        </div>
                        <h3>Desenvolvedor de Sotware</h3>
                        <h3>27 anos</h3>
                        <h3>São Paulo SP</h3>
                        <h3>Analise e desenvolvimento de software</h3>
                        <div className='info-coluna-btn'>

                            <button><a href="https://www.instagram.com/danpqpdan/" target='_blank' ><FaInstagram /></a></button>
                            <button><a href="https://www.linkedin.com/in/daniel-santos-694578333" target='_blank' ><FaLinkedinIn /></a></button>
                            <button><a href="https://github.com/danpqdan" target='_blank' ><FaGithub /></a></button>
                            <button><a href="tel:+5511962696757"><FaWhatsapp /></a></button>
                            <button onClick={openModal}><form><FcLike /> {likes}</form><label></label></button>
                        </div>
                    </div>
                    <Modal showModal={showModal} closeModal={closeModal} postLike={postLike} />
                </div>

            )}
            {
                windowWidth > 1025 && (
                    <div className='info-coluna'>
                        <Modal showModal={showModal} closeModal={closeModal} postLike={postLike} />
                        <img src={imgdan} className='imgperfil' />
                        <div className='info-coluna-header'>
                            <DiAtom id='icon-perfil' />
                            <h2>Daniel Santos</h2>
                            <DiAtom id='icon-perfil' />
                        </div>
                        <div className='info-coluna-foot'>
                            <h3>Desenvolvedor de Sotware</h3>
                            <h3>27 anos</h3>
                            <h3>São Paulo SP</h3>
                            <h3>Analise e desenvolvimento de software</h3>
                        </div>
                        <div className='info-coluna-btn'>

                            <button><a href="https://www.instagram.com/danpqpdan/" target='_blank' ><FaInstagram /></a></button>
                            <button><a href="https://www.linkedin.com/in/daniel-santos-694578333" target='_blank' ><FaLinkedinIn /></a></button>
                            <button><a href="https://github.com/danpqdan" target='_blank' ><FaGithub /></a></button>
                            <button><a href="tel:+5511962696757"><FaWhatsapp /></a></button>
                            <button onClick={openModal}><form><FcLike /> {likes}</form><label></label></button>
                        </div>
                    </div>

                )
            }
        </>
    )
}

export default MainProfile;
