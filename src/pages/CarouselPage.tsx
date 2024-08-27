import { IParallax, Parallax, ParallaxLayer } from '@react-spring/parallax'
import { useEffect, useRef, useState } from 'react'

import ProjectCarousel from './components/ProjectCarousel'
import SkillComponent from './components/SkillComponent'
import SobreComponent from './components/SobreComponent'
import './css/styles.module.caroselPage.css'
import MainProfile from './components/MainProfile'


// Little helpers ...
const img1 = "https://images.stockcake.com/public/d/f/2/df224a92-6796-436e-baa0-781200184ddc_large/illuminated-night-tree-stockcake.jpg"
const img2 = "https://images.stockcake.com/public/8/3/1/83123b41-56c5-475d-9836-cfdeaedfd7d5_large/illuminated-night-fountain-stockcake.jpg"


export default function CarrouselPrimary() {

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
    const parallax = useRef<IParallax>(null!)
    return (
        <div className='parallax' style={{ width: '100%', height: '100%', background: '#6a6c6d', display: 'flex', position: 'relative' }} >
            {windowWidth <= 1025 && (
                <Parallax ref={parallax} pages={4} id='parallax-primario'>

                    <ParallaxLayer offset={0} speed={1} style={{}} />
                    <ParallaxLayer offset={1} speed={1} style={{}} />
                    <ParallaxLayer offset={2} speed={1} style={{}} />
                    <ParallaxLayer offset={3} speed={1} style={{}} />

                    <ParallaxLayer
                        offset={0}
                        speed={0}
                        factor={4}
                        style={{
                        }}
                    />

                    <ParallaxLayer
                        offset={0}
                        speed={-0.4}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            pointerEvents: 'none',
                        }}>
                        <img src={img2} style={{
                            opacity: '0.5',
                            width: '100%',
                            height: '200%'
                        }} />
                    </ParallaxLayer>

                    <ParallaxLayer
                        offset={2}
                        speed={-0.4}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            pointerEvents: 'none',
                        }}>
                        <img src={img1} style={{
                            opacity: '0.5',
                            width: '100%',
                            height: '200%'
                        }} />
                    </ParallaxLayer>

                    <ParallaxLayer
                        offset={2}
                        speed={-0.3}
                        style={{
                            backgroundSize: '100%',
                            backgroundPosition: 'center',
                        }}
                    ></ParallaxLayer>


                    <ParallaxLayer
                        offset={0}
                        speed={0.1}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                        }}>
                        <MainProfile />
                    </ParallaxLayer>


                    <ParallaxLayer
                        offset={1}
                        speed={0.1}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            height:'100%'
                        }}>
                        <SobreComponent />
                    </ParallaxLayer>


                    <ParallaxLayer
                        offset={2.5}
                        speed={0.1}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                        }} />

                    <ParallaxLayer offset={2.5} speed={0.8} className="content-skill">
                        <SkillComponent />
                    </ParallaxLayer>

                    <ParallaxLayer
                        offset={3}
                        speed={0.1}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                        }}>

                    </ParallaxLayer>
                    <ParallaxLayer offset={3} speed={0.8} className='container-project'>
                        <ProjectCarousel />
                    </ParallaxLayer>
                </Parallax>
            )}

            {windowWidth > 1024 && (
                <Parallax ref={parallax} pages={3} id='parallax-primario'>

                    <ParallaxLayer offset={0} speed={1} style={{}} />
                    <ParallaxLayer offset={1} speed={1} style={{}} />
                    <ParallaxLayer offset={2} speed={1} style={{}} />

                    <ParallaxLayer
                        offset={0}
                        speed={0}
                        factor={3}
                        style={{
                        }}
                    />

                    <ParallaxLayer
                        offset={0}
                        speed={-0.4}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            pointerEvents: 'none',
                        }}>
                        <img src={img2} style={{
                            opacity: '0.5',
                            width: '100%',
                            height: '120%'
                        }} />
                    </ParallaxLayer>

                    <ParallaxLayer
                        offset={2}
                        speed={-0.4}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            pointerEvents: 'none',
                        }}>
                        <img src={img1} style={{
                            opacity: '0.5',
                            width: '100%',
                            height: '120%'
                        }} />
                    </ParallaxLayer>

                    <ParallaxLayer
                        offset={2}
                        speed={-0.3}
                        style={{
                            backgroundSize: '100%',
                            backgroundPosition: 'center',
                        }}
                    ></ParallaxLayer>

                    <ParallaxLayer
                        offset={0}
                        speed={0.1}
                        onClick={() => parallax.current.scrollTo(1)}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                        }}>
                        <SobreComponent />
                    </ParallaxLayer>


                    <ParallaxLayer
                        offset={1}
                        speed={0.1}
                        onClick={() => parallax.current.scrollTo(2)}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                        }} />

                    <ParallaxLayer offset={1} speed={0.8} className="content-skill">
                        <SkillComponent />
                    </ParallaxLayer>

                    <ParallaxLayer
                        offset={2}
                        speed={0.1}
                        onClick={() => parallax.current.scrollTo(0)}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                        }}>

                    </ParallaxLayer>
                    <ParallaxLayer offset={2} speed={0.8} className='container-project'>
                        <ProjectCarousel />
                    </ParallaxLayer>
                </Parallax>
            )}


        </div>
    )
}
