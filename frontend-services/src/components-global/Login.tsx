import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/login.css';

interface UserData {
    email: string;
    password: string;
}

export const Login: React.FC = () => {
    const navigate = useNavigate();

    const [userData, setUserData] = useState<UserData>({
        email: '',
        password: '',
    });

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData),
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const data = await response.json();
            console.log('Success:', data);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <div className='container-login'>
            <h1>Login</h1>
            <form onSubmit={handleSubmit} className="form-global">
                <input
                    placeholder='Email or Login:'
                    type="text"
                    id="email"
                    name="email"
                    value={userData.email}
                    onChange={(e) => setUserData({ ...userData, email: e.target.value })}
                />
                <input
                    placeholder='Password:'
                    type="password"
                    id="password"
                    name="password"
                    value={userData.password}
                    minLength={6}
                    onChange={(e) => setUserData({ ...userData, password: e.target.value })}
                />
                <div className='button-content'>

                    <button type="submit">Login</button>
                    <button type="button" onClick={() => navigate('/registro')}>Novo usuario</button>
                </div>

            </form>
        </div>
    );
};

