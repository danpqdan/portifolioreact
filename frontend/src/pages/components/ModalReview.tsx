import React, { useState } from 'react';
import { usePostComentario } from '../../hook/useHookComentario';

interface ModalProps {
    showModal: boolean;
    closeModal: () => void;
    postLike: () => void;
}

// Componente Modal com tipagem de props
export const Modal: React.FC<ModalProps> = ({ showModal, closeModal, postLike }) => {
    const { postComentario } = usePostComentario();

    const [review, setReview] = useState(0); // Agora o estado de avaliação é um número (0 a 5)
    const [subject, setSubject] = useState('');
    const [body, setBody] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const newComentario = {
            subject,
            body,
            review: review.toString(),
        };

        try {
            const response = await postComentario(newComentario);  // Agora retorna a resposta da API

            // Verifica se a resposta foi bem-sucedida (status OK)
            if (response.ok) {
                setSubject('');
                setBody('');
                setReview(0);
                closeModal();
                postLike();
            } else {
                console.error('Erro ao enviar o comentário');
            }
        } catch (error) {
            console.error('Erro ao enviar o comentário:', error);
        }
    };


    const handleClick = (star: number) => {
        setReview(star); // Atualiza a avaliação com a estrela clicada
    };

    if (!showModal) return null;

    return (
        <div style={overlayStyles}>
            <div style={modalStyles}>
                <button onClick={closeModal} style={closeButtonStyles}>X</button>
                <h2 style={{ color: 'black', textAlign: 'center' }}>Adicione um comentario</h2>
                <p style={{ color: 'black', textAlign: 'center' }}>Melhoria ou FeedBack</p>
                <form onSubmit={handleSubmit} style={formStyle}>
                    <div>
                        <input
                            style={inputStyle}
                            type="text"
                            id="subject"
                            value={subject}
                            onChange={(e) => setSubject(e.target.value)}
                            placeholder="Titulo para o Review"
                            required
                        />
                    </div>
                    <div>
                        <textarea
                            style={inputStyle}
                            id="body"
                            value={body}
                            onChange={(e) => setBody(e.target.value)}
                            placeholder="Review"
                            required
                        />
                    </div>
                    <div style={{ display: 'flex', flexDirection: 'row' }}>
                        {[1, 2, 3, 4, 5].map((star) => (
                            <span
                                key={star}
                                onClick={() => handleClick(star)}
                                style={{
                                    cursor: 'pointer',
                                    fontSize: '24px',
                                    color: star <= review ? '#FFD700' : '#ccc', // Estrela preenchida ou vazia
                                    transition: 'color 0.2s', // Efeito de transição suave para a cor
                                }}
                            >
                                ★
                            </span>
                        ))}
                    </div>
                    <button type="submit">Enviar</button>
                </form>
            </div>
        </div>
    );
};

// Estilos para o formulário e o modal
const formStyle: React.CSSProperties = {
    alignItems: 'center',
    display: 'flex',
    flexDirection: 'column',
    width: '100%',
    margin: '6px 0px',
    textAlign: 'center',
    borderRadius: '6px',
};

const inputStyle: React.CSSProperties = {
    width: '100%',
    padding: '8px',
    marginBottom: '10px',
    borderRadius: '4px',
    border: '1px solid #ccc',
};

const overlayStyles: React.CSSProperties = {
    zIndex: '99',
    position: 'fixed',
    top: 0,
    left: 0,
    width: '100%',
    height: '100%',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center', // Centraliza o modal
};

const modalStyles: React.CSSProperties = {
    backgroundColor: 'white',
    borderRadius: '8px',
    width: '400px',
    padding: '20px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
};

const closeButtonStyles: React.CSSProperties = {
    color: 'white',
    position: 'absolute',
    top: '10px',
    right: '10px',
    backgroundColor: 'transparent',
    border: 'none',
    fontSize: '20px',
    cursor: 'pointer',
};
