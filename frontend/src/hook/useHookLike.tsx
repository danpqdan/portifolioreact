import { useEffect, useState } from 'react';
export const securityRoute = import.meta.env.VITE_REACT_SECURITY_ROUTE_PROD;


export const useGetLikes = () => {
    const [likes, setLikes] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchLikes = async () => {
            try {
                const response = await fetch(`${securityRoute}/api/like`)
                if (!response.ok) {
                    throw new Error('Erro ao buscar os dados');
                }
                const data = await response.json();
                setLikes(data);

            } finally {
                setLoading(false);
            }
        };

        fetchLikes();
    }, []); // Empty array ensures this runs only once when the component mounts

    return { likes, loading };
};


export const usePostLike = () => {
    const postLike = async () => {

        try {
            const response = await fetch(`${securityRoute}/api/like`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json', // Define o tipo de conteúdo como JSON, mesmo sem corpo
                },
            });

            if (!response.ok) {
                throw new Error('Erro ao enviar o like');
            }

        } catch {
            return "error"
        }

    };

    return { postLike };
};


