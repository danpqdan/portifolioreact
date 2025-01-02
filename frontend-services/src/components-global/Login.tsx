import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/login.css';
import useLogin from '../hooks/useLogin';

export const Login: React.FC = () => {
    const navigate = useNavigate();
    const { handleLogin, error } = useLogin();

    const [userData, setUserData] = useState({
        email: '',
        password: '',
    });

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const errorMessage = await handleLogin(userData);
        if (errorMessage) {
            alert(error);
        }else{
            alert('Login realizado com sucesso!');
            navigate('/');
        }
    };

    return (
        <div className="container-login">
            <h1>Login</h1>
            <form onSubmit={handleSubmit} className="form-global">
                <input
                    placeholder="Email or Login:"
                    type="text"
                    id="email"
                    name="email"
                    value={userData.email}
                    onChange={(e) => setUserData({ ...userData, email: e.target.value })}
                />
                <input
                    placeholder="Password:"
                    type="password"
                    id="password"
                    name="password"
                    value={userData.password}
                    minLength={6}
                    onChange={(e) => setUserData({ ...userData, password: e.target.value })}
                />
                <div className="button-content">
                    <button type="submit">Login</button>
                    <button type="button" onClick={() => navigate('/registro')}>Novo usuário</button>
                </div>
            </form>
        </div>
    );
};
