import { useEffect, useState } from 'react';
import '../css/style.module.carouselProject.css';

import jsonimg from "../../assets/imagensproject.json";


type Slide = {
    image: string;
    description: string;
    detalhes: string;
};

const ProjectCarousel = () => {
    const [slides, setSlides] = useState<Slide[]>([]);
    useEffect(() => {
        setSlides(jsonimg);
    }, []);

    const [currentIndex, setCurrentIndex] = useState(0);

    const nextSlide = () => {
        setCurrentIndex((prevIndex) => (prevIndex + 1) % slides.length);
    };

    const prevSlide = () => {
        setCurrentIndex((prevIndex) =>
            prevIndex === 0 ? slides.length - 1 : prevIndex - 1
        );
    };

    return (
        <div className="carousel">
            <div
                className="carousel-track"
                style={{
                    transform: `translateX(-${currentIndex * 100}%)`,
                }}
            >
                {slides.map((slide, index) => (
                    <>
                        <div className="carousel-slide" key={index}>
                            <h2 className="tittle-project" style={{ fontWeight: "100", textTransform: "uppercase", color: "white", width: "100%" }}>{slide.description}</h2>
                            <button className='btn-project'>
                                <img src={slide.image} alt={`Slide ${index}`} className="carousel-image" />
                            </button>
                        </div>
                    </>
                ))}
            </div>
            <button className="carousel-button prev" onClick={prevSlide}>
                ◀
            </button>
            <button className="carousel-button next" onClick={nextSlide}>
                ▶
            </button>
        </div>
    );
};

export default ProjectCarousel;
