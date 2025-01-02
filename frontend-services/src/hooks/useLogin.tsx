import { useState, useEffect } from 'react';

interface LoginResponseDTO {
    token: string;
    role: string;
    username: string;
}

const useLogin = () => {
    const [loginData, setLoginData] = useState<LoginResponseDTO | null>(null);
    const [error, setError] = useState<string | null>(null);

    const handleLogin = async (userData: { email: string; password: string }) => {
        try {
            const response = await fetch('http://localhost:8080/auth/singin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData),
            });

            if (response.status !== 200) {
                throw new Error('Network response was not ok');
            }
            const data: LoginResponseDTO = await response.json();
            setLoginData(data);
            setError(null);
            localStorage.setItem('token', data.token);
            localStorage.setItem('role', data.role);
            localStorage.setItem('username', data.username);
            alert(`Login successful! Token: ${data.token}, Role: ${data.role}, Username: ${data.username}`);
            return null;

        } catch (error) {
            if (error instanceof Error) {
                setError(error.message);
            } else {
                setError('An unknown error occurred');
            }
            setLoginData(null);
            return error;
        }
    };

    useEffect(() => {
        return () => {
            setLoginData(null);
            setError(null);
        };
    }, []);

    return { loginData, error, handleLogin };
};

export default useLogin;