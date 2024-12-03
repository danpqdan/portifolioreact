import { useEffect, useState } from 'react';

interface Comentario {
    subject: string;
    body: string;
    review: string;
}

export const useGetComentarios = () => {
    const [comentarios, setComentarios] = useState<Comentario[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchComentarios = async () => {
            try {
                const res = await fetch('http://localhost:8080/api/contato');
                if (!res.ok) {
                    throw new Error('Erro ao buscar os comentários');
                }
                const data = await res.json();
                setComentarios(data);
            } finally {
                setLoading(false);
            }
        };

        fetchComentarios();
    }, []);

    return { comentarios, loading };
};

interface Comentario {
    subject: string;
    body: string;
    review: string;
}

// Exemplo do hook usePostComentario
export const usePostComentario = () => {
    const postComentario = async (comentario: { subject: string; body: string; review: string }) => {
        try {
            const response = await fetch('http://localhost:8080/api/contato', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(comentario),
            });

            if (!response.ok) {
                throw new Error('Erro ao enviar o comentário');
            }

            return response;  // Retorna a resposta para verificação de status
        } catch (error) {
            console.error('Erro no envio do comentário:', error);
            throw error; // Repassa o erro para o handler no componente
        }
    };

    return { postComentario };
};
