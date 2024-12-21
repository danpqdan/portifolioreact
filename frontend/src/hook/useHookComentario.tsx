import { useEffect, useState } from 'react';
import { apiUrl } from '../utils/ExportRoute';
export interface Comentario {
    remetente: string;
    titulo: string;
    body: string;
    review: number;
}

export const useGetComentarios = () => {
    const [comentarios, setComentarios] = useState<Comentario[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchComentarios = async () => {
            try {
                console.log(apiUrl); // Verifique se a URL está correta
                const res = await fetch("http://18.231.152.145/api/review", {

                    method: "GET",
                    credentials: 'include'
                });
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


export const usePostComentario = () => {
    const postComentario = async (comentario: Comentario): Promise<Response> => {
        try {
            const response = await fetch("http://18.231.152.145/api/review", {
                credentials: 'include',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(comentario),
            });

            if (!response.ok) {
                throw new Error('Erro ao enviar o comentário');
            }

            return response; // Retorna a resposta para manipulação no componente
        } catch (error) {
            console.error('Erro no envio do comentário:', error);
            throw error;
        }
    };


    return { postComentario };
};
